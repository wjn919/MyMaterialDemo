package com.example;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import java.util.LinkedList;
import java.util.List;


public class BaseApplication extends Application {

	//运用list来保存们每一个activity是关键
    private List<Activity> mActivities = new LinkedList<Activity>();
	//*******环境切换***********************************
	/**服务器地址**/
	public static String Server_Url;
	/**图片地址**/
	public static String Image_Url;
	
	private static BaseApplication instance=null;


	//实例化一次
    public static BaseApplication getInstance(){
    	if(instance==null){
    		synchronized (BaseApplication.class) {
    			if (null == instance) {   
    	            instance = new BaseApplication();
    	        }
			}
    	}
        return instance;   
    }

	//====================系统环境的设置==========================
	public void onCreate() {
		super.onCreate();
		instance = this;
		Server_Url= Constants.SERVER_DEV_URL;//服务器地址
		Image_Url = Constants.SERVER_DEV_URL;

	}




	// add Activity    
    public void addActivity(Activity activity) {
        mActivities.add(activity);   
    }
	// removes Activity    
    public void removeActivity(Activity activity) {
        mActivities.remove(activity);   
    }  
    //关闭每一个list内的activity  
    public void closeAllActivity() {
		try {
			for (Activity activity : mActivities) {
				if (activity != null)
					activity.finish();
			}
			mActivities.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Context context() {
		return getInstance();
	}
}
