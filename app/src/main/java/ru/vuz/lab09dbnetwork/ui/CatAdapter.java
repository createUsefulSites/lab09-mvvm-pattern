package ru.vuz.lab09dbnetwork.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ru.vuz.lab09dbnetwork.R;
import ru.vuz.lab09dbnetwork.data.CatImage;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.CatViewHolder> {
    private final CatImageLoader imageLoader;
    private final List<CatImage> images = new ArrayList<>();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", new Locale("ru", "RU"));

    public CatAdapter(CatImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    public void submitImages(List<CatImage> newImages) {
        images.clear();
        if (newImages != null) {
            images.addAll(newImages);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cat_image, parent, false);
        return new CatViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CatViewHolder holder, int position) {
        CatImage image = images.get(position);
        holder.titleTextView.setText(image.getTitle());
        holder.sourceTextView.setText(image.isAddedByUser()
                ? R.string.source_user
                : R.string.source_network);
        holder.detailsTextView.setText(holder.itemView.getContext().getString(
                R.string.cat_details_template,
                image.getRemoteId(),
                image.getWidth(),
                image.getHeight(),
                dateFormat.format(new Date(image.getCachedAt()))
        ));
        holder.urlTextView.setText(image.getImageUrl());
        imageLoader.load(image.getImageUrl(), holder.imageView);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    static class CatViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView titleTextView;
        final TextView sourceTextView;
        final TextView detailsTextView;
        final TextView urlTextView;

        CatViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cat_image_view);
            titleTextView = itemView.findViewById(R.id.cat_title_text_view);
            sourceTextView = itemView.findViewById(R.id.cat_source_text_view);
            detailsTextView = itemView.findViewById(R.id.cat_details_text_view);
            urlTextView = itemView.findViewById(R.id.cat_url_text_view);
        }
    }
}
