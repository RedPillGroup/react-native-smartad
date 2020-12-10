package com.smartadserver.android.sassample.util;

import android.graphics.Bitmap;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.smartadserver.android.library.ui.SASAdChoicesView;
import com.smartadserver.android.library.ui.SASNativeAdMediaView;
import com.smartadserver.android.sassample.R;

/**
 * Created by Thomas on 01/08/2016.
 */

public class ListNativeAdWithMediaHolder extends RecyclerView.ViewHolder {

    public TextView textViewTitle;
    public TextView textViewSubtitle;
    public Button button;
    public ImageView iconImageView;
    public ImageView coverImageView;
    public SASNativeAdMediaView mediaView;
    public SASAdChoicesView adChoicesView;

    public ListNativeAdWithMediaHolder(View itemView) {
        super(itemView);

        textViewTitle = (TextView) itemView.findViewById(R.id.titleLabel);
        textViewSubtitle = (TextView) itemView.findViewById(R.id.subtitleLabel);
        button =  (Button) itemView.findViewById(R.id.callToActionButton);
        iconImageView = (ImageView) itemView.findViewById(R.id.iconImageView);
        coverImageView = (ImageView) itemView.findViewById(R.id.coverImageView);
        mediaView = (SASNativeAdMediaView) itemView.findViewById(R.id.mediaView);
        adChoicesView = (SASAdChoicesView) itemView.findViewById(R.id.adChoicesView);

    }

    public SASNativeAdMediaView getMediaView() {
        return mediaView;
    }

    public SASAdChoicesView getAdChoicesView() { return adChoicesView; }

    public void setTextViewTitle(String title) {
        textViewTitle.setText(title);
    }

    public void setTextViewSubtitle(String subtitle) {
        textViewSubtitle.setText(subtitle);
    }

    public void setButtonText(String text) {
        button.setText(text);
    }

    public void configureForAdItem(String title, String subtitle, String callToAction, Bitmap iconImage, Bitmap coverImage) {
        setTextViewTitle(title);
        setTextViewSubtitle(subtitle);
        setButtonText(callToAction);

        //Icon Image
        if (iconImage != null) {
            iconImageView.setImageBitmap(iconImage);
        }

        //Cover Image
        if (coverImage != null) {
            coverImageView.setImageBitmap(coverImage);
        }
    }
}
