package com.creation.where.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.creation.where.R;
import com.creation.where.adapter.ConversationAdapter;
import com.creation.where.po.IMConversation;

public class ConversationFragment extends Fragment {
	private TextView errorText;
    private final static int MSG_REFRESH = 2;
    protected EditText query;
    protected ImageButton clearSearch;
    protected boolean hidden;
    protected List<IMConversation> conversationList ;
	protected ListView conversationListView;
    protected FrameLayout errorItemContainer;

    protected boolean isConflict;
    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fx_fragment_conversation, container, false);
    }
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false))
            return;
        super.onActivityCreated(savedInstanceState);
        initView();
        setUpView();
    }
	
	protected void initView() {
        errorItemContainer = (FrameLayout) getView().findViewById(R.id.fl_error_item);
        View errorView = View.inflate(getActivity(), R.layout.em_chat_neterror_item, null);
        errorItemContainer.addView(errorView);
        errorText = (TextView) errorView.findViewById(R.id.tv_connect_errormsg);
        conversationListView = (ListView) getView().findViewById(R.id.list);
    }
	
	
	protected void setUpView() {
	    loadConversationList();
	    ConversationAdapter adapter = new ConversationAdapter(getActivity(), conversationList);
	    conversationListView.setAdapter(adapter);
    }

    /**
     * 模拟加载数据
     */
    private void loadConversationList(){
        conversationList = new ArrayList<IMConversation>();
        conversationList.add(new IMConversation(R.drawable.user01,"Jobowen",1,"10:23","my love",true));
        conversationList.add(new IMConversation(R.drawable.user02,"李海兰",1,"10:23"," so what",true));
        conversationList.add(new IMConversation(R.drawable.user03,"刘洋",3,"10:23","no zuo no die",true));
        conversationList.add(new IMConversation(R.drawable.user04,"林芷昀",1,"10:23","哈哈哈",false));
        conversationList.add(new IMConversation(R.drawable.user05,"李伟杰",3,"10:23","你们真逗！",false));
        conversationList.add(new IMConversation(R.drawable.user06,"蔡嘉敏",1,"10:23","可不是嘛！",false));
    }
	
}
