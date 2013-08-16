import re
from trac.core import Component, implements, TracError
from trac.web import IRequestHandler


class ActiveTickets(Component):
    implements(IRequestHandler)

    def match_request(self, req):
        match = re.match(r'/tracker/tickets/([a-zA-Z0-9]+)$', req.path_info)
        if match:
            username = match.groups()
            req.args['username'] = username[0]
            trac_auth = req.incookie.get('trac_auth')
            if trac_auth:
                return True
            else:
                raise TracError('Unauthorized')

    def process_request(self, req):
        username = req.args.get('username')
        data = {'tickets': None}
        if req.method == 'GET':
            tickets = self._get_active_tickets(username)
            data['tickets'] = tickets
        return 'active_tickets.xml', data, "text/xml"

    def _get_active_tickets(self, username):
        tickets = []
        query = "SELECT id, summary FROM ticket WHERE status = 'accepted' AND owner = '" + username + "'"
        for id, summary in self.env.db_query(query):
            ticket = {
                'id': id,
                'summary': summary
            }
            tickets.append(ticket)
        return tickets
