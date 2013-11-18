package net.itattractor.screenshot;

import net.itattractor.Config;
import net.itattractor.TimeProvider;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Timer extends Thread {

    private HashMap<Integer, Command> commandList;
    private Integer checkCreatePeriod = Integer.parseInt(Config.getValue("screenshotCheckCreatePeriod"));
    private TimeProvider timeProvider;
    private Boolean isFinished = false;

    public Timer() {
        this.commandList = new HashMap<Integer, Command>();
    }

    @Override
    public void run() {
        while (!getFinished()) {
            try {
                Thread.sleep(this.checkCreatePeriod);
                if(shouldExecute()) {
                    System.out.println("Timer.run");
                    for(Map.Entry<Integer,Command> entry : commandList.entrySet()){
                        entry.getValue().execute();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean shouldExecute() {
        Calendar sentDate = Calendar.getInstance();
        sentDate.setTimeInMillis(timeProvider.getTimeInMilliseconds());
        int minutes = sentDate.get(Calendar.MINUTE);
        return ((minutes % 10) == 0);
    }

    public void addCommand(Integer key, Command command) {
        commandList.put(key, command);
    }

    public void setTimeProvider(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }

    public TimeProvider getTimeProvider() {
        return timeProvider;
    }

    public void setCommandList(HashMap<Integer, Command> commandList) {
        this.commandList = commandList;
    }

    public HashMap<Integer, Command> getCommandList() {
        return commandList;
    }

    public Boolean getFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }
}
