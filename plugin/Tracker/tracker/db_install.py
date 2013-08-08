from trac.core import *
from trac.env import IEnvironmentSetupParticipant

last_db_version = 1


class TrackerInit(Component):
    """
        Initialise database and environment for discussion component.
    """
    implements(IEnvironmentSetupParticipant)

    def environment_created(self):
        pass

    def environment_needs_upgrade(self, db):
        cursor = db.cursor()

        return self._get_db_version(cursor) != last_db_version

    def upgrade_environment(self, db):
        cursor = db.cursor()

        db_version = self._get_db_version(cursor)

        # Perform incremental upgrades.
        for I in range(db_version + 1, last_db_version + 1):
            script_name = 'db%i' % (I)
            module = __import__('tracker.db.%s' % (script_name),
                                globals(), locals(), ['do_upgrade'])
            module.do_upgrade(self.env, cursor)

        db.commit()

    def _get_db_version(self, cursor):
        try:
            sql = ("SELECT value "
                   "FROM system "
                   "WHERE name='tracker'")
            self.log.debug(sql)
            cursor.execute(sql)
            for row in cursor:
                return int(row[0])
            return 0
        except:
            return 0
