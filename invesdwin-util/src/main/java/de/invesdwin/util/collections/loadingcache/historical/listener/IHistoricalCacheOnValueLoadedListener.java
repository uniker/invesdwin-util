package de.invesdwin.util.collections.loadingcache.historical.listener;

import de.invesdwin.util.time.fdate.FDate;

public interface IHistoricalCacheOnValueLoadedListener<V> {

    void onValueLoaded(FDate key, V value);

}
