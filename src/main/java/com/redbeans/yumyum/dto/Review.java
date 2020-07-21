package com.redbeans.yumyum.dto;

public class Review {
	private String id;
	private String userName;
	private String date;
	private String score;
	private int visitCount;
	
	
	public Review(String id, String userName, String date, String score, int visitCount) {
		super();
		this.id = id;
		this.userName = userName;
		this.date = date;
		this.score = score;
		this.visitCount = visitCount;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public int getVisitCount() {
		return visitCount;
	}
	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}
	@Override
	public String toString() {
		return "Review [id=" + id + ", userName=" + userName + ", date=" + date + ", score=" + score + ", visitCount="
				+ visitCount + "]";
	}
}
