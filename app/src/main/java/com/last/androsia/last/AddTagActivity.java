package com.last.androsia.last;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddTagActivity extends Activity {
    private final int TAKE_PICTURE = 1;
    private final int ACTIVITY_SELECT_IMAGE = 2;
    private final String DLG_PICTURE_TITLE = "Picture";
    private final String STR_TAKE_PICTURE = "Take Photo";
    private final String STR_GALLERY = "Choose from Gallery";

    private ImageView m_imagePreview;
    private ImageView m_btnSave;
    private ImageView m_btnBack;
    private LinearLayout m_layoutScreen;
    private EditText m_edtTitle;
    private EditText m_edtCounter;
    private EditText m_edtScreenSeason;
    private EditText m_edtScreenEpisode;
    private RadioButton m_radScreen;
    private File m_takenPictureFile;
    private Bitmap m_img;
    private byte[] m_imgBytes;
    private GlobalUtilities m_global;
    private int m_pictureMode = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.add_tag_activity);

        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_add_tag);

        m_global = (GlobalUtilities) getApplicationContext();

        // 1. Title buttons
        m_btnSave = (ImageView) findViewById(R.id.btnSave);
        m_btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                save();
            }
        });
        m_btnBack = (ImageView) findViewById(R.id.btnBack);
        m_btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                back();
            }
        });

        // 2. User Image
        RelativeLayout layoutGold = (RelativeLayout) this.findViewById(R.id.layoutGold);
        layoutGold.setVisibility(View.VISIBLE);
        m_imagePreview = (ImageView) this.findViewById(R.id.imgUserGold);
        m_imagePreview.setImageResource(R.drawable.select_picture);
        m_imagePreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayImageChoice();
            }
        });

        // 3. Form : screen
        m_layoutScreen = (LinearLayout) this.findViewById(R.id.layScreen);
        View.OnFocusChangeListener focusEmptyCounter = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                // empty counter
                m_edtCounter.setText("");
            }
            }
        };
        m_edtScreenSeason = (EditText) this.findViewById(R.id.edtScreenSeason);
        m_edtScreenSeason.setOnFocusChangeListener(focusEmptyCounter);
        m_edtScreenEpisode = (EditText) this.findViewById(R.id.edtScreenEpisode);
        m_edtScreenEpisode.setOnFocusChangeListener(focusEmptyCounter);

        // 4. Form : basic counter
        m_edtCounter = (EditText) this.findViewById(R.id.edtCounter);
        m_edtCounter.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                // empty screen season/episode
                m_edtScreenEpisode.setText("");
                m_edtScreenSeason.setText("");
            }
            }
        });

        // 5. Form : title
        m_edtTitle = (EditText) this.findViewById(R.id.edtTitle);

        // 6. Form : radio
        m_radScreen = (RadioButton) this.findViewById(R.id.radScreen);

        // 7. Create "Last" folder
        FilesUtility.createLastFolder();
    }

    public void displayImageChoice(){
        final CharSequence[] options = {STR_TAKE_PICTURE, STR_GALLERY};

        AlertDialog.Builder builder = new AlertDialog.Builder(AddTagActivity.this);
        builder.setTitle(DLG_PICTURE_TITLE);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(options[which].equals(STR_TAKE_PICTURE)){
                    takePicture();
                } else if(options[which].equals(STR_GALLERY)){
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, ACTIVITY_SELECT_IMAGE);
                }
            }
        });
        builder.show();
    }

    private void takePicture() {
        m_takenPictureFile = FilesUtility.createNewImageInLast();
        Uri outputFileUri = Uri.fromFile(m_takenPictureFile);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(cameraIntent, TAKE_PICTURE);
    }

    @Override
    public void onActivityResult(int requestcode, int resultcode, Intent intent) {
        super.onActivityResult(requestcode, resultcode, intent);
        if (resultcode == RESULT_OK) {
            m_pictureMode = requestcode;
            switch (requestcode) {
                case TAKE_PICTURE:
                    if(m_takenPictureFile.exists()){
                        m_img = BitmapFactory.decodeFile(m_takenPictureFile.getAbsolutePath());
                        // compress img
                        m_imgBytes = BitmapUtility.getBytes(m_img);
                        m_img = BitmapUtility.getImage(m_imgBytes);
                        m_imagePreview.setImageBitmap(m_img);
                    } else {
                        Toast.makeText(getApplicationContext(), "Unable to retrieve the picture", Toast.LENGTH_LONG).show();
                    }
                    break;
                case ACTIVITY_SELECT_IMAGE:
                    Uri selectedImage = intent.getData();
                    String[] filePath = {MediaStore.Images.Media.DATA};
                    Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePath[0]);
                    String picturePath = c.getString(columnIndex);
                    File pictureFile = new File(picturePath);
                    c.close();
                    try {
                        FilesUtility.copyToLast(pictureFile);
                        m_img = (BitmapFactory.decodeFile(picturePath));
                        // compress img
                        m_imgBytes = BitmapUtility.getBytes(m_img);
                        m_img = BitmapUtility.getImage(m_imgBytes);
                        m_imagePreview.setImageBitmap(m_img);
                    } catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Unable to retrieve the picture", Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        m_edtScreenSeason.setError(null);
        m_edtScreenEpisode.setError(null);
        m_edtCounter.setError(null);

        switch(view.getId()) {
            case R.id.radScreen:
                if (checked){
                    m_layoutScreen.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.radBook:
                if (checked){
                    m_layoutScreen.setVisibility(View.INVISIBLE);
                }
                break;
        }
    }

    public void back() {
        this.finish();
    }

    public void save() {
        if(isFormValid()) {
            if(m_img != null){
                createNewItem();
            } else{
                Toast.makeText(getApplicationContext(), "Please select your picture again", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void deleteLastDirectory(){
        if(m_takenPictureFile != null && m_pictureMode == TAKE_PICTURE){
            File lastDir = m_takenPictureFile.getParentFile();

            if (lastDir.isDirectory()){
                String[] children = lastDir.list();
                for (int i = 0; i < children.length; i++){
                    new File(lastDir, children[i]).delete();
                }
                lastDir.delete();
            }
        }
    }

    private void createNewItem(){
        String title = m_edtTitle.getText().toString();
        String season = m_edtScreenSeason.getText().toString();
        String episode = m_edtScreenEpisode.getText().toString();
        String counter = m_edtCounter.getText().toString();
        TagItem.Type type = TagItem.Type.BOOK;

        if(m_radScreen.isChecked()) {
            type = TagItem.Type.SCREEN;
        }

        Double dCounter;
        if(counter.isEmpty()) {
            double dSeason = Double.parseDouble(season);
            double dEpisode = Double.parseDouble(episode);
            dEpisode /= 100;
            dCounter = dSeason + dEpisode;
        }else{
            dCounter = Double.parseDouble(counter);
        }

        TagItem tagItem = new TagItem();
        tagItem.setTitle(title);
        tagItem.setDate((new Date()).getTime());
        tagItem.setCtrSeen(dCounter);
        tagItem.setType(type);
        tagItem.setImage(m_imgBytes);

        saveTagItem(tagItem);
        m_global.addTagItemBeginning(tagItem);
        // Return it in the main activity
        finish();
    }

    private void saveTagItem(TagItem tagItem) {
        SQLiteDatabase db = m_global.getDB();

        ContentValues values = new ContentValues();
        values.put(DBContract.TagItem.COLUMN_TITLE, tagItem.getTitle());
        values.put(DBContract.TagItem.COLUMN_IMG, tagItem.getImage());
        values.put(DBContract.TagItem.COLUMN_CTR_SEEN, tagItem.getCtrSeen());
        values.put(DBContract.TagItem.COLUMN_CTR_OWNED, tagItem.getCtrOwned());
        values.put(DBContract.TagItem.COLUMN_TYPE, tagItem.getIType());
        values.put(DBContract.TagItem.COLUMN_DATE, tagItem.getDate());

        long id = db.insert(DBContract.TagItem.TABLE_NAME, null, values);
        tagItem.setId(id);
        tagItem.setDB(db);
    }

    private boolean isFormValid(){
        Boolean isValid = true;

        String title = m_edtTitle.getText().toString();
        String season = m_edtScreenSeason.getText().toString();
        String episode = m_edtScreenEpisode.getText().toString();
        String counter = m_edtCounter.getText().toString();

        if(title.isEmpty()){
            isValid = false;
            m_edtTitle.setError("Title ?");
        }

        if(m_radScreen.isChecked()){
            if(counter.isEmpty()){
                if(season.isEmpty()) {
                    if(episode.isEmpty()) {
                        isValid = false;
                        m_edtScreenSeason.setError("Season ?");
                        m_edtScreenEpisode.setError("Episode ?");
                        m_edtCounter.setError("Counter ?");
                    } else{
                        isValid = false;
                        m_edtScreenSeason.setError("Season ?");
                        m_edtCounter.setError(null);
                    }
                } else if(episode.isEmpty()) {
                    isValid = false;
                    m_edtScreenEpisode.setError("Episode ?");
                    m_edtCounter.setError(null);
                }
            }
        } else{
            if(counter.isEmpty()){
                isValid = false;
                m_edtCounter.setError("Counter ?");
                m_edtScreenSeason.setError(null);
                m_edtScreenEpisode.setError(null);
            }
        }

        return isValid;
    }
}
