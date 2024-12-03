package com.swaroop.springmvc2.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swaroop.springmvc2.dao.UserDAO;
import com.swaroop.springmvc2.dao.WebBlogDAO;
import com.swaroop.springmvc2.dto.UserDTO;
import com.swaroop.springmvc2.dto.WebBlogDTO;

@Component
public class WebBlogService {

	@Autowired
	private WebBlogDAO webBlogDAO;

	@Autowired
	private UserDAO userDAO;

	public WebBlogDTO addBlog(String title, String content, String author, UserDTO user) {
		WebBlogDTO webBlog = new WebBlogDTO();
		webBlog.setTitle(title);
		webBlog.setContent(content);
		webBlog.setAuthor(author);
		webBlog.setDate(new Date(System.currentTimeMillis()));
		webBlog.setUserId(user.getId());
		try {
			WebBlogDTO blog = webBlogDAO.addBlog(webBlog);
			userDAO.mapBlogToUser(blog.getId(), user.getId());
			return blog;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<WebBlogDTO> findAllWebBlogs() {
		List<WebBlogDTO> blogs = webBlogDAO.findAllWebBlogs();
		if (blogs.size() > 0) {
			return blogs;
		} else {
			return null;
		}
	}

	public WebBlogDTO deleteBlog(int blogId, int userId) {
		try {
			return webBlogDAO.deleteBlog(blogId, userId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<WebBlogDTO> findMyBlogs(int userId) {
		List<WebBlogDTO> blogs = webBlogDAO.findMyBlogs(userId);
		if (blogs.size() > 0) {
			return blogs;
		} else {
			return null;
		}
	}
	public WebBlogDTO updateBlog(int id, String title, String content, String author) {
		try {
			return webBlogDAO.updateBlog(id, title, content, author);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public List<WebBlogDTO> searchBlogs(String query) {
		List<WebBlogDTO> blogs = webBlogDAO.searchBlogs(query);
		if (blogs.size() > 0) {
			return blogs;
		} else {
			return null;
		}
	}

//	public void incrementLikes(int blogId) {
//        webBlogDAO.incrementLikes(blogId);
//    }
//
//    public void incrementDislikes(int blogId) {
//        webBlogDAO.incrementDislikes(blogId);
//    }
	
	
	
	public void likeBlog(int userId, int blogId) {
        webBlogDAO.likeBlog(userId, blogId);
    }

    public void dislikeBlog(int userId, int blogId) {
        webBlogDAO.dislikeBlog(userId, blogId);
    }
	


}
