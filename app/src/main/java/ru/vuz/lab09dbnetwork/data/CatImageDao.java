package ru.vuz.lab09dbnetwork.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CatImageDao {
    @Query("SELECT * FROM cat_images ORDER BY added_by_user DESC, cached_at DESC")
    LiveData<List<CatImage>> observeImages();

    @Query("SELECT * FROM cat_images ORDER BY added_by_user DESC, cached_at DESC")
    List<CatImage> getImagesSnapshot();

    @Query("SELECT * FROM cat_images WHERE remote_id = :remoteId LIMIT 1")
    CatImage findByRemoteId(String remoteId);

    @Query("SELECT COUNT(*) FROM cat_images")
    int countImages();

    @Query("SELECT MAX(cached_at) FROM cat_images WHERE added_by_user = 0")
    Long latestNetworkCacheTime();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CatImage image);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CatImage> images);

    @Query("DELETE FROM cat_images")
    void clearAll();
}
