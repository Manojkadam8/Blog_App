package com.BlogApi.services.impl;



import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.BlogApi.entities.Category;
import com.BlogApi.entities.Post;
import com.BlogApi.entities.User;
import com.BlogApi.exeption.ResourceNotFoundException;
import com.BlogApi.payloads.PostDto;
import com.BlogApi.payloads.PostResponse;
import com.BlogApi.repository.CategoryRepo;
import com.BlogApi.repository.PostRepo;
import com.BlogApi.repository.Userrepo;
import com.BlogApi.services.FileService;
import com.BlogApi.services.Postservice;
import org.modelmapper.ModelMapper;

@Service
public class Postserviceimpl implements Postservice {

	@Autowired
	private PostRepo postrepo;
	
	@Autowired
	private Userrepo userrepo;
	
	@Autowired
	private CategoryRepo categoryrepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	
	@Autowired
	private FileService fileservice;
	
	@Override
	public PostDto CreatePost(PostDto postdto, Integer userid, Integer categoryid) {
		
		User user=this.userrepo.findById(userid).orElseThrow(()-> new ResourceNotFoundException("User","user id",userid));
		
		Category category=this.categoryrepo.findById(categoryid).orElseThrow(()-> new ResourceNotFoundException("category" , "category id" , categoryid));

		
		Post post=this.modelMapper.map(postdto,Post.class);
		
		post.setImagename("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post NewPost=this.postrepo.save(post);
		
		return this.modelMapper.map(NewPost, PostDto.class);
	}

	@Override
	public PostDto UpdatePost(PostDto postdto, Integer postid) {
		Post post=this.postrepo.findById(postid).orElseThrow(()->new ResourceNotFoundException("post" , "post id" , postid));
		post.setPosttitle(postdto.getPosttitle());
		post.setContent(postdto.getContent());
		post.setImagename(postdto.getImagename());
		Post updatedpost=this.postrepo.save(post);
		return this.modelMapper.map(updatedpost, PostDto.class);
	}

	@Override
	public void DeletePost(Integer postid) {
		Post post=this.postrepo.findById(postid).orElseThrow(()->new ResourceNotFoundException("post" , "post id" , postid));

		this.postrepo.delete(post);

	}

	@Override
	public PostResponse GetAllPost(Integer pageNumber,Integer PageSize,String sortBy,String sortDir) {
		org.springframework.data.domain.Sort sort=null;
		
		if(sortDir.equalsIgnoreCase("asc")) {
			sort=sort.by(sortBy).ascending();
		}else {
			sort=sort.by(sortBy).descending();		
			}
		
		Pageable p=PageRequest.of(pageNumber,PageSize,sort);
		
		Page<Post> pagepost=this.postrepo.findAll(p);
		
		List<Post> posts=pagepost.getContent();
		
		List<PostDto> postdtos=posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse=new PostResponse();
		
		postResponse.setContent(postdtos);
		postResponse.setPagenumber(pagepost.getNumber());
		postResponse.setPageSize(pagepost.getSize());
		postResponse.setTotalElement(pagepost.getTotalElements());
		postResponse.setTotalpages(pagepost.getTotalPages());
		postResponse.setLastpage(pagepost.isLast());
		
		return postResponse;
	}

	@Override
	public PostDto GetPostById(Integer postid) {
		Post post=this.postrepo.findById(postid).orElseThrow(()->new ResourceNotFoundException("post" , "post id" , postid));
//		System.out.println(post);
		
		return this.modelMapper.map(post,PostDto.class);
	}

	@Override
	public List<PostDto> GetPostByCategory(Integer categoryid) {
		Category category=this.categoryrepo.findById(categoryid).orElseThrow(()-> new ResourceNotFoundException("category" , "category id" , categoryid));
    
		List<Post> posts=this.postrepo.findByCategory(category);
		
		List<PostDto> postdtos=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postdtos;
	}

	@Override
	public List<PostDto> GetPostByUser(Integer userid) {
		
		User user=this.userrepo.findById(userid).orElseThrow(()-> new ResourceNotFoundException("User","user id",userid));
//		 System.out.println("service method");
//			System.out.println(user);
        List<Post> posts=this.postrepo.findByUser(user);
//		posts.forEach(post->System.out.println("posts"+post));
		List<PostDto> postdtos=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postdtos;

	}

	@Override
	public List<PostDto> Searchposts(String keywords) {
	List<Post> posts=this.postrepo.findByposttitleContaining(keywords);
	
	List<PostDto> postDtos=posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	

}
