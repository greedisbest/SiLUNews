package com.tangyin.mobile.silunews.model;

/**
 * 新闻选项卡实体
 */
public class Channel {
	
	private String id;
	
	private String name;
	
	public Channel(){
		
	}
	
	public Channel(String id, String name){
		this.id = id;
		this.name = name;
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
}
