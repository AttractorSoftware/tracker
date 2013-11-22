from trac.db import Table, Column, Index, DatabaseManager

tables = [Table('work_log', key='id')[
        Column('id', type='int', auto_increment=True),
        Column('ticket_id', type='int'),
        Column('author'),
        Column('time', type='int'),
        Column('content', type='text')
    ]]



def do_upgrade(env, cursor):
    db_connector, _ = DatabaseManager(env)._get_connector()

    # Create tables
    cursor.execute("ALTER TABLE tracker_screenshots RENAME TO time_slot")
    for table in tables:
        for statement in db_connector.to_sql(table):
            cursor.execute(statement)
    #set database schema version
    cursor.execute("UPDATE system SET value=2 WHERE name='tracker'")

