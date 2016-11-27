package com.bszy.app.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.bszy.admin.mapper.ArticleMapper;
import com.bszy.admin.pojo.Comment;
import com.bszy.app.mapper.AppUserMapper;
import com.bszy.app.pojo.AppUser;
import com.mao.ssm.BaseService;

@Service
public class AppGetuiService extends BaseService<AppUser, AppUserMapper> {
	
	@Inject
	private AppUserMapper mapper;
	public AppUserMapper mapper(){return mapper;}
	
	@Inject
	private AppUserMapper userMapper;
	
	@Inject
	private ArticleMapper articleMapper;
	
	// 评论文章时
	public void comment(Comment mo){
		// 发送推送
		
//		String content = "";
//		GetuiPojo gmo = new GetuiPojo("您有新的消息", content);
//		AppUser gsmo = userMapper.getuiSimple(content.get)
//		GetuiUtil.pushMessageToSingle(cid, msg);
	}
}
