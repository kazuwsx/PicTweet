package com.example.web.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.business.domain.Tweet;
import com.example.business.repository.TweetRepository;
import com.example.business.repository.UserRepository;
import com.example.business.domain.User;
import com.example.util.UserCustom;

//アノテーション-それを付与されたコードに役割を与える記述のこと,@Controller、@RequestMapping 
//@Controllerアノテーション-、付与したクラスに対してコントローラとしての役割を与える
@Controller
public class TweetController {
	
	//Beanオブジェクト-登録されたクラスやインタフェースのオブジェクトが自動的に生成され、アプリケーション内で再利用可能になったもの
	//@Autowiredアノテーション-Beanとして登録されているものから、同じクラスのものを探し、設定されたフィールドに割り当てる役割を持つ
	@Autowired
	private TweetRepository tweetRepository;
	@Autowired
	private UserRepository userRepository;
	
	//ModelAttributeアノテーション-そのメソッドの返り値をコントローラ内の全てのメソッドでaddObjectできる
	@ModelAttribute(name = "login_user")
	public UserDetails setLoginUser(@AuthenticationPrincipal UserCustom userCustom) {
	    return userCustom;
	}

	
	//@RequestMappingアノテーション-@RequestMappingの引数に応じたメソッドを呼び出すことを可能にする
    @RequestMapping(value = "/", method = RequestMethod.GET)
    //ModelAndViewオブジェクト-ビューに表示するhtmlファイルとビューに渡したい情報を一緒に設定
    public ModelAndView index(@PageableDefault(size = 5) Pageable pageable, ModelAndView mav,
    		//AuthenticationPrincipalアノテーション-引数内に追加することで、そのメソッド内でログイン中のユーザーの情報を取得できる
    		@AuthenticationPrincipal UserDetails userDetails) {
    	Page<Tweet> tweets = tweetRepository.findAllByOrderByIdDesc(pageable);
    	mav.addObject("tweets", tweets);
    	//setViewNameメソッド-引数に、表示させたいテンプレートファイルを文字列で指定
        mav.setViewName("tweet/index");
        return mav;
    }
    
    @RequestMapping(value = "/tweet/new", method = RequestMethod.GET)
    public ModelAndView newTweet(ModelAndView mav) {
    	mav.setViewName("tweet/new");
    	return mav;
    }
    
    @RequestMapping(value = "/tweet/new", method = RequestMethod.POST)
    public ModelAndView createTweet(@ModelAttribute Tweet tweet,@AuthenticationPrincipal UserCustom userCustom, ModelAndView mav) {
    	User user = userRepository.findOne(userCustom.getId());
    	tweet.setUser(user);
    	tweetRepository.saveAndFlush(tweet);
    	mav.setViewName("tweet/create"); 
    	return mav;
    }
    
    @RequestMapping(value = "/tweet/{id}", method = RequestMethod.GET)
    public ModelAndView show(@PathVariable Long id, ModelAndView mav) {
    	Tweet tweet = tweetRepository.findOne(id);
    	mav.addObject("tweet", tweet);
    	mav.setViewName("tweet/show");
    	return mav;
    }
    
    @RequestMapping(value = "/tweet/{id}/edit", method = RequestMethod.GET)
    //PathVariableアノテーション-RequestMappingの{}に囲まれた値をメソッドの引数に渡せる
    public ModelAndView editTweet(@PathVariable("id") Long id, ModelAndView mav) {
        Tweet tweet = tweetRepository.findOne(id);
        mav.addObject("tweet", tweet);
        mav.setViewName("tweet/edit");
        return mav;
    }
    
    @RequestMapping(value = "/tweet/{id}/edit", method = RequestMethod.POST)
    public ModelAndView updateTweet(@ModelAttribute Tweet editTweet, @PathVariable("id") Long id, @AuthenticationPrincipal UserCustom userCustom, ModelAndView mav) {
    	Tweet tweet = tweetRepository.findOne(id);
    	//BeanUtils.copyPropertiesメソッド - フォームから送られて来た情報を、データベースから取得したツイートにコピー
    	if (!tweet.getUser().getId().equals(userCustom.getId())) {
    		mav.setViewName("redirect:/tweet" + id + "/edit");
    		return mav;
    	}
    	BeanUtils.copyProperties(editTweet, tweet);
    	tweetRepository.save(tweet);
    	mav.setViewName("/tweet/update");
    	return mav;
    }
    
    @RequestMapping(value = "/tweet/{id}/delete", method = RequestMethod.POST)
    public ModelAndView deteleTweet(@PathVariable("id") Long id, @AuthenticationPrincipal UserCustom userCustom, ModelAndView mav) {
    	Tweet tweet = tweetRepository.findOne(id);
    	if (!tweet.getUser().getId().equals(userCustom.getId())) {
    		mav.setViewName("redirect:/");
    		return mav;
    	}
    	//deleteメソッド - データベースから指定したデータを削除
    	tweetRepository.delete(tweet);
    	mav.setViewName("redirect:/");
    	return mav;
    }
    
}