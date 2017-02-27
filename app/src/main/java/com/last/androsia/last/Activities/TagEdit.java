package com.last.androsia.last.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.last.androsia.last.Common.FilesUtility;
import com.last.androsia.last.Common.GlobalUtilities;
import com.last.androsia.last.Common.TagItem;
import com.last.androsia.last.R;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Samoustique on 27/02/2017.
 */

public abstract class TagEdit extends Activity {
    protected final int TAKE_PICTURE = 1;
    protected final int ACTIVITY_SELECT_IMAGE = 2;
    private final String DLG_PICTURE_TITLE = "Picture";
    private final String STR_TAKE_PICTURE = "Take Photo";
    private final String STR_GALLERY = "Choose from Gallery";

    protected EditText m_edtTitle;
    protected EditText m_edtCounter;
    protected EditText m_edtScreenSeason;
    protected EditText m_edtScreenEpisode;
    protected RadioButton m_radScreen;
    protected RadioButton m_radBook;
    protected GlobalUtilities m_global;
    protected ImageView m_imagePreview;
    protected File m_photoFile;
    protected String m_picturePath;
    protected LinearLayout m_layoutScreen;

    protected void initGUI(){
        m_global = (GlobalUtilities) getApplicationContext();

        // 1. From : screen
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

        // 2. Form : basic counter
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

        // 6. Form : radios
        m_radScreen = (RadioButton) this.findViewById(R.id.radScreen);
        m_radBook = (RadioButton) this.findViewById(R.id.radBook);
    }

    public void setTitle(String title){
        TextView titleView = (TextView) findViewById(R.id.lblTitle);
        titleView.setText(title);
    }

    public void activateGoldImage(){
        RelativeLayout layoutSilver = (RelativeLayout) this.findViewById(R.id.layoutSilver);
        layoutSilver.setVisibility(View.GONE);
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
    }

    public void activateSilverImage(){
        RelativeLayout layoutGold = (RelativeLayout) this.findViewById(R.id.layoutGold);
        layoutGold.setVisibility(View.GONE);
        RelativeLayout layoutSilver = (RelativeLayout) this.findViewById(R.id.layoutSilver);
        layoutSilver.setVisibility(View.VISIBLE);

        // place it correctly
        LinearLayout.LayoutParams relativeParams = (LinearLayout.LayoutParams) layoutSilver.getLayoutParams();
        relativeParams.setMargins(20, 120, 0, 0);
        layoutSilver.setLayoutParams(relativeParams);
        layoutSilver.requestLayout();

        m_imagePreview = (ImageView) this.findViewById(R.id.imgUserSilver);
        m_imagePreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayImageChoice();
            }
        });
    }

    public void displayImageChoice(){
        final CharSequence[] options = {STR_TAKE_PICTURE, STR_GALLERY};

        AlertDialog.Builder builder = new AlertDialog.Builder(TagEdit.this);
        builder.setTitle(DLG_PICTURE_TITLE);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(options[which].equals(STR_TAKE_PICTURE)){
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                        // Create the File where the photo should go
                        m_photoFile = null;
                        try {
                            m_photoFile = FilesUtility.createImageFile();
                        } catch (IOException ex) {}

                        if (m_photoFile != null) {
                            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(m_photoFile));
                            startActivityForResult(cameraIntent, TAKE_PICTURE);
                        }
                    }
                } else if(options[which].equals(STR_GALLERY)){
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, ACTIVITY_SELECT_IMAGE);
                }
            }
        });
        builder.show();
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

    public void save() {
        if(isFormValid()) {
            if(m_picturePath != null && m_picturePath != ""){
                createNewItem();
            } else{
                Toast.makeText(getApplicationContext(), "Please select your picture again", Toast.LENGTH_LONG).show();
            }
        }
    }

    protected void createNewItem() {
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
        try {
            String imgUrl = FilesUtility.saveToInternalSorage(m_picturePath, getApplicationContext());
            if(m_photoFile != null){
                m_photoFile.delete();
            }
            tagItem.setImgUrl(imgUrl);
        } catch(Exception e){
            Toast.makeText(getApplicationContext(), "Trouble while saving the picture", Toast.LENGTH_LONG).show();
        }

        saveTagItem(tagItem);
        m_global.addTagItemBeginning(tagItem);

        // Return it in the main activity
        Intent intent = new Intent();
        intent.putExtra(m_global.IS_ITEM_SAVED, true);
        setResult(m_global.ADD_ACTIVITY, intent);
        finish();
    }

    protected abstract void saveTagItem(TagItem tagItem);

    private boolean isFormValid() {
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

    @Override
    public void onActivityResult(int requestcode, int resultcode, Intent intent) {
        super.onActivityResult(requestcode, resultcode, intent);
        if (resultcode == RESULT_OK) {
            switch (requestcode) {
                case TAKE_PICTURE:
                    if(m_photoFile != null) {
                        m_picturePath = m_photoFile.getAbsolutePath();
                    } else {
                        Toast.makeText(getApplicationContext(), "Unable to retrieve the picture from camera", Toast.LENGTH_LONG).show();
                        return;
                    }
                    break;
                case ACTIVITY_SELECT_IMAGE:
                    Uri selectedImage = intent.getData();
                    String[] filePath = {MediaStore.Images.Media.DATA};
                    Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePath[0]);
                    m_picturePath = c.getString(columnIndex);
                    c.close();
                    break;
            }

            try {
                Bitmap image = FilesUtility.decodeSampledBitmapFromResource(m_picturePath);
                m_imagePreview.setImageBitmap(image);
            } catch (Exception e){
                Toast.makeText(getApplicationContext(), "Unable to retrieve the picture", Toast.LENGTH_LONG).show();
            }
        }
    }
}
