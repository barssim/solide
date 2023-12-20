package utils;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
//import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.*;
import org.krysalis.barcode4j.tools.*;
/**
 * This example demonstrates creating a bitmap barcode using the bean API.
 * 
 * @author Jeremias Maerki
 * @version $Id: SampleBitmapBarcodeWithBean.java,v 1.2 2006/11/07 16:45:28 jmaerki Exp $
 */
public class BarcodeGenerator {
	

	 public static String generateBarcode(String number) throws IOException {
		 BitmapCanvasProvider canvas = null;
		 String b64 = null;
	        try {
            //Create the barcode bean
        	 Code39Bean bean = new Code39Bean();
            final int dpi = 150;

            //Configure the barcode generator
            bean.setModuleWidth(UnitConv.in2mm(1.0f / dpi)); //makes the narrow bar 
                                                             //width exactly one pixel
            bean.setWideFactor(3);
            bean.doQuietZone(false);

            //Open output file
//            File outputFile = new File("out.jpg");
//            OutputStream out = new FileOutputStream(outputFile);
//            try {
                //Set up the canvas provider for monochrome JPEG output 
            	 	
                 canvas = new BitmapCanvasProvider(
                		 dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
                
                //Generate the barcode
                bean.generateBarcode(canvas, number);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write( canvas.getBufferedImage(), "jpg", baos );
                baos.flush();
                byte[] imageInByteArray = baos.toByteArray();
                baos.close();
                b64 = javax.xml.bind.DatatypeConverter.printBase64Binary(imageInByteArray);
                
                //Signal end of generation
                
            } catch (Exception e) {
            	e.printStackTrace();
            
            } finally {
            	canvas.finish();
//                out.close();
            }
	       return  b64;
	 }  
	 
	
}