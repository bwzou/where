package com.creation.where.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.creation.where.R;
import com.creation.where.fragment.TgActivitiesFragment;
import com.lid.lib.LabelTextView;

import java.util.List;

/**
 * Created by zbw on 2017/5/11.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> implements View.OnClickListener{
    private List<TgActivitiesFragment.CategoryData> datas;

    public HomeAdapter(List<TgActivitiesFragment.CategoryData> datas) {
        this.datas = datas;
    }
    private OnItemClickListener mOnItemClickListener = null;

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup,  int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_tgactivity, viewGroup, false);
        MyViewHolder holder  = new MyViewHolder(view);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,  int position) {
        holder.labelTextView.setLabelBackgroundColor(datas.get(position).text);
        holder.labelTextView.setLabelText(datas.get(position).label);
        holder.tv.setBackgroundResource(datas.get(position).image);

        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    class MyViewHolder extends RecyclerView.ViewHolder
    {
        RelativeLayout tv;
        LabelTextView labelTextView;
        public MyViewHolder(View view)
        {
            super(view);
            labelTextView = (LabelTextView) view.findViewById(R.id.labeltextview);
            tv = (RelativeLayout) view.findViewById(R.id.tgactivity_relativelayout);
        }
    }
}
