package net.itattractor;

public class Ticket {
    private String ticketSummary;
    private int ticketId;

    public Ticket(int ticketId, String ticketSummary) {
        this.ticketId = ticketId;
        this.ticketSummary = ticketSummary;
    }

    public Ticket(String ticketDefinition) {
        this.ticketId = parseTicketId(ticketDefinition);
        this.ticketSummary = parserTicketSummary(ticketDefinition);
    }

    private int parseTicketId(String ticketDefinition) {
        return Integer.parseInt(ticketDefinition.substring(1, ticketDefinition.indexOf(":")));
    }

    private String parserTicketSummary(String ticketDefinition) {
        return ticketDefinition.substring(ticketDefinition.indexOf(':') + 1, ticketDefinition.length());
    }

    public String getTicketSummary() {
        return ticketSummary;
    }

    public int getTicketId() {
        return ticketId;
    }

    @Override
    public String toString() {
        return "#" + ticketId + ": " + ticketSummary;
    }
}
