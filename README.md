[![Release](https://jitpack.io/v/ashLikun/CustomMvp.svg)](https://jitpack.io/#ashLikun/CustomMvp)

CustomMvp项目简介
    项目Mvp框架
    基类activity和fragment
## 使用方法

build.gradle文件中添加:
```gradle
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```
并且:

```gradle
dependencies {
    compile 'com.github.ashLikun:CustomMvp:{latest version}'


}
```
需要的其他依赖

    //数字进度条
    implementation 'com.github.ashLikun.frame:numberprogressbar:' + rootProject.ext.frameVersion
    //按钮
    implementation 'com.github.ashLikun.frame:flatbutton:' + rootProject.ext.frameVersion
    implementation 'com.github.ashLikun.CircleProgressView+'
    //MD风格的进度条
    implementation 'me.zhanghai.android.materialprogressbar:library:+'
    //公共工具
    implementation 'com.github.ashLikun:CommonUtils:+'
    //标题栏
    implementation 'com.github.ashLikun.frame:supertoobar:' + rootProject.ext.frameVersion
    //习惯的对话框
    implementation 'com.github.ashLikun:CustomDialog:+'
    //布局切换神器
    implementation 'com.github.ashLikun:LoadSwitch:+'
    //Okhttp工具
    implementation 'com.github.ashLikun:OkHttpUtils:+'

## 详细介绍
