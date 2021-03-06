package de.invesdwin.util.collections.loadingcache.historical.query.internal;

import de.invesdwin.util.collections.loadingcache.ILoadingCache;
import de.invesdwin.util.collections.loadingcache.historical.interceptor.IHistoricalCacheQueryInterceptor;
import de.invesdwin.util.time.fdate.FDate;

public interface IHistoricalCacheInternalMethods<V> {

    IHistoricalCacheQueryInterceptor<V> getQueryInterceptor();

    FDate calculatePreviousKey(FDate key);

    FDate calculateNextKey(FDate key);

    ILoadingCache<FDate, V> getValuesMap();

    FDate adjustKey(FDate key);

    void remove(FDate key);

    FDate extractKey(FDate key, V value);

    Integer getMaximumSize();

    void increaseMaximumSize(int maximumSize);

}
