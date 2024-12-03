package com.swaroop.springmvc2.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.swaroop.springmvc2.dto.UserDTO;
import com.swaroop.springmvc2.dto.WebBlogDTO;

@Component
public class WebBlogDAO {

	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private EntityTransaction entityTransaction;

	public WebBlogDTO addBlog(WebBlogDTO webBlog) {
		openConnection();
		entityTransaction.begin();
		entityManager.persist(webBlog);
		entityTransaction.commit();
		closeConnection();
		return webBlog;
	}

	@SuppressWarnings("unchecked")
	public List<WebBlogDTO> findAllWebBlogs() {
		openConnection();
		Query query = entityManager.createQuery("SELECT blogs FROM WebBlogDTO blogs");
		List<WebBlogDTO> blogs = query.getResultList();
		closeConnection();
		return blogs;
	}

	private void openConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		entityManagerFactory = Persistence.createEntityManagerFactory("springmvc");
		entityManager = entityManagerFactory.createEntityManager();
		entityTransaction = entityManager.getTransaction();
	}

	private void closeConnection() {
		if (entityManagerFactory != null) {
			entityManagerFactory.close();
		}
		if (entityManager != null) {
			entityManager.close();
		}
		if (entityTransaction != null) {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}

		}
	}

	public WebBlogDTO deleteBlog(int blogId, int userId) {
		openConnection();
		WebBlogDTO blog = entityManager.find(WebBlogDTO.class, blogId);
		UserDTO user = entityManager.find(UserDTO.class, userId);
		List<WebBlogDTO> blogs = user.getWebBlogs();
		WebBlogDTO blogToBeDeleted = null;
		for (WebBlogDTO b : blogs) {
			if (b.getId() == blogId) {
				blogToBeDeleted = b;
				break;
			}
		}
		blogs.remove(blogToBeDeleted);
		user.setWebBlogs(blogs);
		entityTransaction.begin();
		entityManager.persist(user);
		entityManager.remove(blog);
		entityTransaction.commit();
		closeConnection();
		return blog;

	}

	
	public List<WebBlogDTO> findMyBlogs(int userId) {
		openConnection();
		UserDTO user = entityManager.find(UserDTO.class, userId);
		List<WebBlogDTO> blogs = user.getWebBlogs();
		closeConnection();
		return blogs;
	}
	public WebBlogDTO updateBlog(int id, String title, String content, String author) {
		openConnection();
		WebBlogDTO blog = entityManager.find(WebBlogDTO.class, id);
		blog.setTitle(title);
		blog.setContent(content);
		blog.setAuthor(author);
		entityTransaction.begin();
		entityManager.persist(blog);
		entityTransaction.commit();
		closeConnection();
		return blog;
	}
	
	@SuppressWarnings("unchecked")
	public List<WebBlogDTO> searchBlogs(String query){
		openConnection();
		Query query2=entityManager.createQuery("SELECT blogs FROM WebBlogDTO blogs WHERE blogs.title LIKE '%" + query
				+ "%' OR blogs.content LIKE '%" + query + "%' OR blogs.author LIKE '%" + query + "%'");
		List<WebBlogDTO> blogs= query2.getResultList();
		closeConnection();
		return blogs;
	}
	
//	public void incrementLikes(int blogId) {
//        openConnection();
//        entityTransaction.begin();
//        
//        WebBlogDTO blog = entityManager.find(WebBlogDTO.class, blogId);
//        if (blog != null) {
//            blog.setLikes(blog.getLikes() + 1);
//            entityManager.merge(blog); // Use merge to update the entity
//        }
//
//        entityTransaction.commit();
//        closeConnection();
//    }
//
//    public void incrementDislikes(int blogId) {
//        openConnection();
//        entityTransaction.begin();
//        
//        WebBlogDTO blog = entityManager.find(WebBlogDTO.class, blogId);
//        if (blog != null) {
//            blog.setDislikes(blog.getDislikes() + 1);
//            entityManager.merge(blog); // Use merge to update the entity
//        }
//
//        entityTransaction.commit();
//        closeConnection();
//    }

	
	
	
	
	public boolean hasUserInteracted(int userId, int blogId,boolean liked) {
		 openConnection();
		    
		    // Use correct field names as per the UserBlogInteraction entity class
		    Query query = entityManager.createQuery(
		        "SELECT u FROM UserBlogInteraction u WHERE u.user.id = :userId AND u.blog.id = :blogId AND u.liked = :liked");
		    query.setParameter("userId", userId);
		    query.setParameter("blogId", blogId);
		    query.setParameter("liked", liked); // Set parameter correctly

		    List<UserBlogInteraction> interactions = query.getResultList();
		    closeConnection();
		    
		    return !interactions.isEmpty();
    }

    public void likeBlog(int userId, int blogId) {
    	openConnection();
        entityTransaction.begin();

        WebBlogDTO blog = entityManager.find(WebBlogDTO.class, blogId);
        UserDTO user = entityManager.find(UserDTO.class, userId);

        if (blog != null && user != null) {
            boolean userLiked = hasUserInteracted(userId, blogId, true);
            
            if (userLiked) {
                // If user has already liked, remove like and interaction
                blog.setLikes(blog.getLikes() - 1);
                removeUserInteraction(userId, blogId, true);
            } else {
                // If user hasn't liked, add like and create interaction
                blog.setLikes(blog.getLikes() + 1);
                addUserInteraction(user, blog, true);
            }
            entityManager.merge(blog);
        }

        entityTransaction.commit();
        closeConnection();
    }

    public void dislikeBlog(int userId, int blogId) {
        try {
            openConnection(); // Ensure connection is open before starting
            entityTransaction.begin(); // Begin the transaction

            WebBlogDTO blog = entityManager.find(WebBlogDTO.class, blogId);
            UserDTO user = entityManager.find(UserDTO.class, userId);

            if (blog != null && user != null) {
                boolean userDisliked = hasUserInteracted(userId, blogId, false);

                if (userDisliked) {
                    // If user has already disliked, remove dislike and interaction
                    if (blog.getDislikes() > 0) { // Prevent dislikes going below zero
                        blog.setDislikes(blog.getDislikes() - 1);
                    }
                    removeUserInteraction(userId, blogId, false);
                } else {
                    // If user hasn't disliked, add dislike and create interaction
                    blog.setDislikes(blog.getDislikes() + 1);
                    addUserInteraction(user, blog, false);
                }
                entityManager.merge(blog); // Merge changes to blog entity
            }

            entityTransaction.commit(); // Commit the transaction
        } catch (Exception e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback(); // Rollback in case of an error
            }
            e.printStackTrace();
        } finally {
            closeConnection(); // Ensure connection is closed after transaction
        }
    }	
    private void addUserInteraction(UserDTO user, WebBlogDTO blog, boolean liked) {
       
    	openConnection();
    	entityTransaction.begin();
    	UserBlogInteraction interaction = new UserBlogInteraction();
        interaction.setUser(user);
        interaction.setBlog(blog);
        interaction.setLiked(liked);
        entityManager.persist(interaction);
        entityTransaction.commit();
        closeConnection();
    }

    private void removeUserInteraction(int userId, int blogId, boolean liked) {
        try {
            openConnection(); // Open connection if not already open
            entityTransaction.begin(); // Begin the transaction

            Query query = entityManager.createQuery(
                "DELETE FROM UserBlogInteraction u WHERE u.user.id = :userId AND u.blog.id = :blogId AND u.liked = :liked");
            query.setParameter("userId", userId);
            query.setParameter("blogId", blogId);
            query.setParameter("liked", liked);
            query.executeUpdate();

            entityTransaction.commit(); // Commit transaction after update
        } catch (Exception e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback(); // Rollback in case of an error
            }
            e.printStackTrace();
        } finally {
            closeConnection(); // Close connection after transaction
        }
	
}
}
