package dch.com.myalipush;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static android.app.Notification.FLAG_AUTO_CANCEL;
import static android.content.Context.NOTIFICATION_SERVICE;
import static com.alibaba.sdk.android.ams.common.global.AmsGlobalHolder.getPackageName;

/**
 * 用于接收推送的通知和消息
 */
public class MyMessageReceiver extends MessageReceiver {
    // 消息接收部分的LOG_TAG
    public static final String REC_TAG = "receiver";
    private int clickNotificationCode=100;

    /**
     * 推送通知的回调方法
     *
     * @param context
     * @param title
     * @param summary
     * @param extraMap
     */
    @Override
    public void onNotification(Context context, String title, String summary, Map<String, String> extraMap) {
        // TODO 处理推送通知
        Log.e("MyMessageReceiver", "Receive notification, title: " + title + ", summary: " + summary + ", extraMap: " + extraMap);
        // Receive notification, title: a, summary: aaaaaaaaa, extraMap: {1=1111111, 2=22222222222, _ALIYUN_NOTIFICATION_ID_=387551}



    }

    /**
     * 推送消息（透传）的回调方法
     *
     * @param context
     * @param cPushMessage
     */
    @Override
    public void onMessage(Context context, CPushMessage cPushMessage) {
        Log.e("MyMessageReceiver", "onMessage, messageId: " + cPushMessage.getMessageId() + ", title: " + cPushMessage.getTitle() + ", content:" + cPushMessage.getContent());

        buildNotification(context, cPushMessage);
    }

    /**
     * 推送消息（透传方式）。
     * 接受到对应消息后，消息的弹出处理
     */
    public void buildNotification(Context context, CPushMessage message) {

//        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.push_layout);
//        remoteViews.setImageViewResource(R.id.custom_icon, R.drawable.ic_launcher);
//        remoteViews.setTextViewText(R.id.tv_custom_title, message.getTitle());
//        remoteViews.setTextViewText(R.id.tv_custom_content, message.getContent());
//        remoteViews.setTextViewText(R.id.tv_custom_time, new SimpleDateFormat("HH:mm").format(new Date()));
//        remoteViews.setTextColor(R.id.tv_custom_title, Color.parseColor("#000000"));
//        remoteViews.setTextColor(R.id.tv_custom_content, Color.parseColor("#000000"));
//        remoteViews.setTextColor(R.id.tv_custom_time, Color.parseColor("#000000"));

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(context)
//                .setContent(remoteViews)
                .setContentTitle(message.getTitle())
                .setContentText(message.getContent())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .build();
        notification.flags = FLAG_AUTO_CANCEL;

        Log.e("111","00getTraceInfo=="+message.getTraceInfo());
        Log.e("111","getAppId=="+message.getAppId());
        Log.e("111","getContent=="+message.getContent());
        Log.e("111","getTitle=="+message.getTitle());
        Log.e("111","getMessageId=="+message.getMessageId());


        notification.contentIntent = buildClickContent(context, message);
        notification.deleteIntent = buildDeleteContent(context, message);
        notificationManager.notify(message.hashCode(), notification);

    }
    public PendingIntent buildClickContent(Context context, CPushMessage message) {
        Intent clickIntent = new Intent();
        clickIntent.setAction("your.notification.click.action");
        //添加其他数据
        clickIntent.putExtra("message key",  message);//将message放入intent中，方便通知自建通知的点击事件
        return PendingIntent.getService(context, clickNotificationCode, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
    public PendingIntent buildDeleteContent(Context context, CPushMessage message) {
        Intent deleteIntent = new Intent();
        deleteIntent.setAction("your.notification.delete.action");
        //添加其他数据
        deleteIntent.putExtra("message key",  message);//将message放入intent中，方便通知自建通知的点击事件
        return PendingIntent.getService(context, clickNotificationCode, deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * 从通知栏打开通知的扩展处理
     *
     * @param context
     * @param title
     * @param summary
     * @param extraMap
     */
    @Override
    public void onNotificationOpened(Context context, String title, String summary, String extraMap) {
        Log.e("MyMessageReceiver", "onNotificationOpened, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap);
        //onNotificationOpened, title: a, summary: aaaaaaaaa, extraMap:{"1":"1111111","2":"22222222222","_ALIYUN_NOTIFICATION_ID_":"387551"}


    }

    /**
     * 无动作通知点击回调。当在后台或阿里云控制台指定的通知动作为无逻辑跳转时,通知点击回调为onNotificationClickedWithNoAction而不是onNotificationOpened
     *
     * @param context
     * @param title
     * @param summary
     * @param extraMap
     */
    @Override
    protected void onNotificationClickedWithNoAction(Context context, String title, String summary, String extraMap) {
        Log.e("MyMessageReceiver", "onNotificationClickedWithNoAction, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap);

        Log.e("111", "onNotificationReceivedInApp, title: " + title +
                ", summary: " + summary +
                ", extraMap:" + extraMap );
        //title: 000,
        // summary: sdsdsdddd发送方,
        // extraMap: {_ALIYUN_NOTIFICATION_ID_=745373}
    }

    /**
     * 应用处于前台时通知到达回调。注意:该方法仅对自定义样式通知有效,
     * 相关详情请参考https://help.aliyun.com/document_detail/30066.html?spm=5176.product30047.6.620.wjcC87#h3-3-4-basiccustompushnotification-api
     *
     * @param context
     * @param title
     * @param summary
     * @param extraMap
     * @param openType
     * @param openActivity
     * @param openUrl
     */
    @Override
    protected void onNotificationReceivedInApp(Context context, String title, String summary, Map<String, String> extraMap, int openType, String openActivity, String openUrl) {
        Log.e("MyMessageReceiver", "onNotificationReceivedInApp, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap + ", openType:" + openType + ", openActivity:" + openActivity + ", openUrl:" + openUrl);
        Log.e("666", "onNotificationReceivedInApp, title: " + title +
                ", summary: " + summary +
                ", extraMap:" + extraMap +
                ", openType:" + openType +
                ", openActivity:" + openActivity +
                ", openUrl:" + openUrl);


    }

    /**
     * 通知删除回调
     *
     * @param context
     * @param messageId
     */
    @Override
    protected void onNotificationRemoved(Context context, String messageId) {
        Log.e("MyMessageReceiver", "onNotificationRemoved");
    }
}
