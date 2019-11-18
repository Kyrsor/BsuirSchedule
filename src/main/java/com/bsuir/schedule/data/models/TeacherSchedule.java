package com.bsuir.schedule.data.models;

public class TeacherSchedule extends Schedule {

    public Teacher getOwner() {
        try {
            return this.classObjects[0].teacher;
        } catch (NullPointerException exception) {
            return null;
        }
    }

    public TeacherSchedule(ClassObject[] classObjects) {
        super(OwnerType.TEACHER, classObjects);
    }
}
