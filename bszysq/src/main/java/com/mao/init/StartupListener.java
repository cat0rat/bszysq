package com.mao.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 网站启动监听器
 * @author mao 2012-6-8 下午10:21:47
 */
@SuppressWarnings("serial")
public class StartupListener extends HttpServlet implements ServletContextListener {
	private static final Logger log = LoggerFactory.getLogger(StartupListener.class);
	
	public void contextInitialized(ServletContextEvent event) {
		new Thread(new StartupThread(event)).start();
		
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		
	}
	
	/**
	 * 网站启动监听器 线程
	 * @author mao 2012-6-8 下午11:11:00
	 */
	class StartupThread implements Runnable{
		ServletContextEvent event;
		public StartupThread(ServletContextEvent event) {
			this.event = event;
		}
		
		public void run() {
			try {
				int i = 0;
				for(i = 0; i < 10; i++){
					if(event != null){
						AppUtil.getInst().init(event.getServletContext());
						break;
					}else{
						Thread.sleep(200);
					}
				}
				if(event == null){
					log.error("网站启动监听器 处理失败!Servlet上下文对象为null");
				}
			} catch (InterruptedException e) {
				log.error("网站启动监听器异常!", e);
			}
		}
	}
	
}
