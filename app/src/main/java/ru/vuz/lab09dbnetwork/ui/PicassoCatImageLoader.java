package ru.vuz.lab09dbnetwork.ui;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import ru.vuz.lab09dbnetwork.R;

public class PicassoCatImageLoader implements CatImageLoader {
    @Override
    public void load(String imageUrl, ImageView imageView) {
        if (!ImageUrlValidator.isValidImageUrl(imageUrl)) {
            imageView.setImageResource(R.drawable.image_error);
            return;
        }
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_error)
                .fit()
                .centerCrop()
                .into(imageView);
    }
}
