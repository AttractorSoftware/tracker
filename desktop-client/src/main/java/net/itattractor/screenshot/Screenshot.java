package net.itattractor.screenshot;

import java.io.File;

public class Screenshot {

    private Integer ticketId = null;
    private Integer mouseEventCount = 0;
    private Integer keyboardEventCount = 0;
    private String fileName;
    private String time;
    private File fileBody;


    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public Integer getMouseEventCount() {
        return mouseEventCount;
    }

    public void setMouseEventCount(Integer mouseEventCount) {
        this.mouseEventCount = mouseEventCount;
    }

    public Integer getKeyboardEventCount() {
        return keyboardEventCount;
    }

    public void setKeyboardEventCount(Integer keyboardEventCount) {
        this.keyboardEventCount = keyboardEventCount;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public File getFileBody() {
        return fileBody;
    }

    public void setFileBody(File fileBody) {
        this.fileBody = fileBody;
    }

    @Override
    public String toString() {
        return String.format("%s : %s : %s : %s ", ticketId, mouseEventCount, keyboardEventCount, time);
    }
}
