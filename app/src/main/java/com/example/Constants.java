package com.example;

import java.io.File;

/**
 * 类名：Constants 类描述：用于存储系统常量
 * Created by jiaxiangdong on 2015/9/22.
 */
public final class Constants {

    /**
     * 开发环境
     **/
    public static final String SERVER_DEV_URL = "https://api.douban.com/";

    //缓存
    public static final String DATA_PATH = BaseApplication.context().getCacheDir().getAbsolutePath() + File.separator + "data";
    public static final String NET_DATA_PATH = DATA_PATH + File.separator + "net_cache";

}
