package com.mao.queue;

import java.io.Serializable;

/**
 * 任务
 * @author jiangzushuai 2016年12月25日 下午11:33:48
 */
public interface Task<T> extends Serializable {

	/** 执行任务 */
	void execute(T callback);
	
}
