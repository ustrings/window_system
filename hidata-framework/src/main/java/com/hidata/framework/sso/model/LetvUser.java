package com.hidata.framework.sso.model;
/**
 * 乐视的user
 * @author houzhaowei
 */
public class LetvUser {
	
	//用户id
	private int userid;
	//用户名
	private String username;
	//昵称
	private String nickname;
	//当前用户登陆的cookie
	private String tk;
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTk() {
		return tk;
	}
	public void setTk(String tk) {
		this.tk = tk;
	}
	@Override
	public String toString() {
		return "userid:" + this.userid + ",username:" + this.username + ",nickname:" + this.nickname + ",tk:" + this.tk;
	}
	
	
	
}
