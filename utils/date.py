from utils.webextractionhelper import WebExtractionHelper
from datetime import datetime


class Date:
    WEEK_DAYS = ['Понедельник', 'Вторник', 'Среда', 'Четверг', 'Пятница', 'Суббота', 'Воскресенье']

    def __init__(self, week_day: str, week_number: int):
        if week_number not in [1, 2, 3, 4]:
            raise ValueError('week can be only in [1, 2, 3, 4]')
        if week_day not in self.WEEK_DAYS:
            raise ValueError(f'week day can be only in {self.WEEK_DAYS}')

        self.week_day = week_day
        self.week_number = week_number

    @classmethod
    def today(cls) -> 'Date':
        week_number = WebExtractionHelper.get_current_week()
        week_day = cls.WEEK_DAYS[datetime.now().weekday()]

        return Date(week_day, week_number)

    def next(self) -> 'Date':
        index = self.WEEK_DAYS.index(self.week_day) + 1
        week_number = self.week_number

        if index == len(self.WEEK_DAYS):
            index = 0
            week_number += 1
            if week_number == 5:
                week_number = 1

        return Date(self.WEEK_DAYS[index], week_number)

    def prev(self) -> 'Date':
        index = self.WEEK_DAYS.index(self.week_day) - 1
        week_number = self.week_number

        if index == - 1:
            index = len(self.WEEK_DAYS) - 1
            week_number -= 1
            if week_number == 0:
                week_number = 4

        return Date(self.WEEK_DAYS[index], week_number)

    def __str__(self):
        return "lol kek czeburiek"
