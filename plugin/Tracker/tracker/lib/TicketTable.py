from trac.ticket.model import Ticket


class TicketTable(object):
    @classmethod
    def update_tracked_time_for_tickets(cls, env):

        with env.db_transaction as db:
            tickets = db("""SELECT id FROM ticket WHERE 1""")

        for ticket_id in tickets:
            ticket = Ticket(env, str(ticket_id[0]))
            calculate_tracked_time = TicketTable.calculate_tracked_time_for_ticket(env, str(ticket_id[0]))
            if str(ticket['tracked_time']) != str(calculate_tracked_time):
                ticket['tracked_time'] = str(calculate_tracked_time)
                ticket.save_changes(author="system", comment="Automaticaly update tracked_time by saved screenshots")

        return True

    @classmethod
    def calculate_tracked_time_for_ticket(cls, env, ticket_id):
        with env.db_transaction as db:
            screenshots = db("""
                SELECT * FROM tracker_screenshots
                WHERE  ticket_id = """ + str(ticket_id))

        return len(screenshots) * 10