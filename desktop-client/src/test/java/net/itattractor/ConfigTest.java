package net.itattractor;

import org.junit.Assert;
import org.junit.Test;

public class ConfigTest {

    @Test
    public void testGetConfig() throws Exception {
        Config.init();
        String remindAgain = Config.getValue("remindAgain");
        Assert.assertEquals("1000", remindAgain);
    }
}
