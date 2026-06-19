package ru.vuz.lab09dbnetwork.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class CachePolicyTest {
    private final long now = TimeUnit.MINUTES.toMillis(60);
    private final CachePolicy policy = new CachePolicy(
            () -> now,
            TimeUnit.MINUTES.toMillis(30)
    );

    @Test
    public void shouldRequestNetworkWhenCacheIsEmpty() {
        assertTrue(policy.shouldRequestNetwork(new CacheState(0, 0), false));
    }

    @Test
    public void shouldUseRoomCacheWhenItIsFresh() {
        long cachedAt = TimeUnit.MINUTES.toMillis(45);
        assertFalse(policy.shouldRequestNetwork(new CacheState(5, cachedAt), false));
    }

    @Test
    public void shouldRequestNetworkWhenCacheIsExpired() {
        long cachedAt = TimeUnit.MINUTES.toMillis(20);
        assertTrue(policy.shouldRequestNetwork(new CacheState(5, cachedAt), false));
    }

    @Test
    public void shouldRequestNetworkWhenUserForcesRefresh() {
        long cachedAt = TimeUnit.MINUTES.toMillis(58);
        assertTrue(policy.shouldRequestNetwork(new CacheState(5, cachedAt), true));
    }
}
