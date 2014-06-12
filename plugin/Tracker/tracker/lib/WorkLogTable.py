from datetime import datetime


class WorkLogTable(object):
    @classmethod
    def getByTicketId(cls, env, ticket_id):

        return_logs = []

        with env.db_transaction as db:
            work_logs = db("SELECT * FROM work_log WHERE ticket_id=" + str(ticket_id) + " ORDER BY time ASC")

        with env.db_transaction as db:
            slots = db("SELECT * FROM time_slot WHERE ticket_id="+ str(ticket_id) + " ORDER BY time ASC")

        for key, log in enumerate(work_logs):
            time_spent = 0
            append_log = {
                "id": log[0],
                "ticket_id": log[1],
                "author": log[2],
                "time": datetime.fromtimestamp(int(log[3])).strftime("%d.%m.%Y %H:%M:%S"),
                "comment": log[4]}

            for slot in slots:
                if log[0] == slot[9]:
                    time_spent += 10

            append_log["time_spent"] = time_spent
            return_logs.append(append_log)

        return return_logs







