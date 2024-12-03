package com.swaroop.springmvc2.dao;

import javax.persistence.*;

import com.swaroop.springmvc2.dto.UserDTO;
import com.swaroop.springmvc2.dto.WebBlogDTO;

import lombok.Data;

@Data
@Entity
@Table(name = "user_blog_interactions")
public class UserBlogInteraction {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserDTO user;

    @ManyToOne
    @JoinColumn(name = "blog_id", nullable = false)
    private WebBlogDTO blog;

    private boolean liked;
}
