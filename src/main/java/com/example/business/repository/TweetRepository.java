package com.example.business.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.business.domain.Tweet;

//@Repositoryアノテーション-付与したインタフェースをリポジトリコンポーネントとして機能するようにさせるためのもの
@Repository
//JPA-Javaアプリケーションとリレーショナルデータベース(MySQL)を結びつける役割を持つもの
// JpaRepository<T, ID> -T部分には該当リポジトリに関係するエンティティクラス、ID部分にはそのエンティティのプライマリーキーの型 
public interface TweetRepository extends JpaRepository<Tweet, Long > {
	
	List<Tweet> findAllByOrderByIdDesc();
	
	Page<Tweet> findAllByOrderByIdDesc(Pageable pageable);
}
