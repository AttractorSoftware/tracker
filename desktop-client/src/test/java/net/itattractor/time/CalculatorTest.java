package net.itattractor.time;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class CalculatorTest {
    @Test
    public void testCalculateFromTo() throws Exception {
        Calculator calculator = new Calculator();
        String from = "13.09.2013 18:21:10";
        String to = "13.09.2013 18:59:10";
        ArrayList<String> periods = calculator.calculate(from, to);
        ArrayList<String> expected = new ArrayList<String>();
        expected.add("13.09.2013 18:30:00");
        expected.add("13.09.2013 18:40:00");
        expected.add("13.09.2013 18:50:00");
        Assert.assertEquals(expected, periods);

    }

    @Test
    public void testCalculateWithProximity() throws Exception {
        Calculator calculator = new Calculator();
        String from = "13.09.2013 18:30:10";
        String to = "13.09.2013 18:59:10";
        ArrayList<String> periods = calculator.calculate(from, to);
        ArrayList<String> expected = new ArrayList<String>();
        expected.add("13.09.2013 18:30:00");
        expected.add("13.09.2013 18:40:00");
        expected.add("13.09.2013 18:50:00");
        Assert.assertEquals(expected, periods);

    }

    @Test
    public void testCalculateWithUpperProximity() throws Exception {
        Calculator calculator = new Calculator();
        String from = "13.09.2013 18:30:10";
        String to = "13.09.2013 19:00:10";
        ArrayList<String> periods = calculator.calculate(from, to);
        ArrayList<String> expected = new ArrayList<String>();
        expected.add("13.09.2013 18:30:00");
        expected.add("13.09.2013 18:40:00");
        expected.add("13.09.2013 18:50:00");
        expected.add("13.09.2013 19:00:00");
        Assert.assertEquals(expected, periods);

    }

    @Test
    public void testCalculateWithUpperProximityNearTo59Second() throws Exception {
        Calculator calculator = new Calculator();
        String from = "13.09.2013 18:30:59";
        String to = "13.09.2013 19:00:10";
        ArrayList<String> periods = calculator.calculate(from, to);
        ArrayList<String> expected = new ArrayList<String>();
        expected.add("13.09.2013 18:30:00");
        expected.add("13.09.2013 18:40:00");
        expected.add("13.09.2013 18:50:00");
        expected.add("13.09.2013 19:00:00");
        Assert.assertEquals(expected, periods);
    }

    @Test
    public void testCalculateWithUpperProximityExactAt00() throws Exception {
        Calculator calculator = new Calculator();
        String from = "13.09.2013 18:31:00";
        String to = "13.09.2013 19:00:10";
        ArrayList<String> periods = calculator.calculate(from, to);
        ArrayList<String> expected = new ArrayList<String>();
        expected.add("13.09.2013 18:40:00");
        expected.add("13.09.2013 18:50:00");
        expected.add("13.09.2013 19:00:00");
        Assert.assertEquals(expected, periods);
    }


}
