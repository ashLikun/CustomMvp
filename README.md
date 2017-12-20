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

    compile 'com.github.ashLikun.frame:supertoobar:' + rootProject.ext.frameVersion
    compile 'com.github.ashLikun:XRecycleView:1.1.5'
    compile 'com.github.ashLikun.CommonAdapter:databindadapter:1.1.9'

    compile 'com.github.ashLikun:CommonUtils:1.3.4'
    compile 'com.github.ashLikun:CustomDialog:1.1.1'
    compile 'com.github.ashLikun:LoadSwitch:1.0.4'
    compile 'com.github.ashLikun:OkHttpUtils:1.4.2'

## 详细介绍
