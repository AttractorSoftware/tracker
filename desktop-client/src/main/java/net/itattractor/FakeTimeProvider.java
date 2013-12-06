package net.itattractor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FakeTimeProvider implements TimeProvider {
    public static final long MILISECONDS_IN_MINUTE = 60000l;
    private static final long MILISECONDS_IN_HOUR = 60 * MILISECONDS_IN_MINUTE;
    public static final String DEFAULT_DATE = "13.09.2013 18:20:10";
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
    private long currentTimeInMilis;
    private String dateTime;

    public FakeTimeProvider() {
        currentTimeInMilis = convertToMiliseconds(DEFAULT_DATE);
    }

    public FakeTimeProvider(String dateInString) {
        this.currentTimeInMilis = convertToMiliseconds(dateInString);
    }

    @Override
    public long getTimeInMilliseconds() {
        return currentTimeInMilis;
    }

    private long convertToMiliseconds(String dateInString) {
        try {
            dateFormat.parse(dateInString).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFormat.getCalendar().getTimeInMillis();
    }

    @Override
    public String getDateInString() {
        return new Date(currentTimeInMilis).toString();
    }

    public void forwardRewind(int i, int dimension) {
        long timeInMilis = 0;
        if (dimension == TimeProvider.MINUTES)
            timeInMilis = i * MILISECONDS_IN_MINUTE;
        if(dimension == TimeProvider.HOURS)
            timeInMilis = i * MILISECONDS_IN_HOUR;
        currentTimeInMilis += timeInMilis;
    }

    public void setDateTime(String dateTime) {
        this.currentTimeInMilis = convertToMiliseconds(dateTime);
    }
}
