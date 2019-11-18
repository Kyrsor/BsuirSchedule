package com.bsuir.schedule.data.models;

public class Teacher {
    public String name;
    public String photo_url;

    public Teacher(String name, String photo_url) {
        this.name = name;
        this.photo_url = photo_url;
    }

    public boolean equals(Object object) {
        if (super.equals(object))
            return true;
        if (object instanceof Teacher)
            return name.equals(((Teacher)object).name);

        return false;
    }
}
