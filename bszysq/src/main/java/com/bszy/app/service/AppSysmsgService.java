package com.bszy.app.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bszy.admin.mapper.ArticleMapper;
import com.bszy.admin.pojo.Comment;
import com.bszy.app.mapper.AppSysmsgMapper;
import com.bszy.app.mapper.AppUserMapper;
import com.bszy.app.pojo.AppSysmsg;
import com.bszy.app.vo.AppUserGetui;
import com.mao.getui.GetuiPojo;
import com.mao.getui.GetuiUtil;
import com.mao.ssm.AjaxResultUtil;
import com.mao.ssm.BaseService;

@Service
public class AppSysmsgService extends BaseService<AppSysmsg, AppSysmsgMapper> {
	
	@Inject
	private AppSysmsgMapper mapper;
	public AppSysmsgMapper mapper(){return mapper;}
	@Inject
	private AppUserMapper userMapper;
	@Inject
	private ArticleMapper articleMapper;
	
	// 评论文章时
	@Transactional
	public void comment(Comment mo){
		Long authorid = articleMapper.authorId(mo.getArtid());
		AppSysmsg smsg = new AppSysmsg();
		smsg.setName("您有新的评论");
		smsg.setContent(mo.getContent());
		smsg.setUserid(authorid);
		smsg.setTypex(AppSysmsg.Typex_Art);
		smsg.setExtra(String.valueOf(mo.getArtid()));
		commMsg(smsg);
	}
	
	// 评论评论时
	@Transactional
	public void commentex(Comment mo){
		AppSysmsg smsg = new AppSysmsg();
		smsg.setName("您有新的评论");
		smsg.setContent(mo.getContent());
		smsg.setUserid(mo.getTouserid());
		smsg.setTypex(AppSysmsg.Typex_Art);
		smsg.setExtra(String.valueOf(mo.getArtid()));
		commMsg(smsg);
	}
	

	
	/** 给用户推送消息 */
	@Transactional
	public void user(Comment mo){
		AppSysmsg smsg = new AppSysmsg();
		smsg.setName("您有新的系统消息");
		smsg.setContent(mo.getContent());
		smsg.setUserid(mo.getTouserid());
		smsg.setTypex(AppSysmsg.Typex_Sys);
		smsg.setExtra(String.valueOf(mo.getArtid()));
		commMsg(smsg);
	}
	
	// TODO 辅助
	
	/** 发送推送 */
	public void commMsg(List<AppSysmsg> smsgs){
		for(AppSysmsg smsg : smsgs){
			commMsg(smsg);
		}
	}
	
	/** 发送推送 */
	@Transactional
	public String commMsg(AppSysmsg smsg){
		String emsg = null;
		Long touid = smsg.getUserid();	 // 目标用户个推信息
		AppUserGetui getui = userMapper.getuiSimple(touid);
		String getuicid = getui.getGetuicid();
		if(getuicid == null || getuicid.length() == 0){return "未找到用户个推ID";};
		smsg.setGetuicid(getuicid);	
		
		Integer iphone = Integer.valueOf(1);
		boolean isan = !iphone.equals(getui.getPhonetype());
		
		// 给客户端 的 消息体
		GetuiPojo gmo = new GetuiPojo(smsg.getName(), smsg.getContent(), 
				smsg.getTypex(), smsg.getExtra());
		String gmsg = AjaxResultUtil.toJsonStr(gmo);
		try {
			GetuiUtil.pushMsgToSingle(smsg.getGetuicid(), gmsg, isan);
			emsg = null;
		} catch (Exception e1) {
			emsg = "调用消息推送接口异常";
			e1.printStackTrace();
		}
		
		try {
			smsg.init_add();
			mapper.add(smsg);
		} catch (Exception e) {
			emsg = "保存推送记录失败";
			e.printStackTrace();
		}
		return emsg;
	}
}
