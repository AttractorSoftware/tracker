# -*- coding: utf-8 -*-

from trac.core import *


class ITrackerScreenshotsRenderer(Interface):
    def render_screenshots(self, req, id):
        pass

    def get_screenshots_view(self):
        pass


class TrackerApi(object):
    def _get_items(self, context, date, table, columns, distinct, where='', values=()):
        sql_values = {
            'distinct': ' DISTINCT ' if distinct else '',
            'columns': ', '.join(columns),
            'table': table,
            'where': (' WHERE ' + where) if where else '',
            'between': (' AND time BETWEEN  ' + str(date['from_date']) + ' AND ' + str(date['to_date'])) if where and date and
                                date['from_date'] and date['to_date'] else ''
        }

        sql = ("""SELECT %(distinct)s %(columns)s
                   FROM %(table)s
                   %(where)s
                   %(between)s """ % sql_values)

        #print 'sql -> ', sql

        context.cursor.execute(sql, values)

        items = []
        for row in context.cursor:
            row = dict(zip(columns, row))
            items.append(row)
        return items

    def get_screenshots(self, context, username, date):
        return self._get_items(
            context,
            date,
            'tracker_screenshots',
            ('id',
             'filename',
             'author',
             'path',
             'time',
             'mouse_event_count',
             'keyboard_event_count',
             'interval',
             'ticket_id'),
            False,
            'author = %s', (username,))

    def get_screenshot(self, context, id):
        screenshot = self._get_items(context, None, 'tracker_screenshots', ('id', 'filename', 'author', 'path', 'time'),
                                     False,
                                     'id = %s', (id,))

        return screenshot if screenshot else None

    def get_users(self, context):
        users = self._get_items(context, None, 'tracker_screenshots', ('author',), True, '')
        return users if users else None

    def get_screenshot_by_time(self):
        pass



    def _add_item(self):
        pass

    def add_screenshot(self):
        pass

