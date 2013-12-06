package net.itattractor.screenshot;

import net.itattractor.TimeProvider;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

public class TimerTaskImpl extends TimerTask {
    private HashMap<Integer, Command> commandList;
    private TimeProvider timeProvider;

    public TimerTaskImpl() {
        this.commandList = new HashMap<Integer, Command>();
    }

    @Override
    public void run() {
        if(shouldExecute()) {
            System.out.println("TimerTaskImpl.run");
            for(Map.Entry<Integer,Command> entry : commandList.entrySet()){
                entry.getValue().execute();
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

}
