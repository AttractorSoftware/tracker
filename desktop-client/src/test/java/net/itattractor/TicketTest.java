package net.itattractor;

import org.junit.Assert;
import org.junit.Test;

public class TicketTest {
    @Test
    public void testGetTicketSummaryAndTicketIdWithFirstConstructor() throws Exception {
        String expectedTicketSummary = "test";
        int expectedTicketId = 1;
        Ticket ticket = new Ticket(expectedTicketId, expectedTicketSummary);
        String actualTicketSummary = ticket.getTicketSummary();
        int actualTicketId = ticket.getTicketId();
        Assert.assertEquals(expectedTicketId, actualTicketId);
        Assert.assertEquals(expectedTicketSummary, actualTicketSummary);
    }

    @Test
    public void testGetTicketSummaryAndTicketIdWithSecondConstructor() throws Exception {
        String expectedTicketSummary = "test";
        int expectedTicketId = 1;
        Ticket ticket = new Ticket("#1: test");
        String actualTicketSummary = ticket.getTicketSummary();
        int actualTicketId = ticket.getTicketId();
        Assert.assertEquals(expectedTicketId, actualTicketId);
        Assert.assertEquals(expectedTicketSummary, actualTicketSummary);
    }

    @Test
    public void testToString() throws Exception {
        String expectedTicketDefinition = "#1: test";
        Ticket ticket = new Ticket(1, "test");
        String actualTicketDefinition = ticket.toString();
        Assert.assertEquals(expectedTicketDefinition, actualTicketDefinition);
    }
}
