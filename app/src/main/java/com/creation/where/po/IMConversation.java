package com.creation.where.po;


public class IMConversation {
	private int avatar;
	private String userName;
	private int unreadMsgCount;
	private String msgTime;
	private String message;
	private boolean msgState;
	
	public IMConversation() {
		super();
	}
	public IMConversation(int avatar, String userName, int unreadMsgCount,
			String msgTime, String message, boolean msgState) {
		super();
		this.avatar = avatar;
		this.userName = userName;
		this.unreadMsgCount = unreadMsgCount;
		this.msgTime = msgTime;
		this.message = message;
		this.msgState = msgState;
	}
	public int getAvatar() {
		return avatar;
	}
	public void setAvatar(int avatar) {
		this.avatar = avatar;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getUnreadMsgCount() {
		return unreadMsgCount;
	}
	public void setUnreadMsgCount(int unreadMsgCount) {
		this.unreadMsgCount = unreadMsgCount;
	}
	public String getMsgTime() {
		return msgTime;
	}
	public void setMsgTime(String msgTime) {
		this.msgTime = msgTime;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isMsgState() {
		return msgState;
	}
	public void setMsgState(boolean msgState) {
		this.msgState = msgState;
	}
	
}
