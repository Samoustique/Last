package com.last.androsia.last;

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
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddTagActivity extends AppCompatActivity {
    private final int TAKE_PICTURE = 1;
    private final int ACTIVITY_SELECT_IMAGE = 2;
    private final String DLG_PICTURE_TITLE = "Picture";
    private final String STR_TAKE_PICTURE = "Take Photo";
    private final String STR_GALLERY = "Choose from Gallery";
    private final String STR_CANCEL = "Cancel";

    private Button m_btnPicture;
    private ImageView m_imagePreview;
    private String m_pictureImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_tag_activity);

        ActionBar bar = getSupportActionBar();
        bar.setHomeButtonEnabled(true);
        bar.setDisplayHomeAsUpEnabled(true);

        m_imagePreview = (ImageView) this.findViewById(R.id.picture);
        m_btnPicture = (Button)findViewById(R.id.btnPicture);
        m_btnPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayImageChoice();
            }
        });
    }

    public void displayImageChoice()
    {
        final CharSequence[] options = {STR_TAKE_PICTURE, STR_GALLERY, STR_CANCEL};

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
                else if(options[which].equals(STR_CANCEL))
                {
                    dialog.dismiss();
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
    }

}
