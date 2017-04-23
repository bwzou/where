package com.creation.where.po;

import java.io.Serializable;

public class Together implements Serializable{
	private static final long serialVersionUID = 1L;
	private int together_id;
	private String what_time;
	private String destination;
	private String event;
	private int people_number;
	private String transportation;
	private double cost;
	private int user_id;
	private String join_user;
	private int join_number;
	
	public int getTogether_id() {
		return together_id;
	}
	public void setTogether_id(int together_id) {
		this.together_id = together_id;
	}
	public String getWhat_time() {
		return what_time;
	}
	public void setWhat_time(String what_time) {
		this.what_time = what_time;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public int getPeople_number() {
		return people_number;
	}
	public void setPeople_number(int people_number) {
		this.people_number = people_number;
	}
	public String getTransportation() {
		return transportation;
	}
	public void setTransportation(String transportation) {
		this.transportation = transportation;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getJoin_user() {
		return join_user;
	}
	public void setJoin_user(String join_user) {
		this.join_user = join_user;
	}
	public int getJoin_number() {
		return join_number;
	}
	public void setJoin_number(int join_number) {
		this.join_number = join_number;
	}

}
