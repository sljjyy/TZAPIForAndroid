# TZAPIForAndroid
已经添加至maven  <br />
```groovy
compile 'com.github.sljjyy:tzapi4android:1.5.4'
```
目前里面并有没更多内容 近期将逐步搬运

一、初始化
```java
public class SysApplication extends TZApplication{
	private void init() {
		MultiDex.install(this); //防止方法数超额
		TZLog.init(TZLogLevel.ERROR); //设置日志最低打印等级
		TZToastTool.init(TZToastLevel.MARK); //设置Toast最低显示等级
	}
}
```
二、 控件
　1、TZPhotoBoxGroup  拍照控件 
``` xml
　<com.tianzunchina.android.api.widget.photo.TZPhotoBoxGroup
	android:id="@+id/pbg"
	android:layout_height="wrap_content"
	android:layout_width="wrap_content"
	api:boxCount="3"
	api:boxHeight="200dp"
	api:boxWidth="200dp"/>
```


三、 工具类
