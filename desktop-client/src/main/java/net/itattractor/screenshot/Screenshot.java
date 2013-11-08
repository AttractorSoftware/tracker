package net.itattractor.screenshot;

import java.io.File;
import java.util.Date;

public class Screenshot {

    private Integer id = null;
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

    public String getFileName() {
        return fileName;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public File getFileBody() {
        return fileBody;
    }

    public void setFileBody(File fileBody) {
        this.fileBody = fileBody;
    }
}
