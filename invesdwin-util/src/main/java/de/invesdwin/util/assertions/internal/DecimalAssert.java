package de.invesdwin.util.assertions.internal;

import java.util.Comparator;

import javax.annotation.concurrent.NotThreadSafe;

import org.assertj.core.api.AbstractComparableAssert;
import org.assertj.core.api.NumberAssert;
import org.assertj.core.data.Offset;
import org.assertj.core.data.Percentage;

import de.invesdwin.util.math.decimal.ADecimal;

@NotThreadSafe
public class DecimalAssert<E extends ADecimal<E>> extends AbstractComparableAssert<DecimalAssert<E>, E> implements
NumberAssert<DecimalAssert<E>, E> {
    private Decimals decimals = Decimals.INSTANCE;

    public DecimalAssert(final E actual) {
        super(actual, DecimalAssert.class);
    }

    @Override
    public DecimalAssert<E> isZero() {
        decimals.assertIsZero(info, actual);
        return myself;
    }

    @Override
    public DecimalAssert<E> isNotZero() {
        decimals.assertIsNotZero(info, actual);
        return myself;
    }

    @Override
    public DecimalAssert<E> isPositive() {
        decimals.assertIsPositive(info, actual);
        return myself;
    }

    @Override
    public DecimalAssert<E> isNegative() {
        decimals.assertIsNegative(info, actual);
        return myself;
    }

    @Override
    public DecimalAssert<E> isNotPositive() {
        decimals.assertIsNotPositive(info, actual);
        return myself;
    }

    @Override
    public DecimalAssert<E> isNotNegative() {
        decimals.assertIsNotNegative(info, actual);
        return myself;
    }

    /**
     * Verifies that the actual value is in ]start, end[ range (start excluded, end excluded).
     * 
     * <p>
     * Example:
     * 
     * <pre>
     * // assertions will pass
     * assertThat(new Decimal(&quot;8.0&quot;)).isBetween(new Decimal(&quot;7.0&quot;), new Decimal(&quot;9.0&quot;));
     * assertThat(new Decimal(&quot;8.0&quot;)).isBetween(new Decimal(&quot;8.0&quot;), new Decimal(&quot;9.0&quot;));
     * assertThat(new Decimal(&quot;8.0&quot;)).isBetween(new Decimal(&quot;7.0&quot;), new Decimal(&quot;8.0&quot;));
     * 
     * // assertion will fail
     * assertThat(new Decimal(&quot;8.0&quot;)).isBetween(new Decimal(&quot;6.0&quot;), new Decimal(&quot;7.0&quot;));
     * </pre>
     * 
     * </p>
     */
    @Override
    public DecimalAssert<E> isBetween(final E start, final E end) {
        decimals.assertIsBetween(info, actual, start, end);
        return myself;
    }

    /**
     * Verifies that the actual value is in ]start, end[ range (start excluded, end excluded).
     * 
     * <p>
     * Example:
     * 
     * <pre>
     * // assertion will pass
     * assertThat(new Decimal(&quot;8.0&quot;)).isStrictlyBetween(new Decimal(&quot;7.0&quot;), new Decimal(&quot;9.0&quot;));
     * 
     * // assertions will fail
     * assertThat(new Decimal(&quot;8.0&quot;)).isStrictlyBetween(new Decimal(&quot;8.0&quot;), new Decimal(&quot;9.0&quot;));
     * assertThat(new Decimal(&quot;8.0&quot;)).isStrictlyBetween(new Decimal(&quot;7.0&quot;), new Decimal(&quot;8.0&quot;));
     * </pre>
     * 
     * </p>
     */
    @Override
    public DecimalAssert<E> isStrictlyBetween(final E start, final E end) {
        decimals.assertIsStrictlyBetween(info, actual, start, end);
        return myself;
    }

    @Override
    public DecimalAssert<E> usingComparator(final Comparator<? super E> customComparator) {
        super.usingComparator(customComparator);
        this.decimals = new Decimals(new org.assertj.core.internal.ComparatorBasedComparisonStrategy(customComparator));
        return myself;
    }

    @Override
    public DecimalAssert<E> usingDefaultComparator() {
        super.usingDefaultComparator();
        this.decimals = Decimals.INSTANCE;
        return myself;
    }

    @Override
    public DecimalAssert<E> isCloseTo(final E expected, final Offset<E> offset) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public DecimalAssert<E> isCloseTo(final E expected, final Percentage percentage) {
        throw new UnsupportedOperationException("TODO");
    }
}
