package com.ssafy.enjoytrip.post.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.enjoytrip.post.model.CommentDto;
import com.ssafy.enjoytrip.post.model.PostDto;
import com.ssafy.enjoytrip.post.model.service.PostService;


@RestController
@RequestMapping("/postapi")
@CrossOrigin("*")
public class PostController {
	
	private final PostService postService;
	
	public PostController(PostService postService) {
		super();
		this.postService = postService;
	}
	
	
	@PostMapping("/post")
	public ResponseEntity<?> writePost(@RequestPart(value = "file" ,required = false) MultipartFile multipartFile, PostDto postDto){
		System.out.println("this is" + multipartFile);
		try {
			// 파일 저장 경로 설정
			if(multipartFile != null) {
	            String uploadDir = "C:/uploads/";
	            File uploadDirFile = new File(uploadDir);
	            if (!uploadDirFile.exists()) {
	                uploadDirFile.mkdirs();
	                System.out.println("폴더 생성");
	            }
	
	            // 파일 저장
	            String filePath = uploadDir + multipartFile.getOriginalFilename();
	            multipartFile.transferTo(new File(filePath));
	            System.out.println("파일 업로드 성공: " + filePath);
	            postDto.setImgPath(filePath);
			}
            postService.writePost(postDto);
			return new ResponseEntity<PostDto>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/post/comment")
	public ResponseEntity<?> writeComment(@RequestBody CommentDto commentDto){
		System.out.println(commentDto.getComment());
		System.out.println(commentDto.getUserId());
		System.out.println(commentDto.getPostId());
		try {
            postService.writeComment(commentDto);
			return new ResponseEntity<CommentDto>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	@GetMapping("/list")
	public ResponseEntity<?> getPostList(){
		List<PostDto> list = new ArrayList<>();
		try {
			list = postService.listPost();
			return new ResponseEntity<List>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/post/{postId}/comments")
	public ResponseEntity<?> getComments(@PathVariable("postId") int postId){
		List<CommentDto> list = new ArrayList<>();
		try {
			list = postService.getComments(postId);
			return new ResponseEntity<List>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/list/{page}")
	public ResponseEntity<?> getPostList(@PathVariable("page") int page){
		System.out.println(page);
		List<PostDto> list = new ArrayList<>();
		try {
			list = postService.listPostLoad(page, 10);
			return new ResponseEntity<List>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/search/{keyword}")
	public ResponseEntity<?> searchPostList(@PathVariable("keyword") String keyword){
		System.out.println(keyword);
		List<PostDto> list = new ArrayList<>();
		try {
			list = postService.searchPostList(keyword);
			return new ResponseEntity<List>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/post/{postId}")
	public ResponseEntity<?> getPost(@PathVariable("postId") int postId){
		PostDto postDto;
		try {
			postDto = postService.getPost(postId);
			if(postDto != null) {
				// 파일을 byte 배열로 읽어들입니다.
				String filePath = postDto.getImgPath();
				if(filePath != null) {
				byte[] bytes = Files.readAllBytes(Paths.get(filePath)); //실제파일 불러오기
				
				// 바이트 배열을 Base64로 인코딩합니다.
				String encodedImage = Base64.getEncoder().encodeToString(bytes);
				
				// 인코딩된 이미지 데이터를 DTO에 설정합니다.
				postDto.setImgPath("data:image/jpeg;base64," + encodedImage);
			}
				return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}   
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PutMapping("/post/{postId}")
	public ResponseEntity<?> modifyPost(@RequestBody PostDto postDto){
		try {
			postService.modifyPost(postDto);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/post/setnull/{userId}")
	public ResponseEntity<?> setNull(@PathVariable("userId") String userId){
		System.out.println(userId);
		try {
			postService.setNull(userId);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@DeleteMapping("/post/{postId}")
	public ResponseEntity<?> deletePost(@PathVariable("postId") int postId) {
		try {
			postService.deletePost(postId);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
}
