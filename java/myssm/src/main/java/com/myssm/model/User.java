package com.myssm.model;

import java.io.Serializable;

public class User implements Serializable{
	private int id;
	private int state;
	private String nickname;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
    @Override
    public String toString() {
        return "User  [id=" + id + ", state=" + state
                + ", nickname=" + nickname + "]";
    }

}
