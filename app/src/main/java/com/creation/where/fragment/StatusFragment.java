package com.creation.where.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creation.where.R;
import com.creation.where.activity.FootPrintActivity;
import com.creation.where.adapter.ShopRecyclerViewAdapter;
import com.creation.where.po.DummyContent;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

public class StatusFragment extends Fragment {

	XRecyclerView mRecyclerView;
	// TODO: Customize parameters

	private OnListFragmentInteractionListener mListener;

	public StatusFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fx_fragment_status, container, false);
	}

	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

	public void init() {
		mRecyclerView=(XRecyclerView)getActivity().findViewById(R.id.list_xrecycler_view);
		mListener=new OnListFragmentInteractionListener() {
			@Override
			public void onListFragmentInteraction(DummyContent.DummyItem item) {
				startActivity(new Intent(getActivity(), FootPrintActivity.class));
			}
		};
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		mRecyclerView.setAdapter(new ShopRecyclerViewAdapter(DummyContent.ITEMS, mListener));
		mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
			@Override
			public void onRefresh() {
				//refresh data here
				new Handler(getActivity().getMainLooper()).postDelayed(new Runnable() {
					@Override
					public void run() {
						mRecyclerView.refreshComplete();
					}
				},3000);
			}

			@Override
			public void onLoadMore() {
				// load more data here
				new Handler(getActivity().getMainLooper()).postDelayed(new Runnable() {
					@Override
					public void run() {
						mRecyclerView.loadMoreComplete();
					}
				},3000);
			}
		});
		mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallBeat);
		mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallPulseSync);
	}

	public interface OnListFragmentInteractionListener {
		// TODO: Update argument type and name
		void onListFragmentInteraction(DummyContent.DummyItem item);
	}

//	private String getNowTime() {
//		Date date = new Date(System.currentTimeMillis());
//		SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHmmssSS");
//		return dateFormat.format(date);
//	}

}
