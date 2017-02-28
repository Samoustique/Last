package com.last.androsia.last.Common;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by SPhilipps on 1/12/2017.
 */

public class ImgCounterView {
    private ImageView m_img;
    private TextView m_txt;
    private RelativeLayout m_layout;

    public ImgCounterView(ImageView img, TextView txt, RelativeLayout layout){
        m_img = img;
        m_txt = txt;
        m_layout = layout;
    }

    public ImageView getImg(){
        return m_img;
    }

    public TextView getTxt(){
        return m_txt;
    }

    public void show(){
        m_layout.setVisibility(View.VISIBLE);
    }

    public void focus(){
        m_img.setFocusableInTouchMode(true);
        m_img.requestFocus();
    }

    public void hide() {
        m_layout.setVisibility(View.INVISIBLE);
    }
}
