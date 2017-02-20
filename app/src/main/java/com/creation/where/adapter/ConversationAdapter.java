package com.creation.where.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.creation.where.R;
import com.creation.where.po.IMConversation;

public class ConversationAdapter extends BaseAdapter{
	private static final String TAG = "ChatAllHistoryAdapter";
    
    //存储会话信息
	private Context ctx;
    private List<IMConversation> easeConversation;
	
	public ConversationAdapter(Context context, List<IMConversation> objects) {
		this.ctx=context;
		this.easeConversation = objects;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return easeConversation.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return easeConversation.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
//		int type=getItemViewType(arg0);
		int type=0;
		if (arg1 == null) {
			arg1=   getViewByType(type,arg2);
        }
        ViewHolder holder = (ViewHolder) arg1.getTag();
        if (holder == null) {
            holder = new ViewHolder();
            
            holder.avatar = (ImageView) arg1.findViewById(R.id.avatar);
            holder.name = (TextView) arg1.findViewById(R.id.name);
            holder.unreadLabel = (TextView) arg1.findViewById(R.id.unread_msg_number);
            holder.message = (TextView) arg1.findViewById(R.id.message);
            holder.time = (TextView) arg1.findViewById(R.id.time);

            holder.msgState = arg1.findViewById(R.id.msg_state);
            holder.list_itease_layout = (RelativeLayout) arg1.findViewById(R.id.list_itease_layout);
            holder.motioned = (TextView) arg1.findViewById(R.id.mentioned);     //@me的消息
            arg1.setTag(holder);
            
            // get conversation
            IMConversation conversation = (IMConversation) getItem(arg0);
            // get username or group id
            String username = conversation.getUserName();
            holder.name.setText(username);
            holder.avatar.setImageResource(conversation.getAvatar());
            holder.message.setText(conversation.getMessage());
            holder.time.setText(conversation.getMsgTime());
            if(conversation.isMsgState()){
            	holder.msgState.setVisibility(View.VISIBLE);
            } else {
                holder.msgState.setVisibility(View.GONE);
            }
            if (conversation.getUnreadMsgCount() > 0) {
                // show unread message count
                holder.unreadLabel.setText(String.valueOf(conversation.getUnreadMsgCount()));
                holder.unreadLabel.setVisibility(View.VISIBLE);
            } else {
                holder.unreadLabel.setVisibility(View.INVISIBLE);
            }
        }
        
        return arg1;
	}
	
	//用ViewHolder，主要是进行一些性能优化，减少一些不必要的重复操作
	private static class ViewHolder {
        /**
         * who you chat with
         */
        TextView name;
        /**
         * unread message count
         */
        TextView unreadLabel;
        /**
         * content of last message
         */
        TextView message;
        /**
         * time of last message
         */
        TextView time;
        /**
         * avatar
         */
        ImageView avatar;
        /**
         * status of last message
         */
        View msgState;
        /**
         * layout
         */
        RelativeLayout list_itease_layout;
        
        TextView motioned;

    }
	
	private View getViewByType(int type, ViewGroup parent) {

        if (type == 0) {
            return LayoutInflater.from(ctx).inflate(R.layout.fx_item_conversation_single, parent, false);
        } else {
            View view = LayoutInflater.from(ctx).inflate(R.layout.fx_item_conversation_group, parent, false);
            return view;
        }
    }
	

}
