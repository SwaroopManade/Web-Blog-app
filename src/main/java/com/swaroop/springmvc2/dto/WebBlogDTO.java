package com.swaroop.springmvc2.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "blogs")
@Data
public class WebBlogDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false, unique = false)
	private String title;
	@Column(nullable = false, unique = false)
	private String content;
	@Column(nullable = false, unique = false)
	private Date date;
	@Column(nullable = false, unique = false)
	private String author;
	@Column(nullable = false, unique = false)
	private int userId;
	@Column(nullable = false, unique = false)
	private int likes; 
	@Column(nullable = false, unique = false)
    private int dislikes;

}
