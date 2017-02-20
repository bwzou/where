package com.creation.where.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EaseConversationList extends ListView {
	
    protected final int MSG_REFRESH_ADAPTER_DATA = 0;
    protected Context context;
    private  ArrayAdapter baseAdapter;

	public EaseConversationList(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public EaseConversationList(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }
	
	private void init(Context context, AttributeSet attrs) {
        this.context = context;
    }
	
	
}
