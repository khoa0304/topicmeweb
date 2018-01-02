package com.topicme.web.model;

public class LinkAndNotes {

	private String link;
	private String topic;
	private String notes;

	
	private String postAnswer;
	private String postReview;
	private String postComment;
	
	public String getPostAnswer() {
		return postAnswer;
	}
	public void setPostAnswer(String postAnswer) {
		this.postAnswer = postAnswer;
	}
	public String getPostReview() {
		return postReview;
	}
	public void setPostReview(String postReview) {
		this.postReview = postReview;
	}
	public String getPostComment() {
		return postComment;
	}
	public void setPostComment(String postComment) {
		this.postComment = postComment;
	}
	
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
}
