package de.invesdwin.util.concurrent;

import javax.annotation.concurrent.ThreadSafe;

import de.invesdwin.util.collections.loadingcache.ALoadingCache;

@ThreadSafe
public abstract class ANestedExecutor {

    private static final String NESTED_LEVEL_SEPARATOR = "_";
    private final ALoadingCache<String, WrappedExecutorService> nestedExecutor = new ALoadingCache<String, WrappedExecutorService>() {
        @Override
        protected boolean isHighConcurrency() {
            return true;
        }

        @Override
        protected WrappedExecutorService loadValue(final String key) {
            return newNestedExecutor(name + NESTED_LEVEL_SEPARATOR + key);
        }

    };
    private final String name;

    public ANestedExecutor(final String name) {
        this.name = name;
    }

    public final WrappedExecutorService getNestedExecutor() {
        return nestedExecutor.get(String.valueOf(getCurrentNestedThreadLevel()));
    }

    public final WrappedExecutorService getNestedExecutor(final String suffix) {
        return nestedExecutor.get(getCurrentNestedThreadLevel() + NESTED_LEVEL_SEPARATOR + suffix);
    }

    public int getCurrentNestedThreadLevel() {
        return Threads.getCurrentNestedThreadLevel(name + NESTED_LEVEL_SEPARATOR);
    }

    protected abstract WrappedExecutorService newNestedExecutor(final String nestedName);
}
