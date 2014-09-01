from unittest import TestCase
import datetime

grouped_activity_feed = []

def group_screenshots(screenshots):
    del grouped_activity_feed[:]
    grouped_screenshots=[]
    for hour in range(0, 24):
        screenshots_by_hour = [screenshot for screenshot in screenshots if hour == get_hour_format(screenshot)]
        if screenshots_by_hour:
            screenshots_by_minutes = []
            minutes = 0
            while minutes <= 50:
                Screenshot = {}
                for screenshot in screenshots_by_hour:
                    if minutes == get_minutes_format(screenshot):
                        Screenshot = screenshot
                screenshots_by_minutes.append(Screenshot)
                minutes += 10
            grouped_activity_feed.append(group_activity_feed(screenshots_by_minutes))
            grouped_screenshots.append({"workingHour": hour, "screenshots": screenshots_by_minutes})
    return grouped_screenshots

def group_activity_feed(screenshots_row):
    grouped_activity_row = []
    index = 0
    colspan = 1

    while index < 6:
        next_index = index + 1
        if screenshots_row[index] != {} and len(screenshots_row[index]) > 1:
            if next_index < 6 and screenshots_row[next_index] != {} and \
               screenshots_row[index]['time_slot.ticket_id'] == screenshots_row[next_index]['time_slot.ticket_id'] and \
               screenshots_row[index]['content'] == screenshots_row[next_index]['content']:
                colspan += 1
            else:
                grouped_activity_row.append({
                    'ticket_id': screenshots_row[index]['time_slot.ticket_id'],
                    'content': screenshots_row[index]['content'],
                    'colspan': colspan
                })
                colspan = 1
        else:
            grouped_activity_row.append({'colspan': 0})
        index += 1
    return grouped_activity_row


class ScreenshotsMock(object):
    def __init__(self, time):
        super(ScreenshotsMock, self).__init__()
        self.time = []
        for unit in time:
            self.time.append({'time_slot.time': unit})

class WorkLogMock(object):
    def __init__(self, tickets_id, work_log):
        super(WorkLogMock, self).__init__()
        self.work_log = []
        for i in range(0, 6):
            if tickets_id[i] and work_log[i]:
                self.work_log.append({'time_slot.ticket_id': tickets_id[i], 'content': work_log[i]})
            else:
                self.work_log.append({})

def get_hour_format(screenshot):
    return int(datetime.datetime.fromtimestamp(screenshot["time_slot.time"]).strftime('%H'))

def get_minutes_format(screenshot):
    return int(datetime.datetime.fromtimestamp(screenshot["time_slot.time"]).strftime('%M'))

class ScreenshotGroupManagerTest(TestCase):

    def test_screenshot_hour_minutes_format(self):
        time = []
        time_12_10 = 1406707800
        time.append(time_12_10)
        screenshots = ScreenshotsMock(time)

        expected_hour = 12
        self.assertEqual(expected_hour, get_hour_format(screenshots.time[0]))
        expected_minutes = 10
        self.assertEqual(expected_minutes, get_minutes_format(screenshots.time[0]))

    def test_group_empty_list(self):
        screenshots = ScreenshotsMock([])
        self.assertEqual([], group_screenshots(screenshots.time))

    def test_group_one_screenshot(self):
        time = []
        time_12_10 = 1406707800
        time.append(time_12_10)
        screenshots = ScreenshotsMock(time)
        one_grouped_screenshot = group_screenshots(screenshots.time)

        expected_hour = 12
        working_hour = one_grouped_screenshot[0]['workingHour']
        self.assertEqual(expected_hour, working_hour)
        expected_minutes = 10
        time_in_screenshot_index_1 = one_grouped_screenshot[0]['screenshots'][1]
        self.assertEqual(expected_minutes, get_minutes_format(time_in_screenshot_index_1))

    def test_group_two_screenshots_created__in_the_same_hour(self):
        time_14_30 = 1406716200
        time_14_50 = 1406717400
        time = [time_14_30, time_14_50]
        screenshots = ScreenshotsMock(time)
        two_grouped_screenshots = group_screenshots(screenshots.time)

        expected_amount_working_hours = 1
        amount_working_hours = len(two_grouped_screenshots)
        self.assertEqual(expected_amount_working_hours,  amount_working_hours)

        expected_hour = 14
        working_hour = two_grouped_screenshots[0]['workingHour']
        self.assertEqual(expected_hour, working_hour)

        expected_minutes_1 = 30
        time_in_screenshot_index_3 = two_grouped_screenshots[0]['screenshots'][3]
        self.assertEqual(expected_minutes_1, get_minutes_format(time_in_screenshot_index_3))

        expected_minutes_2 = 50
        time_in_screenshot_index_5 = two_grouped_screenshots[0]['screenshots'][5]
        self.assertEqual(expected_minutes_2, get_minutes_format(time_in_screenshot_index_5))

    def test_group_two_screenshots_created_in_different_hours(self):
        time_14_30 = 1406716200
        time_15_50 = 1406721000
        time = [time_14_30, time_15_50]
        screenshots = ScreenshotsMock(time)
        two_grouped_screenshots = group_screenshots(screenshots.time)

        expected_amount_working_hours = 2
        amount_working_hours = len(two_grouped_screenshots)
        self.assertEqual(expected_amount_working_hours,  amount_working_hours)

        expected_hour_1 = 14
        working_hour = two_grouped_screenshots[0]['workingHour']
        self.assertEqual(expected_hour_1, working_hour)

        expected_minutes_1 = 30
        time_in_screenshot_index_3 = two_grouped_screenshots[0]['screenshots'][3]
        self.assertEqual(expected_minutes_1, get_minutes_format(time_in_screenshot_index_3))

        expected_hour_2 = 15
        working_hour = two_grouped_screenshots[1]['workingHour']
        self.assertEqual(expected_hour_2, working_hour)

        expected_minutes_2 = 50
        time_in_screenshot_index_5 = two_grouped_screenshots[1]['screenshots'][5]
        self.assertEqual(expected_minutes_2, get_minutes_format(time_in_screenshot_index_5))

    def test_group_activity_row_with_equal_logs(self):
        ticket_id_1 = ticket_id_2 = ticket_id_3 = ticket_id_4 = ticket_id_5 = ticket_id_6 = 1
        tickets_id = [ticket_id_1, ticket_id_2, ticket_id_3, ticket_id_4, ticket_id_5, ticket_id_6]

        comment_1 = comment_2 = comment_3 = comment_4 = comment_5 = comment_6 = "some task"
        work_log = [comment_1, comment_2, comment_3, comment_4, comment_5, comment_6]

        activity_row = WorkLogMock(tickets_id, work_log)
        grouped_activity_row = group_activity_feed(activity_row.work_log)

        expected_activity_feeds_in_row = 1
        activity_feeds_for_row_with_equal_logs = len(grouped_activity_row)
        self.assertEqual(expected_activity_feeds_in_row,  activity_feeds_for_row_with_equal_logs)

        expected_colspan = 6
        colspan_for_row_with_equal_logs = grouped_activity_row[0]['colspan']
        self.assertEqual(expected_colspan,  colspan_for_row_with_equal_logs)

    def test_group_activity_row_with_equal_logs_and_first_empty_slot(self):
        ticket_id_1 = None
        ticket_id_2 = ticket_id_3 = ticket_id_4 = ticket_id_5 = ticket_id_6 = 1
        tickets_id = [ticket_id_1, ticket_id_2, ticket_id_3, ticket_id_4, ticket_id_5, ticket_id_6]

        comment_1 = ""
        comment_2 = comment_3 = comment_4 = comment_5 = comment_6 = "some task"
        work_log = [comment_1, comment_2, comment_3, comment_4, comment_5, comment_6]

        activity_row = WorkLogMock(tickets_id, work_log)
        grouped_activity_row = group_activity_feed(activity_row.work_log)

        expected_activity_feeds_in_row = 2
        activity_feeds_for_row_with_equal_logs_including_first_empty_slot = len(grouped_activity_row)
        self.assertEqual(expected_activity_feeds_in_row,  activity_feeds_for_row_with_equal_logs_including_first_empty_slot)

        expected_colspan_1 = 0
        colspan_for_first_feed_with_empty_slot = grouped_activity_row[0]['colspan']
        self.assertEqual(expected_colspan_1,  colspan_for_first_feed_with_empty_slot)

        expected_colspan_2 = 5
        colspan_for_second_feed_with_five_equal_logs = grouped_activity_row[1]['colspan']
        self.assertEqual(expected_colspan_2,  colspan_for_second_feed_with_five_equal_logs)

    def test_group_activity_row_with_equal_logs_and_third_empty_slot(self):
        ticket_id_1 = ticket_id_2 = 1
        ticket_id_3 = None
        ticket_id_4 = ticket_id_5 = ticket_id_6 = 1
        tickets_id = [ticket_id_1, ticket_id_2, ticket_id_3, ticket_id_4, ticket_id_5, ticket_id_6]

        comment_1 = comment_2 = "some task"
        comment_3 = ""
        comment_4 = comment_5 = comment_6 = "some task"
        work_log = [comment_1, comment_2, comment_3, comment_4, comment_5, comment_6]

        activity_row = WorkLogMock(tickets_id, work_log)
        grouped_activity_row = group_activity_feed(activity_row.work_log)

        expected_activity_feeds_in_row = 3
        activity_feeds_for_row_with_equal_logs_including_third_empty_slot = len(grouped_activity_row)
        self.assertEqual(expected_activity_feeds_in_row,  activity_feeds_for_row_with_equal_logs_including_third_empty_slot)

        expected_colspan_1 = 2
        colspan_for_first_feed_with_two_equal_logs = grouped_activity_row[0]['colspan']
        self.assertEqual(expected_colspan_1,  colspan_for_first_feed_with_two_equal_logs)

        expected_colspan_2 = 0
        colspan_for_second_feed_with_empty_slot = grouped_activity_row[1]['colspan']
        self.assertEqual(expected_colspan_2,  colspan_for_second_feed_with_empty_slot)

        expected_colspan_3 = 3
        colspan_for_third_feed_with_three_equal_logs = grouped_activity_row[2]['colspan']
        self.assertEqual(expected_colspan_3,  colspan_for_third_feed_with_three_equal_logs)

    def test_group_activity_row_with_different_logs(self):
        ticket_id_1 = 1
        ticket_id_2 = 1
        ticket_id_3 = 2
        ticket_id_4 = 3
        ticket_id_5 = 4
        ticket_id_6 = 4
        tickets_id = [ticket_id_1, ticket_id_2, ticket_id_3, ticket_id_4, ticket_id_5, ticket_id_6]

        comment_1 = "task 1"
        comment_2 = "task 2"
        comment_3 = "some task"
        comment_4 = "some task"
        comment_5 = "task 1"
        comment_6 = "task 2"
        work_log = [comment_1, comment_2, comment_3, comment_4, comment_5, comment_6]

        activity_row = WorkLogMock(tickets_id, work_log)
        grouped_activity_row = group_activity_feed(activity_row.work_log)

        expected_activity_feeds_in_row = 6
        activity_feeds_for_row_with_with_different_logs = len(grouped_activity_row)
        self.assertEqual(expected_activity_feeds_in_row,  activity_feeds_for_row_with_with_different_logs)

        expected_colspan_1 = 1
        colspan_for_first_feed = grouped_activity_row[0]['colspan']
        self.assertEqual(expected_colspan_1,  colspan_for_first_feed)

        expected_colspan_2 = 1
        colspan_for_second_feed = grouped_activity_row[1]['colspan']
        self.assertEqual(expected_colspan_2,  colspan_for_second_feed)

        expected_colspan_3 = 1
        colspan_for_third_feed = grouped_activity_row[2]['colspan']
        self.assertEqual(expected_colspan_3,  colspan_for_third_feed)

        expected_colspan_4 = 1
        colspan_for_fourth_feed = grouped_activity_row[3]['colspan']
        self.assertEqual(expected_colspan_4,  colspan_for_fourth_feed)

        expected_colspan_5 = 1
        colspan_for_fifth_feed = grouped_activity_row[4]['colspan']
        self.assertEqual(expected_colspan_5,  colspan_for_fifth_feed)

        expected_colspan_6 = 1
        colspan_for_sixth_feed = grouped_activity_row[5]['colspan']
        self.assertEqual(expected_colspan_6,  colspan_for_sixth_feed)