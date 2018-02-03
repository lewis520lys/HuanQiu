package com.lewis.cp.view.frgm;

import android.content.Intent;

import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;

import com.hyphenate.easeui.ui.EaseConversationListFragment;

import com.hyphenate.util.NetUtils;

import com.lewis.cp.R;
import com.lewis.cp.base.BaseApplication;
import com.lewis.cp.http.APIService;
import com.lewis.cp.http.RetrofitManager;
import com.lewis.cp.model.BaseCallModel;
import com.lewis.cp.model.UserModel;
import com.lewis.cp.utils.Constant;
import com.lewis.cp.view.act.ChatActivity;
import com.lewis.cp.widget.ACache;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Administrator on 2018/1/29.
 */

public class YuleFragment extends EaseConversationListFragment {
    private TextView errorText;
    private AlertDialog dialog;
    private ACache cache;
    private UserModel.UserBean user;

    @Override
    protected void initView() {
        super.initView();
        cache = ACache.get(BaseApplication.getContext());
        user = (UserModel.UserBean) cache.getAsObject("user");
        View errorView = (LinearLayout) View.inflate(getActivity(), R.layout.em_chat_neterror_item, null);
        errorItemContainer.addView(errorView);
        errorText = (TextView) errorView.findViewById(R.id.tv_connect_errormsg);
        tv_jiaqun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                View inflate = View.inflate(getActivity(), R.layout.diog_jiaqun, null);
                builder.setView(inflate);
                final EditText et_qun = inflate.findViewById(R.id.et_qun);
                Button bt_add = inflate.findViewById(R.id.bt_add);
                bt_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addQun(et_qun.getText().toString().trim());
                    }
                });
                dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void addQun(String id) {
        Map<String,String> map=new HashMap<>();
        map.put("userName",user.userName);
        map.put("managerId",id);

        RetrofitManager.getInstance()
                .createReq(APIService.class)
                .addQunReq(map)
                .enqueue(new Callback<BaseCallModel>() {
                    @Override
                    public void onResponse(Call<BaseCallModel> call, Response<BaseCallModel> response) {
                        BaseCallModel body = response.body();
                        if (body != null) {
                            Toast.makeText(getActivity(),response.body().info,Toast.LENGTH_SHORT).show();
                            if ("N".equals(response.body().hasException)) {
                                dialog.dismiss();
                                refresh();
                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<BaseCallModel> call, Throwable t) {

                    }
                });


    }

    @Override
    protected void setUpView() {
        super.setUpView();

        conversationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EMConversation conversation = conversationListView.getItem(position);
                String username = conversation.conversationId();
                if (username.equals(EMClient.getInstance().getCurrentUser()))
                    Toast.makeText(getActivity(), R.string.Cant_chat_with_yourself, Toast.LENGTH_SHORT).show();
                else {
                    // start chat acitivity
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    if(conversation.isGroup()){
                        if(conversation.getType() == EMConversation.EMConversationType.ChatRoom){
                            // it's group chat
                            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_CHATROOM);
                        }else{
                            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_GROUP);
                        }

                    }else {
                        intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                    }
                    // it's single chat
                    intent.putExtra(Constant.EXTRA_USER_ID, username);
                    startActivity(intent);
                }
            }
        });
        //red packet code : 红包回执消息在会话列表最后一条消息的展示
//        conversationListView.setConversationListHelper(new EaseConversationList.EaseConversationListHelper() {
//            @Override
//            public String onSetItemSecondaryText(EMMessage lastMessage) {
//                if (lastMessage.getBooleanAttribute(RPConstant.MESSAGE_ATTR_IS_RED_PACKET_ACK_MESSAGE, false)) {
//                    String sendNick = lastMessage.getStringAttribute(RPConstant.EXTRA_RED_PACKET_SENDER_NAME, "");
//                    String receiveNick = lastMessage.getStringAttribute(RPConstant.EXTRA_RED_PACKET_RECEIVER_NAME, "");
//                    String msg;
//                    if (lastMessage.direct() == EMMessage.Direct.RECEIVE) {
//                        msg = String.format(getResources().getString(R.string.msg_someone_take_red_packet), receiveNick);
//                    } else {
//                        if (sendNick.equals(receiveNick)) {
//                            msg = getResources().getString(R.string.msg_take_red_packet);
//                        } else {
//                            msg = String.format(getResources().getString(R.string.msg_take_someone_red_packet), sendNick);
//                        }
//                    }
//                    return msg;
//                }
//                return null;
//            }
//        });
        super.setUpView();
        //end of red packet code
    }

    @Override
    protected void onConnectionDisconnected() {
        super.onConnectionDisconnected();
        if (NetUtils.hasNetwork(getActivity())){
            errorText.setText(R.string.can_not_connect_chat_server_connection);
        } else {
            errorText.setText(R.string.the_current_network);
        }
    }


//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        getActivity().getMenuInflater().inflate(R.menu.em_delete_message, menu);
//    }
//
//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        boolean deleteMessage = false;
//        if (item.getItemId() == R.id.delete_message) {
//            deleteMessage = true;
//        } else if (item.getItemId() == R.id.delete_conversation) {
//            deleteMessage = false;
//        }
//        EMConversation tobeDeleteCons = conversationListView.getItem(((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position);
//        if (tobeDeleteCons == null) {
//            return true;
//        }
//        if(tobeDeleteCons.getType() == EMConversation.EMConversationType.GroupChat){
//            EaseAtMessageHelper.get().removeAtMeGroup(tobeDeleteCons.conversationId());
//        }
//        try {
//            // delete conversation
//            EMClient.getInstance().chatManager().deleteConversation(tobeDeleteCons.conversationId(), deleteMessage);
//            InviteMessgeDao inviteMessgeDao = new InviteMessgeDao(getActivity());
//            inviteMessgeDao.deleteMessage(tobeDeleteCons.conversationId());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        refresh();
//
//        // update unread count
//        ((MainActivity) getActivity()).updateUnreadLabel();
//        return true;
//    }
}
