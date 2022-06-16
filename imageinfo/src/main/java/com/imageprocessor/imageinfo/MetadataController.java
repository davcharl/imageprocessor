package com.imageprocessor.imageinfo;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MetadataController {

	/*
	@GetMapping("/upload")
	public String upload(@RequestParam(value="imagedimension", defaultValue="1x1") String imageDimension,
			@RequestParam(value="imagesize", defaultValue="1KB") String imageSize ) {
		// Metadata myMeta = new Metadata("200x400","243KB");
		Metadata myMeta = new Metadata(imageDimension, imageSize);
		return String.format("dimension: %s; size: %s", myMeta.getImageDimension(), myMeta.getImageSize());
	}
	*/
	
	@PostMapping("/upload")
	// @RequestParam("file") MultipartFile file
	// @RequestParam(value="imagedimension") Image image
	public String upload( @RequestParam("file") MultipartFile file ) {
		if(file!=null) {
			
			Metadata myMetaData = new Metadata(file);
			ImageData myImageData = new ImageData(file);
			
			String fileName = file.getOriginalFilename();
			String fileContentType = file.getContentType();
			String fileSize = myMetaData.getImageSize();
			
			String imageDimension = myImageData.getImageDimension();
			String imageSize = myImageData.getImageSize();
			String listOfReaders = myImageData.getStringListOfReaders();
			
			// String imageMatrix = myImageData.getArrayFromMultipartAsString();
			String imageMatrix = myImageData.getModifiedArrayAsString();
			
			
			
			
			return( "\nFile name: " + fileName +
					"\nFile content type: " + fileContentType +
					"\nFile size from Meta: " + fileSize + 
					"\nImage dimension from myImageData: " + imageDimension + 
					"\nImage size from myImageData: " + imageSize +
					"\nList of readers: " + listOfReaders + 
					"\nModified matrix with " + myImageData.getCounter() +" elements to follow:\n" + imageMatrix);
			
			/*
			// ArrayList<Byte> image = new ArrayList<Byte>();
			byte[] image = null;
			try {
				image = file.getBytes();
				return "File name: " + fileName +
						" File content type: " + fileContentType +
						" File dimensions: " + fileDimension +
						" File size: " + fileSize;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "exception has occurred";
			}
			*/
			
			// Image image = (Image)file ;
			// ImageData myImageData = new ImageData(image);
			// return String.format("%s %s", myImageData.getImageDimension(), myImageData.getImageSize());
			// return String.format("%s %s", result, result2);
		} else {
			return "No picture was sent";
		}
		
	}
}
