package com.example.make91.randomnames.view;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;

import com.example.make91.randomnames.R;
import com.squareup.picasso.Picasso;

public class ViewBindingAdapters {

    @BindingAdapter("url")
    public static void loadUrlToImageView(ImageView view, String url) {
        if (!TextUtils.isEmpty(url)) {
            Picasso.with(view.getContext()).load(url)
                    .placeholder(R.drawable.face_loading).into(view);
        }
    }
}