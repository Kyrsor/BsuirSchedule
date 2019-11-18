package com.bsuir.schedule.data.models;

import java.util.Arrays;
import java.util.List;

public class ClassObject {
    public enum ClassType { LECTURE, PRACTICE, LABORATORY };

    public String name;
    public Teacher teacher;
    public ClassType type;
    public ClassPosition[] classPositions;
    public String group;

    public ClassObject(String name, Teacher teacher, ClassType type, ClassPosition[] classPositions, String group) {
        this.name = name;
        this.classPositions = classPositions;
        this.type = type;
        this.teacher = teacher;
        this.group = group;
    }

    public boolean mergeableWith(ClassObject other) {
        return name.equals(other.name) && teacher.name.equals(other.teacher.name) &&
                type.equals(other.type) && group.equals(other.group);
    }

    public ClassObject mergeWith(ClassObject other) {
        if (!mergeableWith(other))
            return null;
        List<ClassPosition> positions = Arrays.asList(classPositions);
        for (ClassPosition position: other.classPositions)
            if (!positions.contains(position))
                positions.add(position);

        return new ClassObject(name, teacher, type, (ClassPosition[])positions.toArray(), group);
    }
}
