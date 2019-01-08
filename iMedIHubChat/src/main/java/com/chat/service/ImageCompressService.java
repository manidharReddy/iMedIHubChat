/**
 * 
 */
package com.chat.service;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.chat.utility.ImageCompressUtility;

/**
 * @author aparnareddychalla
 *
 */

public class ImageCompressService {
	
public static BufferedImage  resizeImage(int width,int height,File file) throws IOException {
	BufferedImage originalImage = ImageIO.read(file);
    int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB
        : originalImage.getType();
    
   return ImageCompressUtility.resizeImage(originalImage, width, height, type);
}
}
