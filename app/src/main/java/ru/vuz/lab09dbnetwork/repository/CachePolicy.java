package ru.vuz.lab09dbnetwork.repository;

import java.util.concurrent.TimeUnit;
import java.util.function.LongSupplier;

public class CachePolicy {
    private static final long DEFAULT_MAX_CACHE_AGE_MS = TimeUnit.MINUTES.toMillis(30);

    private final LongSupplier nowProvider;
    private final long maxCacheAgeMs;

    public CachePolicy() {
        this(System::currentTimeMillis, DEFAULT_MAX_CACHE_AGE_MS);
    }

    public CachePolicy(LongSupplier nowProvider, long maxCacheAgeMs) {
        this.nowProvider = nowProvider;
        this.maxCacheAgeMs = maxCacheAgeMs;
    }

    public boolean shouldRequestNetwork(CacheState state, boolean forceNetwork) {
        if (forceNetwork) {
            return true;
        }
        if (state.getItemCount() == 0) {
            return true;
        }
        if (state.getLatestNetworkCacheTime() <= 0) {
            return true;
        }
        return nowProvider.getAsLong() - state.getLatestNetworkCacheTime() > maxCacheAgeMs;
    }
}
