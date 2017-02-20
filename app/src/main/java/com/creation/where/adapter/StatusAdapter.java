package com.creation.where.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.creation.where.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StatusAdapter extends BaseAdapter {

	//存储会话信息
	private Activity context;
	private List<JSONObject> users;
	private LayoutInflater inflater;
	private String myuserID;
	private int myAvatar;
    private String myNick;
	
    public StatusAdapter(Activity context1, List<JSONObject> jsonArray) {
        this.context = context1;

        this.users = jsonArray;
        inflater = LayoutInflater.from(context);

        // 底部评论输入框
        myuserID = "阿文" ;   // getCurrentUsernName();
        myNick = "Jobowen";	   //获取用户昵称nickname
        myAvatar = R.drawable.fx_default_jobowen;   ///获取用户头像
    }
	    
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return  users.size() + 1;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (position == 0) {
            return null;
        } else {
            return users.get(position - 1);
        }
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.fx_item_status, parent,false);
		if (position == 0) {
            View  view = inflater.inflate(R.layout.fx_item_status_header, null,false);
            ImageView iv_avatar = (ImageView) view.findViewById(R.id.iv_avatar);
            //Glide.with(context).load(FXConstant.URL_AVATAR + myAvatar).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_avatar);
            iv_avatar.setImageResource(myAvatar);
            return view;
        } else {
        	ViewHolder holder = (ViewHolder) convertView.getTag();
            if (holder == null) {
                holder = new ViewHolder();
                holder.tv_nick = (TextView) convertView.findViewById(R.id.tv_nick);
                holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
                holder.iv_avatar = (ImageView) convertView.findViewById(R.id.sdv_image);
                holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
                holder.tv_location = (TextView) convertView.findViewById(R.id.tv_location);
                holder.iv_pop = (ImageView) convertView.findViewById(R.id.iv_pop);

                holder.tv_goodmembers = (TextView) convertView.findViewById(R.id.tv_goodmembers);
                holder.ll_goodmembers = (LinearLayout) convertView.findViewById(R.id.ll_goodmembers);
                holder.tv_commentmembers = (TextView) convertView.findViewById(R.id.tv_commentmembers);
                holder.view_pop = (View) convertView.findViewById(R.id.view_pop);
                holder.tv_delete = (TextView) convertView.findViewById(R.id.tv_delete);
                convertView.setTag(holder);
            }
            final View view_pop = holder.view_pop;
            JSONObject json = users.get(position - 1);
            // 如果数据出错....
            if (json == null ) {
                users.remove(position - 1);
                this.notifyDataSetChanged();
            }
            
            String userID="";
            String content="";
            String location="";
            String sID="";
            String rel_time="";
            JSONArray goodArray;
            JSONArray albumArray;
    		try {
    		    userID = json.getString("userID");
    			content = json.getString("content");     //是要显示的地图格子
    	        location = json.getString("location");
    	        sID = json.getString("sID");
    	        rel_time = json.getString("time");
    	        goodArray = json.getJSONArray("good");    //点赞
    	        albumArray = json.getJSONArray("album");    //收藏
    	        
    		} catch (JSONException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}

            // 设置删除键
    		if (userID.equals(myuserID)) {
    			holder.tv_delete.setVisibility(View.VISIBLE);
    			holder.tv_delete.setOnClickListener(new OnClickListener() {
    				@Override
    				public void onClick(View v) {
    					
    				}
    			});
    		} else {
    			holder.tv_delete.setVisibility(View.GONE);
    		}

            // 设置头像.....
            String nick = userID;
            if (userID.equals(myuserID)) {
                nick = myNick;
                holder.iv_avatar.setImageResource(myAvatar);
            }else{
            	//暂时使用默认值吧
            	
            }

            // 设置昵称
            holder.tv_nick.setText(nick);
            holder.tv_nick.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
//                    context.startActivity(new Intent(context, SocialFriendActivity.class).putExtra("friendID",
//                            userID));
                }
            });

            // 显示位置
            if (location != null && !location.equals("0")) {
                holder.tv_location.setVisibility(View.VISIBLE);
                holder.tv_location.setText(location);
            }
            
            // 显示文章内容
//          setUrlTextView(content, holder.tv_content);
            holder.tv_content.setText(content);
            
            final ImageView iv_temp = holder.iv_pop;
            final LinearLayout ll_goodmembers_temp = holder.ll_goodmembers;
            
            // 点赞
            //setGoodTextClick(holder.tv_goodmembers, goodArray,ll_goodmembers_temp, view_pop, goodArray.size());
//            boolean is_good_temp = true;
//            for (int i = 0; i < goodArray.size(); i++) {
//                JSONObject json_good = goodArray.getJSONObject(i);
//                if (json_good.getString("userID").equals(myuserID)) {
//                    is_good_temp = false;
//                }
//            }
            
            // 收藏
//            if (albumArray != null && albumArray.size() != 0) {
//                holder.tv_commentmembers.setVisibility(View.VISIBLE);
//                setCommentTextClick(holder.tv_commentmembers, albumArray,view_pop, albumArray.size());
//
//            }

//            final boolean is_good = is_good_temp;
//            String goodStr = "赞";
//            if (!is_good) {
//                goodStr = "取消";
//
//            }
//            iv_temp.setTag(goodStr);

            // 显示时间
            //holder.tv_time.setText(getTime(rel_time, WhereApplication.getInstance().getTime()));

            holder.iv_avatar.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    
                }
            });
            return convertView;
        }
        
	}

	
	public static class ViewHolder {
        ImageView iv_avatar;
        // 昵称
        TextView tv_nick;
        // 时间
        TextView tv_time;

        // 动态内容
        TextView tv_content;

        // 删除
        TextView tv_delete;

        // 位置
        TextView tv_location;

        // 评论点赞   或者收藏
        ImageView iv_pop;
        LinearLayout ll_goodmembers;
        TextView tv_goodmembers;
        TextView tv_commentmembers;
        View view_pop;
    }

	@SuppressLint("SimpleDateFormat")
    private String getTime(String rel_time, String now_time) {
        String backStr = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(rel_time);
            d2 = format.parse(now_time);

            // 毫秒ms
            long diff = d2.getTime() - d1.getTime();

            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            if (diffDays != 0) {
                if (diffDays < 30) {
                    if (1 < diffDays && diffDays < 2) {
                        backStr = "昨天";
                    } else if (1 < diffDays && diffDays < 2) {
                        backStr = "前天";
                    } else {
                        backStr = String.valueOf(diffDays) + "天前";
                    }
                } else {
                    backStr = "很久以前";
                }
            } else if (diffHours != 0) {
                backStr = String.valueOf(diffHours) + "小时前";
            } else if (diffMinutes != 0) {
                backStr = String.valueOf(diffMinutes) + "分钟前";
            } else {
                backStr = "刚刚";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return backStr;
	}

	class ImageListener implements OnClickListener {
		String[] images;
		int page;

		public ImageListener(String[] images, int page) {
			this.images = images;
			this.page = page;
		}

		@Override
		public void onClick(View v) {

		}
	}
	
}
