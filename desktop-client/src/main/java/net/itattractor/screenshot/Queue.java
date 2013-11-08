package net.itattractor.screenshot;

import java.util.HashMap;

public class Queue {

    private static HashMap<Integer, Screenshot> screenshotMap = new HashMap<Integer, Screenshot>();

    public static Screenshot getLatest() {
        return screenshotMap.get(screenshotMap.size()-1);
    }

    public static void append(Screenshot screenshot) {
      screenshotMap.put(screenshotMap.size(), screenshot);
    }

}
