import re
import time

from trac.core import Component, implements
from trac.web import IRequestHandler
from trac.web.chrome import ITemplateProvider

class TrackerTimelineModule(Component):

    implements(IRequestHandler, ITemplateProvider)

    def get_htdocs_dirs(self):
        pass

    def get_templates_dirs(self):
        pass

    def match_request(self, req):
        match = re.match(r'/timeline(?:/(\D+))?(?:/(\d+))?$', req.path_info)
        if match:
            username, s_id = match.groups()
            req.args['username'] = username
            return True
        return match

    def process_request(self, req):
        if req.args.get('username'):
            username = req.args.get('username')
            allWorklogs = self._get_users_allWorklogs(username)
            ticketId,tickets = self._get_tickets(allWorklogs)
            timeslots=self._get_timeslots(username)
            worklogs=self._get_worklogs(allWorklogs,timeslots)
        req.data = {
                'tickets': tickets,
                'worklogs':worklogs,
                'username': username
            }

        return "tracker_timeline.html", req.data, None

    def _get_users_allWorklogs(self, username):
        allWorklogs = []
        query = "SELECT work_log.id, ticket.id as ticketId, owner, summary, work_log.time, content " \
                "FROM ticket " \
                "INNER JOIN work_log ON ticket.id = ticket_id AND owner = author " \
                "WHERE owner = '" + username + "'"

        for id, ticketId, owner, summary, time, content in self.env.db_query(query):
            comment = {
                'id': id,
                'ticketId': ticketId,
                'owner': owner,
                'summary': summary,
                'time': time,
                'content': content
            }
            allWorklogs.append(comment)
        return allWorklogs

    def _get_worklogs(self,allWorklogs,timeslots):
        number=0
        worklogs=[]
        for worklog in allWorklogs:
            if (allWorklogs.index(worklog)+1<len(allWorklogs)):
                start_time=worklog['time']
                end_time=allWorklogs[allWorklogs.index(worklog)+1]['time']
            else:
                start_time=worklog['time']
                end_time=timeslots[-1]['time']
            for slot in timeslots:
                if slot['time']>=start_time and slot['time']<end_time:
                    number+=1
            task = {
            'id': worklog['ticketId'],
            'content': worklog['content'],
            'time_spent': number*10
            }
            worklogs.append(task)
            number=0

        return worklogs


    def _get_tickets(self,allWorklogs):
        ticketId=[]
        tickets=[]
        for ticket in allWorklogs:
            if ticket['ticketId'] not in ticketId:
                ticketId.append(ticket['ticketId'])
                task = {
                'id': ticket['ticketId'],
                'name': ticket['summary']
                }
                tickets.append(task)
        tickets.sort()
        return ticketId,tickets


    def _get_timeslots(self,username):
        timeslots = []
        query = "SELECT author,time " \
                "FROM time_slot " \
                "WHERE author = '" + username + "'"

        for author, time in self.env.db_query(query):
            slot = {
                'author':author,
                'time': time,
            }
            timeslots.append(slot)
        return timeslots


