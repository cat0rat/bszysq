package com.bszy.admin.srv;

import java.io.File;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.mao.lang.DateUtil;
import com.mao.upimg.UpImg;

/**
 * 上传服务工具类
 * @author Mao 2016年7月19日 下午11:49:23
 */
public class UpSrv {

	/**
	 * 处理上传的处理 图片
	 * @param imgFile
	 * @param request
	 */
	public static String deal_up(MultipartFile mfile, HttpServletRequest request) throws Exception{
		String url = null;
		if(mfile != null && !mfile.isEmpty()){
			String dir = DateUtil.getDateStr(new Date(), "yyyyMM");
			String newFn = UUID.randomUUID().toString() + mfile.getOriginalFilename();
			String newFpath = dir + File.separator + newFn;
			byte[] bytes = mfile.getBytes();
			UpImg upimg = new UpImg();
			url = upimg.upload(bytes, newFpath);
		}
		//ar.t_succ_not_null(url, "1601");
		return url;
	}
}
