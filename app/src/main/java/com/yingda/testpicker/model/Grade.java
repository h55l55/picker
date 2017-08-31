package com.yingda.testpicker.model;

/**
 * Created by wenyong.hu on 2017/8/30.
 */

public enum Grade {
    GRADE_1(1, "一年级", 1, Constants.STAGE_PRIMARY),

    GRADE_2(2, "二年级", 1, Constants.STAGE_PRIMARY),

    GRADE_3(3, "三年级", 1, Constants.STAGE_PRIMARY),

    GRADE_4(4, "四年级", 1, Constants.STAGE_PRIMARY),

    GRADE_5(5, "五年级", 1, Constants.STAGE_PRIMARY),

    GRADE_6(6, "六年级", 1, Constants.STAGE_PRIMARY),

    GRADE_7(7, "七年级", 2, Constants.STAGE_JUNIOR),

    GRADE_8(8, "八年级", 2, Constants.STAGE_JUNIOR),

    GRADE_9(9, "九年级", 2, Constants.STAGE_JUNIOR),

    SENIOR_1(10, "高一", 3, Constants.STAGE_SENIOR),

    SENIOR_2(11, "高二", 3, Constants.STAGE_SENIOR),

    SENIOR_3(12, "高三", 3, Constants.STAGE_SENIOR),

    UNKNOWN(-1, Constants.UNKOWN, -1, Constants.UNKOWN);

    private final int gradeId;
    private final String gradeName;
    private final int stageId;
    private final String stageName;

    Grade(String gradeName) {
        this.gradeId = -1;
        this.gradeName = gradeName;
        this.stageId = -1;
        this.stageName = Constants.UNKOWN;
    }

    Grade(int gradeId, String gradeName, int stageId, String stageName) {
        this.gradeId = gradeId;
        this.gradeName = gradeName;
        this.stageId = stageId;
        this.stageName = stageName;
    }

    @Override
    public String toString() {
        return "年级：" + gradeName + "，学段：" + stageName;
    }

    public static Grade getEnum(int gradeId) {
        Grade grade;
        switch (gradeId) {
            case 1:
                grade = GRADE_1;
                break;
            case 2:
                grade = GRADE_2;
                break;
            case 3:
                grade = GRADE_3;
                break;
            case 4:
                grade = GRADE_4;
                break;
            case 5:
                grade = GRADE_5;
                break;
            case 6:
                grade = GRADE_6;
                break;
            case 7:
                grade = GRADE_7;
                break;
            case 8:
                grade = GRADE_8;
                break;
            case 9:
                grade = GRADE_9;
                break;
            case 10:
                grade = SENIOR_1;
                break;
            case 11:
                grade = SENIOR_2;
                break;
            case 12:
                grade = SENIOR_3;
                break;
            default:
                grade = UNKNOWN;
                break;
        }
        return grade;
    }

    public static Grade getEnum(String gradeName) {
        for (Grade grade : Grade.values()) {
            if (grade.gradeName.equals(gradeName)) {
                return grade;
            }
        }
        return UNKNOWN;
    }

    public int getGradeId() {
        return gradeId;
    }

    public String getGradeName() {
        return gradeName;
    }

    public int getStageId() {
        return stageId;
    }

    public String getStageName() {
        return stageName;
    }

    private static class Constants {
        public static final String STAGE_PRIMARY = "小学";
        public static final String STAGE_JUNIOR = "初中";
        public static final String STAGE_SENIOR = "高中";
        public static final String UNKOWN = "未知";
    }
}

