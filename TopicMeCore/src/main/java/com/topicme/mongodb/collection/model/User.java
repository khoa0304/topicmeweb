package com.topicme.mongodb.collection.model;

import java.security.Timestamp;

/*
 * @since 11/27/2017
 */
public class User {

	private String userName;
	
	private Timestamp createdDateTime;
	
	private Timestamp lastActivity;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Timestamp getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Timestamp createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public Timestamp getLastActivity() {
		return lastActivity;
	}

	public void setLastActivity(Timestamp lastActivity) {
		this.lastActivity = lastActivity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdDateTime == null) ? 0 : createdDateTime.hashCode());
		result = prime * result + ((lastActivity == null) ? 0 : lastActivity.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
		User other = (User) obj;
		if (createdDateTime == null) {
			if (other.createdDateTime != null)
				return false;
		} else if (!createdDateTime.equals(other.createdDateTime))
			return false;
		if (lastActivity == null) {
			if (other.lastActivity != null)
				return false;
		} else if (!lastActivity.equals(other.lastActivity))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	
}
