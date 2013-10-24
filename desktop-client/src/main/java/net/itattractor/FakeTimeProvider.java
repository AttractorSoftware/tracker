package net.itattractor;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FakeTimeProvider implements TimeProvider {
    private SimpleDateFormat date;
    private String dateInString;

    public FakeTimeProvider() {
        this.date = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
    }

    @Override
    public long getTimeInMilliseconds() {
        return date.getCalendar().getTimeInMillis();
    }

    @Override
    public String getDate() {
        String localDate = "";
        try {
            localDate = date.parse(dateInString).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return localDate;
    }

    public void setDateInString(String dateInString) {
        this.dateInString = dateInString;
    }
}
