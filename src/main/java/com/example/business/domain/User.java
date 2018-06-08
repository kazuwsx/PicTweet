package com.example.business.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String email;
    @Transient
    private String passwordConfirmation;
    @OneToMany(mappedBy="user")
    private List<Tweet> tweets;
    
    @OneToMany(mappedBy="user")
    private List<Comment> comments;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }
    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }
    
    public String getEmail() {
    	return email;
    }
    public void setEmail(String email) {
    	this.email = email;
    }
    
    public List<Tweet> getTweets(){
    	return tweets;
    }
    
    public void setTweets(List<Tweet> tweets) {
    	this.tweets = tweets;
    }
    
    public List<Comment> getComments() {
    	return comments;
    }
    
    public void setComments(List<Comment> comments) {
    	this.comments = comments;
    }
}
