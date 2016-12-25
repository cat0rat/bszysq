package com.mao.queue;

/**
 * 任务 队列
 * @param <T> 任务实现类
 */
@SuppressWarnings("rawtypes")
public class TaskQueue<T extends Task> implements ObjectQueue<T> {

	private final TaskInjector<T> taskInjector;
	private final ObjectQueue<T> delegate;

	public TaskQueue(ObjectQueue<T> delegate) {
		this(delegate, null);
	}

	public TaskQueue(ObjectQueue<T> delegate, TaskInjector<T> taskInjector) {
		this.delegate = delegate;
		this.taskInjector = taskInjector;
	}

	/**
	 * {@inheritDoc} Overridden to inject members into Tasks.
	 */
	public T peek() {
		T task = delegate.peek();
		if (task != null && taskInjector != null) {
			taskInjector.injectMembers(task);
		}
		return task;
	}

	public int size() {
		return delegate.size();
	}

	public void add(T entry) {
		delegate.add(entry);
	}

	public void remove() {
		delegate.remove();
	}

	public void setListener(final Listener<T> listener) {
		if (listener != null) {
			// Intercept event delivery to pass the correct TaskQueue instance to listener.
			delegate.setListener(new Listener<T>() {
				public void onAdd(ObjectQueue<T> queue, T entry) {
					listener.onAdd(TaskQueue.this, entry);
				}
				public void onRemove(ObjectQueue<T> queue) {
					listener.onRemove(TaskQueue.this);
				}
			});
		} else {
			delegate.setListener(null);
		}
	}
}
