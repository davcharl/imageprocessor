package com.imageprocessor.imageinfo;

import java.awt.Image;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.springframework.web.multipart.MultipartFile;

public class ImageData {
	
	private String imageDimension;
	private String imageSize;
	private String stringListOfReaders;
	private String[] tempListOfReaders; 
	
	public ImageData() {}
	
	public ImageData(MultipartFile myMultipartFile) {
		
		InputStream inputStream = null;
		ImageInputStream imageInputStream = null;
		String myFileType = myMultipartFile.getContentType();

		// //////////////////////////////////
		// 1) Read multipart to byte-array/multipart-buffer
		// //////////////////////////////////
		
		// create a multipartfileInputStream
		inputStream = createInputStream(myMultipartFile);	
		// create a byte-array from the multipartfile payload/image
		byte[] arrayFromMultipart = readMultipartToByteArray(myMultipartFile, imageInputStream);
		
		
		// sets the stringListOfReaders with the readers that can decode the image in the byte-array
		generateListOfReaders(inputStream);
		
		// set the image dimensions
		extractImageDimensions(arrayFromMultipart);
		
		// Convert byte-array to multipartfile
		
		// create a new multipart file with buffered image
		
	}

	
	// ///////////
	// ///////////
	// GETTERS & SETTERS
	// ///////////
	// ///////////

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

	public String getStringListOfReaders() {
		return stringListOfReaders;
	}

	public void setStringListOfReaders(String stringListOfReaders) {
		this.stringListOfReaders = stringListOfReaders;
	}

	public String[] getTempListOfReaders() {
		return tempListOfReaders;
	}

	public void setTempListOfReaders(String[] tempListOfReaders) {
		this.tempListOfReaders = tempListOfReaders;
	}
	
	
	// ///////////
	// ///////////
	// METHODS
	// ///////////
	// ///////////

	
	private void extractImageDimensions(byte[] multipartToByteArray) {
			
		BufferedImage tempBufferedImage = null;
		int myWidth = -1;
		int myHeight = -1;
		
		try {
		// Create byteArrayInputStream for byte-array that contains the extracted multipart payload
		InputStream bais = new ByteArrayInputStream(multipartToByteArray);
		tempBufferedImage = ImageIO.read(bais);
		bais.close();
		
		// Messing about
		// String bufferedImageAsString = bufferedImage.toString();
		// System.out.println("For a laugh: " + bufferedImageAsString);	
		} catch (IOException e) {
			
			System.out.println( "My Error: " + e.getMessage());
			e.printStackTrace();
		}
		
		// BufferedImage awt
		// ImageObserver
		
		if(tempBufferedImage != null) {
			myWidth = tempBufferedImage.getWidth(null);
			myHeight = tempBufferedImage.getHeight(null);
		}	
		
		setImageDimension(String.format("width: %d, height: %d", myWidth, myHeight));
		setImageSize(String.format("estimated image size: %dKB", (myWidth * myHeight * 3 * (2^8))/1024));
		
	}

	
	
	// //////////////////////////////////
	// 2) Return a list of readers that can decode the info in the byte array
	// //////////////////////////////////
	private void generateListOfReaders(InputStream multipartfileInputStream) {
		
		// ALT: imageIO processing attempt using ImageInputStream
		// 1) Create IIS to multipart
		// 2) Get reader(s) that can interpret the "object" in the buffer
		// 3) Iterate through the Readers
		ArrayList<ImageReader> listOfReaders = new ArrayList<ImageReader>();
		
		try {
			// create handler for input stream to passed multipart file
			ImageInputStream imageInputStream = ImageIO.createImageInputStream(multipartfileInputStream);
			
			// get image reader
			Iterator<ImageReader> iter = ImageIO.getImageReaders(imageInputStream) ;
			setTempListOfReaders(ImageIO.getReaderFileSuffixes());
			
			///////////////
			// DO I NEED A ImageReaderSpi TO MAKE THIS WORK???
			///////////////
			
			// Create list of readers
			while(iter.hasNext()) {
				// Get first reader available
				listOfReaders.add(iter.next());
			}
			
		} catch (IOException e1) {
			System.out.println("Error in myImageStream: " +  e1.getMessage() );
			e1.printStackTrace();
		}
		
		// Convert list of readers to stringList
		StringBuilder myTempString = new StringBuilder();
		for(ImageReader reader : listOfReaders) {
			try {
				myTempString.append( reader.getFormatName() );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		setStringListOfReaders(myTempString.toString());
	}
	
	
	
	// //////////////////////////////////
	// 1) Receive a multipartfile, extract image to byte-array and return it
	// //////////////////////////////////
	private byte[] readMultipartToByteArray(MultipartFile myMultipartFile, ImageInputStream imageInputStream) {

		InputStream multipartfileInputStream = null;
		byte[] multipartToByteArray = null;
			
		try {
			// Create an Input stream referencing the multipart
			multipartfileInputStream = myMultipartFile.getInputStream();
	
			// ESTIMATE of the number of bytes in the multipart and create the byteArray based on this figure
			multipartToByteArray = new byte[multipartfileInputStream.available()];
			
			// Read multipart to byte array
			multipartfileInputStream.read(multipartToByteArray);
			
		} catch (IOException e) {
			System.out.println("Error in myImageStream: " +  e.getMessage() );
			e.printStackTrace();
		}
		
		// Can put in a check to see if array is null/empty
		// If so create an empty one or basic one
		
		return multipartToByteArray;
		
	}
	
	// Create an InputStream for a MultipartFile
	private InputStream createInputStream(MultipartFile myMultipartFile) {

		InputStream multipartfileInputStream = null;
		try {
			// Create an Input stream referencing the multipart
			multipartfileInputStream = myMultipartFile.getInputStream();
			
		} catch (IOException e) {
			System.out.println("Error in myImageStream: " +  e.getMessage() );
			e.printStackTrace();
		}
		return multipartfileInputStream;
	}
	
	
}
