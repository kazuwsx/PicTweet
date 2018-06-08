package com.example.business.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.util.List;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
//@Entityアノテーション-付与したクラスにデータベースに保存するデータセットを定義していることを設定するのに使います。
@Entity
//@Tableアノテーション-@Entityアノテーションを付与したクラスに付与することで、そのクラスに対応するテーブルを生成してくれます。
@Table(name = "Tweets")
public class Tweet {
	//@Idアノテーション-カラムに追加するプロパティの中でも、プライマリーキー(Primary key) と呼ばれるプロパティに付与します。
	@Id
	//@GeneratedValueアノテーション-@GeneratedValueアノテーションは、プライマリーキーに付与することで、ユニークな値を自動生成してくれます。
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String image;
	private String text;
	
	@ManyToOne
	//updatable = false - このオプションがなされたプロパティは、新規作成時に設定された値から変更不可能にする
	@JoinColumn(updatable = false)
	private User user;
	
	@OneToMany(mappedBy = "tweet")
	private List<Comment> comments;
	
	//それぞれのフィールドに対するセッター＆ゲッターを定義
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public List<Comment> getComments() {
		return comments;
	}
	
	public void setComment(List<Comment> comments) {
		this.comments = comments;
	}
}
