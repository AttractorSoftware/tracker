package net.itattractor;

import org.junit.Assert;
import org.junit.Test;

public class FakeTimeProviderTest {
    @Test
    public void timeInMiliSeconds() throws Exception {
        FakeTimeProvider timeProvider = new FakeTimeProvider("13.09.2013 18:20:10");
        long datetimeInMilliseconds = timeProvider.getTimeInMilliseconds();
        long expected = 1379074810000l;
        Assert.assertEquals(expected, datetimeInMilliseconds);
    }

    @Test
    public void forwardRewindingInMinutes() throws Exception {
        FakeTimeProvider timeProvider = new FakeTimeProvider("13.09.2013 18:20:10");
        timeProvider.forwardRewind(10, TimeProvider.MINUTES);
        long expected = 1379074810000l + 10 * 60000l;

        Assert.assertEquals(expected, timeProvider.getTimeInMilliseconds());
    }

    @Test
    public void forwardRewindingInHours() throws Exception {
        FakeTimeProvider timeProvider = new FakeTimeProvider("13.09.2013 18:20:10");
        timeProvider.forwardRewind(1, TimeProvider.HOURS);
        long expected = 1379074810000l + 60l * 60000l;

        Assert.assertEquals(expected, timeProvider.getTimeInMilliseconds());
    }

    @Test
    public void getDateInString() throws Exception {
        FakeTimeProvider timeProvider = new FakeTimeProvider();
        String expected = "Fri Sep 13 18:20:10 KGT 2013";

        Assert.assertEquals(expected, timeProvider.getDateInString());
    }

    @Test
    public void getDateInStringAfterRewindIn10Minutes() throws Exception {
        FakeTimeProvider timeProvider = new FakeTimeProvider();
        timeProvider.forwardRewind(10, TimeProvider.MINUTES);
        String expected = "Fri Sep 13 18:30:10 KGT 2013";

        Assert.assertEquals(expected, timeProvider.getDateInString());
    }

    @Test
    public void getDateInStringAfterRewindIn1Hour() throws Exception {
        FakeTimeProvider timeProvider = new FakeTimeProvider();
        timeProvider.forwardRewind(1, TimeProvider.HOURS);
        String expected = "Fri Sep 13 19:20:10 KGT 2013";

        Assert.assertEquals(expected, timeProvider.getDateInString());
    }

}
