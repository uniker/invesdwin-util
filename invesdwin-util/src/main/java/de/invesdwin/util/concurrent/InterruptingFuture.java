package de.invesdwin.util.concurrent;

import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.annotation.concurrent.Immutable;

/**
 * May be delivered if a task cannot be started because an InterruptedException occured. In this case the interrupt is
 * propagated. Executors cannot propagate InterruptedExceptions otherwise sometimes.
 * 
 * @author subes
 * 
 */
@Immutable
public class InterruptingFuture<V> implements ScheduledFuture<V> {

    @Override
    public boolean cancel(final boolean mayInterruptIfRunning) {
        return true;
    }

    @Override
    public boolean isCancelled() {
        return true;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        throw new InterruptedException();
    }

    @Override
    public V get(final long timeout, final TimeUnit unit) throws InterruptedException, ExecutionException,
    TimeoutException {
        throw new InterruptedException();
    }

    @Override
    public long getDelay(final TimeUnit unit) {
        return 0;
    }

    @Override
    public int compareTo(final Delayed o) {
        return Long.valueOf(getDelay(TimeUnit.NANOSECONDS)).compareTo(o.getDelay(TimeUnit.NANOSECONDS));
    }

}
