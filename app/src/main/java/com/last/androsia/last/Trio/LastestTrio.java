package com.last.androsia.last.Trio;

import com.last.androsia.last.Activities.TagsActivity;
import com.last.androsia.last.Common.ImgCounterView;
import com.last.androsia.last.Common.TagItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samoustique on 07/01/2017.
 */

public class LastestTrio {
    private List<TagView> m_tagViews;
    private TagsActivity m_tagsActivity;

    public LastestTrio(TagsActivity tagsActivity,
                       List<TagItem> tags,
                       ImgCounterView... imgCounterViews) {
        m_tagViews = new ArrayList<>();
        m_tagsActivity = tagsActivity;
        for(int i = 0 ; i < tags.size() ; ++i){
            imgCounterViews[i].show();
            m_tagViews.add(new TagView(i, imgCounterViews[i], tags.get(i), tagsActivity.getBaseContext()));
        }

        if(tags.size() > 0) {
            m_tagViews.get(0).focus();
        }
    }

    public void setupClickListeners() {
        for (TagView tagView : m_tagViews) {
            tagView.setupClickListeners(m_tagsActivity);
        }
    }

    public void display() {
        for (TagView tagView : m_tagViews) {
            tagView.display(true);
        }
    }
}
