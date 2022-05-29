package com.imageprocessor.imageinfo;

import org.springframework.web.multipart.MultipartFile;

public class Metadata {
	
	private String imageDimension;
	private long imageSize;

	
	public Metadata() {}
	
	public Metadata(MultipartFile file) {
		
		imageSize = file.getSize()/1024;
		setImageSize(imageSize);
		setImageDimension("256x256");
		

	}

	public String getImageDimension() {
		return this.imageDimension;
	}

	private void setImageDimension(String imageDimension) {
		this.imageDimension = imageDimension;
	}

	public String getImageSize() {
		return "+" + this.imageSize + "KB";
	}

	private void setImageSize(Long imageSize) {
		this.imageSize = imageSize;
	}

	public void setImageSize(long imageSize) {
		this.imageSize = imageSize;
	}


}
