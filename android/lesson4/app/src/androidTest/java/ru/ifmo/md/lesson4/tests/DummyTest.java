package ru.ifmo.md.lesson4.tests;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import ru.ifmo.md.lesson4.CalculationEngine;
import ru.ifmo.md.lesson4.CalculationEngineFactory;
import ru.ifmo.md.lesson4.CalculationException;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class DummyTest {
    private static double eps = 1e-9;
    private CalculationEngine engine;

    @Before
    public void setup() {
        //do whatever is necessary before every test
        engine = CalculationEngineFactory.defaultEngine();
    }

    @Test
    public void testParser() {
        boolean flag = true;
        try {
            engine.calculate("alena");
            engine.calculate("()");
            engine.calculate("(((");
            engine.calculate(")))");
            engine.calculate("(*)");
            engine.calculate("(10 +(3-6)");
            engine.calculate("-");
            engine.calculate("555.5.5");
            engine.calculate(".");
            engine.calculate("+.");
            engine.calculate("(5E)");
            engine.calculate("4,5");
            engine.calculate("7-");
            engine.calculate("72+");
            engine.calculate(")-4+(8)");
            flag = false;

        } catch (CalculationException e) {
            flag = true;

        } finally {
            if (!flag)
                Assert.fail();
        }

        try {
            engine.calculate("");
            engine.calculate(".5");
            engine.calculate("5.");
            engine.calculate("+.5");
            engine.calculate("-.5");
            engine.calculate("(---5)");
            engine.calculate("+5555");
            engine.calculate("5-.5");
            engine.calculate("5+5.");
            engine.calculate("+(-(43./0.3+12*(4/4/4*4)))--2.6+.3");

        } catch (CalculationException e) {
            Assert.fail();

        }
    }

    @Test
    public void testArithmetic() {

        try {
            Assert.assertEquals(10., engine.calculate("5+ 5  "), eps);
            Assert.assertEquals(-2500., engine.calculate("2.5*-1000"), eps);
            Assert.assertEquals(805.83115, engine.calculate("53.718    * 15   + - \t (-  -0.777-2   )/20"), eps);
            Assert.assertEquals(4., engine.calculate("((2+2))-0/(--2)*555.555"), eps);
            Assert.assertEquals(-14., engine.calculate("-7-(+7)"), eps);
            Assert.assertEquals(4, engine.calculate("----4"), eps);
            Assert.assertEquals(Double.NEGATIVE_INFINITY, engine.calculate("-1/0"), eps);
            Assert.assertEquals(Double.NaN, engine.calculate("0/0"), eps);
            Assert.assertEquals(72.0 / 532 * 729 * 177 / 699 - 267.0 * 471 / 481, engine.calculate("72.0/532*729*177/699-267.0*471/481"), eps);
            Assert.assertEquals(-(+(-18.0 / 36 * (10.0 + 13.0 / 36 / 16)) + (-21.0 / 7 * 24 + 21.0 * 41 + 6.0)), engine.calculate("-(+(-18.0/36*(10.0+13.0/36/16))+(-21.0/7*24+21.0*41+6.0))"), eps);
        } catch (CalculationException e) {
            Assert.fail();

        }
    }
}