package com.swaroop.springmvc2.controller;

import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.swaroop.springmvc2.dao.WebBlogDAO;
import com.swaroop.springmvc2.dto.UserDTO;
import com.swaroop.springmvc2.dto.WebBlogDTO;
import com.swaroop.springmvc2.service.WebBlogService;

@Controller
public class WebBlogController {

	@Autowired
	private WebBlogService webBlogService;
	private WebBlogDAO webBlogDAO;
	

	@RequestMapping(value = "/add-blog-page", method = RequestMethod.GET)
	protected String getAddBlogPage(HttpSession httpSession) {
		UserDTO user = (UserDTO) httpSession.getAttribute("user");
		if (user != null) {
			return "add_blog";
		} else {
			return "login";
		}
	}

	@RequestMapping(value = "/add-blog", method = RequestMethod.POST)
	protected String addBlog(@RequestParam(name = "title") String title, @RequestParam(name = "content") String content,
			@RequestParam(name = "author") String author, ModelMap modelMap, HttpSession httpSession) {
		UserDTO user = (UserDTO) httpSession.getAttribute("user");
		WebBlogDTO addedBlog = webBlogService.addBlog(title, content, author, user);
		if (addedBlog != null) {
			List<WebBlogDTO> blogs = webBlogService.findMyBlogs(user.getId());
			modelMap.addAttribute("blogs", blogs);
			return "my_blogs";
		} else {
			modelMap.addAttribute("message", "Something went wrong..");
			return "add_blog";
		}
	}

	@RequestMapping(value = "/blogs", method = RequestMethod.GET)
	protected String findAllBlogs(ModelMap modelMap, HttpSession httpSession) {
		UserDTO user = (UserDTO) httpSession.getAttribute("user");
		if (user != null) {
			List<WebBlogDTO> blogs = webBlogService.findAllWebBlogs();
			if (blogs != null) {
				modelMap.addAttribute("blogs", blogs);
			} else {
				modelMap.addAttribute("message", "Blogs not found..");
			}
			modelMap.addAttribute("role", user.getRole());
			return "blogs";
		} else {
			return "login";
		}
	}

	@RequestMapping(value = "/my-blogs", method = RequestMethod.GET)
	protected String findMyBlogs(ModelMap modelMap, HttpSession httpSession) {
		UserDTO user = (UserDTO) httpSession.getAttribute("user");
		if (user != null) {
			List<WebBlogDTO> blogs = webBlogService.findMyBlogs(user.getId());
			if (blogs != null) {
				modelMap.addAttribute("blogs", blogs);
			} else {
				modelMap.addAttribute("message", "Blogs not found..");
			}
			return "my_blogs";
		} else {
			return "login";
		}
	}

	@RequestMapping(value = "/delete-blog", method = RequestMethod.GET)
	protected String deleteBlog(@RequestParam(name = "blog-id") int blogId, @RequestParam(name = "user-id") int userId,
			ModelMap modelMap, HttpSession httpSession) {
		UserDTO user = (UserDTO) httpSession.getAttribute("user");
		if (user != null) {
			WebBlogDTO deletedBlog = webBlogService.deleteBlog(blogId, userId);
			if (deletedBlog == null) {
				modelMap.addAttribute("message", "Something went wrong..");
			}
			List<WebBlogDTO> blogs = webBlogService.findMyBlogs(user.getId());
			if (blogs != null) {
				modelMap.addAttribute("blogs", blogs);
			} else {
				modelMap.addAttribute("message", "Blogs not found..");
			}
			return "my_blogs";
		} else {
			return "login";
		}
	}
	
	@RequestMapping(value = "/edit-blog", method = RequestMethod.GET)
	protected String editBlog(@RequestParam(name = "blog-id") int blogId, ModelMap modelMap, HttpSession httpSession) {
	    UserDTO user = (UserDTO) httpSession.getAttribute("user"); // Retrieve user from session
	    if (user == null) {
	        return "login"; // Redirect to login if user is not authenticated
	    }

	    // If user is authenticated, proceed to get the blog
	    WebBlogDTO blogToBePassed=null;
	    List<WebBlogDTO> blogs = webBlogService.findMyBlogs(user.getId());
	    if (blogs != null && !blogs.isEmpty()) {
	       for (WebBlogDTO blog : blogs) {
			if(blog.getId()==blogId)
				blogToBePassed=blog;
		}
	       modelMap.addAttribute("blog", blogToBePassed);
	       return "update_blog";
	    }
	   

	    // If the blog is not found or user doesn't have access
	    modelMap.addAttribute("message", "Blog not found or access denied.");
	    return "home"; // Return to My Blogs
	}



	@RequestMapping(value = "/update-blog", method = RequestMethod.POST)
	protected String updateBlog(@RequestParam(name = "id") int id, @RequestParam(name = "title") String title,
			@RequestParam(name = "content") String content, @RequestParam(name = "author") String author,
			ModelMap modelMap, HttpSession httpSession) {
		WebBlogDTO updatedBlog = webBlogService.updateBlog(id, title, content, author);
		if (updatedBlog != null) {
			modelMap.addAttribute("message", "Blog updated..");
		} else {
			modelMap.addAttribute("message", "Something went wrong..");
		}
		UserDTO user = (UserDTO) httpSession.getAttribute("user");
		List<WebBlogDTO> blogs = webBlogService.findMyBlogs(user.getId());
		modelMap.addAttribute("blogs", blogs);
		return "my_blogs";
	}
	
//	
//	@GetMapping("/blog-list")
//	public String getBlogs(HttpServletRequest request, Model model) {
//	    List<WebBlogDTO> blogs = webBlogService.findAllWebBlogs();
//
//	    String sortParam = request.getParameter("sort");
//	    if ("author".equals(sortParam)) {
//	        blogs.sort(Comparator.comparing(WebBlogDTO::getAuthor, Comparator.nullsLast(Comparator.naturalOrder())));
//	    } else if ("newest_first".equals(sortParam)) {
//	        blogs.sort(Comparator.comparing(WebBlogDTO::getDate, Comparator.nullsLast(Comparator.reverseOrder())));
//	    } else if ("oldest_first".equals(sortParam)) {
//	        blogs.sort(Comparator.comparing(WebBlogDTO::getDate, Comparator.nullsLast(Comparator.naturalOrder())));
//	    }
//	   
//
//
//	    model.addAttribute("blogs", blogs);
//	    return "blogs";
//	}


	
	
	

	@GetMapping("/blog-list")
    public String getBlogs(HttpServletRequest request, Model model) {
        List<WebBlogDTO> blogs = webBlogService.findAllWebBlogs(); 
        
        String sortParam = request.getParameter("sort");
        if ("author".equals(sortParam)) {
            blogs.sort(Comparator.comparing(WebBlogDTO::getAuthor)); 
        } else if ("newest_first".equals(sortParam)) {
            blogs.sort(Comparator.comparing(WebBlogDTO::getDate).reversed()); 
        } else if ("oldest_first".equals(sortParam)) {
            blogs.sort(Comparator.comparing(WebBlogDTO::getDate)); 
        }
        
        model.addAttribute("blogs", blogs);
        return "blogs";
    }
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	protected String searchBlogs(@RequestParam(name = "query") String query, HttpSession httpSession,
			ModelMap modelMap) {
		UserDTO user = (UserDTO) httpSession.getAttribute("user");
		if (user != null) {
			List<WebBlogDTO> blogs = webBlogService.searchBlogs(query);
			if (blogs != null) {
				modelMap.addAttribute("blogs", blogs);
			} else {
				modelMap.addAttribute("message", "Blogs not found..");
			}
			modelMap.addAttribute("role", user.getRole());
			return "blogs";
		} else {
			return "login";
		}
		
	}
	
//	@RequestMapping(value = "/like-blog", method = RequestMethod.GET)
//    public String likeBlog(@RequestParam(name = "blog-id") int blogId) {
//        webBlogService.incrementLikes(blogId);
//        return "blogs";
//    }
//
//    @PostMapping("/dislike-blog")
//    public String dislikeBlog(@PathVariable("id") int blogId) {
//        webBlogService.incrementDislikes(blogId);
//        return "blogs";
//    }


	@RequestMapping(value = "/like-blog", method = RequestMethod.GET)
    public String likeBlog(@RequestParam(name = "blog-id") int blogId, @RequestParam("user-id") int userId) {
        webBlogService.likeBlog(userId, blogId);
        return "redirect:blogs";
    }

	@RequestMapping(value = "/dislike-blog", method = RequestMethod.GET)
    public String dislikeBlog(@RequestParam(name = "blog-id") int blogId, @RequestParam("user-id") int userId) {
        webBlogService.dislikeBlog(userId, blogId);
        return "redirect:/blogs";
    }

}
