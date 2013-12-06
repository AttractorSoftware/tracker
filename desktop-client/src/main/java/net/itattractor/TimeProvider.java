package net.itattractor;

public interface TimeProvider {
    int MINUTES = 0;
    int HOURS = 1;

    long getTimeInMilliseconds();
    String getDateInString();
}
