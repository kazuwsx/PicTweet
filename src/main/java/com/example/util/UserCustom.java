package com.example.util;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

//Spring SecurityのUserを拡張した、idの情報を含むクラス
public class UserCustom extends User {
	private static final long serialVersionUID = 1L;
	private Long id;
	
	public UserCustom(Long id, String username, String password, Set<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.id = id;
	}
	
	public Long getId() {
		return this.id;
	}
}
