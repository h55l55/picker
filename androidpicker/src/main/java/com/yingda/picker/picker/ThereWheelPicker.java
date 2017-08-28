package com.yingda.picker.picker;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.yingda.picker.R;
import com.yingda.picker.model.PickerModel;
import com.yingda.picker.wheel.OnWheelChangedListener;
import com.yingda.picker.wheel.WheelView;

import java.util.ArrayList;
import java.util.List;

public class ThereWheelPicker implements CanShow, OnWheelChangedListener {
    private static final int DEF_VISIBLE_ITEMS = 5;

    private int visibleItems = DEF_VISIBLE_ITEMS;
    private boolean isFirstCyclic = true;
    private boolean isSecondCyclic = true;
    private boolean isThirdCyclic = true;
    private String cancelTextColorStr = "#000000";
    private String confirmTextColorStr = "#0000FF";
    private String titleTextColorStr = "#ff3a3a3a";
    private boolean onlyShowTwoWheel = false;
    private String title = "";
    private PickerModel defaultThirdName = new PickerModel();
    private PickerModel defaultSecondName = new PickerModel();
    private PickerModel defaultFirstName = new PickerModel();
    private int backgroundPop = 0xaf000000;
    private Context context;
    private PopupWindow popwindow;
    private View popview;
    private WheelView firstWheelView;
    private WheelView secondWheelView;
    private WheelView thirdWheelView;
    private TextView enterBtn;
    private TextView titleTv;
    private TextView cancelTv;
    private LinearLayout linearLayout;

    protected List<PickerModel> allFirstDataList = new ArrayList<>();
    protected List<PickerModel> allSecondDataList = new ArrayList<>();
    protected PickerModel firstWheelName;
    protected PickerModel secondWheelName;
    protected PickerModel thirdWheelName = new PickerModel();
    private List<PickerModel> models;
    private OnWheelItemClickListener listener;

    private ThereWheelPicker(Builder builder) {
        initDefaultData(builder);
        findViewById();
        initView();
        //初始化数据
        initData(models);
        setListener();
    }

    private void initDefaultData(Builder builder) {
        this.visibleItems = builder.visibleItems;
        this.isFirstCyclic = builder.isFirstWheelCyclic;
        this.isThirdCyclic = builder.isThirdWheelCyclic;
        this.isSecondCyclic = builder.isSecondWheelCyclic;
        this.context = builder.mContext;
        this.title = builder.title;
        this.confirmTextColorStr = builder.confirmTextColorStr;
        this.cancelTextColorStr = builder.cancelTextColorStr;

        this.defaultThirdName = new PickerModel(builder.defaultThirdWheelData);
        this.defaultSecondName = new PickerModel(builder.defaultSecondWheelData);
        this.defaultFirstName = new PickerModel(builder.defaultFirstWheelData);

        this.onlyShowTwoWheel = builder.onlyShowTwoView;
        this.backgroundPop = builder.backgroundPop;
        this.titleTextColorStr = builder.titleTextColorStr;
        this.models = builder.models;
    }

    private void findViewById() {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        popview = layoutInflater.inflate(R.layout.pop_three_wheel_picker, null);

        firstWheelView = (WheelView) popview.findViewById(R.id.first_wheel_view);
        secondWheelView = (WheelView) popview.findViewById(R.id.second_wheel_view);
        thirdWheelView = (WheelView) popview.findViewById(R.id.third_wheel_view);
        enterBtn = (TextView) popview.findViewById(R.id.tv_confirm);
        titleTv = (TextView) popview.findViewById(R.id.tv_title);
        cancelTv = (TextView) popview.findViewById(R.id.tv_cancel);
        linearLayout = (LinearLayout) popview.findViewById(R.id.ll_title_background);
    }

    private void initView() {
        popwindow = new PopupWindow(popview, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        popwindow.setBackgroundDrawable(new ColorDrawable(backgroundPop));
        popwindow.setTouchable(true);
        popwindow.setOutsideTouchable(false);
        popwindow.setFocusable(true);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_picker_in);
        linearLayout.setAnimation(animation);

        // 设置标题
        if (!TextUtils.isEmpty(this.title)) {
            titleTv.setText(this.title);
        }
        //设置确认按钮文字颜色
        if (!TextUtils.isEmpty(this.titleTextColorStr)) {
            titleTv.setTextColor(Color.parseColor(this.titleTextColorStr));
        }
        //设置确认按钮文字颜色
        if (!TextUtils.isEmpty(this.confirmTextColorStr)) {
            enterBtn.setTextColor(Color.parseColor(this.confirmTextColorStr));
        }

        //设置取消按钮文字颜色
        if (!TextUtils.isEmpty(this.cancelTextColorStr)) {
            cancelTv.setTextColor(Color.parseColor(this.cancelTextColorStr));
        }

        //如果第二级数据下没有下级数据，则只显示两级数据
        if (models.get(0).getModels().get(0).getModels() == null) {
            onlyShowTwoWheel = true;
        }

        //只显示两级联动
        if (this.onlyShowTwoWheel) {
            thirdWheelView.setVisibility(View.GONE);
        } else {
            thirdWheelView.setVisibility(View.VISIBLE);
        }
        // 设置可见条目数量
        firstWheelView.setVisibleItems(visibleItems);
        secondWheelView.setVisibleItems(visibleItems);
        thirdWheelView.setVisibleItems(visibleItems);
        firstWheelView.setCyclic(isFirstCyclic);
        secondWheelView.setCyclic(isSecondCyclic);
        thirdWheelView.setCyclic(isThirdCyclic);
    }

    private void setListener() {
        firstWheelView.addChangingListener(this);
        secondWheelView.addChangingListener(this);
        thirdWheelView.addChangingListener(this);
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCancel();
                hide();
            }
        });
        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onlyShowTwoWheel) {
                    listener.onSelected(firstWheelName, secondWheelName, new PickerModel());
                } else {
                    listener.onSelected(firstWheelName, secondWheelName, thirdWheelName);
                }
                hide();
            }
        });
    }

    /**
     * 初始化数据
     */

    protected void initData(final List<PickerModel> firstModelList) {
        allFirstDataList = firstModelList;
        for (PickerModel firstPickerModel : allFirstDataList) {
            List<PickerModel> secondModelList = firstPickerModel.getModels();
            allSecondDataList.addAll(secondModelList);
        }
    }

    private void setUpData() {
        WheelPickerAdapter arrayWheelAdapter = new WheelPickerAdapter(allFirstDataList, context);
        firstWheelView.setViewAdapter(arrayWheelAdapter);
        chooseFristDefaultData();
        updateSecondWheelData();
        updateThirdWheelData();
    }

    private void chooseFristDefaultData() {
        int firstDefault = -1;
        if (!TextUtils.isEmpty(defaultFirstName.getName()) && allFirstDataList.size() > 0) {
            for (int i = 0; i < allFirstDataList.size(); i++) {
                if (allFirstDataList.get(i).getName().contains(defaultFirstName.getName())) {
                    firstDefault = i;
                    break;
                }
            }
        }
        //获取所设置的第一个字段的位置，直接定位到该位置
        if (-1 != firstDefault) {
            firstWheelView.setCurrentItem(firstDefault);
        }
    }

    /**
     * 根据一级数据，更新二级数据
     */
    private void updateSecondWheelData() {
        int pCurrent = firstWheelView.getCurrentItem();
        firstWheelName = allFirstDataList.get(pCurrent);
        List<PickerModel> secondList = getSecondList();

        WheelPickerAdapter wheelPickerAdapter = new WheelPickerAdapter(secondList, context);
        secondWheelView.setViewAdapter(wheelPickerAdapter);

        chooseSecondDefaultData(secondList);
        updateThirdWheelData();
    }

    private void chooseSecondDefaultData(List<PickerModel> secondList) {
        int secondWheelDefault = -1;
        if (!TextUtils.isEmpty(defaultSecondName.getName()) && secondList.size() > 0) {
            for (int i = 0; i < secondList.size(); i++) {
                if (secondList.get(i).getName().contains(defaultSecondName.getName())) {
                    secondWheelDefault = i;
                    break;
                }
            }
        }
        if (-1 != secondWheelDefault) {
            secondWheelView.setCurrentItem(secondWheelDefault);
        } else {
            secondWheelView.setCurrentItem(0);
        }
    }

    /**
     * 根据二级数据更新三级数据
     */
    private void updateThirdWheelData() {
        int pCurrent = secondWheelView.getCurrentItem();
        secondWheelName = getSecondName(pCurrent);
        if (!onlyShowTwoWheel) {
            List<PickerModel> thirdList = getThirdList();
            WheelPickerAdapter thirdWheelAdapter = new WheelPickerAdapter(thirdList, context);
            thirdWheelView.setViewAdapter(thirdWheelAdapter);

            chooseThirdDefaultData(thirdList);
        }
    }

    private void chooseThirdDefaultData(List<PickerModel> thirdList) {
        int thirdDefault = -1;
        if (!TextUtils.isEmpty(defaultThirdName.getName()) && thirdList.size() > 0) {
            for (int i = 0; i < thirdList.size(); i++) {
                if (thirdList.get(i).getName().contains(defaultThirdName.getName())) {
                    thirdDefault = i;
                    break;
                }
            }
        }
        if (-1 != thirdDefault) {
            thirdWheelView.setCurrentItem(thirdDefault);
            //获取默认值
            thirdWheelName = defaultThirdName;
        } else {
            thirdWheelView.setCurrentItem(0);
            //获取第一个数据
            thirdWheelName = getThirdName(0);

        }
    }

    private PickerModel getSecondName(int pCurrent) {
        for (PickerModel pickerModel : allFirstDataList) {
            if (pickerModel.getName().contains(firstWheelName.getName())) {
                return pickerModel.getModels().get(pCurrent);
            }
        }
        return new PickerModel();
    }

    private List<PickerModel> getSecondList() {
        for (PickerModel pickerModel : allFirstDataList) {
            if (pickerModel.getName().contains(firstWheelName.getName())) {
                return pickerModel.getModels();
            }
        }
        return new ArrayList<>();
    }

    private List<PickerModel> getThirdList() {
        for (PickerModel pickerModel : allSecondDataList) {
            if (pickerModel.getName().contains(secondWheelName.getName())) {
                return pickerModel.getModels();
            }
        }
        return new ArrayList<>();
    }

    private PickerModel getThirdName(int pCurrent) {
        for (PickerModel pickerModel : allSecondDataList) {
            if (pickerModel.getName().contains(secondWheelName.getName())) {
                return pickerModel.getModels().get(pCurrent);
            }
        }
        return new PickerModel();
    }

    @Override
    public void setType(int type) {
    }

    @Override
    public void show() {
        if (!isShow()) {
            setUpData();
            popwindow.showAtLocation(popview, Gravity.NO_GRAVITY, 0, 0);
        }
    }

    @Override
    public void hide() {
        if (isShow()) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_picker_out);
            linearLayout.setAnimation(animation);
            linearLayout.startAnimation(animation);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    popwindow.dismiss();

                }
            }, 200);
        }
    }

    @Override
    public boolean isShow() {
        return popwindow.isShowing();
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == firstWheelView) {
            updateSecondWheelData();
        } else if (wheel == secondWheelView) {
            updateThirdWheelData();
        } else if (wheel == thirdWheelView) {
            thirdWheelName = getThirdName(newValue);
        }
    }

    public interface OnWheelItemClickListener {
        void onSelected(PickerModel firstValue, PickerModel secondValue, PickerModel thirdValue);

        void onCancel();
    }

    public void setOnWheelItemClickListener(OnWheelItemClickListener listener) {
        this.listener = listener;
    }

    public static class Builder {
        private static final int DEF_VISIBLE_ITEMS = 5;
        private int visibleItems = DEF_VISIBLE_ITEMS;
        private boolean isFirstWheelCyclic = false;
        private boolean isSecondWheelCyclic = false;
        private boolean isThirdWheelCyclic = false;
        private Context mContext;

        /**
         * item间距
         */
        private int padding = 5;


        /**
         * Color.BLACK
         */
        private String cancelTextColorStr = "#ff9c9c9c";


        private String confirmTextColorStr = "#ffff8023";

        /**
         * 标题颜色
         */
        private String titleTextColorStr = "#ff3a3a3a";

        /**
         * 第一级默认值
         */
        private String defaultFirstWheelData = "";

        /**
         * 第二级默认值
         */
        private String defaultSecondWheelData = "";

        /**
         * 第三级默认值
         */
        private String defaultThirdWheelData = "";

        /**
         * 标题
         */
        private String title = "";

        /**
         * 两级联动
         */
        private boolean onlyShowTwoView = false;

        private List<PickerModel> models;

        /**
         * 设置popwindow的背景
         */
        private int backgroundPop = 0x11000000;

        public Builder(Context context, List<PickerModel> models) {
            this.mContext = context;
            this.models = models;
        }

        /**
         * 设置popwindow的背景
         *
         * @param backgroundPopColor
         * @return
         */
        public Builder backgroundPop(int backgroundPopColor) {
            this.backgroundPop = backgroundPopColor;
            return this;
        }

        /**
         * 设置标题背景颜色
         *
         * @param titleTextColorStr
         * @return
         */
        public Builder titleTextColor(String titleTextColorStr) {
            this.titleTextColorStr = titleTextColorStr;
            return this;
        }


        /**
         * 设置标题
         *
         * @param mtitle
         * @return
         */
        public Builder title(String mtitle) {
            this.title = mtitle;
            return this;
        }

        /**
         * 设置第一个默认值
         *
         * @param defaultFirstWheelData
         * @return
         */
        public Builder firstWheelData(String defaultFirstWheelData) {
            this.defaultFirstWheelData = defaultFirstWheelData;
            return this;
        }

        /**
         * 设置第二个默认值
         *
         * @param secondWheelData
         * @return
         */
        public Builder secondWheelData(String secondWheelData) {
            this.defaultSecondWheelData = secondWheelData;
            return this;
        }

        /**
         * 第一次默认地区显示，一般配合定位，使用
         *
         * @param thirdWheelData
         * @return
         */
        public Builder thirdWheelData(String thirdWheelData) {
            this.defaultThirdWheelData = thirdWheelData;
            return this;
        }

        /**
         * 确认按钮文字颜色
         *
         * @param color
         * @return
         */
        public Builder confirmTextColor(String color) {
            this.confirmTextColorStr = color;
            return this;
        }

        /**
         * 取消按钮文字颜色
         *
         * @param color
         * @return
         */
        public Builder cancelTextColor(String color) {
            this.cancelTextColorStr = color;
            return this;
        }

        /**
         * 滚轮显示的item个数
         *
         * @param visibleItems
         * @return
         */
        public Builder visibleItemsCount(int visibleItems) {
            this.visibleItems = visibleItems;
            return this;
        }

        /**
         * 第一级是否循环滚动
         *
         * @param isFirstWheelCyclic
         * @return
         */
        public Builder firstWheelCyclic(boolean isFirstWheelCyclic) {
            this.isFirstWheelCyclic = isFirstWheelCyclic;
            return this;
        }

        /**
         * 第二级是否循环滚动
         *
         * @param isSecondWheelCyclic
         * @return
         */
        public Builder secondWheelCyclic(boolean isSecondWheelCyclic) {
            this.isSecondWheelCyclic = isSecondWheelCyclic;
            return this;
        }

        /**
         * 第三季是否循环滚动
         *
         * @param isThirdWheelCyclic
         * @return
         */
        public Builder thirdWheelCyclic(boolean isThirdWheelCyclic) {
            this.isThirdWheelCyclic = isThirdWheelCyclic;
            return this;
        }

        /**
         * item间距
         *
         * @param itemPadding
         * @return
         */
        public Builder itemPadding(int itemPadding) {
            this.padding = itemPadding;
            return this;
        }

        public ThereWheelPicker build() {
            ThereWheelPicker thereWheelPicker = new ThereWheelPicker(this);
            return thereWheelPicker;
        }

    }
}
