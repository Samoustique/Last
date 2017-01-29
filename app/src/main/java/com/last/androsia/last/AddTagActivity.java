package com.last.androsia.last;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
    private String m_pictureImagePath;
    private LinearLayout m_layoutScreen;
    private EditText m_edtTitle;
    private EditText m_edtCounter;
    private EditText m_edtScreenSeason;
    private EditText m_edtScreenEpisode;
    private RadioButton m_radScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.add_tag_activity);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_add_tag);

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
    }

    public void displayImageChoice()
    {
        final CharSequence[] options = {STR_TAKE_PICTURE, STR_GALLERY};

        AlertDialog.Builder builder = new AlertDialog.Builder(AddTagActivity.this);
        builder.setTitle(DLG_PICTURE_TITLE);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(options[which].equals(STR_TAKE_PICTURE))
                {
                    takePicture();
                }
                else if(options[which].equals(STR_GALLERY))
                {
                    Intent intent=new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, ACTIVITY_SELECT_IMAGE);
                }
            }
        });
        builder.show();
    }

    private void takePicture() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Last/" + timeStamp + ".jpg";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        m_pictureImagePath = storageDir.getAbsolutePath() + "/" + imageFileName;
        File file = new File(m_pictureImagePath);
        Uri outputFileUri = Uri.fromFile(file);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(cameraIntent, TAKE_PICTURE);
    }

    public void onActivityResult(int requestcode,int resultcode,Intent intent) {
        super.onActivityResult(requestcode, resultcode, intent);
        if (resultcode == RESULT_OK) {
            switch (requestcode) {
                case TAKE_PICTURE:
                    File imgFile = new File(m_pictureImagePath);
                    if(imgFile.exists()){
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        m_imagePreview.setImageBitmap(myBitmap);
                    }
                    break;
                case ACTIVITY_SELECT_IMAGE:
                    Uri selectedImage = intent.getData();
                    String[] filePath = {MediaStore.Images.Media.DATA};
                    Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePath[0]);
                    String picturePath = c.getString(columnIndex);
                    c.close();
                    Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                    Drawable drawable = new BitmapDrawable(thumbnail);
                    m_imagePreview.setImageDrawable(drawable);
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
                    break;
                }
            case R.id.radBook:
                if (checked){
                    m_layoutScreen.setVisibility(View.INVISIBLE);
                    break;
                }
        }
    }

    public void back() {
        this.finish();
    }

    public void save() {
        Boolean isValid = true;
        TagsListItem.Type type = TagsListItem.Type.BOOK;

        String title = m_edtTitle.getText().toString();
        String season = m_edtScreenSeason.getText().toString();
        String episode = m_edtScreenEpisode.getText().toString();
        String counter = m_edtCounter.getText().toString();

        if(title.isEmpty()){
            isValid = false;
            m_edtTitle.setError("Title ?");
        }

        if(m_radScreen.isChecked()){
            type = TagsListItem.Type.SCREEN;
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

        if(isValid) {
            Toast.makeText(getApplicationContext(), "Save", Toast.LENGTH_LONG).show();
            this.finish();
        }
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_tag_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.save_tag:
                Toast.makeText(getApplicationContext(),
                        "Let's pretend the tag is saved...",
                        Toast.LENGTH_LONG).show();
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/
}
