package net.itattractor;

import java.util.Date;

public class SystemTimeProvider implements TimeProvider {
    @Override
    public long getTimeInMilliseconds() {
        return System.currentTimeMillis();
    }

    @Override
    public String getDate() {
        return new Date().toString();
    }
}
