package com.creation.where.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.creation.where.R;
import com.creation.where.activity.LocationMainActivity;
import com.creation.where.adapter.StatusAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.creation.where.util.DebugUtils.ShowErrorInf;

public class StatusFragment extends Fragment {
	private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择


	private String imageName;
	private PullToRefreshListView pull_refresh_list;      //使用第三方类库
	private List<JSONObject> articles=new ArrayList<JSONObject>();
	
//	private JSONArray datas = new JSONArray();
	private StatusAdapter adapter;
	private ListView actualListView;
	private int page = 0;

	String userID;
	List<String> sIDs = new ArrayList<String>();
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fx_fragment_status, container, false);
    }
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

	private void initView() {
		try {
			getData(0);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		pull_refresh_list = (PullToRefreshListView) getView().findViewById(R.id.pull_refresh_list);
		pull_refresh_list.setMode(PullToRefreshBase.Mode.BOTH);
		actualListView = pull_refresh_list.getRefreshableView();
		adapter =  new StatusAdapter(getActivity(), articles);
		actualListView.setAdapter(adapter);
		actualListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch(position)
				{
					case 1:         //转到定位的界面去
						Intent intent;
						intent = new Intent(getActivity(), LocationMainActivity.class);
						startActivity(intent);
						break;
					default:
						ShowErrorInf("没有选择内容");
						break;
				}

			}
		});
	}

	private void getData(final int page_num) throws JSONException {
		 JSONArray goodArray = new JSONArray(); 
		 JSONArray albumArray= new JSONArray();

		for (int i = 0; i < 2; i ++) {
			JSONObject node = new JSONObject();
			node.put("userID", "刘洋");
			node.put("content", "今天天气真好啊！");
			node.put("location", "博白南大街");
			node.put("sID", "什么鬼");
			node.put("time", getNowTime());
			node.put("latitude", 22.560);
			node.put("longtitude", 114.064);
			for(int j=0;j<3;j++){
				JSONObject goodPerson = new JSONObject();
				goodPerson.put("userID", "李海兰");
				goodArray.put(goodPerson);
				albumArray.put(goodPerson);
			}
			node.put("good", goodArray);
			node.put("album", albumArray);
			articles.add(node);
		}
	}

	private String getNowTime() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHmmssSS");
		return dateFormat.format(date);
	}
	
}
