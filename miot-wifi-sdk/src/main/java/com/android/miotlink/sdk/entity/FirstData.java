package com.android.miotlink.sdk.entity;

public class FirstData {
	private String index="";
	private String content="";
	private String contentAck_CodeName="";
	private String contentAck_result="";
	
	public FirstData (String index,String content,String contentAck_CodeName,String contentAck_result){
		this.content=content;
		this.index=index;
		this.contentAck_CodeName=contentAck_CodeName;
		this.contentAck_result=contentAck_result;
	}
	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentAck_CodeName() {
		return contentAck_CodeName;
	}

	public void setContentAck_CodeName(String contentAck_CodeName) {
		this.contentAck_CodeName = contentAck_CodeName;
	}

	public String getContentAck_result() {
		return contentAck_result;
	}

	public void setContentAck_result(String contentAck_result) {
		this.contentAck_result = contentAck_result;
	}
	
	
	

}
