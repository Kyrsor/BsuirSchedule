package com.bsuir.schedule.data.models;

public class GroupSchedule extends Schedule {
    public String getOwner() {
        try {
            return this.classObjects[0].group;
        } catch (NullPointerException exception) {
            return null;
        }
    }

    public GroupSchedule(ClassObject[] classObjects) {
        super(OwnerType.GROUP, classObjects);
    }
}
