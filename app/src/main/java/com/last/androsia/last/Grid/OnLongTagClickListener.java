package com.last.androsia.last.Grid;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;

import com.last.androsia.last.Activities.TagsActivity;

/**
 * Created by Samoustique on 26/02/2017.
 */

public class OnLongTagClickListener implements OnItemLongClickListener {
    private TagsActivity m_activity;
    private CustomAdapter m_adapter;

    public OnLongTagClickListener(TagsActivity activity, CustomAdapter adapter){
        m_activity = activity;
        m_adapter = adapter;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long id) {
        m_activity.createPopUp(m_adapter.getItem(pos));
        return true;
    }
}
