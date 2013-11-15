from trac.core import *
from trac.admin import IAdminCommandProvider
from trac.util.text import print_table, printout
from tracker.lib.TicketTable import TicketTable


class UpdateTrackedTimeAdminCommandProvider(Component):

    implements(IAdminCommandProvider)

    # IAdminCommandProvider methods

    def get_admin_commands(self):
        yield ('update-tracked-time', '',
               'Update tracked time',
               None, self._do_update)

    def _do_update(self):
        print('Update tracked time for tickets is starting now. Please wait...')
        TicketTable.update_tracked_time_for_tickets(self.env)
        print('Update complete.')