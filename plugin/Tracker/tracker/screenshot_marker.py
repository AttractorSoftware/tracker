import calendar
import json
import re
from trac.core import *
from trac.web import IRequestHandler


class TrackerScreenshotMarkerModule(Component):
    implements(IRequestHandler)

    def match_request(self, req):
        return re.match(r'/thereScreenshot.*?$', req.path_info)

    def process_request(self, req):
        if req.method == "GET":
            thisMonth = req.args.get("thisMonth")
            thisYear = req.args.get("thisYear")
            user_name = req.args.get("userName")
            days = calendar.monthrange(int(thisYear), int(self._get_correct_month_name(thisMonth)))
            startDay, lastDay = days
            startDay = 1;
            data = {}
            for day in range(startDay, lastDay):
                if day<10:
                    date = str('0'+str(day)) + "/" + self._get_correct_month_name(thisMonth) + "/" + str(thisYear)
                else:
                    date = str(day) + "/" + self._get_correct_month_name(thisMonth) + "/" + str(thisYear)
                date = date.strip()

                if day == 0:
                    continue
                if self.at_this_date_there_are_screenshots(date, user_name):
                    data[day] = day
            jsondata = json.dumps(data)
            req.send_header('Content-Type', 'application/json')
            req.send_header('Content-length', str(len(jsondata)))
            req.end_headers()
            req.write(jsondata)

    def _get_correct_month_name(self, name):
        month = ""

        if ("January" == name):
            month = "01"
        elif ("February" == name):
            month = "02"
        elif ("March" == name):
            month = "03"
        elif ("April" == name):
            month = "04"
        elif ("May" == name):
            month = "05"
        elif ("June" == name):
            month = "06"
        elif ("July" == name):
            month = "07"
        elif ("August" == name):
            month = "08"
        elif ("September" == name):
            month = "09"
        elif ("October" == name):
            month = "10"
        elif ("November" == name):
            month = "11"
        elif ("December" == name):
            month = "12"

        return month

    def at_this_date_there_are_screenshots(self, date, user_name):

        if self.env.db_query(
                                                "SELECT strftime('%d/%m/%Y', datetime(time, 'unixepoch')) AS countScreenshots FROM tracker_screenshots WHERE countScreenshots = \"" + date + "\" AND author = \"" + user_name + "\""):
            return True
        else:
            return False
