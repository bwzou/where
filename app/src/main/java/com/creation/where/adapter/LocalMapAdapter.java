package com.creation.where.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.mapapi.map.offline.MKOLUpdateElement;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.creation.where.R;

import java.util.ArrayList;

/**
 * Created by zbw on 2017/2/23.
 * 离线地图管理列表适配器
 */

public class LocalMapAdapter extends BaseAdapter {
    private ArrayList<MKOLUpdateElement> localMapList = null;
    private MKOfflineMap mOffline = null;
    private LayoutInflater inflater;

    public LocalMapAdapter(Context context, ArrayList<MKOLUpdateElement> localMapList,MKOfflineMap mOffline) {
        this.inflater=LayoutInflater.from(context);
        this.localMapList=localMapList;
        this.mOffline=mOffline;
    }

    @Override
    public int getCount() {
        return localMapList.size();
    }

    @Override
    public Object getItem(int index) {
        return localMapList.get(index);
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public View getView(int index, View view, ViewGroup arg2) {
        MKOLUpdateElement e = (MKOLUpdateElement) getItem(index);
        view = inflater.inflate(R.layout.offline_localmap_list, null);
        initViewItem(view, e);
        return view;
    }

    void initViewItem(View view, final MKOLUpdateElement e) {
        Button remove = (Button) view.findViewById(R.id.remove);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView update = (TextView) view.findViewById(R.id.update);
        TextView ratio = (TextView) view.findViewById(R.id.ratio);
        ratio.setText(e.ratio + "%");
        title.setText(e.cityName);
        if (e.update) {
            update.setText("可更新");
        } else {
            update.setText("最新");
        }

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mOffline.remove(e.cityID);
                updateView();
            }
        });
    }

    /**
     * 更新状态显示
     */
    public void updateView() {
        localMapList = mOffline.getAllUpdateInfo();
        if (localMapList == null) {
            localMapList = new ArrayList<MKOLUpdateElement>();
        }
        notifyDataSetChanged();
    }
}
