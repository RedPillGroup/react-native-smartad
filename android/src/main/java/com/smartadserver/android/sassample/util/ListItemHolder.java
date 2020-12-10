package com.smartadserver.android.sassample.util;

import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;
import android.view.View;
import com.smartadserver.android.sassample.R;

/*
 * Created by Thomas on 01/06/2016.
 */

public class ListItemHolder extends RecyclerView.ViewHolder {

    private TextView textViewIndex;
    private TextView textViewTitle;
    private TextView textViewSubtitle;

    public ListItemHolder(View itemView) {
        super(itemView);
        textViewIndex = (TextView) itemView.findViewById(R.id.indexLabel);
        textViewTitle = (TextView) itemView.findViewById(R.id.title);
        textViewSubtitle = (TextView) itemView.findViewById(R.id.subtitle);
    }

    public void setTextViewTitle(String title) {
        textViewTitle.setText(title);
    }

    public void setTextViewSubtitle(String subtitle) {
        textViewSubtitle.setText(subtitle);
    }

    public void setTextViewIndex(int index) {
        if (index > 0) {
            textViewIndex.setVisibility(View.VISIBLE);
            textViewIndex.setText(String.valueOf(index));
            textViewSubtitle.setTextColor(Color.parseColor("#bbbbbb"));
        } else {
            textViewIndex.setVisibility(View.GONE);
            textViewSubtitle.setTextColor(Color.parseColor("#ff009688"));
        }
    }

    public void configureForItem(String title, String subtitle, int position) {
        setTextViewTitle(title);
        setTextViewSubtitle(subtitle);
        setTextViewIndex(position);
    }

}
