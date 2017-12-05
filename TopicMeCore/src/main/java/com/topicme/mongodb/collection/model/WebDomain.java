package com.topicme.mongodb.collection.model;

public class WebDomain {

	private long createdDateTime = System.currentTimeMillis();
	private String urlString;
	private String htmlContent;
	private String htmlText;
    private User user;
    
    
	public long getCreatedDateTime() {
		return createdDateTime;
	}
	public void setCreatedDateTime(long createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
	public String getUrlString() {
		return urlString;
	}
	public void setUrlString(String urlString) {
		this.urlString = urlString;
	}
	public String getHtmlContent() {
		return htmlContent;
	}
	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}
	public String getHtmlText() {
		return htmlText;
	}
	public void setHtmlText(String htmlText) {
		this.htmlText = htmlText;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (createdDateTime ^ (createdDateTime >>> 32));
		result = prime * result + ((htmlContent == null) ? 0 : htmlContent.hashCode());
		result = prime * result + ((htmlText == null) ? 0 : htmlText.hashCode());
		result = prime * result + ((urlString == null) ? 0 : urlString.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WebDomain other = (WebDomain) obj;
		if (createdDateTime != other.createdDateTime)
			return false;
		if (htmlContent == null) {
			if (other.htmlContent != null)
				return false;
		} else if (!htmlContent.equals(other.htmlContent))
			return false;
		if (htmlText == null) {
			if (other.htmlText != null)
				return false;
		} else if (!htmlText.equals(other.htmlText))
			return false;
		if (urlString == null) {
			if (other.urlString != null)
				return false;
		} else if (!urlString.equals(other.urlString))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
}
