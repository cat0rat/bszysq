package com.mao.queue;

/**
 * 队列 接口
 * @author jiangzushuai 2016年12月25日 下午11:35:27
 */
public interface ObjectQueue<T> {

	/** 队列大小. */
	int size();

	/** 追加元素. */
	void add(T entry);

	/** 返回 队首元素 */
	T peek();

	/** 移除 队首元素. */
	void remove();

	/** 设置队列监听器. Invokes {@link Listener#onAdd} */
	void setListener(Listener<T> listener);

	/** 监听器接口 */
	public interface Listener<T> {

		/** 添加元素后调用 */
		void onAdd(ObjectQueue<T> queue, T entry);

		/** 移除元素后调用 */
		void onRemove(ObjectQueue<T> queue);
		
	}
}
