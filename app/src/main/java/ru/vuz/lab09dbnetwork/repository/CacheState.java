package ru.vuz.lab09dbnetwork.repository;

public class CacheState {
    private final int itemCount;
    private final long latestNetworkCacheTime;

    public CacheState(int itemCount, long latestNetworkCacheTime) {
        this.itemCount = itemCount;
        this.latestNetworkCacheTime = latestNetworkCacheTime;
    }

    public int getItemCount() {
        return itemCount;
    }

    public long getLatestNetworkCacheTime() {
        return latestNetworkCacheTime;
    }
}
