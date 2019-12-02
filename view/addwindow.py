from tkinter import Tk, Entry, EventType, Listbox, END, Button, TclError

from utils.fileextractionhelper import FileExtractionHelper
from utils.webextractionhelper import WebExtractionHelper


class AddWindow(Tk):
    def __init__(self, parent_window):
        super().__init__()

        self.title("add schedule")

        self.entry = Entry(self)
        self.entry.grid(row=0, column=0, columnspan=2)
        self.entry.bind("<Key>", self.key_typed)

        self.listBox = Listbox(self)
        for i in WebExtractionHelper.get_hint(""):
            self.listBox.insert(END, i)
        self.listBox.grid(row=1, column=0, columnspan=2)

        self.cancel_button = Button(self, text='cancel', command=self.cancel)
        self.cancel_button.grid(row=2, column=0)

        self.selected_button = Button(self, text='select', command=self.selected)
        self.selected_button.grid(row=2, column=1)

        self.parent_window = parent_window

    def key_typed(self, event: EventType):
        self.listBox.delete(0, END)
        postfix = ''
        if event.char.isdigit() or event.char.isalpha():
            postfix = event.char
        hints = WebExtractionHelper.get_hint(self.entry.get() + postfix)
        for hint in hints:
            self.listBox.insert(END, hint)

    def cancel(self):
        self.destroy()

    def selected(self):
        try:
            name: str = self.listBox.get(self.listBox.curselection())
            FileExtractionHelper.put_to_file(name)
        except TclError:
            pass

        self.destroy()
        self.parent_window.rerender_list()
