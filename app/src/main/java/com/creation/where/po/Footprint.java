package com.creation.where.po;

import java.io.Serializable;
import java.sql.Timestamp;

public class Footprint implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int foot_id;
	private int user_id;
	private double latitude;
	private double longitude;

	public Footprint(){

	}

	public Footprint(int foot_id, int user_id, double latitude, double longitude, String comment, String picture, Timestamp create_time) {
		this.foot_id = foot_id;
		this.user_id = user_id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.comment = comment;
		this.picture = picture;
		this.create_time = create_time;
	}

	private String comment;
	private String picture;
	private Timestamp create_time;
	
	public int getFoot_id() {
		return foot_id;
	}
	public void setFoot_id(int foot_id) {
		this.foot_id = foot_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	
}
