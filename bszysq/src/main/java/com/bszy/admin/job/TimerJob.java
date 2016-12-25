package com.bszy.admin.job;

import javax.inject.Inject;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bszy.admin.pojo.Comment;
import com.bszy.app.service.AppSysmsgService;

@Component
public class TimerJob {

	Logger log = LogManager.getLogger(TimerJob.class);

	@Inject
	private AppSysmsgService appSysmsgService;

	/** 检查 评论主题消息, 每10秒一次 */
	@Scheduled(cron = "0/10 * * * * ? ")
	public void App_Comment_Msg() {
		for(;;){
			Comment mo = ClientQueue.App_Comment_Msg.peek();
			if(mo == null && ClientQueue.App_Comment_Msg.size() == 0) break;
			ClientQueue.App_Comment_Msg.remove();
			try {
				appSysmsgService.comment(mo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/** 检查 评论评论消息, 每10秒一次 */
	@Scheduled(cron = "0/10 * * * * ? ")
	public void App_Commentex_Msg() {
		for(;;){
			Comment mo = ClientQueue.App_Commentex_Msg.peek();
			if(mo == null && ClientQueue.App_Commentex_Msg.size() == 0) break;
			ClientQueue.App_Commentex_Msg.remove();
			try {
				appSysmsgService.commentex(mo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * CRON表达式 含义 
	 * "0 0 12 * * ?" 每天中午十二点触发 
	 * "0 15 10 ? * *" 每天早上10：15触发
	 * "0 15 10 * * ?" 每天早上10：15触发
	 * "0 15 10 * * ? *" 每天早上10：15触发
	 * "0 15 10 * * ? 2005" 2005年的每天早上10：15触发
	 * "0 * 14 * * ?" * 每天从下午2点开始到2点59分每分钟一次触发
	 * "0 0/5 14 * * ?" 每天从下午2点开始到2：55分结束每5分钟一次触发
	 * "0 0/5 14,18 * * ?" 每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发
	 * "0 0-5 14 * * ?"每天14:00至14:05每分钟一次触发 
	 * "0 10,44 14 ? 3 WED" 三月的每周三的14：10和14：44触发
	 * "0 15 10 ? * MON-FRI" 每个周一、周二、周三、周四、周五的10：15触发
	 */

}
