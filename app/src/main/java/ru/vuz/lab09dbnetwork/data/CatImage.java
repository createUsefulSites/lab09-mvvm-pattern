package ru.vuz.lab09dbnetwork.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(
        tableName = "cat_images",
        indices = {@Index(value = {"remote_id"}, unique = true)}
)
public class CatImage {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "local_id")
    private long localId;

    @NonNull
    @SerializedName("id")
    @ColumnInfo(name = "remote_id")
    private String remoteId = "";

    @NonNull
    @SerializedName("url")
    @ColumnInfo(name = "image_url")
    private String imageUrl = "";

    @SerializedName("width")
    @ColumnInfo(name = "width")
    private int width;

    @SerializedName("height")
    @ColumnInfo(name = "height")
    private int height;

    @NonNull
    @ColumnInfo(name = "title")
    private String title = "";

    @ColumnInfo(name = "added_by_user")
    private boolean addedByUser;

    @ColumnInfo(name = "cached_at")
    private long cachedAt;

    public CatImage() {
    }

    public static CatImage createManual(String imageUrl, long createdAt) {
        CatImage image = new CatImage();
        image.setRemoteId("manual-" + createdAt);
        image.setImageUrl(imageUrl.trim());
        image.setTitle("Добавлено вручную");
        image.setAddedByUser(true);
        image.setCachedAt(createdAt);
        return image;
    }

    public void normalizeNetworkImage(long cachedAt) {
        if (remoteId == null || remoteId.trim().isEmpty()) {
            remoteId = "network-" + Math.abs(imageUrl.hashCode());
        }
        if (imageUrl == null) {
            imageUrl = "";
        }
        title = "The Cat API";
        addedByUser = false;
        this.cachedAt = cachedAt;
    }

    public long getLocalId() {
        return localId;
    }

    public void setLocalId(long localId) {
        this.localId = localId;
    }

    @NonNull
    public String getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(@NonNull String remoteId) {
        this.remoteId = remoteId;
    }

    @NonNull
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@NonNull String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public boolean isAddedByUser() {
        return addedByUser;
    }

    public void setAddedByUser(boolean addedByUser) {
        this.addedByUser = addedByUser;
    }

    public long getCachedAt() {
        return cachedAt;
    }

    public void setCachedAt(long cachedAt) {
        this.cachedAt = cachedAt;
    }
}
