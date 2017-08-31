package com.yingda.testpicker.utils;

import android.app.Activity;

import com.yingda.picker.model.PickerModel;
import com.yingda.picker.picker.ThereWheelPicker;
import com.yingda.testpicker.listener.OnGradeSelectedListener;
import com.yingda.testpicker.model.Grade;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenyong.hu on 2017/8/30.
 */

final class PickerGradeUtils {
    private PickerGradeUtils() {
        //空方法
    }

    static void showGradePicker(final Activity activity, final OnGradeSelectedListener listener, Grade grade) {
        ThereWheelPicker gradePicker;
        if (grade != null && grade != Grade.UNKNOWN) {
            gradePicker = PickerUtils.getWheelPicker(getGradeList(), "请选择年级",
                    activity, grade.getStageName(), grade.getGradeName(), "");
        } else {
            gradePicker = PickerUtils.getWheelPicker(getGradeList(), "请选择年级", activity);
        }
        gradePicker.show();
        gradePicker.setOnWheelItemClickListener(new ThereWheelPicker.OnWheelItemClickListener() {

            @Override
            public void onSelected(PickerModel firstValue, PickerModel secondValue, PickerModel thirdValue) {
                listener.onGradeSelected(Grade.getEnum(secondValue.getName()));
            }

            @Override
            public void onCancel() {
                //空方法
            }
        });

    }

    private static List<PickerModel> getGradeList() {
        List<PickerModel> stageModels = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            stageModels.add(getStageModel(i));
        }
        return stageModels;
    }

    private static PickerModel getStageModel(int stageId) {
        PickerModel stageModel = new PickerModel();
        stageModel.setModels(getGradeModels(stageId));
        switch (stageId) {
            case 1:
                stageModel.setName("小学");
                break;
            case 2:
                stageModel.setName("初中");
                break;
            case 3:
                stageModel.setName("高中");
                break;
            default:
                break;
        }
        return stageModel;
    }

    private static List<PickerModel> getGradeModels(int stageId) {
        List<PickerModel> gradeModels = new ArrayList<>();
        PickerModel gradeModel;
        for (Grade grade : Grade.values()) {
            if (grade.getStageId() == stageId) {
                gradeModel = new PickerModel();
                gradeModel.setName(grade.getGradeName());
                gradeModels.add(gradeModel);
            }
        }
        return gradeModels;
    }
}

