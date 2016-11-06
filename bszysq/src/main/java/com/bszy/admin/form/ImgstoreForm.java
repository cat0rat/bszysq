package com.bszy.admin.form;

import org.springframework.web.multipart.MultipartFile;


public class ImgstoreForm {
	
	private MultipartFile file;		// 文件

	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
}
