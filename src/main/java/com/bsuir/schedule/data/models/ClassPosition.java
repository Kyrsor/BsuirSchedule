package com.bsuir.schedule.data.models;

public class ClassPosition {
    public int weekDay;
    public int weekNumber;
    public String time;
    public int subgroup; // 1 - first sub, 2 - second sub, 0 == all group
    public String auditory;

    public ClassPosition(int weekDay, int weekNumber, String time, int subgroup, String auditory) {
        this.weekDay = weekDay;
        this.weekNumber = weekNumber;
        this.time = time;
        this.subgroup = subgroup;
        this.auditory = auditory;
    }

    public boolean equals(ClassPosition other) {
        if (super.equals(other))
            return true;
        else return  weekDay == other.weekDay && weekNumber == other.weekNumber &&
                time.equals(other.time) && subgroup == other.subgroup;

    }
}
