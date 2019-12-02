from typing import Dict, Any

from utils.fileextractionhelper import FileExtractionHelper


class Schedule:
    def __init__(self, name: str):
        self.data = FileExtractionHelper.get_schedule_from_file(name).get('schedules')

    def get_lines(self, subgroup: int, week_day: str, week_number: int):
        lines = []
        current_day_schedule = None
        for schedule_day in self.data:
            if schedule_day['weekDay'] == week_day:
                current_day_schedule = schedule_day['schedule']

        if current_day_schedule is None:
            return lines

        for lesson in current_day_schedule:
            if week_number in lesson['weekNumber']:
                if lesson['numSubgroup'] == 0 or lesson['numSubgroup'] == subgroup or subgroup == -1:
                    lines.append(self._lesson_representation_factory(lesson))

        return lines

    @staticmethod
    def _align(text: str, size: int) -> str:
        if len(text) < 3:
            text += '\t'
        if 3 <= len(text) <= 6:
            text += '\t'
        return text

    @classmethod
    def _lesson_representation_factory(cls, lesson):
        representation = ''
        representation += cls._align(f'{lesson["subject"]}', 11) + '\t\t'
        representation += f'{lesson["lessonType"]} '
        representation += f'{lesson["lessonTime"]} '
        representation += f'{lesson["numSubgroup"]} '
        representation += cls._align(f'{lesson["weekNumber"]}', 10) + ' '

        return representation
