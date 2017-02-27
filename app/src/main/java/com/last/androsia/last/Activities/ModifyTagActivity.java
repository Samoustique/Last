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

public class ModifyTagActivity extends TagEdit {
    private TagItem m_item;

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

        initGUI();

        setTitle("Edit Tag");

        // 2. User Image
        activateSilverImage();

        initItem();
    }

    private void initItem(){
        m_item = m_global.getSelectedItem();

        m_edtTitle.setText(m_item.getTitle());
        double dCtr = m_item.getCtrSeen();
        if(m_item.getType() == TagItem.Type.SCREEN){
            m_radScreen.setChecked(true);
            m_layoutScreen.setVisibility(View.VISIBLE);

            Integer iPart;
            iPart = (int) dCtr;
            double decimal = 100 * dCtr;
            decimal -= 100 * iPart;
            Integer fPart = (int) decimal;
            if (fPart < 1){
                m_edtCounter.setText(iPart.toString());
            } else {
                m_edtScreenEpisode.setText(fPart.toString());
                m_edtScreenSeason.setText(iPart.toString());
            }
        } else {
            m_radBook.setChecked(true);
            Integer ctr = (int) dCtr;
            m_edtCounter.setText(ctr.toString());
        }

        // fill the image
        Bitmap img = FilesUtility.decodeSampledBitmapFromResource(m_item.getImgUrl());
        m_imagePreview.setImageBitmap(img);
    }

    @Override
    protected void saveTagItem(TagItem tagItem) {

    }
}
