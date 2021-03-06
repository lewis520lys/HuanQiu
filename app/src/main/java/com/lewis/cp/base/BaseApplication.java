package com.lewis.cp.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.domain.EaseEmojiconGroupEntity;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.lewis.cp.R;
import com.lewis.cp.utils.PicassoImageLoader;
import com.lewis.cp.widget.EmojiconExampleGroupData;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import cn.bmob.v3.Bmob;

import static com.hyphenate.easeui.utils.EaseUserUtils.getUserInfo;

public class BaseApplication extends Application {
    /*
    * 初始化TAG
    * */
    private  static String TAG=BaseApplication.class.getName();

    /*Activity堆*/
    private Stack<Activity> activityStack = new Stack<Activity>();
    // 提供一个单件
    private static BaseApplication application;
    // 记录环信是否已经初始化
    private boolean isInit = false;
    private EaseUI easeUI;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //Avoiding the 64K Limit
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application=this;
        printAppParameter();
        // 初始化环信SDK
        initEasemob();
        initPic();
        Bmob.initialize(this, "0770f5db331b6dcd9cf2eaeeafd718d0");
    }
    public static Context getContext()
    {
        return application;
    }

    public static BaseApplication getInstance()
    {
        return application;
    }
    /*打印出一些app的参数*/
    private void printAppParameter(){
       // LogUtils.d(TAG, "OS : "+ Build.VERSION.RELEASE + " ( " + Build.VERSION.SDK_INT + " )");
        //DeviceMgr.ScrSize realSize = DeviceMgr.getScreenRealSize(this);
        //LogUtils.d(TAG, "Screen Size: " + realSize.w + " X " + realSize.h);

    }
   private void initPic(){
       ImagePicker imagePicker = ImagePicker.getInstance();
       imagePicker.setImageLoader(new PicassoImageLoader());   //设置图片加载器
       imagePicker.setShowCamera(true);  //显示拍照按钮
       imagePicker.setCrop(true);        //允许裁剪（单选才有效）
       imagePicker.setSaveRectangle(true); //是否按矩形区域保存
       imagePicker.setSelectLimit(1);    //选中数量限制
       imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
       imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
       imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
       imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
       imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
   }
    public void addActivity(final Activity curAT) {
        if (null == activityStack) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(curAT);
    }

    public void removeActivity(final Activity curAT) {
        if (null == activityStack) {
            activityStack = new Stack<Activity>();
        }
        activityStack.remove(curAT);
    }

    //获取最后一个Activity
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    //返回寨内Activity的总数
    public int howManyActivities() {
        return activityStack.size();
    }

    //关闭所有Activity
    public void finishAllActivities() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }
    private void initEasemob() {
        // 获取当前进程 id 并取得进程名
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        /**
         * 如果app启用了远程的service，此application:onCreate会被调用2次
         * 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
         * 默认的app会在以包名为默认的process name下运行，如果查到的process name不是app的process name就立即返回
         */
        if (processAppName == null || !processAppName.equalsIgnoreCase(getContext().getPackageName())) {
            // 则此application的onCreate 是被service 调用的，直接返回
            return;
        }
        if (isInit) {
            return;
        }

        // 调用初始化方法初始化sdk
        EMClient.getInstance().init(getContext(), initOptions());

        // 设置开启debug模式
        EMClient.getInstance().setDebugMode(true);
        //初始化EaseUI
        easeUI = EaseUI.getInstance();
        easeUI.init(this, null);
        // 设置初始化已经完成
        //设置表情provider
        easeUI.setEmojiconInfoProvider(new EaseUI.EaseEmojiconInfoProvider() {

            @Override
            public EaseEmojicon getEmojiconInfo(String emojiconIdentityCode) {
                //通过表情id返回具体表情数据
                EaseEmojiconGroupEntity data = EmojiconExampleGroupData.getData();
                for(EaseEmojicon emojicon : data.getEmojiconList()){
                    if(emojicon.getIdentityCode().equals(emojiconIdentityCode)){
                        return emojicon;
                    }
                }
                return null;
            }

            @Override
            public Map<String, Object> getTextEmojiconMapping() {
                //返回文字表情emoji文本和图片(resource id或者本地路径)的映射map
                return null;
            }
        });
        //set notification options, will use default if you don't set it
        easeUI.getNotifier().setNotificationInfoProvider(new EaseNotifier.EaseNotificationInfoProvider() {

            @Override
            public String getTitle(EMMessage message) {
                //you can update title here
                return null;
            }

            @Override
            public int getSmallIcon(EMMessage message) {
                //you can update icon here
                return 0;
            }

            @Override
            public String getDisplayedText(EMMessage message) {
//                // be used on notification bar, different text according the message type.
//                String ticker = EaseCommonUtils.getMessageDigest(message, appContext);
//                if(message.getType() == EMMessage.Type.TXT){
//                    ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
//                }
//                EaseUser user = getUserInfo(message.getFrom());
//                if(user != null){
//                    if(EaseAtMessageHelper.get().isAtMeMsg(message)){
//                        return String.format(appContext.getString(R.string.at_your_in_group), user.getNick());
//                    }
//                    return user.getNick() + ": " + ticker;
//                }else{
//                    if(EaseAtMessageHelper.get().isAtMeMsg(message)){
//                        return String.format(appContext.getString(R.string.at_your_in_group), message.getFrom());
//                    }
//                    return message.getFrom() + ": " + ticker;
//                }

            }

            @Override
            public String getLatestText(EMMessage message, int fromUsersNum, int messageNum) {
                // here you can customize the text.
                // return fromUsersNum + "contacts send " + messageNum + "messages to you";
                return null;
            }

            @Override
            public Intent getLaunchIntent(EMMessage message) {
//                // you can set what activity you want display when user click the notification
//                Intent intent = new Intent(appContext, ChatActivity.class);
//                // open calling activity if there is call
//                if(isVideoCalling){
//                    intent = new Intent(appContext, VideoCallActivity.class);
//                }else if(isVoiceCalling){
//                    intent = new Intent(appContext, VoiceCallActivity.class);
//                }else{
//                    ChatType chatType = message.getChatType();
//                    if (chatType == ChatType.Chat) { // single chat message
//                        intent.putExtra("userId", message.getFrom());
//                        intent.putExtra("chatType", Constant.CHATTYPE_SINGLE);
//                    } else { // group chat message
//                        // message.getTo() is the group id
//                        intent.putExtra("userId", message.getTo());
//                        if(chatType == ChatType.GroupChat){
//                            intent.putExtra("chatType", Constant.CHATTYPE_GROUP);
//                        }else{
//                            intent.putExtra("chatType", Constant.CHATTYPE_CHATROOM);
//                        }
//
//                    }
//                }
                return null;
            }
        });

        isInit = true;
    }

    /**
     * SDK初始化的一些配置
     * 关于 EMOptions 可以参考官方的 API 文档
     * http://www.easemob.com/apidoc/android/chat3.0/classcom_1_1hyphenate_1_1chat_1_1_e_m_options.html
     */
    private EMOptions initOptions() {

        EMOptions options = new EMOptions();
        // 设置Appkey，如果配置文件已经配置，这里可以不用设置
        // options.setAppKey("lzan13#hxsdkdemo");
        // 设置自动登录
        options.setAutoLogin(true);
        // 设置是否需要发送已读回执
        options.setRequireAck(true);
        // 设置是否需要发送回执，
        options.setRequireDeliveryAck(true);
        // 设置是否需要服务器收到消息确认
       // options.setRequireServerAck(true);
        // 设置是否根据服务器时间排序，默认是true
        options.setSortMessageByServerTime(false);
        // 收到好友申请是否自动同意，如果是自动同意就不会收到好友请求的回调，因为sdk会自动处理，默认为true
        options.setAcceptInvitationAlways(false);
        // 设置是否自动接收加群邀请，如果设置了当收到群邀请会自动同意加入
        options.setAutoAcceptGroupInvitation(false);
        // 设置（主动或被动）退出群组时，是否删除群聊聊天记录
        options.setDeleteMessagesAsExitGroup(false);
        // 设置是否允许聊天室的Owner 离开并删除聊天室的会话
        options.allowChatroomOwnerLeave(true);
        // 设置google GCM推送id，国内可以不用设置
        // options.setGCMNumber(MLConstants.ML_GCM_NUMBER);
        // 设置集成小米推送的appid和appkey
        // options.setMipushConfig(MLConstants.ML_MI_APP_ID, MLConstants.ML_MI_APP_KEY);

        return options;
    }

    /**
     * 根据Pid获取当前进程的名字，一般就是当前app的包名
     *
     * @param pid 进程的id
     * @return 返回进程的名字
     */
    private String getAppName(int pid) {
        String processName = null;
        ActivityManager activityManager = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List list = activityManager.getRunningAppProcesses();
        Iterator i = list.iterator();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pid) {
                    // 根据进程的信息获取当前进程的名字
                    processName = info.processName;
                    // 返回当前进程名
                    return processName;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 没有匹配的项，返回为null
        return null;
    }
}