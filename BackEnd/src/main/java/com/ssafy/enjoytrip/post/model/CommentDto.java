package com.ssafy.enjoytrip.post.model;

public class CommentDto {
	private int commentId;
	private String comment;
	private String registerTime;
	private int postId;
	private String userId;

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "CommentDto [commentId=" + commentId + ", comment=" + comment + ", registerTime=" + registerTime
				+ ", postId=" + postId + ", userId=" + userId + "]";
	}
}
