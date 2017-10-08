package dch.com.myalipush;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.alibaba.sdk.android.push.notification.CPushMessage;

/**
 * Created by lenovo on 2017/9/19.
 */

public class NotificationService extends Service {
    public static final String TAG = "NotificationService";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String action = intent.getAction();
        if (action.equals("your.notification.click.action")) {
            //添加您的通知点击处理逻辑
            CPushMessage message = intent.getParcelableExtra("message key");//获取message
            PushServiceFactory.getCloudPushService().clickMessage(message);//上报通知点击事件，点击事件相关信息可以在推送控制台查看到

            Log.e("111", "11getTraceInfo==" + message.getTraceInfo());
            Log.e("111", "getAppId==" + message.getAppId());
            Log.e("111", "getContent==" + message.getContent());
            Log.e("111", "getTitle==" + message.getTitle());
            Log.e("111", "getMessageId==" + message.getMessageId());

        } else if (action.equals("your.notification.delete.action")) {
            //添加您的通知删除处理逻辑
            CPushMessage message = intent.getParcelableExtra("message key");//获取message
            PushServiceFactory.getCloudPushService().dismissMessage(message);//上报通知删除事件，点击事件相关信息可以在推送控制台查看到

            Log.e("111", "22getTraceInfo==" + message.getTraceInfo());
            Log.e("111", "getAppId==" + message.getAppId());
            Log.e("111", "getContent==" + message.getContent());
            Log.e("111", "getTitle==" + message.getTitle());
            Log.e("111", "getMessageId==" + message.getMessageId());
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}












