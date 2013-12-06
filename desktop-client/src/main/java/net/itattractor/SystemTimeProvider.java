package net.itattractor;

import java.util.Date;

public class SystemTimeProvider implements TimeProvider {
    @Override
    public long getTimeInMilliseconds() {
        return System.currentTimeMillis();
    }

    @Override
    public String getDateInString() {
        return new Date().toString();
    }

}
