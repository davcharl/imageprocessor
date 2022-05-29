package com.imageprocessor.imageinfo;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.springframework.web.multipart.MultipartFile;

public class ImageData {
	
	private String imageDimension;
	private String imageSize;
	
	public ImageData() {}
	
	public ImageData(MultipartFile myMultipartFile) {
		
		// //////////////////////////////////
		// 1) Read multipart to memory buffer
		// //////////////////////////////////
		
		// Create an Input stream referencing the multipart
		ImageInputStream imageInputStream = null;
		InputStream multipartfileInputStream = null;
		byte[] imageByteArray = null;
		BufferedImage tempBufferedImage = null;
		// Image tempImage = null;
		String myFileType = myMultipartFile.getContentType();
		
		try {
			// create an input stream
			multipartfileInputStream = myMultipartFile.getInputStream();
			// try conversion to byte[] as input to imageInputStream
			// ESTIMATE of the number of bytes in the multipart and create the byteArray
			imageByteArray = new byte[multipartfileInputStream.available()];
			
			// Read multipart to byte array
			multipartfileInputStream.read(imageByteArray);
			
			
			
		} catch (IOException e) {
			System.out.println("Error in myImageStream: " +  e.getMessage() );
			e.printStackTrace();
		} 
		
		
		
		try {
			// create handler for input stream to passed multipart file
			imageInputStream = ImageIO.createImageInputStream(multipartfileInputStream);  // myMultipartFile.getInputStream();
			
			// get image reader
			Iterator<ImageReader> iter = ImageIO.getImageReaders(imageInputStream);
			
			while(iter.hasNext()) {
				ImageReader reader = iter.next();
				System.out.println("Format: " + reader.getFormatName());
			}
			// close stream
			// imageInputStream.close();
			
			
			
			
		} catch (IOException e1) {
			System.out.println("Error in myImageStream: " +  e1.getMessage() );
			e1.printStackTrace();
		} 
		
		 
		// //////////////////////////////////
		// 2) IN-MEMORY: Convert array to Image
		// //////////////////////////////////
		
		int myWidth = -1;
		int myHeight = -1;
		try {
			//returns a buffered image
			//tempBufferedImage = ImageIO.read(imageInputStream);
			
			// Create handler to inputStream to Array
			InputStream bais = new ByteArrayInputStream(imageByteArray);
			// Read array to a bufferdImage (sub Image)
			// ImageReader myImageReader = null;
			
			tempBufferedImage = ImageIO.read(bais);
			
			
			// Messing about
			// String bufferedImageAsString = bufferedImage.toString();
			// System.out.println("For a laugh: " + bufferedImageAsString);
			
			
			
		} catch (IOException e) {
			
			System.out.println( "My Error: " + e.getMessage());
			e.printStackTrace();
			
		}
		// BufferedImage awt
		// ImageObserver
		
		if(!(tempBufferedImage == null)) {
			myWidth = tempBufferedImage.getWidth(null);
			myHeight = tempBufferedImage.getHeight(null);
		} else {
			myWidth = 20;
			myHeight = 30;
		}
		
		
		setImageDimension(String.format("%dx%d", myWidth, myHeight));
		setImageSize(String.format("%dKB", (myWidth * myHeight * 3 * (2^8))/1024));
		
		// close stream
		try {
			imageInputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public String getImageDimension() {
		return this.imageDimension;
	}
	private void setImageDimension(String imageDimension) {
		this.imageDimension = imageDimension;
	}
	public String getImageSize() {
		return this.imageSize;
	}
	private void setImageSize(String imageSize) {
		this.imageSize = imageSize;
	}

}
