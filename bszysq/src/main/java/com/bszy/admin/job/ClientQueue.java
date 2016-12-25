package com.bszy.admin.job;

import com.bszy.admin.pojo.Comment;
import com.mao.queue.InMemoryObjectQueue;

/**
 * 队列 列表
 * @author jiangzushuai 2016年12月25日 下午11:53:54
 */
public class ClientQueue {
	/** 评论主题消息 队列 */
	public static InMemoryObjectQueue<Comment> App_Comment_Msg = new InMemoryObjectQueue<Comment>();
	/** 评论评论消息 队列 */
	public static InMemoryObjectQueue<Comment> App_Commentex_Msg = new InMemoryObjectQueue<Comment>();
}
