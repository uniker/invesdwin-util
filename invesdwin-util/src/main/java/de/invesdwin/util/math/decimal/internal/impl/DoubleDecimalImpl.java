package de.invesdwin.util.math.decimal.internal.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.annotation.concurrent.ThreadSafe;

import org.apache.commons.math3.dfp.Dfp;

import de.invesdwin.util.assertions.Assertions;
import de.invesdwin.util.error.UnknownArgumentException;
import de.invesdwin.util.lang.Strings;
import de.invesdwin.util.math.decimal.ADecimal;
import de.invesdwin.util.math.decimal.Decimal;

@ThreadSafe
public class DoubleDecimalImpl extends ADecimalImpl<DoubleDecimalImpl, Double> {

    private static final Double FIRST_ABOVE_ZERO = 0.000000001;
    private static final Double FIRST_BELOW_ZERO = -0.000000001;
    private static final Double ZERO = 0d;

    static {
        //ensure rounding performance fix uses correct scale
        final NumberFormat df = new DecimalFormat("0.##########################");
        Assertions.assertThat(Strings.countMatches(df.format(FIRST_ABOVE_ZERO), "0"))
                .isEqualTo(ADecimal.DEFAULT_ROUNDING_SCALE);
        Assertions.assertThat(Strings.countMatches(df.format(FIRST_BELOW_ZERO), "0"))
                .isEqualTo(ADecimal.DEFAULT_ROUNDING_SCALE);
    }

    public DoubleDecimalImpl(final Double value, final Double defaultRoundedValue) {
        super(value, defaultRoundedValue);
    }

    @Override
    public boolean isZero() {
        return getDefaultRoundedValue().equals(ZERO);
    }

    @Override
    public boolean isPositive() {
        return getDefaultRoundedValue() >= ZERO;
    }

    @Override
    public String internalToString() {
        final NumberFormat format = NumberFormat.getNumberInstance(Locale.ENGLISH);
        format.setMaximumFractionDigits(MathContext.DECIMAL128.getPrecision());
        format.setRoundingMode(Decimal.DEFAULT_ROUNDING_MODE);
        format.setGroupingUsed(false);
        return format.format(getDefaultRoundedValue());
    }

    @Override
    protected int internalCompareTo(final ADecimal<?> decimalOther) {
        //improve compare performance by rounding less often
        final DoubleDecimalImpl doubleDecimalOther = (DoubleDecimalImpl) decimalOther.getImpl();
        final Double doubleOther = doubleDecimalOther.getValue();
        final Double doubleThis = getValue();
        final Double difference = doubleThis - doubleOther;
        if (difference > FIRST_ABOVE_ZERO) {
            return 1;
        } else if (difference < FIRST_BELOW_ZERO) {
            return -1;
        } else if (difference == ZERO) {
            return 0;
        } else {
            final double roundedOther = decimalOther.round().doubleValue();
            return getDefaultRoundedValue().compareTo(roundedOther);
        }
    }

    @Override
    public DoubleDecimalImpl scaleByPowerOfTen(final int n) {
        return multiply(Math.pow(10, n));
    }

    @Override
    public Number numberValue() {
        return getDefaultRoundedValue();
    }

    @Override
    public double doubleValue() {
        return getDefaultRoundedValue().doubleValue();
    }

    @Override
    public double doubleValueRaw() {
        return getValue();
    }

    @Override
    public float floatValue() {
        return getDefaultRoundedValue().floatValue();
    }

    @Override
    public int intValue() {
        return getDefaultRoundedValue().intValue();
    }

    @Override
    public long longValue() {
        return getDefaultRoundedValue().longValue();
    }

    @Override
    public byte byteValue() {
        return getDefaultRoundedValue().byteValue();
    }

    @Override
    public short shortValue() {
        return getDefaultRoundedValue().shortValue();
    }

    @Override
    public DoubleDecimalImpl abs() {
        return newValueCopy(Math.abs(getValue()));
    }

    @Override
    public DoubleDecimalImpl root(final ADecimal<?> n) {
        return root(n.doubleValueRaw());
    }

    @Override
    public DoubleDecimalImpl root(final Number n) {
        final double log = Math.log(getValue());
        final double result = Math.exp(log / n.doubleValue());
        return newValueCopy(result);
    }

    @Override
    public DoubleDecimalImpl sqrt() {
        return newValueCopy(Math.sqrt(getValue()));
    }

    @Override
    public DoubleDecimalImpl pow(final Number exponent) {
        return newValueCopy(Math.pow(getValue(), exponent.doubleValue()));
    }

    @Override
    public DoubleDecimalImpl pow(final ADecimal<?> exponent) {
        return pow(exponent.doubleValueRaw());
    }

    @Override
    public DoubleDecimalImpl subtract(final ADecimal<?> subtrahend) {
        final double value = getValue() - subtrahend.doubleValueRaw();
        return newValueCopy(value, value);
    }

    @Override
    public DoubleDecimalImpl add(final ADecimal<?> augend) {
        final double value = getValue() + augend.doubleValueRaw();
        return newValueCopy(value, value);
    }

    @Override
    public DoubleDecimalImpl multiply(final ADecimal<?> multiplicant) {
        return newValueCopy(getValue() * multiplicant.doubleValueRaw());
    }

    @Override
    public DoubleDecimalImpl multiply(final Number multiplicant) {
        return newValueCopy(getValue() * multiplicant.doubleValue());
    }

    @Override
    public DoubleDecimalImpl divide(final ADecimal<?> divisor) {
        return newValueCopy(getValue() / divisor.doubleValueRaw());
    }

    @Override
    public DoubleDecimalImpl divide(final Number divisor) {
        return newValueCopy(getValue() / divisor.doubleValue());
    }

    @Override
    public DoubleDecimalImpl remainder(final ADecimal<?> divisor) {
        return newValueCopy(getValue() % divisor.doubleValueRaw());
    }

    @Override
    public DoubleDecimalImpl remainder(final Number divisor) {
        return newValueCopy(getValue() % divisor.doubleValue());
    }

    @Override
    public DoubleDecimalImpl log() {
        return newValueCopy(Math.log(getValue()));
    }

    @Override
    public DoubleDecimalImpl exp() {
        return newValueCopy(Math.exp(getValue()));
    }

    @Override
    public BigDecimal bigDecimalValue() {
        return BigDecimalDecimalImplFactory.toBigDecimal(getValue());
    }

    @Override
    public BigInteger bigIntegerValue() {
        return bigDecimalValue().toBigInteger();
    }

    @Override
    public Dfp dfpValue() {
        return DfpDecimalImplFactory.toDfp(getDefaultRoundedValue());
    }

    @Override
    protected Double internalRound(final Double value, final int scale, final RoundingMode roundingMode) {
        final long factor = (long) Math.pow(10, scale);
        final double toBeRoundedValue;
        if (scale < Decimal.DEFAULT_ROUNDING_SCALE && roundingMode != Decimal.DEFAULT_ROUNDING_MODE) {
            //fix 1 represented as 0.9999999 becoming 0 here instead of correctly being 1; for instance in FLOOR rounding mode
            toBeRoundedValue = internalRound(value, scale + Decimal.DEFAULT_ROUNDING_SCALE,
                    Decimal.DEFAULT_ROUNDING_MODE) * factor;
        } else {
            toBeRoundedValue = value * factor;
        }

        final double roundedValue;
        switch (roundingMode) {
        case CEILING:
            roundedValue = Math.ceil(toBeRoundedValue);
            break;
        case UP:
            if (toBeRoundedValue >= 0) {
                roundedValue = (long) (toBeRoundedValue + 1d);
            } else {
                roundedValue = (long) (toBeRoundedValue - 1d);
            }
            break;
        case FLOOR:
            roundedValue = Math.floor(toBeRoundedValue);
            break;
        case DOWN:
            roundedValue = (long) toBeRoundedValue;
            break;
        case HALF_DOWN:
            if (toBeRoundedValue >= 0) {
                roundedValue = Math.ceil(toBeRoundedValue - 0.5d);
            } else {
                roundedValue = Math.floor(toBeRoundedValue + 0.5d);
            }
            break;
        case HALF_EVEN:
            //if the value is even and the fraction is 0.5, we need to round to the even number
            final long longValue = (long) toBeRoundedValue;
            if (longValue % 2 == 0) {
                //need to rounded here, since 0.5 can not be represented properly for doubles
                final long firstFractionalDigit = Math.abs(Math.round(toBeRoundedValue % 1 * 10));
                if (firstFractionalDigit == 5) {
                    roundedValue = longValue;
                    break;
                }
            }

            //otherwise round to the nearest number
            roundedValue = Math.rint(toBeRoundedValue);
            break;
        case HALF_UP:
            if (toBeRoundedValue >= 0) {
                roundedValue = Math.floor(toBeRoundedValue + 0.5d);
            } else {
                roundedValue = Math.ceil(toBeRoundedValue - 0.5);
            }
            break;
        default:
            throw UnknownArgumentException.newInstance(RoundingMode.class, roundingMode);
        }
        return roundedValue / factor;
    }

    @Override
    protected Double getZero() {
        return ZERO;
    }

    @Override
    protected DoubleDecimalImpl newValueCopy(final Double value, final Double defaultRoundedValue) {
        return new DoubleDecimalImpl(value, defaultRoundedValue);
    }

    @Override
    protected DoubleDecimalImpl getGenericThis() {
        return this;
    }

}
