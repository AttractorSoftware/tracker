package net.itattractor;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FakeTimeProvider implements TimeProvider {
    private SimpleDateFormat date;
    private String dateInString="13.09.2013 18:20:10";

    public FakeTimeProvider() {
        this.date = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
    }

    @Override
    public long getTimeInMilliseconds() {
        try {
            date.parse(dateInString).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
