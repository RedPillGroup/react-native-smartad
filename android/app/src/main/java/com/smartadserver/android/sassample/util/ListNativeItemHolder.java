package com.smartadserver.android.sassample.util;

import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.smartadserver.android.sassample.R;
import android.graphics.Bitmap;

/**
 * Created by Thomas on 01/06/2016.
 */

public class ListNativeItemHolder extends RecyclerView.ViewHolder {

    public TextView textViewIndex;
    public TextView textViewTitle;
    public TextView textViewSubtitle;
    public Button button;
    public LinearLayout subParentLayout;
    public LinearLayout adOptionsLayout;

    // Optional, only for items that are ads
    public ImageView posterImageView;

    public ListNativeItemHolder(View itemView) {
        super(itemView);
        textViewIndex = (TextView) itemView.findViewById(R.id.indexLabel);
        textViewTitle = (TextView) itemView.findViewById(R.id.titleLabel);
        textViewSubtitle = (TextView) itemView.findViewById(R.id.subtitleLabel);
        posterImageView = (ImageView)itemView.findViewById(R.id.iconImageView);
        button = (Button)itemView.findViewById(R.id.callToActionButton);
        subParentLayout = (LinearLayout)itemView.findViewById(R.id.subParentLayout);
        adOptionsLayout = (LinearLayout) itemView.findViewById(R.id.adOptionsLayout);
    }

    public void setTextViewTitle(String title) {
        textViewTitle.setText(title);
    }

    public void setTextViewSubtitle(String subtitle) {
        textViewSubtitle.setText(subtitle);
    }

    public void setButtonText(String text) {
        button.setText(text);
    }

    public void setTextViewIndex(int index) {
        if (index > 0) {
            textViewIndex.setVisibility(View.VISIBLE);
            textViewSubtitle.setTextColor(Color.parseColor("#bbbbbb"));
            textViewIndex.setText("" + index);
        } else {
            textViewIndex.setVisibility(View.GONE);
            textViewSubtitle.setTextColor(Color.parseColor("#ff009688"));
            textViewIndex.setText("");
        }
    }

    /*
     * Configure the View for a content item
     */
    public void configureForContentItem(String title, String subtitle, int position) {
        setTextViewTitle(title);
        setTextViewSubtitle(subtitle);
        setTextViewIndex(position);
        subParentLayout.setVisibility(View.VISIBLE);
        posterImageView.setVisibility(View.GONE);
        textViewTitle.setGravity(Gravity.LEFT);
        adOptionsLayout.setVisibility(View.GONE);
    }

    public void configureForAdItem(String title, String subtitle, String callToAction, Bitmap image) {
        subParentLayout.setVisibility(View.VISIBLE);
        setTextViewTitle(title);
        setTextViewSubtitle(subtitle);
        setButtonText(callToAction);
        textViewIndex.setVisibility(View.GONE);
        posterImageView.setVisibility(View.VISIBLE);
        adOptionsLayout.setVisibility(View.VISIBLE);

        if (image != null) {
            posterImageView.setImageBitmap(image);
        }

    }

}
