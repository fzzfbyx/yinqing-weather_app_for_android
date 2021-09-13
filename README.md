# AndroidStudio新手开发：天气app（百度地图api+和风天气api+城市查询+折线展示）

## 1、内容简介
 &nbsp;  &nbsp; &nbsp;  &nbsp;学校b测，碰巧选到了app开发，之前只有一些网站开发经历，第一次接触安卓端，从配环境查攻略，到运行他人demo一步步理解与修改，到最后实现定位天气查询、折线图展示等功能，手机上安装了自己的app后顿时成就感十足，写下此文记录此次开发的要点与攻略，同时为其他新手开发者排一些bug。界面展示如下：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200625000934938.png)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200625001023728.png)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200625001216682.png)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200625033538415.png)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200625033647400.png)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200625020743569.png)



## 2、环境配置
首先下载安装Android Studio并进行虚拟机配置，推荐参考链接如下：[AS配置](https://blog.csdn.net/qq_41976613/article/details/91432304?utm_medium=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase)

## 3、导入他人demo
导入他人AS demo时往往会出现许多令人头疼的bug，在这里提醒大家一定要替换demo本身的一些配置信息后再用AS open an exisiting android studio project，推荐参考链接如下：[导入他人demo](https://blog.csdn.net/weixin_39220472/article/details/79947295?utm_medium=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase)

另外，如果出现长时间sync不成功很可能是因为从google下载被墙的原因，在源目录下的build.gradle文件中进行阿里云镜像替换
```
 //       google()
    //    jcenter()
        maven { url 'https://maven.aliyun.com/repository/google' }
        maven { url 'https://maven.aliyun.com/repository/jcenter' }
        maven { url 'http://maven.aliyun.com/nexus/content/groups/public' }
```

## 4、AS项目分析
成功运行他人demo之后重要的事情就是分析项目的架构了，相关资料链接：[项目目录分析](https://blog.csdn.net/wulex/article/details/84935929)

在这里我再次着重强调几个文件：
1.app中的build.gradle中引入了所有的包，如果报错缺少所需的包或版本不匹配时需要在文件中的dependencies中进行修改
```
  dependencies {

        implementation fileTree(dir: 'libs', include: ['*.jar'])

//    implementation 'com.android.support:appcompat-v7:29.+'

        implementation 'com.android.support.constraint:constraint-layout:1.1.3'

        testImplementation 'junit:junit:4.12'

        androidTestImplementation 'com.android.support.test:runner:1.0.2'

        androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'

        implementation 'com.github.PhilJay:MPAndroidChart:v3.0.1'

//    implementation 'com.github.PhilJay:MPAndroidChart:v3.1.0'

    }

```
2.运行项目后生成的app-debug.apk位于app.build.outputs.apk文件夹中。

3.所有的源码都位于app.main.src中，其中工作框架在java.activity中，页面布局在res.layout中。这两个文件夹为项目中的重中之重，涵盖了项目的运行流程以及页面设计，需要仔细理解。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200625013456437.png)![在这里插入图片描述](https://img-blog.csdnimg.cn/2020062501352642.png)
4.配置文件AndroidManife.XML中定义了app的名称和图标可进行修改。
```
<application
    android:name=".MainApplication"
    android:allowBackup="true"
    android:icon="@mipmap/ic_launcher"
    android:label="阴晴"
    android:roundIcon="@mipmap/ic_launcher"
    android:supportsRtl="true"
    android:theme="@style/AppTheme">
```
## 5、天气项目流程
终于进入到了正题，本次开发的天气app名为“阴晴”，打开app，系统首先显示默认城市（西安）的天气信息，之后用户可以选择进行定位查询或自由选择城市：定位查询通过申请定位权限，调用百度地图api定位当前城市地图信息，并通过缓存定位城市至sharepreference，调用和风天气api生成当前城市的天气信息；自由选择城市通过数据表查询，调用和风天气api生成查询城市对应天气信息并显示。之后将此次天气信息保存至缓存sharepreference，下次打开app时显示缓存中城市的天气信息。页面显示上通过保存天气查询数据至sharedpreference，通过建立图表可选择折线图显示近七天最高最低的温度变化情况。流程图设计如下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200625025809764.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQyNjc5NTcz,size_16,color_FFFFFF,t_70#pic_center)
## 6、数据探寻
1.天气信息获取
 此次天气及空气质量等信息获取我们选用和风天气提供的免费api，和风天气每天有提供4000次免费的基础天气查询，用来做开发测试足够使用，且其提供的空气质量等信息较为全面，而且和风天气api接口返回的JSON数据类型也比较简单，对于我们Android的初学者做项目较为方便，申请流程官方网站有详细教程，申请成功后如下，key为api调用的关键信息：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200625015333202.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQyNjc5NTcz,size_16,color_FFFFFF,t_70)
3.2定位信息获取的api
    我们选择使用了百度提供的免费api接口http://lbsyun.baidu.com/apiconsole/key，因为Android原生定位API在国产手机中一般被阉割了，或者国内网络限制的原因，使用Android原生定位API一般是很难获取到定位信息的，跟手机厂商和网络环境都有关系。所以这边为了避免这种情况的不确定因素，我们选择了使用百度提供的免费地位接口，其api申请如下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200625015440685.png)
3.3城市信息获取
    下载省份、城市信息，建立城市列表数据库，对每个省份、城市进行编号，采用mysql进行查询读取，数据列表如下：
  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200625015524464.png)![在这里插入图片描述](https://img-blog.csdnimg.cn/20200625015532460.png)

## 7、模块架构
1.城市信息获取模块（cityselcetor activity）
	通过数据库查询，依次查询选择的省份、城市，通过回调函数onActivityResult获取菜单选择城市的回调，将该城市名保存至sharedpreference后传递至天气查询模块获得天气信息。
```
/**
 * 查询全国所有的省，从数据库查询
 */
private void queryProvinces() {
    ProvinceList.addAll(WeatherDB.loadProvinces(DBManager.getInstance().getDatabase()));
    data.clear();
    for (Province province : ProvinceList) {
        data.add(province.mProName);
    }
    currentLevel = LEVEL_PROVINCE;
    if(mAdapter==null)
    {
        initAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    else mAdapter.notifyDataSetChanged();
}
/**
 * 查询选中省份的所有城市，从数据库查询
 */
private void queryCities() {
    CityList.addAll(WeatherDB.loadCities(DBManager.getInstance().getDatabase(), selectedProvince.mProSort));
    data.clear();
    for (City city:CityList)
    {
        data.add(city.mCityName);
    }
    currentLevel = LEVEL_CITY;
    mAdapter.notifyDataSetChanged();
    mRecyclerView.smoothScrollToPosition(0);
}

protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == CitySelectorActivity.SelectorSuccessFromMenu) {
        //来自于菜单选择城市的回调
        if (resultCode == RESULT_OK) {
            if (data == null) {
                return;
            }
            Bundle bundle = data.getExtras();
            //城市名称
            String cityName = bundle.getString("CityName");
            //获取到城市名称后可自行使用...
            UpdataViewForMain(cityName);
        }
    }
}
private void UpdataViewForMain(String cityName)
{
    //关闭侧滑栏
    mDrawerLayout.closeDrawer(mNavView);
    //获取OldCity 以便新城市不可加载时恢复数据
    String OldCity = MySharedpreference.preferences.getString("City",null);
    //保存城市
    editor.putString("City",cityName);
    editor.commit();
    //更新数据
    mMainFragment.UpDataUi(OldCity);
    //RecyclerView回到顶部
    mMainFragment.mRecyclerView.smoothScrollToPosition(0);
}
```
2.定位信息获取模块（Map activity）
	通过调用百度地图api，读取当前定位并显示地图，通过定义方法navigate to移动至当前位置，并通过共享缓存区域sharedpreference将定位得到的城市进行保存，传递至天气查询模块获得当前城市的天气信息。	

```
private void navigateTo(BDLocation location) {
      if (isFirstLocate) {
          LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
          MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
          baiduMap.animateMapStatus(update);
          update = MapStatusUpdateFactory.zoomTo(16f);
          baiduMap.animateMapStatus(update);
          isFirstLocate = false;
      }
      MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
      locationBuilder.latitude(location.getLatitude());
      locationBuilder.longitude(location.getLongitude());
      MyLocationData locationData = locationBuilder.build();
      baiduMap.setMyLocationData(locationData);
  }

  public class MyLocationListener implements BDLocationListener {
      public String city;

      private SharedPreferences.Editor  editor;       //共享参数

      public void onReceiveLocation(final BDLocation Location) {
          runOnUiThread(new Runnable() {

              @Override
              public void run() {


                  if (Location.getLocType() == BDLocation.TypeGpsLocation || Location.getLocType() == BDLocation.TypeNetWorkLocation) {
                      navigateTo(Location);
                      editor = MySharedpreference.getInstance(MapActivity.this);
                      city = Location.getCity();
                      editor.putString("City", city);
                      editor.commit();
                  }

                  button1 =(Button)findViewById(R.id.button);
                  button1.setOnClickListener(new View.OnClickListener(){
                      @Override
                      public void  onClick(View v){
                          editor = MySharedpreference.getInstance(MapActivity.this);
                          city = Location.getCity();
                          editor.putString("City", city);
                          editor.commit();
                          Intent intent=new Intent(MapActivity.this,MainActivity.class);
                          startActivity(intent);
                      }
                  });
                      }
                  });
              }
          }
      }
```
3.天气信息获取模块（MultipleItemQuickAdapter）
    为了用GSON解析，首先将和风天气api接口中的JSON数据全部都写成了实体类（NewWeatherBean），利用模拟请求工具
Rest Client测试api接口工具是否正常，最后用getHeWeather6获取我们所需的数据。
```
case MultipleItem.DetailsAirInfo://空气细节信息
    try {
        helper.setText(R.id.air_aiq_tv, "空气指数:  " + item.mAirBean
                .getHeWeather6().get(0).getAir_now_city().getAqi());
        helper.setText(R.id.air_qlty_tv, "空气质量:  " + item.mAirBean
                .getHeWeather6().get(0).getAir_now_city().getQlty());
        helper.setText(R.id.air_main_pollutant_tv, "主要污染物:  " + item.mAirBean
                .getHeWeather6().get(0).getAir_now_city().getMain());
        helper.setText(R.id.air_pm25_tv, "PM2.5指数:  " + item.mAirBean
                .getHeWeather6().get(0).getAir_now_city().getPm25());
        helper.setText(R.id.air_no2_tv, "二氧化氮指数:  " + item.mAirBean
                .getHeWeather6().get(0).getAir_now_city().getNo2());
        helper.setText(R.id.air_so2_tv, "二氧化硫指数:  " + item.mAirBean
                .getHeWeather6().get(0).getAir_now_city().getSo2());
        helper.setText(R.id.air_co_tv, "一氧化碳指数:  " + item.mAirBean
                .getHeWeather6().get(0).getAir_now_city().getCo());
        helper.setText(R.id.air_o3_tv, "臭氧指数:  " + item.mAirBean
                .getHeWeather6().get(0).getAir_now_city().getO3());
    } catch (Exception e) {
    }
    break;
```

4.图表信息绘制模块（Main2activity）
    通过sharedpreference将天气查询最近七天的最高温度和最低温度获取的数据进行储存，在图表绘制模块中调用这些数据绘制图表。
```
     for (int i = 1; i < 8; i++) {
                            tmp_max[i-1]=item.mNewWeatherBean.getHeWeather6()
                                    .get(0).getDaily_forecast().get(i-1).getTmp_max();
                            tmp_min[i-1]=item.mNewWeatherBean.getHeWeather6()
                                    .get(0).getDaily_forecast().get(i-1).getTmp_min();
                        }
                        SharedPreferences sp = mContext.getSharedPreferences("data", MODE_PRIVATE);
//获取编辑器
                        SharedPreferences.Editor editor = sp.edit();
//存入String型数据
                        editor.putString("tmp_max0", tmp_max[0]);
                        editor.putString("tmp_max1", tmp_max[1]);
                        editor.putString("tmp_max2", tmp_max[2]);
                        editor.putString("tmp_max3", tmp_max[3]);
                        editor.putString("tmp_max4", tmp_max[4]);
                        editor.putString("tmp_max5", tmp_max[5]);
                        editor.putString("tmp_max6", tmp_max[6]);
                        editor.putString("tmp_min0", tmp_min[0]);
                        editor.putString("tmp_min1", tmp_min[1]);
                        editor.putString("tmp_min2", tmp_min[2]);
                        editor.putString("tmp_min3", tmp_min[3]);
                        editor.putString("tmp_min4", tmp_min[4]);
                        editor.putString("tmp_min5", tmp_min[5]);
                        editor.putString("tmp_min6", tmp_min[6]);
private void initData()
{
    editor = MySharedpreference.getInstance(Main2Activity.this);
    ArrayList<WeatherItem> list= new ArrayList<WeatherItem>();
    SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
    list.add(new WeatherItem("1", Float.parseFloat(sp.getString("tmp_max0", ""))));
    list.add(new WeatherItem("2",Float.parseFloat(sp.getString("tmp_max1", ""))));
    list.add(new WeatherItem("3", Float.parseFloat(sp.getString("tmp_max2", ""))));
    list.add(new WeatherItem("4",Float.parseFloat(sp.getString("tmp_max3", ""))));
    list.add(new WeatherItem("5",Float.parseFloat(sp.getString("tmp_max4", ""))));
    list.add(new WeatherItem("6",Float.parseFloat(sp.getString("tmp_max5", ""))));
    list.add(new WeatherItem("7", Float.parseFloat(sp.getString("tmp_max6", ""))));
    chart1.SetTuView(list, "最高温度：");//单位: 摄氏度
chart1.invalidate();
    ArrayList<WeatherItem> list1= new ArrayList<WeatherItem>();
    list1.add(new WeatherItem("",Float.parseFloat(sp.getString("tmp_min0", ""))));
    list1.add(new WeatherItem("",Float.parseFloat(sp.getString("tmp_min1", ""))));
    list1.add(new WeatherItem("", Float.parseFloat(sp.getString("tmp_min2", ""))));
    list1.add(new WeatherItem("",Float.parseFloat(sp.getString("tmp_min3", ""))));
    list1.add(new WeatherItem("", Float.parseFloat(sp.getString("tmp_min4", ""))));
    list1.add(new WeatherItem("",Float.parseFloat(sp.getString("tmp_min5", ""))));
    list1.add(new WeatherItem("",Float.parseFloat(sp.getString("tmp_min6", ""))));
   chart2.SetTuView(list1, "最低温度：");
  chart2.invalidate();
}
```
## 8、sharedpreference
   为了保存软件的设置参数，Android平台为我们提供了一个SharedPreferences接口，它是一个轻量级的存储类，特别适合用于保存软件配置参数。使用SharedPreferences保存数据，其背后是用xml文件存放数据，文件存放在/data/data//shared_prefs目录下。由于本次项目未采用服务器数据库保存数据，sharedpreference在各个activiti之间将获取的城市信息进行传递就显得尤为重要。除了上述提到选择查询某地后将该地通过储存至sharedpreference传递定位信息给天气查询模块、定位时通过sharedpreference传递定位信息给天气查询模块、天气查询获得气温数据后通过sharedpreference传递至图表绘制模块之外，每次下拉刷新、打开app时，都会读取sharedpreference中的城市信息进行更新，保证每次进入app时都能获取到上次退出前最后查询的城市天气，十分方便。读写格式如下：


```
//写操作
SharedPreferences sp = mContext.getSharedPreferences("data", MODE_PRIVATE);
//获取编辑器
SharedPreferences.Editor editor = sp.edit(); 
//存入String型数据,此处kindItemBean.getName()为所需要的传递的数据
editor.putString("goodsname", kindItemBean.getName()); 
//提交修改，否则不生效
editor.commit();     

//读操作
SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
//第二个参数为缺省值，如果不存在该key，返回缺省值
String goodsname = sp.getString("goodsname", "");

```
## 9、常见bug
1.模拟器中定位地不会变一直在北京
解决：一般为模拟器定位问题，手机端能定位即可，一定要在手机安装app进行尝试。
2.闪退后空指针异常
解决：我们经常会遇见打开app直接闪退的情形，在AS下方命令窗选择run可看到闪退原因，一般为空指针异常，出现如下代码提示：

```
Caused by: java.lang.NullPointerException: Attempt to invoke virtual method 'xxxxxxxxxxxx' on a null object reference
```
出现这种现象基本均为流程和视图不匹配，比如说添加控件却没有在layout中进行定义，将控件添加到一个空的地方即为空指针异常，因此需仔细核对layout与activity的关联性即可找出问题。

## 10.声明

此次项目是在网上开源demo上进行修改与添加功能而得，如有侵权请及时告知。

CSDN原文链接：https://blog.csdn.net/qq_42679573/article/details/106953009

欢迎关注博主！
