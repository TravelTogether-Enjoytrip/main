package com.ssafy.enjoytrip.post.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.post.model.CommentDto;
import com.ssafy.enjoytrip.post.model.PostDto;

@Mapper
public interface PostMapper {
	void writePost(PostDto postDto) throws Exception;
	List<PostDto> listPost() throws Exception;
	PostDto getPost(int postId) throws Exception;
	
	void modifyPost(PostDto postDto) throws Exception;
	void deletePost(int postId) throws Exception;
	void setNull(String userId);
	List<PostDto> listPostLoad(int offset, int size);
	List<PostDto> searchPostList(String keyword);
	List<CommentDto> getComments(int postId);
	void writeComment(CommentDto commentDto);
}
