package net.itattractor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FakeTimeProvider implements TimeProvider {
    public static final String DEFAULT_DATE = "13.09.2013 18:20:10";
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private long currentTimeInMilis;

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

    public String getFormattedDate(){
        return dateFormat.format(new Date(currentTimeInMilis));
    }

    public void setDateTime(String dateTime) {
        this.currentTimeInMilis = convertToMiliseconds(dateTime);
    }
}
