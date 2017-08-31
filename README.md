# picker
#### 使用方法：

1. 构建数据

   使用的是一个自定义类，支持传入泛型。基本数据类如下：

   ```java
   public class PickerModel<T> {
       private String name = "";
       private List<PickerModel> models;
       private T t;
   }
   ```

   以时间选择器为例，构建数据的方法如下：

   ````java
    private static List<PickerModel> getTimeModelList() {
           List<PickerModel> models = new ArrayList<>();
           long nowTime = System.currentTimeMillis();
           String year = getTimeByPattern(nowTime, "yyyy");
           int nowYear = Integer.valueOf(year);
           PickerModel yearModel;
           for (int i = 1950; i <= nowYear; i++) {
               yearModel = new PickerModel();
               yearModel.setName(i + "年");
               yearModel.setModels(getMouthModel(i));
               models.add(yearModel);
           }
           return models;
       }

       public static List<PickerModel> getMouthModel(int year) {
           List<PickerModel> mouthModels = new ArrayList<>();
           PickerModel mouthModel;
           for (int i = 1; i <= 12; i++) {
               mouthModel = new PickerModel();
               mouthModel.setName(i + "月");
               mouthModel.setModels(getDayModels(year, i));
               mouthModels.add(mouthModel);
           }
           return mouthModels;
       }

       private static List<PickerModel> getDayModels(int year, int mouth) {
           List<PickerModel> dayModels = new ArrayList<>();
           PickerModel dayModel;
           int dayOfMouth = getDayOfMouth(year, mouth);
           for (int i = 1; i <= dayOfMouth; i++) {
               dayModel = new PickerModel();
               dayModel.setName(i + "日");
               dayModels.add(dayModel);
           }
           return dayModels;
       }

       private static int getDayOfMouth(int year, int mouth) {
           int days;
           switch (mouth) {
               case 1:
               case 3:
               case 5:
               case 7:
               case 8:
               case 10:
               case 12:
                   days = 31;
                   break;
               case 2:
                   if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                       days = 29;
                   } else {
                       days = 28;
                   }
                   break;
               default:
                   days = 30;
                   break;
           }
           return days;
       }

       public static String getTimeByPattern(long timeLong, String pattern) {
           Date date = new Date(timeLong);
           SimpleDateFormat ft = new SimpleDateFormat(pattern);
           return ft.format(date);
       }
   ````

   基本原理就是有一个总的数据集合，里面用来存储所有的数据。其中每个数据都包含一个list，用来表明下一级数据。当这个list为空时候，会默认为下级数据为空。所以使用两级联动选择时候只需要包含两级数据，使用三级联动选择时候需要包含三级数据。控件会自动根据当前是几级数据来动态调整显示。

2. 显示选择器

   采用链式调用的方法，可以设置默认值、标题、字号等基本属性。具体代码如下：

   ```java
       static ThereWheelPicker getWheelPicker(List<PickerModel> pickerModels,
                                              String title, Activity activity, String firstName,
                                              String secondName, String thirdName) {
           ThereWheelPicker wheelPicker = new ThereWheelPicker.Builder(activity, pickerModels)
                   .firstWheelCyclic(false)
                   .secondWheelCyclic(false)
                   .thirdWheelCyclic(false)//对应的滚轮是否可以循环滚动
                   .visibleItemsCount(5)//默认可见的item数量
                   .itemPadding(40)//每个item的间距
                   .title(title)//标题
                   .firstWheelData(firstName)
                   .secondWheelData(secondName)
                   .thirdWheelData(thirdName)//三个滚轮的默认值
                   .build();
           return wheelPicker;
       }
   ```

   效果图如下：![时间选择器](https://github.com/h55l55/picker/blob/master/device-2017-08-30-180558.png)
