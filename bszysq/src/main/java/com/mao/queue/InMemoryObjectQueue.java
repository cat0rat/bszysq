package com.mao.queue;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 内存 队列
 * @author jiangzushuai 2016年12月25日 下午11:50:28
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class InMemoryObjectQueue<T> implements ObjectQueue<T> {
	private final Queue<T> tasks;
	private Listener<T> listener;

	public InMemoryObjectQueue() {
		tasks = (Queue<T>) new ConcurrentLinkedQueue();		// 线程安全
	}

	public void add(T entry) {
		tasks.add(entry);
		if (listener != null)
			listener.onAdd(this, entry);
	}

	public T peek() {
		return tasks.peek();
	}

	public int size() {
		return tasks.size();
	}

	public void remove() {
		tasks.remove();
		if (listener != null)
			listener.onRemove(this);
	}

	public void setListener(Listener<T> listener) {
		if (listener != null) {
			for (T task : tasks) {
				listener.onAdd(this, task);
			}
		}
		this.listener = listener;
	}
}
