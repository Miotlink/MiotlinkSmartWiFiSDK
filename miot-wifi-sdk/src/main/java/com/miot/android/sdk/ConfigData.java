package com.miot.android.sdk;

public class ConfigData {
	private String index = null;

	private String content = null;

	private String contentAck_CodeName = null;

	private String contentAck_result = null;

	public ConfigData(String index, String content, String contentAck_CodeName,
			String contentAck_result) {
		this.index = index;
		this.content = content;
		this.contentAck_CodeName = contentAck_CodeName;
		this.contentAck_result = contentAck_result;
	}

	public String getIndex() {
		return this.index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentAck_CodeName() {
		return this.contentAck_CodeName;
	}

	public void setContentAck_CodeName(String contentAck_CodeName) {
		this.contentAck_CodeName = contentAck_CodeName;
	}

	public String getContentAck_result() {
		return this.contentAck_result;
	}

	public void setContentAck_result(String contentAck_result) {
		this.contentAck_result = contentAck_result;
	}

	@Override
	public String toString() {
		return "ConfigData [index=" + index + ", content=" + content
				+ ", contentAck_CodeName=" + contentAck_CodeName
				+ ", contentAck_result=" + contentAck_result + "]";
	}

}
