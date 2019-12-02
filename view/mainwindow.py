from tkinter import Tk, Listbox, Button, END, TclError, EventType, OptionMenu, StringVar

from utils.fileextractionhelper import FileExtractionHelper
from utils.schedule import Schedule
from utils.date import Date
from view.addwindow import AddWindow


class MainWindow(Tk):
    def __init__(self):
        super().__init__()

        self.title("BsuirSchedule")
        self.list_schedule = Listbox(self, height=20, width=15)
        self.list_schedule.bind("<<ListboxSelect>>", self.rerender_schedule)
        self.list_schedule.grid(column=0, row=1, columnspan=3, rowspan=2)

        self.schedule = Listbox(self, height=20, width=50)
        self.schedule.grid(column=3, row=2, columnspan=5, rowspan=2)

        self.add_button = Button(self, text='add', command=self.add_buton_clicked)
        self.add_button.grid(column=0, row=3)

        self.remove_button = Button(self, text='remove', command=self.remove_button_clicked)
        self.remove_button.grid(column=1, row=3)

        self.refresh_button = Button(self, text='refresh', command=self.refresh_button_clicked)
        self.refresh_button.grid(column=2, row=3)

        self.next_button = Button(self, text='next', command=self.next_button_clicked)
        self.next_button.grid(column=4, row=1)

        self.prev_button = Button(self, text='prev', command=self.prev_button_clicked)
        self.prev_button.grid(column=3, row=1)

        self.string_var = StringVar(self)
        self.string_var.set("0")
        self.string_var.trace('w', self.option_menu_changed)
        self.option_menu = OptionMenu(self, self.string_var, '0', '1', '2')
        self.option_menu.grid(column=5, row=1)

        self.date = Date.today()
        self.rerender_list()

    def option_menu_changed(self, *args):
        self.rerender_schedule()

    def next_button_clicked(self):
        self.date = self.date.next()
        self.rerender_schedule()

    def prev_button_clicked(self):
        self.date = self.date.prev()
        self.rerender_schedule()

    def add_buton_clicked(self):
        add_window = AddWindow(self)
        add_window.mainloop()

    def refresh_button_clicked(self):
        saveds = FileExtractionHelper.get_list_saved()
        for saved in saveds:
            FileExtractionHelper.put_to_file(saved)

    def remove_button_clicked(self):
        try:
            name: str = self.list_schedule.get(self.list_schedule.curselection())
            FileExtractionHelper.remove_from_file(name)
        except TclError:
            pass

        self.rerender_list()

    def rerender_list(self):
        saveds = FileExtractionHelper.get_list_saved()
        self.list_schedule.delete(0, END)
        for saved in saveds:
            self.list_schedule.insert(END, saved)

    def rerender_schedule(self, event: EventType = None):
        self.schedule.delete(0, END)
        try:
            name: str = self.list_schedule.get(self.list_schedule.curselection())
            schedule = Schedule(name)
            subgroup = int(self.string_var.get())
            subgroup = -1 if subgroup == 0 else subgroup
            for lesson in schedule.get_lines(subgroup, self.date.week_day, self.date.week_number):
                self.schedule.insert(END, lesson)

        except TclError:
            pass
