package net.itattractor;

import org.junit.Assert;
import org.junit.Test;

public class FakeTimeProviderTest {
    @Test
    public void testTimeInMiliSeconds() throws Exception {
        FakeTimeProvider timeProvider = new FakeTimeProvider("13.09.2013 18:20:10");
        long datetimeInMilliseconds = timeProvider.getTimeInMilliseconds();
        long expected = 1379074810000l;
        Assert.assertEquals(expected, datetimeInMilliseconds);
    }

    @Test
    public void testGetDateInString() throws Exception {
        FakeTimeProvider timeProvider = new FakeTimeProvider();
        String expected = "Fri Sep 13 18:20:10 KGT 2013";

        Assert.assertEquals(expected, timeProvider.getDateInString());
    }

    @Test
    public void testGetFormattedDate() throws Exception {
        FakeTimeProvider timeProvider = new FakeTimeProvider("13.09.2013 18:20:20");
        String expected = "13.09.2013 18:20:20";

        Assert.assertEquals(expected, timeProvider.getFormattedDate());
    }
}
