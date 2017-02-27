package com.last.androsia.last.Activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.last.androsia.last.Common.FilesUtility;
import com.last.androsia.last.Common.GlobalUtilities;
import com.last.androsia.last.Common.TagItem;
import com.last.androsia.last.R;

/**
 * Created by Samoustique on 22/02/2017.
 */

public class ModifyTagActivity extends Activity {
    private TextView m_txtTitle;
    private RadioButton m_radScreen;
    private RadioButton m_radBook;
    private EditText m_edtCounter;
    private ImageView m_imagePreview;

    private TagItem m_item;
    private GlobalUtilities m_global;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.modify_tag_activity);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * .9),(int)(height * 0.6));

        TextView title = (TextView) findViewById(R.id.lblTitle);
        title.setText("Edit Tag");

        initItem();
    }

    private void initItem(){
        m_global = (GlobalUtilities) getApplicationContext();

        m_item = m_global.getSelectedItem();

        m_txtTitle = (TextView) findViewById(R.id.edtTitle);
        m_txtTitle.setText(m_item.getTitle());

        m_radScreen = (RadioButton) findViewById(R.id.radScreen);
        m_radBook = (RadioButton) findViewById(R.id.radBook);
        m_edtCounter = (EditText) findViewById(R.id.edtCounter);

        if(m_item.getType() == TagItem.Type.SCREEN){
            m_radScreen.setChecked(true);
        } else {
            m_radBook.setChecked(true);
            Integer ctr = (int) m_item.getCtrSeen();
            m_edtCounter.setText(ctr.toString());
        }

        // Image. Remove gold, display silver
        RelativeLayout layoutGold = (RelativeLayout) this.findViewById(R.id.layoutGold);
        layoutGold.setVisibility(View.GONE);
        RelativeLayout layoutSilver = (RelativeLayout) this.findViewById(R.id.layoutSilver);
        layoutSilver.setVisibility(View.VISIBLE);

        // and place it correctly
        LinearLayout.LayoutParams relativeParams = (LinearLayout.LayoutParams) layoutSilver.getLayoutParams();
        relativeParams.setMargins(20, 60, 0, 0);
        layoutSilver.setLayoutParams(relativeParams);
        layoutSilver.requestLayout();

        // fill the image
        m_imagePreview = (ImageView) this.findViewById(R.id.imgUserSilver);
        Bitmap img = FilesUtility.decodeSampledBitmapFromResource(m_item.getImgUrl());
        m_imagePreview.setImageBitmap(img);
    }
}
