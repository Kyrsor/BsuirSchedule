import json
from typing import List, Dict, Any

import requests


class WebExtractionHelper:
    GET_GROUPS_URL = 'https://journal.bsuir.by/api/v1/groups'
    GET_TEACHERS_URL = 'https://journal.bsuir.by/api/v1/employees'
    GET_GROUP_SCHEDULE = 'https://journal.bsuir.by/api/v1/studentGroup/schedule'
    GET_TEACHER_SCHEDULE = 'https://journal.bsuir.by/api/v1/portal/employeeSchedule'
    GET_CURRENT_WEEK = 'http://journal.bsuir.by/api/v1/week'

    cached_groups = []
    cached_teacher = []
    cached_teacher_ids = []

    @classmethod
    def get_hint(cls, name: str) -> List[str]:
        if len(cls.cached_groups) == 0:
            r = requests.get(cls.GET_GROUPS_URL)
            groups = json.loads(r.content.decode('utf8'))
            cls.cached_groups += map(lambda jsn: jsn['name'], groups)

        if len(cls.cached_teacher) == 0:
            r = requests.get(cls.GET_TEACHERS_URL)
            teachers = json.loads(r.content.decode('utf8'))
            cls.cached_teacher += map(
                lambda jsn: jsn['firstName'] + ' ' + jsn['middleName'] + ' ' + jsn['lastName'], teachers
            )
            cls.cached_teacher_ids = {
                jsn['firstName'] + ' ' + jsn['middleName'] + ' ' + jsn['lastName']: jsn['id'] for jsn in teachers
            }

        if name.isdigit():
            return sorted([*filter(lambda a: a.startswith(name), cls.cached_groups)])
        elif name == '':
            return sorted(cls.cached_teacher + cls.cached_groups)
        else:
            return sorted([*filter(lambda a: name.lower() in a.lower(), cls.cached_teacher)])

    @classmethod
    def get_group_schedule(cls, group: str) -> Dict[str, Any]:
        r = requests.get(cls.GET_GROUP_SCHEDULE, {'studentGroup': group})
        return json.loads(r.content.decode('utf8'))

    @classmethod
    def get_teacher_schedule(cls, name: str) -> Dict[str, Any]:
        cls.get_hint('')
        r = requests.get(cls.GET_TEACHER_SCHEDULE, {'employeeId': cls.cached_teacher_ids[name]})
        return json.loads(r.content.decode('utf8'))

    @classmethod
    def get_schedule_of_thing(cls, name: str) -> Dict[str, Any]:
        if name.isdigit():
            return cls.get_group_schedule(name)
        else:
            return cls.get_teacher_schedule(name)

    @classmethod
    def get_current_week(cls):
        r = requests.get(cls.GET_CURRENT_WEEK)

        return int(r.content)


if __name__ == '__main__':
    print(WebExtractionHelper.get_schedule_of_thing('750505'))
