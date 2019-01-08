/**
 * 
 */
package com.chat.utility;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 * @author aparnareddychalla
 *
 */
public class ImageCompressUtility {

	public static BufferedImage resizeImage(BufferedImage bufferImage,int width,int height,int type) {
		BufferedImage bufferImg = new BufferedImage(width, height, type);
		
		Graphics2D graphics = bufferImg.createGraphics();
		
		graphics.drawImage(bufferImage, 0, 0, width, height, null);
		graphics.dispose();
		graphics.setComposite(AlphaComposite.Src);

		graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
	        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics.setRenderingHint(RenderingHints.KEY_RENDERING,
	        RenderingHints.VALUE_RENDER_QUALITY);
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	        RenderingHints.VALUE_ANTIALIAS_ON);
		
		return bufferImg;
	}
}
