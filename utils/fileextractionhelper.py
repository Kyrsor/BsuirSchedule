import json
import os
from typing import List, Union, Dict, Any

from utils.webextractionhelper import WebExtractionHelper


class FileExtractionHelper:
    LIST_FILE = 'list.json'

    @classmethod
    def put_to_file(cls, name: str):
        if not os.path.isfile('./' + cls.LIST_FILE):
            data = []
        else:
            with open('./' + cls.LIST_FILE, 'r') as file:
                data: List = json.load(file)

        if name not in data:
            data.append(name)

        with open('./' + cls.LIST_FILE, 'w') as file:
            json.dump(data, file)

        cls.schedule_to_file(name, WebExtractionHelper.get_schedule_of_thing(name))

    @classmethod
    def remove_from_file(cls, name: str):
        if not os.path.isfile('./' + cls.LIST_FILE):
            return

        with open('./' + cls.LIST_FILE, 'r') as file:
            data: List = json.load(file)

        if name in data:
            data.remove(name)

        with open('./' + cls.LIST_FILE, 'w') as file:
            json.dump(data, file)

        cls.delete_file(name)

    @classmethod
    def schedule_to_file(cls, name: str, content: Union[Dict, str]):
        if isinstance(content, dict):
            content = json.dumps(content)

        with open('./' + name + '.json', 'w') as file:
            file.write(content)

    @classmethod
    def delete_file(cls, name: str):
        os.remove('./' + name + '.json')

    @classmethod
    def get_list_saved(cls) -> List[str]:
        with open('./' + cls.LIST_FILE) as file:
            return json.load(file)

    @classmethod
    def get_schedule_from_file(cls, name: str) -> Dict[str, Any]:
        with open('./' + name + '.json') as file:
            return json.load(file)
