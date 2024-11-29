package com.ssafy.enjoytrip.post.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.enjoytrip.post.model.CommentDto;
import com.ssafy.enjoytrip.post.model.PostDto;
import com.ssafy.enjoytrip.post.model.mapper.PostMapper;

@Service
public class PostServiceImpl implements PostService{

	private final PostMapper postMapper;
	
	public PostServiceImpl(PostMapper postMapper) {
		super();
		this.postMapper = postMapper;
	}

	@Override
	@Transactional
	public void writePost(PostDto postDto) throws Exception {
		postMapper.writePost(postDto);
	}

	@Override
	public List<PostDto> listPost() throws Exception {
		return postMapper.listPost();
	}

	@Override
	public PostDto getPost(int postId) throws Exception {
		return postMapper.getPost(postId);
	}

	@Override
	@Transactional
	public void modifyPost(PostDto postDto) throws Exception {
		postMapper.modifyPost(postDto);
	}

	@Override
	@Transactional
	public void deletePost(int postId) throws Exception {
		postMapper.deletePost(postId);
	}

	@Override
	public void setNull(String userId) {
		postMapper.setNull(userId);
	}

	@Override
	public List<PostDto> listPostLoad(int page, int size) {
		int offset = (page) * size;
		return postMapper.listPostLoad(offset, size);
	}

	@Override
	public List<PostDto> searchPostList(String keyword) {
		return postMapper.searchPostList(keyword);
	}

	@Override
	public List<CommentDto> getComments(int postId) {
		return postMapper.getComments(postId);
	}

	@Override
	public void writeComment(CommentDto commentDto) {
		postMapper.writeComment(commentDto);
	}

}
