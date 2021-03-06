package de.invesdwin.util.math.decimal;

import java.math.RoundingMode;
import java.util.List;

import de.invesdwin.util.math.decimal.config.BSplineInterpolationConfig;
import de.invesdwin.util.math.decimal.config.InterpolationConfig;
import de.invesdwin.util.math.decimal.config.LoessInterpolationConfig;

public interface IDecimalAggregate<E extends ADecimal<E>> {

    /**
     * All growth rates separately
     */
    IDecimalAggregate<E> growthRates();

    /**
     * The average of all growthRates.
     */
    E growthRate();

    /**
     * The growthRate of the growthRates.
     */
    E growthRatesTrend();

    IDecimalAggregate<E> reverse();

    /**
     * Returns a weighted average where the first value has the least weight and the last value has the highest weight.
     */
    E avgWeightedAsc();

    /**
     * Returns a weighted average where the first value has the highest weight and the last value has the least weight.
     */
    E avgWeightedDesc();

    E sum();

    /**
     * x_quer = (x_1 + x_2 + ... + x_n) / n
     * 
     * @see <a href="http://de.wikipedia.org/wiki/Arithmetisches_Mittel">Source</a>
     */
    E avg();

    /**
     * Product = x_1 * x_2 * ... * x_n
     * 
     * @see <a href="http://de.wikipedia.org/wiki/Arithmetisches_Mittel">Source</a>
     */
    E product();

    /**
     * x_quer = (x_1 * x_2 * ... * x_n)^1/n
     * 
     * @see <a href="http://de.wikipedia.org/wiki/Geometrisches_Mittel">Source</a>
     * @see <a href="http://www.ee.ucl.ac.uk/~mflanaga/java/Stat.html#geom2">Source with BigDecimal</a>
     */
    E geomAvg();

    E max();

    E min();

    /**
     * distance = abs(max()-min())
     */
    E minMaxDistance();

    /**
     * s = (1/(n-1) * sum((x_i - x_quer)^2))^1/2
     */
    E sampleStandardDeviation();

    /**
     * s = (1/(n) * sum((x_i - x_quer)^2))^1/2
     */
    E standardDeviation();

    /**
     * s^2 = 1/(n-1) * sum((x_i - x_quer)^2)
     */
    E variance();

    /**
     * s^2 = 1/(n) * sum((x_i - x_quer)^2)
     * 
     * <a href="http://de.wikipedia.org/wiki/Stichprobenvarianz">Source</a>
     */
    E sampleVariance();

    List<? extends E> values();

    IDecimalAggregate<E> round();

    IDecimalAggregate<E> round(final RoundingMode roundingMode);

    IDecimalAggregate<E> round(final int scale);

    IDecimalAggregate<E> round(final int scale, final RoundingMode roundingMode);

    IDecimalAggregate<E> roundToStep(final E step);

    IDecimalAggregate<E> roundToStep(final E step, final RoundingMode roundingMode);

    IDecimalAggregate<E> positiveValues();

    IDecimalAggregate<E> positiveNonZeroValues();

    IDecimalAggregate<E> negativeValues();

    IDecimalAggregate<E> negativeOrZeroValues();

    IDecimalAggregate<E> nonZeroValues();

    IDecimalAggregate<E> addEach(E augend);

    IDecimalAggregate<E> subtractEach(E subtrahend);

    IDecimalAggregate<E> multiplyEach(E multiplicant);

    IDecimalAggregate<E> divideEach(E divisor);

    /**
     * True when each element is >= its previous element
     */
    boolean isStableOrRisingEach();

    /**
     * True when each element is <= its previous element
     */
    boolean isStableOrFallingEach();

    IDecimalAggregate<E> loessInterpolation(LoessInterpolationConfig config);

    IDecimalAggregate<E> bSplineInterpolation(BSplineInterpolationConfig config);

    IDecimalAggregate<E> cubicBSplineInterpolation(InterpolationConfig config);

    /**
     * bezier is fast O(n) but cannot calculate value sizes larger than 1030. You might want to consider to fallback to
     * an equivalent variation of BSpline with degree n, which sadly is exponentially slower with O(2^n) and might thus
     * not even complete in your lifetime...
     */
    IDecimalAggregate<E> bezierCurveInterpolation(InterpolationConfig config);

    /**
     * VarK(x) = stddev(x) / expectedValue(x)
     */
    E coefficientOfVariance();

    /**
     * VarK(x) = samplestddev(x) / expectedValue(x)
     */
    E sampleCoefficientOfVariance();

    int count();

}
