package com.creation.where.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creation.where.R;
import com.creation.where.activity.TgcontentActivity;
import com.creation.where.adapter.HomeAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zbw on 2017/5/9.
 */
public class TgActivitiesFragment extends Fragment {

    public class CategoryData {
        public int image;
        public int text;
        public String label;
    }

    private View view;
    private List<CategoryData> datas;
    private HomeAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fx_fragment_tgactivities,container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        datas = new ArrayList<>();
        mRecyclerView=(RecyclerView)view.findViewById(R.id.tgactivity_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new HomeAdapter(datas);
        mAdapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view , int position){
                startActivity(new Intent(getActivity(), TgcontentActivity.class));
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        getCategoryData();
        mAdapter.notifyDataSetChanged();
    }

    private void getCategoryData() {

        {
            CategoryData item = new CategoryData();
            item.text = getResources().getColor(R.color.fbutton_color_orange);
            item.image = R.drawable.sea ;
            item.label = "未进行";
            datas.add(item);
        }

        {
            CategoryData item = new CategoryData();
            item.text = getResources().getColor(R.color.fbutton_color_orange);
            item.image = R.drawable.car;
            item.label = "未进行";
            datas.add(item);
        }

        {
            CategoryData item = new CategoryData();
            item.text = getResources().getColor(R.color.fbutton_color_alizarin);
            item.image = R.drawable.comics;
            item.label = "进行中";
            datas.add(item);
        }

        {
            CategoryData item = new CategoryData();
            item.text = getResources().getColor(R.color.fbutton_color_alizarin);
            item.image = R.drawable.leave ;
            item.label = "进行中";
            datas.add(item);
        }

        {
            CategoryData item = new CategoryData();
            item.text = getResources().getColor(R.color.fbutton_color_concrete);
            item.image = R.drawable.cartoon;
            item.label = "已结束";
            datas.add(item);
        }

        {
            CategoryData item = new CategoryData();
            item.text = getResources().getColor(R.color.fbutton_color_concrete);
            item.image = R.drawable.music ;
            item.label = "已结束";
            datas.add(item);
        }
    }

}
