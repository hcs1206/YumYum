package com.redbeans.yumyum.dto;

public class Detail {
	private String id;
	private String name;
	private String phone;
	private String doro;
	private String jibun;
	private String time;
	private String menu1;
	private String cost1;
	private String menu2;
	private String cost2;
	private String contents;

	public Detail(String id, String name, String phone, String doro, String jibun, String time, String menu1,
			String cost1, String menu2, String cost2, String contents) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.doro = doro;
		this.jibun = jibun;
		this.time = time;
		this.menu1 = menu1;
		this.cost1 = cost1;
		this.menu2 = menu2;
		this.cost2 = cost2;
		this.contents = contents;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDoro() {
		return doro;
	}

	public void setDoro(String doro) {
		this.doro = doro;
	}

	public String getJibun() {
		return jibun;
	}

	public void setJibun(String jibun) {
		this.jibun = jibun;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getMenu1() {
		return menu1;
	}

	public void setMenu1(String menu1) {
		this.menu1 = menu1;
	}

	public String getCost1() {
		return cost1;
	}

	public void setCost1(String cost1) {
		this.cost1 = cost1;
	}

	public String getMenu2() {
		return menu2;
	}

	public void setMenu2(String menu2) {
		this.menu2 = menu2;
	}

	public String getCost2() {
		return cost2;
	}

	public void setCost2(String cost2) {
		this.cost2 = cost2;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	@Override
	public String toString() {
		return "Detail [id=" + id + ", phone=" + phone + ", doro=" + doro + ", jibun=" + jibun + ", time=" + time
				+ ", menu1=" + menu1 + ", cost1=" + cost1 + ", menu2=" + menu2 + ", cost2=" + cost2 + ", contents="
				+ contents + "]";
	}

}
