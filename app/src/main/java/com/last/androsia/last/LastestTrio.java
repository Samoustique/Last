package com.last.androsia.last;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samoustique on 07/01/2017.
 */

public class LastestTrio {
    private List<TagView> m_tagViews;

    public LastestTrio(Context context,
                       List<TagItem> tags,
                       ImgCounterView... imgCounterViews) {
        m_tagViews = new ArrayList<>();
        for(int i = 0 ; i < tags.size() ; ++i){
            imgCounterViews[i].show();
            m_tagViews.add(new TagView(imgCounterViews[i], tags.get(i), context));
        }

        if(tags.size() > 0) {
            m_tagViews.get(0).focus();
        }
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
