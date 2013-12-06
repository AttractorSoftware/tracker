package net.itattractor;

import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class EventCounterTest {
    @Test
    public void testEvenCounter() throws Exception {
        EventCounter eventCounter = new EventCounter();
        eventCounter.init();
        int keyboardCount = 10;
        int mouseCount = 10;
        makeEvents(keyboardCount, mouseCount);

        Assert.assertEquals(keyboardCount, eventCounter.getKeyCounter());
        Assert.assertEquals(mouseCount, eventCounter.getMouseCounter());

    }

    @Test
    public void testEventCounterReset() throws Exception {
        EventCounter eventCounter = new EventCounter();
        eventCounter.init();
        int keyboardCount = 10;
        int mouseCount = 10;
        makeEvents(keyboardCount, mouseCount);
        eventCounter.reset();

        Assert.assertEquals(0, eventCounter.getKeyCounter());
        Assert.assertEquals(0, eventCounter.getMouseCounter());

    }

    @Test
    public void testEventCounterActivityAfterReset() throws Exception {
        EventCounter eventCounter = new EventCounter();
        eventCounter.init();
        int keyboardCount = 10;
        int mouseCount = 10;
        makeEvents(keyboardCount, mouseCount);
        eventCounter.reset();
        makeEvents(keyboardCount, mouseCount);

        Assert.assertEquals(keyboardCount, eventCounter.getKeyCounter());
        Assert.assertEquals(mouseCount, eventCounter.getMouseCounter());

    }

    private void makeEvents(int keyboardCount, int mouseCount) throws AWTException {
        Robot robot = new Robot();

        for (int i = 0; i < mouseCount; i++) {
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
        }

        for (int j = 0; j < keyboardCount; j++) {

            robot.keyPress(KeyEvent.SHIFT_MASK);
            robot.keyRelease(KeyEvent.SHIFT_MASK);

        }
    }
}
