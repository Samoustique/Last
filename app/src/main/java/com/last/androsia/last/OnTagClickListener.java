package com.last.androsia.last;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Created by Samoustique on 07/01/2017.
 */

public class OnTagClickListener implements OnItemClickListener {
    private CustomAdapter m_adapter;

    public OnTagClickListener(CustomAdapter adapter){
        m_adapter = adapter;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
        TagItem item = (TagItem) adapterView.getItemAtPosition(pos);
        item.incrementCounter();
        m_adapter.dataWillChange(pos);
        m_adapter.notifyDataSetChanged();
    }
}
