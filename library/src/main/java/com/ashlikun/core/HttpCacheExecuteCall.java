package com.ashlikun.core;

import com.ashlikun.okhttputils.http.ExecuteCall;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 作者　　: 李坤
 * 创建时间: 2018/2/1　16:41
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：缓存整个项目的Http请求
 */

/**
 * 作者　　: 李坤
 * 创建时间: 2018/2/1 16:56
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */

public class HttpCacheExecuteCall {
    private static volatile HttpCacheExecuteCall instance = null;

    private HttpCacheExecuteCall() {
    }

    public static HttpCacheExecuteCall getInstance() {
        if (instance == null) {//双重校验DCL单例模式
            synchronized (HttpCacheExecuteCall.class) {//同步代码块
                if (instance == null) {
                    instance = new HttpCacheExecuteCall();//创建一个新的实例
                }
            }
        }
        return instance;//返回一个实例
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:13
     * 方法功能：http请求执行后的集合   方便销毁
     */
    private Map<Integer, List<ExecuteCall>> mHttpCalls = new ConcurrentHashMap<>();

    /**
     * 作者　　: 李坤
     * 创建时间: 2018/2/1 17:01
     * 邮箱　　：496546144@qq.com
     * <p>
     * 方法功能：注册一个请求
     */

    public boolean register(Object key, ExecuteCall call) {
        List<ExecuteCall> list = get(key);
        if (list == null) {
            list = new ArrayList<>();
            this.mHttpCalls.put(getKey(key), list);
        }
        return list.add(call);
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2018/2/1 17:01
     * 邮箱　　：496546144@qq.com
     * <p>
     * 方法功能：取消注册一个key对应的全部请求
     */

    public boolean unregister(Object key) {
        return this.mHttpCalls.remove(getKey(key)) != null;
    }

    /**
     * 销毁网络访问
     */
    public void cancelAllToKey(Object key) {
        List<ExecuteCall> list = get(key);
        for (ExecuteCall call : list) {
            if (!call.isCompleted() && !call.isCanceled()) {
                call.cancel();
            }
        }
        unregister(key);
    }

    public List<ExecuteCall> get(Object key) {
        return this.mHttpCalls.get(getKey(key));
    }

    private int getKey(Object key) {
        return key != null ? key.hashCode() : 0;
    }
}
