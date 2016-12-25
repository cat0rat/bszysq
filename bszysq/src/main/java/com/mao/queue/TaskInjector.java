package com.mao.queue;

/**
 * Inject dependencies into tasks of any kind.
 * @param <T> The type of tasks to inject.
 * @author jiangzushuai 2016年12月25日 下午11:42:42
 */
@SuppressWarnings("rawtypes")
public interface TaskInjector<T extends Task> {
	void injectMembers(T task);
}
