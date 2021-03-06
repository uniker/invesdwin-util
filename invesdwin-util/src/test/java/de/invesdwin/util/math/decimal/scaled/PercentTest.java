package de.invesdwin.util.math.decimal.scaled;

import javax.annotation.concurrent.ThreadSafe;

import org.junit.Test;

import de.invesdwin.util.assertions.Assertions;
import de.invesdwin.util.math.decimal.Decimal;

@ThreadSafe
public class PercentTest {

    @Test
    public void test() {
        Assertions.assertThat(new Percent(Decimal.ONE, PercentScale.RATE).getValue(PercentScale.RATE)).isEqualTo(
                Decimal.ONE);
        Assertions.assertThat(new Percent(Decimal.ONE, PercentScale.RATE).getValue(PercentScale.PERCENT)).isEqualTo(
                new Decimal("100"));
        Assertions.assertThat(new Percent(Decimal.ONE, PercentScale.RATE).getValue(PercentScale.PERMILLE)).isEqualTo(
                new Decimal("1000"));

        Assertions.assertThat(new Percent(Decimal.ONE, PercentScale.PERCENT).getValue(PercentScale.PERCENT)).isEqualTo(
                Decimal.ONE);
        Assertions.assertThat(new Percent(Decimal.ONE, PercentScale.PERCENT).getValue(PercentScale.PERMILLE))
        .isEqualTo(new Decimal("10"));
        Assertions.assertThat(new Percent(Decimal.ONE, PercentScale.PERCENT).getValue(PercentScale.RATE)).isEqualTo(
                new Decimal("0.01"));

        Assertions.assertThat(new Percent(Decimal.ONE, PercentScale.PERMILLE).getValue(PercentScale.PERMILLE))
        .isEqualTo(Decimal.ONE);
        Assertions.assertThat(new Percent(Decimal.ONE, PercentScale.PERMILLE).getValue(PercentScale.PERCENT))
        .isEqualTo(new Decimal("0.1"));
        Assertions.assertThat(new Percent(Decimal.ONE, PercentScale.PERMILLE).getValue(PercentScale.RATE)).isEqualTo(
                new Decimal("0.001"));

        Assertions.assertThat(new Percent(Decimal.ONE, PercentScale.PERMILLE).toString(PercentScale.PERMILLE))
        .isEqualTo("1" + PercentScale.PERMILLE.getSymbol());
    }
}
