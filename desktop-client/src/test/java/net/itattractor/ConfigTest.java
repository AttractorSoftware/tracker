package net.itattractor;

import org.junit.Assert;
import org.junit.Test;

public class ConfigTest {

    @Test
    public void testGetConfig() throws Exception {
        Config.init();
        String remindAgain = Config.getValue("remindAgainInSeconds");
        Assert.assertEquals("1000", remindAgain);
        int remindAgainInSeconds = Integer.parseInt(Config.getValue("remindAgainInSeconds"));
        Assert.assertEquals(1000, remindAgainInSeconds);
    }
}
