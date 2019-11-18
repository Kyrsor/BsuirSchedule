package com.bsuir.schedule.data.models;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

abstract public class Schedule {

    enum OwnerType {GROUP, TEACHER};
    public ClassObject[] classObjects;
    public OwnerType ownerType;

    abstract public Object getOwner();

    Schedule(OwnerType ownerType, ClassObject[] classObjects) {
        this.ownerType = ownerType;
        this.classObjects = classObjects;
    }

    public LinkedList<ClassObject> selector(Request request) {
        LinkedList<ClassObject> result = new LinkedList<>(Arrays.asList(classObjects));
        for (ClassObject classObject : classObjects)
            if (andMatch(classObject, request.andBlock) && !result.contains(classObject) && !request.andBlock.isEmpty())
                result.add(classObject);
        for (ClassObject classObject: classObjects)
            if (orMatch(classObject, request.orBlock) && !result.contains(classObject) && !request.orBlock.isEmpty())
                result.add(classObject);

        return result;
    }

    private boolean andMatch(ClassObject classObject, Map<String, Object> andBlock) {
        for (String key : andBlock.keySet()) {
            boolean notMatches = true;
            switch (key) {
                case Request.TEACHER:
                    if (!andBlock.get(Request.TEACHER).equals(classObject.teacher))
                        return false;
                    break;
                case Request.TEACHER_NAME:
                    if (!andBlock.get(Request.TEACHER_NAME).equals(classObject.teacher.name))
                        return false;
                    break;
                case Request.CLASS_NAME:
                    if (!andBlock.get(Request.CLASS_NAME).equals(classObject.name))
                        return false;
                    break;
                case Request.CLASS_DAY:
                    for (ClassPosition position: classObject.classPositions)
                        if(andBlock.get(Request.CLASS_DAY).equals(position.weekDay))
                            notMatches = false;
                    if (notMatches)
                        return false;
                    break;
                case Request.CLASS_WEEK:
                    for (ClassPosition position: classObject.classPositions)
                        if(andBlock.get(Request.CLASS_WEEK).equals(position.weekNumber))
                            notMatches = false;
                    if (notMatches)
                        return false;
                    break;
                case Request.CLASS_TIME:
                    for (ClassPosition position: classObject.classPositions)
                        if(andBlock.get(Request.CLASS_TIME).equals(position.time))
                            notMatches = false;
                    if (notMatches)
                        return false;
                    break;
                case Request.SUBGROUP:
                    for (ClassPosition position: classObject.classPositions)
                        if(andBlock.get(Request.SUBGROUP).equals(position.subgroup))
                            notMatches = false;
                    if (notMatches)
                        return false;
                    break;
                case Request.TYPE:
                    if (!andBlock.get(Request.TYPE).equals(classObject.type))
                        return false;
                    break;
            }
        }
        return true;
    }

    private boolean orMatch(ClassObject classObject, Map<String, Object> orBlock) {
        for (String key : orBlock.keySet()) {
            switch (key) {
                case Request.TEACHER:
                    if (orBlock.get(Request.TEACHER).equals(classObject.teacher))
                        return true;
                    break;
                case Request.TEACHER_NAME:
                    if (orBlock.get(Request.TEACHER_NAME).equals(classObject.teacher.name))
                        return true;
                    break;
                case Request.CLASS_NAME:
                    if (orBlock.get(Request.CLASS_NAME).equals(classObject.name))
                        return true;
                    break;
                case Request.CLASS_DAY:
                    for (ClassPosition position: classObject.classPositions)
                        if(orBlock.get(Request.CLASS_DAY).equals(position.weekDay))
                            return true;
                    break;
                case Request.CLASS_WEEK:
                    for (ClassPosition position: classObject.classPositions)
                        if(orBlock.get(Request.CLASS_WEEK).equals(position.weekNumber))
                            return true;
                    break;
                case Request.CLASS_TIME:
                    for (ClassPosition position: classObject.classPositions)
                        if(orBlock.get(Request.CLASS_TIME).equals(position.time))
                            return true;
                    break;
                case Request.SUBGROUP:
                    for (ClassPosition position: classObject.classPositions)
                        if (orBlock.get(Request.SUBGROUP).equals(position.subgroup))
                            return true;
                case Request.TYPE:
                    if (!orBlock.get(Request.TYPE).equals(classObject.type))
                        return true;
                    break;
            }
        }
        return false;
    }
}
