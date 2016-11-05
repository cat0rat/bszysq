package com.mao.ssh2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.mao.lang.MIniUtil;
import com.mao.lang.Timer;

/**
 * Ssh2工具类
 * @author Mao 2015年11月14日 下午7:54:27
 */
public class Ssh2Util {
	
	private static final Logger logger = LoggerFactory.getLogger(Ssh2Util.class);
	
	public static final int Ctrl_C = 3;
	
	private static final String config_path = "/ssh2.properties";
	private final static Boolean single_block = Boolean.TRUE;
	private static Ssh2Util Ssh2_Util;
	/** 单例 */
	public static Ssh2Util getInst(){
		if(Ssh2_Util == null){
			synchronized (single_block) {
				if(Ssh2_Util == null){
					MIniUtil config = new MIniUtil(getRealPath(config_path));
//					Ssh2Config config = new Ssh2Config();
					String host = config.getValue("ssh2_host");
					int port = toInt(config.getValue("ssh2_port"), 22);
					String username = config.getValue("ssh2_username");
					String password = config.getValue("ssh2_password");
					Ssh2_Util = new Ssh2Util(host, port, username, password);
				}
			}
		}
		return Ssh2_Util;
	}
	/** 重新加载单例 */
	public static void reloadInst(){
		Ssh2_Util = null;
		getInst();
	}
	
	/** 单例，会话 */
	public static Session getSessionInst() throws JSchException{
		return getInst().session();
	}
	
	/** 单例，SFTP文件上传框 */
	public static ChannelSftp getSftpInst() throws JSchException{
		return getInst().sftp();
	}
	
	protected JSch ssh2;			// SSH2对象
	protected Session session;		// 会话
	protected ChannelSftp sftp;		// SFTP文件上传框
	protected ChannelExec exec;		// 命令行
	protected ChannelShell shell;	// Shell命令
	
	protected String host;		// 主机
	protected int port;			// 端口
	protected String username;	// 用户名
	protected String password;	// 密码
	
	/**
	 * 构造器
	 * @param host (Stirng) 主机
	 * @param port (Stirng) 端口
	 * @param username (Stirng) 用户名
	 * @param password (Stirng) 密码
	 */
	public Ssh2Util(String host, int port, String username, String password){
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
	}
	
	/** 获取SSH2对象, 可能未打开会话 */
	public JSch ssh2(){
		if(ssh2 == null){
			synchronized (single_block) {
				if(ssh2 == null){
					ssh2 = new JSch();
					logger.info("创建SSH2对象.");
				}
			}
		}
		return ssh2;
	}
	
	/** 获取一个对话 */
	public Session session() throws JSchException{
		if(session == null){
			synchronized (single_block) {
				if(session == null){
					session = ssh2().getSession(username, host, port);
					logger.info("创建会话.");
					session.setPassword(password);
					Properties sshConfig = new Properties();
					sshConfig.put("StrictHostKeyChecking", "no");
					session.setConfig(sshConfig);
					session.connect();
					logger.info("连接会话.");
				}
			}
		}
		return session;
	}
	
	/** 获取SFTP, 用于文件上传 */
	public ChannelSftp sftp() throws JSchException{
		if(sftp == null){
			synchronized (single_block) {
				if(sftp == null){
					sftp = (ChannelSftp)session().openChannel("sftp");
					logger.info("打开SFTP文件上传框.");
					sftp.connect();
					logger.info("连接SFTP文件上传框.");
				}
			}
		}
		return sftp;
	}
	
	/** 获取命令行 */
	public ChannelExec exec() throws JSchException{
		if(exec == null){
			synchronized (single_block) {
				if(exec == null){
					exec = (ChannelExec)session().openChannel("exec");
					logger.info("打开命令行.");
					//exec.connect();
					logger.info("连接命令行.");
				}
			}
		}
		return exec;
	}
	
	/** 获取shell命令 */
	public ChannelShell shell() throws JSchException{
		if(shell == null){
			synchronized (single_block) {
				if(shell == null){
					shell = (ChannelShell)session().openChannel("shell");
					logger.info("打开shell命令.");
					shell.connect();
					logger.info("连接shell命令.");
				}
			}
		}
		return shell;
	}
	
	/** 关闭shell命令 */
	public void closeShell(){
		if(shell != null){
			try {
				shell.disconnect();
			} catch (Exception e) {
				logger.error("关闭shell命令异常", e);
			}finally{
				shell = null;
			}
		}
	}
	
	/** 关闭命令行 */
	public void closeExec(){
		if(exec != null){
			try {
				exec.disconnect();
			} catch (Exception e) {
				logger.error("关闭命令行异常", e);
			}finally{
				exec = null;
			}
		}
	}
	
	/** 关闭SFTP文件上传框 */
	public void closeSftp(){
		if(sftp != null){
			try {
				sftp.disconnect();
				sftp.quit();
				sftp.exit();
			} catch (Exception e) {
				logger.error("关闭SFTP文件上传框异常", e);
			}finally{
				sftp = null;
			}
		}
	}
	
	/** 关闭会话 */
	public void closeSession(){
		if(session != null){
			try {
				session.disconnect();
			} catch (Exception e) {
				logger.error("关闭会话异常", e);
			}finally{
				session = null;
			}
		}
	}
	
	/** 关闭SSH2对象 */
	public void closeSsh2(){
		if(ssh2 != null){
			ssh2 = null;
		}
	}
	
	/** 关闭 */
	public void close(){
		closeShell();
		closeExec();
		closeSftp();
		closeSession();
		closeSsh2();
	}
	
	// TODO 功能方法 （文件上传）
	
	/**
	 * 上传文件
	 * @param dir (String) 上传到的目录
	 * @param uploadFile (String) 要上传的文件
	 */
	public boolean sftp_upload(String dir, String uploadFile) {
		try {
			if(isNotEmpty(dir)) sftp().cd(dir);
			File file = new File(uploadFile);
			sftp().put(new FileInputStream(file), file.getName());
			return true;
		} catch (Exception e) {
			logger.error("上传文件异常", e);
		}
		return false;
	}
	
	/** 上传文件 */
	public boolean sftp_upload(File file) {
		try {
			sftp().put(new FileInputStream(file), file.getName());
			return true;
		} catch (Exception e) {
			logger.error("上传文件异常", e);
		}
		return false;
	}
	
//	/**
//	 * 上传文件夹
//	 * @param dir (File) 要上传的文件夹
//	 * @param bork (boolean) true: 若某个文件上传失败即中断上传
//	 */
//	public boolean sftp_upload_dir(File dir, boolean bork) {
//		try {
//			sftp().put(new FileInputStream(file), file.getName());
//			return true;
//		} catch (Exception e) {
//			logger.error("上传文件异常", e);
//		}
//		return false;
//	}
	
	/**
	 * 删除文件
	 * @param dir (String) 要删除文件所在目录
	 * @param deleteFile (String) 要删除的文件
	 */
	public void sftp_delete(String dir, String deleteFile) {
		try {
			if(isNotEmpty(dir)) sftp().cd(dir);
			sftp().rm(deleteFile);
		} catch (Exception e) {
			logger.error("删除文件异常", e);
		}
	}
	
	/**
	 * 下载文件
	 * @param dir (String) 下载目录
	 * @param downloadFile (String) 下载的文件
	 * @param saveFile (String) 存在本地的路径
	 */
	public void sftp_download(String dir, String downloadFile, String saveFile) {
		try {
			if(isNotEmpty(dir)) sftp().cd(dir);
			File file = new File(saveFile);
			sftp().get(downloadFile, new FileOutputStream(file));
		} catch (Exception e) {
			logger.error("下载文件异常", e);
		}
	}
	
	// TODO 功能方法 （命令行）
	
	/** 执行命令, oa[0]:成功失败, oa[1]:执行结果 */
	public Object[] exec_cmd(String cmd){
		return exec_cmd(cmd, 2000);
	}
	
	/** 执行命令, oa[0]:成功失败, oa[1]:执行结果 */
	public Object[] exec_cmd(String cmd, long timeout){
		Object[] oa = {false, null};
		BufferedReader reader = null;
		try {
			exec().setCommand(cmd);
			
			exec().setInputStream(null);
			exec().setErrStream(System.err);
			
			exec().connect();
			
			InputStream in = exec().getInputStream();
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder sb = new StringBuilder();
			String buf = null;
			while ((buf = reader.readLine()) != null) {
				sb.append(buf);
			}
			oa[0] = true;
			oa[1] = sb.toString();
		} catch (Exception e) {
			logger.error("执行命令异常", e);
		}finally{
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
		}
		return oa;
	}
	
	// TODO 功能方法 （shell命令）
	
	/** 执行shell命令, oa[0]:成功失败, oa[1]:执行结果 */
	public String shell_cmd(String cmd)throws Exception{
		return shell_cmd(cmd, 2000);
	}
	
	/** 执行shell命令, oa[0]:成功失败, oa[1]:执行结果 */
	public String shell_cmd(String cmd, long timeout) throws Exception{
		Timer t = new Timer();
		t.setPrint(false);
		//获取输入流和输出流
		InputStream instream = shell().getInputStream();
		OutputStream outstream = shell().getOutputStream();
		t.printLastTimeElapsed("1");
		wait_stream(instream, timeout);
		t.printLastTimeElapsed("2");
		read_stream(instream);
		t.printLastTimeElapsed("3");

		// 发送需要执行的SHELL命令，需要用\n结尾，表示回车
		//cmd = "ps -ef|grep java";
		if(!(cmd.lastIndexOf('\n') > 0)) cmd += "\n";
		outstream.write(cmd.getBytes());
		outstream.flush();
		t.printLastTimeElapsed("4");
		
        //获取命令执行的结果
		wait_stream(instream, timeout);
		t.printLastTimeElapsed("5");
		String rstr = read_stream_str(instream, null);
		t.printLastTimeElapsed("6");
		System.out.println(rstr);
        
		return rstr;
	}
	
	/** 执行shell命令, 返回 执行结果 */
	public String shell_cmd(int cmd)throws Exception{
		return shell_cmd(cmd, 2000);
	}
	
	/** 执行shell命令, 返回 执行结果 */
	public String shell_cmd(int cmd, long timeout) throws Exception{
		//获取输入流和输出流
		InputStream instream = shell().getInputStream();
		OutputStream outstream = shell().getOutputStream();
		wait_stream(instream, timeout);
		read_stream(instream);

		// 发送需要执行的SHELL命令，需要用\n结尾，表示回车
		//cmd = "ps -ef|grep java";
		outstream.write(cmd);
		outstream.flush();
		
        //获取命令执行的结果
		wait_stream(instream, timeout);
		String rstr = read_stream_str(instream, null);
		System.out.println(rstr);
        
		return rstr;
	}
	
	/**
	 * 获取命令执行的结果
	 * @param timeout (long) 等待超时时间
	 * @param sleep (long) 睡眠时间(每隔)
	 */
	public String shell_read_stream_str(long timeout, long sleep) throws Exception{
		//获取输入流
		InputStream instream = shell().getInputStream();
		wait_stream(instream, timeout);
		String rstr = null;
		StringBuilder sb = new StringBuilder();
		//获取命令执行的结果
		while((rstr = read_stream_str(instream, null)) != null){
			sb.append(rstr);
			System.out.println(rstr);
			Thread.sleep(sleep);
		}
		return sb.length() > 0 ? sb.toString() : null;
	}
	
	/**
	 * 等待流的响应
	 * @param instream (InputStream)
	 * @param to (long) 超时时间
	 * @return true: 流响应; false: 流未响应或超时
	 */
	public static boolean wait_stream(InputStream instream, long to){
		boolean af = false;
		long toi = to / 10;
		toi = toi > 500 ? 500 : (toi < 20 ? 20 : toi); 
		long toc = 0;
		int p_c = 0;
		int c_c = 0;
		while(c_c > 0 || toc < to){
			toc += toi;
			try {
				c_c = instream.available();
				if(c_c > 0){	// 如果有数据
//					System.out.println("p_c:" + p_c);
//					System.out.println("c_c:" + c_c);
					if(c_c > p_c){	// 如果数据增加了, 说明还在执行, 不再增加了, 则执行完毕(实际可能不是)
						p_c = c_c;
					}else{
						af = true;
						break;
					}
				}
			} catch (Exception e) {
				break;
			}
			try {
				Thread.sleep(toi);
			} catch (Exception e) {
			}
		}
		return af;
	}
	
	/** 读流  */
	public static byte[] read_stream(InputStream instream) throws Exception {
		int nLen = instream.available();
		if(nLen < 1) return null;
		byte[] data =  new byte[nLen];
		nLen = instream.read(data);
        if (nLen < 0) {
            throw new Exception("network error.");
        }
		return data;
	}
	
	/** 读流  */
	public static String read_stream_str(InputStream instream, String cs) throws Exception {
		byte[] data = read_stream(instream);
		String str = null;
		if(data != null && data.length > 0){
			if(cs == null || cs.length() == 0) cs = "ISO8859-1";
			str = new String(data, 0, data.length, cs);
		}
		return str;
	}
	
	
	// TODO 辅助方法
	
	/** 字符串转int */
	public static int toInt(String str, int def){
		if(str != null && str.length() > 0){
			try {
				def = Integer.parseInt(str);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return def;
	}
	
	/** true: 字符串不为null及"" */
	public static boolean isNotEmpty(String str){
		return str != null && str.length() != 0;
	}
	
	// TODO 方法
	
	/** 获取类根路径 */
	public static String getClassRoot(){
		String path = Ssh2Util.class.getResource("/").getPath();
		if(path != null && path.length() > 1){
			if(path.indexOf(':') > 0){
				return path.substring(1);
			}
			return path;
		}
		return "/";
	}
	
	/** 获取真实的路径 */
	public static String getRealPath(String path){
		if(path.charAt(0) == '/'){
			path = getClassRoot() + path.substring(1);
		}else if(path.charAt(1) != ':'){
			path = getClassRoot() + path;
		}
		return path;
	}
	
	/**获取当前项目在Tomcat下根路径，格式为：E:/workspace/jee_workspace/heifp/*/
	public static String getProjectRoot(){
		String root = getClassRoot();
		return root.substring(0, root.indexOf("WEB-INF/classes/"));
	}
	
	// TODO 测试

	public static void main(String[] args) throws Exception {
//		test();
//		check_tomcat_pid("qqq");
	}
	
	public static void test() throws Exception {
		String dir = "/home/pwifi/jshop2/temp/";
		String uploadFile = "E:\\temp\\Mao.js";
		String deleteFile = "Mao.js";
		String downloadFile = "Mao.js";
		String saveFile = "E:\\temp\\Mao3.js";
//		Ssh2Util.getInst().sftp_upload(dir, uploadFile);
//		Ssh2Util.getInst().sftp_delete(dir, deleteFile);
//		Ssh2Util.getInst().sftp_download(dir, downloadFile, saveFile);
		
		String ck_key = "\\/tomcat-jshop2\\/";
		String rstr = null;
		String tomcat_pid = null;
		
		Ssh2Util.getInst().shell_cmd("su");
		Ssh2Util.getInst().shell_cmd("111");
		Ssh2Util.getInst().shell_cmd("cd /home/pwifi/jshop2");
		rstr = Ssh2Util.getInst().shell_cmd("./jshop2_build.sh");
		tomcat_pid = check_tomcat_pid(rstr, ck_key);
		if(isNotEmpty(tomcat_pid)){
			Ssh2Util.getInst().shell_cmd("kill -9 " + tomcat_pid);
		}else{
			rstr = Ssh2Util.getInst().shell_read_stream_str(2000, 2000);
			tomcat_pid = check_tomcat_pid(rstr, ck_key);
			if(isNotEmpty(tomcat_pid)){
				Ssh2Util.getInst().shell_cmd(tomcat_pid);
			}else{
				Ssh2Util.getInst().shell_cmd("\n");
			}
		}
		Ssh2Util.getInst().shell_read_stream_str(3000, 10000);
		
		Thread.sleep(10000);
		Ssh2Util.getInst().shell_cmd(Ctrl_C);
		
		Ssh2Util.getInst().close();
	}
	
	/**
	 * 检测tomcat的pid
	 * @param str (String) 文本集
	 * @param ck_key (String) 关键字, 如:<code>\\/tomcat-jshop2\\/</code>
	 */
	public static String check_tomcat_pid(String str, String ck_key){
		String pid = null;
		if(str != null && str.length() > 0){
			Pattern pattern = Pattern.compile("root\\s*?(\\d{1,}).*?" + ck_key);
			Matcher matcher = pattern.matcher(str);
			if(matcher.find()){
				pid = matcher.group(1);
				System.out.println("pid:" + pid);
			}
		}
		return pid;
	}
	
	
	
	
}
