package com.mao.packpro;

import java.io.File;

import com.mao.cmpr.ZipCmpr;
import com.mao.lang.Timer;
import com.mao.ssh2.Ssh2Util;

/***
 * 项目发布 基类
 * @author Mao 2015年11月27日 下午4:47:12
 */
public class Publish_Pro_Base {
	
	public String remote_dir = "";
	public String root_pw = "";
	public String build_sh = "";
	public String ck_key = "";
	public String pro_zip_path = "";
	public String[] cmpr_filter_Dir = null;
	
	/** 打包项目 */
	public String pack(){
		System.out.println("正在打包项目...");
		String pro_zip = packRootZip();
		System.out.println("打包项目:" + pro_zip);
		System.out.println("项目大小:" + (new File(pro_zip).length() / 1024));
		return pro_zip;
	}
	
	/** 上传项目 */
	public void upload(){
		System.out.println("正在上传项目...:" + pro_zip_path);
		Ssh2Util.getInst().sftp_upload(remote_dir, pro_zip_path);
		System.out.println("项目上传完毕!");
	}
	
	/** 发布项目 */
	public void publish() throws Exception {
		System.out.println("正在发布项目...");
		String rstr = null;
		String tomcat_pid = null;
		
		Ssh2Util.getInst().shell_cmd("su");
		Ssh2Util.getInst().shell_cmd(root_pw);
		Ssh2Util.getInst().shell_cmd("cd " + remote_dir);
		rstr = Ssh2Util.getInst().shell_cmd(build_sh);
		tomcat_pid = Ssh2Util.check_tomcat_pid(rstr, ck_key);
		if(Ssh2Util.isNotEmpty(tomcat_pid)){
			Ssh2Util.getInst().shell_cmd(tomcat_pid);
		}else{
			rstr = Ssh2Util.getInst().shell_read_stream_str(4000, 4000);
			tomcat_pid = Ssh2Util.check_tomcat_pid(rstr, ck_key);
			if(Ssh2Util.isNotEmpty(tomcat_pid)){
				Ssh2Util.getInst().shell_cmd(tomcat_pid);
			}else{
				Ssh2Util.getInst().shell_cmd("\n");
			}
		}
		for(int ii = 0; ii<5; ii++){
			Ssh2Util.getInst().shell_read_stream_str(5000, 5000);
			Thread.sleep(800);
		}
		
		Ssh2Util.getInst().shell_cmd(Ssh2Util.Ctrl_C);
		
		Ssh2Util.getInst().close();
	}
	
	/**
	 * 将jshop2项目压缩成zip包, 并排除 lib和js文件夹, 以减少文件大小
	 * @return 返回压缩包路径
	 */
	public String packRootZip(){
		String pro_dir = getWebRoot();//"E:/pro/java/tomcats-jxgc/tomcat-8080-jshop2/webapps/ROOT";
		String pro_zip = pro_zip_path;
		packRootZip(pro_dir, pro_zip);
		return pro_zip;
	}
	
	/**
	 * 将jshop2项目压缩成zip包, 并排除 lib和js文件夹, 以减少文件大小
	 * @param pro_dir (String) tomcat下的ROOT文件夹, <br>
	 * 	如: "E:/pro/java/tomcats-jxgc/tomcat-8080-jshop2/webapps/ROOT" <br>
	 *  或使用 getWebRoot(), 从项目中获取。
	 * @param pro_zip (String) 临时存放压缩文件, 如: "/temp/jshop2/ROOT.zip"
	 * @return 返回压缩包路径
	 */
	public String packRootZip(String pro_dir, String pro_zip){
		Timer t = new Timer();
		ZipCmpr cmpr = new ZipCmpr(pro_zip);
		if(cmpr_filter_Dir != null && cmpr_filter_Dir.length > 0){
			String fdn = "";
			for(int i = 0; i < cmpr_filter_Dir.length; i++){
				fdn = cmpr_filter_Dir[i];
				if(fdn != null && fdn.length() > 0){
					cmpr.exclDir(fdn);
				}
			}
		}
		cmpr.exclDirName(".svn");
		try {
			cmpr.createZipOut();
			cmpr.packToolFiles(pro_dir, "ROOT");
			cmpr.closeZipOut();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		t.printLastTimeElapsed("all");
		return pro_zip;
	}
	
	/**
	 * 获取项目WebRoot路径, 如:"E:/java/m10works/jxgc/jshop2/WebRoot/"
	 */
	public static String getWebRoot(){
		String path = getClassesPath();
		int ix = path.lastIndexOf("WEB-INF");
		if(ix > 0){
			path = path.substring(0, ix);
		}
		System.out.println(path);
		return path;
	}
	
	/**
	 * 获取当前classes路径, 如:"E:/java/m10works/jxgc/jshop2/WebRoot/WEB-INF/classes/"
	 */
	public static String getClassesPath(){
		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		int ix = path.indexOf(':');
		if(ix > 0){
			path = path.substring(ix - 1);
		}
		System.out.println(path);
		return path;
	}
}
