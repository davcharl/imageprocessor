package com.imageprocessor.imageinfo;

import org.springframework.web.multipart.MultipartFile;

public class Metadata {

	private long imageSize;

	
	public Metadata() {}
	
	public Metadata(MultipartFile file) {

		setImageSize(file.getSize()/1024);

	}


	public String getImageSize() {
		return "+" + this.imageSize + "KB";
	}

	public void setImageSize(long imageSize) {
		this.imageSize = imageSize;
	}


}
