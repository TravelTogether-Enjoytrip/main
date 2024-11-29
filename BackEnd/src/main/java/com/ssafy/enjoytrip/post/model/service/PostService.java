package com.ssafy.enjoytrip.post.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.post.model.CommentDto;
import com.ssafy.enjoytrip.post.model.PostDto;

public interface PostService {
	void writePost(PostDto postDto) throws Exception;
	void writeComment(CommentDto commentDto);
	List<PostDto> listPost() throws Exception;
	PostDto getPost(int postId) throws Exception;
	
	void modifyPost(PostDto postDto) throws Exception;
	void deletePost(int postId) throws Exception;
	void setNull(String userId);
	List<PostDto> listPostLoad(int page, int size);
	List<PostDto> searchPostList(String keyword);
	List<CommentDto> getComments(int postId);
}
