package net.itattractor.time;

import java.text.*;
import java.util.ArrayList;
import java.util.Date;

public class Calculator {

    public static final int MILISECONDS_IN_SECOND = 1000;
    private static final int SECONDS_IN_MINUTE = 60;
    public static final int SECONDS_IN_10_MINUTES = 10 * SECONDS_IN_MINUTE;
    private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    public ArrayList<String> calculate(String from, String to) {
        ArrayList<String> timePoints = new ArrayList<String>();
        long fromInSeconds = convertToMiliseconds(from) / MILISECONDS_IN_SECOND;
        long toInSeconds = convertToMiliseconds(to) / MILISECONDS_IN_SECOND;

        for (long i = 0; i < (toInSeconds - fromInSeconds); i++) {
            long timeInSeconds = fromInSeconds + i;
            if (isTenthMinute(timeInSeconds)) {
                timePoints.add(convertToString(roundTo10Minute(timeInSeconds) * MILISECONDS_IN_SECOND));
                i += 60;
            }

        }
        return timePoints;
    }

    private long roundTo10Minute(long timeInSeconds) {
        return timeInSeconds - (timeInSeconds % SECONDS_IN_10_MINUTES);
    }

    private boolean isTenthMinute(long timeInSeconds) {
        return timeInSeconds % SECONDS_IN_10_MINUTES < 60;
    }

    private String convertToString(long time) {
        return dateFormat.format(new Date(time));
    }

    public long convertToMiliseconds(String dateInString) {
        try {
            dateFormat.parse(dateInString).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFormat.getCalendar().getTimeInMillis();
    }

}
