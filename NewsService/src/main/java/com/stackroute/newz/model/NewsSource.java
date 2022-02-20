package com.stackroute.newz.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class NewsSource {
	
	/*
	 * This class should have five fields
	 * (newssourceId,newssourceName,newssourceDesc,newssourceCreatedBy,newssourceCreationDate).
	 * This class should also contain the getters and setters for the 
	 * fields along with the parameterized	constructor and toString method.
	 * The value of newssourceCreationDate should not be accepted from the user but should be
	 * always initialized with the system date.
	 */
	@Id
	private Integer newsSourceId;
	private String newsSourceName;
	private String newsSourceDesc;
	private String newsSourceCreatedBy;
	private LocalDateTime newsSourceCreationDate;
	
	public NewsSource() {
		super();
		this.newsSourceCreationDate = LocalDateTime.now();
	}
	
	public NewsSource(Integer newsSourceId, String newsSourceName, String newsSourceDesc, String newsSourceCreatedBy,
			LocalDateTime newsSourceCreationDate) {
		super();
		this.newsSourceId = newsSourceId;
		this.newsSourceName = newsSourceName;
		this.newsSourceDesc = newsSourceDesc;
		this.newsSourceCreatedBy = newsSourceCreatedBy;
	}

	public Integer getNewsSourceId() {
		return newsSourceId;
	}

	public void setNewsSourceId(Integer newsSourceId) {
		this.newsSourceId = newsSourceId;
	}

	public String getNewsSourceName() {
		return newsSourceName;
	}

	public void setNewsSourceName(String newsSourceName) {
		this.newsSourceName = newsSourceName;
	}

	public String getNewsSourceDesc() {
		return newsSourceDesc;
	}

	public void setNewsSourceDesc(String newsSourceDesc) {
		this.newsSourceDesc = newsSourceDesc;
	}

	public String getNewsSourceCreatedBy() {
		return newsSourceCreatedBy;
	}

	public void setNewsSourceCreatedBy(String newsSourceCreatedBy) {
		this.newsSourceCreatedBy = newsSourceCreatedBy;
	}

	public LocalDateTime getNewsSourceCreationDate() {
		return newsSourceCreationDate;
	}

	public void setNewsSourceCreationDate() {
		this.newsSourceCreationDate = LocalDateTime.now();
	}

	@Override
	public String toString() {
		return "NewsSource [newsSourceId=" + newsSourceId + ", newsSourceName=" + newsSourceName + ", newsSourceDesc="
				+ newsSourceDesc + ", newsSourceCreatedBy=" + newsSourceCreatedBy + ", newsSourceCreationDate="
				+ newsSourceCreationDate + "]";
	}

}
