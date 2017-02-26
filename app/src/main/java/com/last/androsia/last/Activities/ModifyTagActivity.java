package com.last.androsia.last.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.last.androsia.last.Common.GlobalUtilities;
import com.last.androsia.last.Common.TagItem;
import com.last.androsia.last.R;

/**
 * Created by Samoustique on 22/02/2017.
 */

public class ModifyTagActivity extends Activity {
    private TextView m_txtTitle;

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

        initItem();
    }

    private void initItem(){
        m_global = (GlobalUtilities) getApplicationContext();

        m_item = m_global.getSelectedItem();

        m_txtTitle = (TextView) findViewById(R.id.edtTitle);
        m_txtTitle.setText(m_item.getTitle());
    }
}
