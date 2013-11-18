package net.itattractor;

public class TaskReaderProxyImpl implements TaskReader {
    private TaskReaderImpl taskReaderImpl;

    @Override
    public Ticket[] getTickets() {
        Ticket[] tickets = new Ticket[0];
        if(ConnectionProvider.getInstance() != null) {
            taskReaderImpl = new TaskReaderImpl();
            tickets = taskReaderImpl.getTickets();
        }
        return tickets;
    }
}
