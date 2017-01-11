package com.last.androsia.last;

import android.content.Context;
import android.text.SpannableString;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samoustique on 07/01/2017.
 */

public class LastestTrio {
    private static int GOLD = 0;
    private static int SILVER = 1;
    private static int BRONZE = 2;
    private List<TagView> m_tagViews;

    /*private ImageView m_imgSilver;
    private TextView m_txtSilver;
    private ImageView m_imgBronze;
    private TextView m_txtBronze;*/

    public LastestTrio(Context context,
                       List<TagsListItem> tags,
                       ImageView imgGold,
                       TextView txtGold,
                       ImageView imgSilver,
                       TextView txtSilver,
                       ImageView imgBronze,
                       TextView txtBronze) {
        m_tagViews = new ArrayList<>(3);
        m_tagViews.add(new TagView(imgGold, txtGold, tags.get(GOLD), context));
        m_tagViews.add(new TagView(imgSilver, txtSilver, tags.get(SILVER), context));
        m_tagViews.add(new TagView(imgBronze, txtBronze, tags.get(BRONZE), context));

        m_tagViews.get(GOLD).focus();
    }

    public void setupClickListeners() {
        for (TagView tagView : m_tagViews) {
            tagView.setupClickListener();
        }
    }

    public void display() {
        for (TagView tagView : m_tagViews) {
            tagView.display(true);
        }
    }
}
