package com.yingda.testpicker;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yingda.testpicker.listener.OnGradeSelectedListener;
import com.yingda.testpicker.listener.OnTimeSelectedListener;
import com.yingda.testpicker.model.Grade;
import com.yingda.testpicker.utils.PickerUtils;

public class MainActivity extends Activity {
    private TextView content;
    private Button threeWheelButton;
    private Button twoWheelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();
    }

    private void initView() {
        content = (TextView) findViewById(R.id.content);
        threeWheelButton = (Button) findViewById(R.id.three_wheel_picker);
        twoWheelButton = (Button) findViewById(R.id.two_wheel_picker);
    }

    private void setListener() {
        threeWheelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickerUtils.showTimePicker(MainActivity.this,
                        new OnTimeSelectedListener() {
                            @Override
                            public void onTimeSelect(String year, String mouth, String day) {
                                content.setText(year + mouth + day);
                            }
                        });
            }
        });

        twoWheelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickerUtils.showGradePicker(MainActivity.this,
                        new OnGradeSelectedListener() {
                            @Override
                            public void onGradeSelected(Grade grade) {
                                content.setText(grade.getStageName() + grade.getGradeName());
                            }
                        }, Grade.GRADE_1);
            }
        });

    }
}
