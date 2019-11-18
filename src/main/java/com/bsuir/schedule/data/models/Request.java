package com.bsuir.schedule.data.models;

import java.util.Map;

public class Request {
    public static final String TEACHER = "teacher";
    public static final String TEACHER_NAME = "teacherName";
    public static final String CLASS_NAME = "className";
    public static final String CLASS_WEEK = "classWeek";
    public static final String CLASS_DAY = "classDay";
    public static final String CLASS_TIME = "classTime";
    public static final String SUBGROUP = "subgroup";
    public static final String TYPE = "type";
    public Map<String, Object> orBlock;
    public Map<String, Object> andBlock;

    Request(Map<String, Object> orBlock, Map<String, Object> andBlock) {
        this.orBlock = orBlock;
        this.andBlock = andBlock;
    }
}
