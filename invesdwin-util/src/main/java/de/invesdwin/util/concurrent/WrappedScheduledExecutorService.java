package de.invesdwin.util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class WrappedScheduledExecutorService extends WrappedExecutorService implements ScheduledExecutorService {

    WrappedScheduledExecutorService(final java.util.concurrent.ScheduledThreadPoolExecutor delegate,
            final String name) {
        super(delegate, name);
        withLogExceptions(true);
    }

    @Override
    public java.util.concurrent.ScheduledThreadPoolExecutor getWrappedInstance() {
        return (java.util.concurrent.ScheduledThreadPoolExecutor) super.getWrappedInstance();
    }

    @Override
    public ScheduledFuture<?> schedule(final Runnable command, final long delay, final TimeUnit unit) {
        try {
            return getWrappedInstance().schedule(WrappedRunnable.newInstance(this, command), delay, unit);
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
            return new InterruptingFuture<Object>();
        }
    }

    @Override
    public <V> ScheduledFuture<V> schedule(final Callable<V> callable, final long delay, final TimeUnit unit) {
        try {
            return getWrappedInstance().schedule(WrappedCallable.newInstance(this, callable), delay, unit);
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
            return new InterruptingFuture<V>();
        }
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(final Runnable command, final long initialDelay, final long period,
            final TimeUnit unit) {
        try {
            return getWrappedInstance().scheduleAtFixedRate(WrappedRunnable.newInstance(this, command), initialDelay,
                    period, unit);
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
            return new InterruptingFuture<Object>();
        }
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(final Runnable command, final long initialDelay, final long delay,
            final TimeUnit unit) {
        try {
            return getWrappedInstance().scheduleWithFixedDelay(WrappedRunnable.newInstance(this, command), initialDelay,
                    delay, unit);
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
            return new InterruptingFuture<Object>();
        }
    }

}
