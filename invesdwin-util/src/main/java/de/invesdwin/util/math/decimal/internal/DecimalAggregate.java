package de.invesdwin.util.math.decimal.internal;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.concurrent.Immutable;

import de.invesdwin.util.collections.Lists;
import de.invesdwin.util.math.decimal.ADecimal;
import de.invesdwin.util.math.decimal.Decimal;
import de.invesdwin.util.math.decimal.IDecimalAggregate;
import de.invesdwin.util.math.decimal.config.BSplineInterpolationConfig;
import de.invesdwin.util.math.decimal.config.InterpolationConfig;
import de.invesdwin.util.math.decimal.config.LoessInterpolationConfig;

@Immutable
public class DecimalAggregate<E extends ADecimal<E>> implements IDecimalAggregate<E> {

    private E converter;
    private final List<? extends E> values;

    public DecimalAggregate(final List<? extends E> values, final E converter) {
        this.values = values;
        this.converter = converter;
    }

    public E getConverter() {
        if (converter == null) {
            for (final E scaledValue : values) {
                if (scaledValue != null) {
                    converter = scaledValue;
                    break;
                }
            }
        }
        return converter;

    }

    /**
     * All growth rates separately
     */
    @Override
    public IDecimalAggregate<E> growthRates() {
        final List<E> growthRates = new ArrayList<E>();
        E previousValue = (E) null;
        for (final E value : values) {
            if (previousValue != null) {
                growthRates.add(previousValue.growthRate(value));
            }
            previousValue = value;
        }
        return new DecimalAggregate<E>(growthRates, getConverter());
    }

    /**
     * The average of all growthRates.
     */
    @Override
    public E growthRate() {
        return growthRates().avg();
    }

    /**
     * The growthRate of the growthRates.
     */
    @Override
    public E growthRatesTrend() {
        return growthRates().growthRate();
    }

    @Override
    public IDecimalAggregate<E> reverse() {
        return new DecimalAggregate<E>(Lists.reverse(values), getConverter());
    }

    /**
     * Returns a weighted average where the first value has the least weight and the last value has the highest weight.
     */
    @Override
    public E avgWeightedAsc() {
        return reverse().avgWeightedDesc();
    }

    /**
     * Returns a weighted average where the first value has the highest weight and the last value has the least weight.
     */
    @Override
    public E avgWeightedDesc() {
        int sumOfWeights = 0;
        Decimal sumOfWeightedValues = Decimal.ZERO;
        for (int i = 0, weight = count(); i < count(); i++, weight--) {
            final Decimal weightedValue = values.get(i).getDefaultValue().multiply(weight);
            sumOfWeights += weight;
            sumOfWeightedValues = sumOfWeightedValues.add(weightedValue);
        }
        return getConverter().fromDefaultValue(sumOfWeightedValues.divide(sumOfWeights));
    }

    @Override
    public E sum() {
        Decimal sum = Decimal.ZERO;
        for (final E value : values) {
            if (value != null) {
                sum = sum.add(value.getDefaultValue());
            }
        }
        return getConverter().fromDefaultValue(sum);
    }

    /**
     * x_quer = (x_1 + x_2 + ... + x_n) / n
     * 
     * @see <a href="http://de.wikipedia.org/wiki/Arithmetisches_Mittel">Source</a>
     */
    @Override
    public E avg() {
        return sum().divide(count());
    }

    /**
     * Product = x_1 * x_2 * ... * x_n
     * 
     * @see <a href="http://de.wikipedia.org/wiki/Arithmetisches_Mittel">Source</a>
     */
    @Override
    public E product() {
        Decimal product = Decimal.ONE;
        for (final E value : values) {
            product = product.multiply(value.getDefaultValue());
        }
        return getConverter().fromDefaultValue(product);
    }

    /**
     * x_quer = (x_1 * x_2 * ... * x_n)^1/n
     * 
     * @see <a href="http://de.wikipedia.org/wiki/Geometrisches_Mittel">Source</a>
     * @see <a href="http://www.ee.ucl.ac.uk/~mflanaga/java/Stat.html#geom2">Source with BigDecimal</a>
     */
    @Override
    public E geomAvg() {
        Decimal logSum = Decimal.ZERO;
        for (int i = 0; i < count(); i++) {
            logSum = Decimal.sum(logSum, values.get(i).getDefaultValue().log());
        }
        final Decimal result = logSum.divide(count()).exp();
        return getConverter().fromDefaultValue(result);
    }

    @Override
    public E max() {
        E highest = (E) null;
        for (final E value : values) {
            if (highest == null) {
                highest = value;
            } else if (value == null) {
                continue;
            } else if (highest.compareTo(value) < 0) {
                highest = value;
            }
        }
        return highest;
    }

    @Override
    public E min() {
        E lowest = (E) null;
        for (final E value : values) {
            if (lowest == null) {
                lowest = value;
            } else if (value == null) {
                continue;
            } else if (value.compareTo(lowest) < 0) {
                lowest = value;
            }
        }
        return lowest;
    }

    @Override
    public E minMaxDistance() {
        final E min = min();
        if (min == null) {
            return null;
        }
        final E max = max();
        if (max == null) {
            return null;
        }
        return min.distance(max);
    }

    /**
     * s = (1/(n-1) * sum((x_i - x_quer)^2))^1/2
     */
    @Override
    public E sampleStandardDeviation() {
        final E avg = avg();
        Decimal sum = Decimal.ZERO;
        for (final E value : values) {
            sum = sum.add(value.subtract(avg).getDefaultValue().pow(2));
        }
        return getConverter().fromDefaultValue(sum.divide(count() - 1).sqrt());
    }

    /**
     * s = (1/(n) * sum((x_i - x_quer)^2))^1/2
     */
    @Override
    public E standardDeviation() {
        final E avg = avg();
        Decimal sum = Decimal.ZERO;
        for (final E value : values) {
            sum = sum.add(value.subtract(avg).getDefaultValue().pow(2));
        }
        return getConverter().fromDefaultValue(sum.divide(count()).sqrt());
    }

    /**
     * s^2 = 1/(n-1) * sum((x_i - x_quer)^2)
     */
    @Override
    public E variance() {
        final E avg = avg();
        Decimal sum = Decimal.ZERO;
        for (final E value : values) {
            sum = sum.add(value.subtract(avg).getDefaultValue().pow(2));
        }
        return getConverter().fromDefaultValue(sum.divide(count() - 1));
    }

    /**
     * s^2 = 1/(n) * sum((x_i - x_quer)^2)
     * 
     * <a href="http://de.wikipedia.org/wiki/Stichprobenvarianz">Source</a>
     */
    @Override
    public E sampleVariance() {
        final E avg = avg();
        Decimal sum = Decimal.ZERO;
        for (final E value : values) {
            sum = sum.add(value.subtract(avg).getDefaultValue().pow(2));
        }
        return getConverter().fromDefaultValue(sum.divide(count()));
    }

    @Override
    public E coefficientOfVariance() {
        return standardDeviation().divide(avg());
    }

    @Override
    public E sampleCoefficientOfVariance() {
        return sampleStandardDeviation().divide(avg());
    }

    @Override
    public int count() {
        return values.size();
    }

    @Override
    public IDecimalAggregate<E> bSplineInterpolation(final BSplineInterpolationConfig config) {
        return new DecimalAggregateInterpolations<E>(this).bSplineInterpolation(config);
    }

    @Override
    public IDecimalAggregate<E> loessInterpolation(final LoessInterpolationConfig config) {
        return new DecimalAggregateInterpolations<E>(this).loessInterpolation(config);
    }

    @Override
    public IDecimalAggregate<E> cubicBSplineInterpolation(final InterpolationConfig config) {
        return new DecimalAggregateInterpolations<E>(this).cubicBSplineInterpolation(config);
    }

    @Override
    public IDecimalAggregate<E> bezierCurveInterpolation(final InterpolationConfig config) {
        return new DecimalAggregateInterpolations<E>(this).bezierCurveInterpolation(config);
    }

    @Override
    public List<? extends E> values() {
        return Collections.unmodifiableList(values);
    }

    @Override
    public IDecimalAggregate<E> round() {
        return round(Decimal.DEFAULT_ROUNDING_SCALE);
    }

    @Override
    public IDecimalAggregate<E> round(final RoundingMode roundingMode) {
        return round(Decimal.DEFAULT_ROUNDING_SCALE, roundingMode);
    }

    @Override
    public IDecimalAggregate<E> round(final int scale) {
        return round(scale, Decimal.DEFAULT_ROUNDING_MODE);
    }

    @Override
    public IDecimalAggregate<E> round(final int scale, final RoundingMode roundingMode) {
        final List<E> rounded = new ArrayList<E>(count());
        for (final E value : values) {
            rounded.add(value.round(scale, roundingMode));
        }
        return new DecimalAggregate<E>(rounded, getConverter());
    }

    @Override
    public IDecimalAggregate<E> roundToStep(final E step) {
        return roundToStep(step, Decimal.DEFAULT_ROUNDING_MODE);
    }

    @Override
    public IDecimalAggregate<E> roundToStep(final E step, final RoundingMode roundingMode) {
        final List<E> rounded = new ArrayList<E>(count());
        for (final E value : values) {
            rounded.add(value.roundToStep(step, roundingMode));
        }
        return new DecimalAggregate<E>(rounded, getConverter());
    }

    @Override
    public String toString() {
        return values.toString();
    }

    @Override
    public IDecimalAggregate<E> positiveValues() {
        final List<E> positives = new ArrayList<E>();
        for (final E value : values) {
            if (value.isPositive()) {
                positives.add(value);
            }
        }
        return new DecimalAggregate<E>(positives, getConverter());
    }

    @Override
    public IDecimalAggregate<E> positiveNonZeroValues() {
        final List<E> positives = new ArrayList<E>();
        for (final E value : values) {
            if (value.isPositiveNonZero()) {
                positives.add(value);
            }
        }
        return new DecimalAggregate<E>(positives, getConverter());
    }

    @Override
    public IDecimalAggregate<E> negativeValues() {
        final List<E> negatives = new ArrayList<E>();
        for (final E value : values) {
            if (value.isNegative()) {
                negatives.add(value);
            }
        }
        return new DecimalAggregate<E>(negatives, getConverter());
    }

    @Override
    public IDecimalAggregate<E> negativeOrZeroValues() {
        final List<E> negatives = new ArrayList<E>();
        for (final E value : values) {
            if (value.isNegativeOrZero()) {
                negatives.add(value);
            }
        }
        return new DecimalAggregate<E>(negatives, getConverter());
    }

    @Override
    public IDecimalAggregate<E> nonZeroValues() {
        final List<E> nonZeros = new ArrayList<E>();
        for (final E value : values) {
            if (value.isNotZero()) {
                nonZeros.add(value);
            }
        }
        return new DecimalAggregate<E>(nonZeros, getConverter());

    }

    @Override
    public IDecimalAggregate<E> addEach(final E augend) {
        final List<E> added = new ArrayList<E>();
        for (final E value : values) {
            added.add(value.add(augend));
        }
        return new DecimalAggregate<E>(added, getConverter());
    }

    @Override
    public IDecimalAggregate<E> subtractEach(final E subtrahend) {
        final List<E> subtracted = new ArrayList<E>();
        for (final E value : values) {
            subtracted.add(value.subtract(subtrahend));
        }
        return new DecimalAggregate<E>(subtracted, getConverter());
    }

    @Override
    public IDecimalAggregate<E> multiplyEach(final E multiplicant) {
        final List<E> multiplied = new ArrayList<E>();
        for (final E value : values) {
            multiplied.add(value.add(multiplicant));
        }
        return new DecimalAggregate<E>(multiplied, getConverter());
    }

    @Override
    public IDecimalAggregate<E> divideEach(final E divisor) {
        final List<E> divided = new ArrayList<E>();
        for (final E value : values) {
            divided.add(value.add(divisor));
        }
        return new DecimalAggregate<E>(divided, getConverter());
    }

    @Override
    public boolean isStableOrRisingEach() {
        E prevValue = null;
        for (final E value : values) {
            if (prevValue != null) {
                if (value.isLessThan(prevValue)) {
                    return false;
                }
            }
            prevValue = value;
        }
        return true;
    }

    @Override
    public boolean isStableOrFallingEach() {
        E prevValue = null;
        for (final E value : values) {
            if (prevValue != null) {
                if (value.isGreaterThan(prevValue)) {
                    return false;
                }
            }
            prevValue = value;
        }
        return true;
    }

}
