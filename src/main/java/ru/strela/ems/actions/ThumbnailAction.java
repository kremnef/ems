package ru.strela.ems.actions;

/**
 * User: andrejkremnev
 * Date: 05.02.12
 * Time: 18:06
 */

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.tastika.tools.image.GraphicsUtilities;
//import ru.tastika.tools.util.INIFile

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ThumbnailAction {
    private final static Logger log = LoggerFactory.getLogger(ThumbnailAction.class);


    /**
     * Reads an image in a file and creates a thumbnail in another file.
     * largestDimension is the largest dimension of the thumbnail, the other dimension is scaled accordingly.
     * Utilises weighted stepping method to gradually reduce the image size for better results,
     * i.e. larger steps to start with then smaller steps to finish with.
     * Note: always writes a JPEG because GIF is protected or something - so always make your outFilename end in 'jpg'.
     * PNG's with transparency are given white backgrounds
     */
//	public String createThumbnail(String inFilename, String outFilename, int largestDimension)
    public String createThumbnail(File sourceFile, String outFilename, int largestDimension, String aspectRatio) throws IOException {
        try {
            log.warn("NEW FILE 1");
            double scale;
            int sizeDifference, originalImageLargestDim, originalImageSmallestDim;
            /*	if(!inFilename.endsWith(".jpg") && !inFilename.endsWith(".jpeg") && !inFilename.endsWith(".gif") && !inFilename.endsWith(".png"))
               {
                   return "Error: Unsupported image type, please only either JPG, GIF or PNG";
               }
               else
               {*/

//               BufferedImage inImage = GraphicsUtilities.getBufferedImage(sourceFile);

            BufferedImage inImage = GraphicsUtilities.getBufferedImage(sourceFile);
//				Image inImage = Toolkit.getDefaultToolkit().getImage(inFilename);
            if (inImage.getWidth(null) == -1 || inImage.getHeight(null) == -1) {
//                log.warn("NEW FILE 2");
                return "Error loading file: \"" + sourceFile.getName() + "\"";
            }
            else {
//                log.warn("NEW FILE 3");

                //find biggest dimension
                int width = inImage.getWidth();
                int height = inImage.getHeight();
                if (width > height) {
                    scale = (double) largestDimension / (double) width;
                    sizeDifference = width - largestDimension;
                    originalImageLargestDim = width;
                    originalImageSmallestDim = height;
//                    log.warn("NEW FILE 4");
                }
                else {
//                    log.warn("NEW FILE 5");
                    scale = (double) largestDimension / (double) height;
                    sizeDifference = height - largestDimension;
                    originalImageLargestDim = height;
                    originalImageSmallestDim = width;
                }
                //create an image buffer to draw to
                BufferedImage outImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB); //arbitrary init so code compiles
                Graphics2D g2d;
                AffineTransform tx;


                if (scale < 1.0d) {//only scale if desired size is smaller than original

                    if (aspectRatio.equalsIgnoreCase("16:9")) {
//                          log.warn("16:9");
                        outImage = Scalr.resize(inImage, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH, largestDimension, Scalr.OP_ANTIALIAS);
                        inImage.flush();
//                          log.warn("16:9 -2");
                    }
                    else if (aspectRatio.equalsIgnoreCase("1:1")) {
//                        log.warn("SQUARE -1");
//                        double scaleFactor = (double) largestDimension / minDimension;
//                        image = GraphicsUtilities.scaleBufferedImage(image, scaleFactor);
                        int largestDimensionBeforeCrop = (int) Math.ceil((double) (originalImageLargestDim * largestDimension) / (double) originalImageSmallestDim);

                        outImage = Scalr.resize(inImage, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH, largestDimensionBeforeCrop, Scalr.OP_ANTIALIAS);
                        int x, y;
                        width = outImage.getWidth();
                        height = outImage.getHeight();
                        if (width > height) {
                            x = (width - largestDimension) / 2;
                            y = 0;
                        }
                        else {
                            x = 0;
                            y = (height - largestDimension) / 2;
                        }
                        outImage = Scalr.crop(outImage, x, y, largestDimension, largestDimension, Scalr.OP_ANTIALIAS);
                        log.warn("SQUARE -2");
                        inImage.flush();
                        //outImage.flush();
                        //outImage = resImage;

                    }
                    /*} else {

                        int numSteps = sizeDifference / 100;
                        int stepSize = sizeDifference / numSteps;
                        int stepWeight = stepSize / 2;
                        int heavierStepSize = stepSize + stepWeight;
                        int lighterStepSize = stepSize - stepWeight;
                        int currentStepSize, centerStep;
                        double scaledW = inImage.getWidth(null);
                        double scaledH = inImage.getHeight(null);
                        if (numSteps % 2 == 1) //if there's an odd number of steps
                            centerStep = (int) Math.ceil((double) numSteps / 2d); //find the center step
                        else
                            centerStep = -1; //set it to -1 so it's ignored later
                        Integer intermediateSize = originalImageLargestDim, previousIntermediateSize = originalImageLargestDim;
                        Integer calculatedDim;
                        for (Integer i = 0; i < numSteps; i++) {
                            if (i + 1 != centerStep) //if this isn't the center step
                            {
                                if (i == numSteps - 1) //if this is the last step
                                {
                                    //fix the stepsize to account for decimal place errors previously
                                    currentStepSize = previousIntermediateSize - largestDimension;
                                } else {
                                    if (numSteps - i > numSteps / 2) //if we're in the first half of the reductions
                                        currentStepSize = heavierStepSize;
                                    else
                                        currentStepSize = lighterStepSize;
                                }
                            } else //center step, use natural step size
                            {
                                currentStepSize = stepSize;
                            }
                            intermediateSize = previousIntermediateSize - currentStepSize;
                            scale = (double) intermediateSize / (double) previousIntermediateSize;


                            scaledW = (int) scaledW * scale;
                            scaledH = (int) scaledH * scale;
                            outImage = new BufferedImage((int) scaledW, (int) scaledH, BufferedImage.TYPE_INT_RGB);
                            g2d = outImage.createGraphics();
                            g2d.setBackground(Color.WHITE);
                            g2d.clearRect(0, 0, outImage.getWidth(), outImage.getHeight());
                            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                            tx = new AffineTransform();
                            tx.scale(scale, scale);
                            g2d.drawImage(inImage, tx, null);
                            g2d.dispose();
                            inImage = new ImageIcon(outImage).getImage();
                            previousIntermediateSize = intermediateSize;
                        }


                    }*/
                } else {
                    //just copy the original
                    outImage = new BufferedImage(inImage.getWidth(null), inImage.getHeight(null), BufferedImage.TYPE_INT_RGB);
                    g2d = outImage.createGraphics();
                    g2d.setBackground(Color.WHITE);
                    g2d.clearRect(0, 0, outImage.getWidth(), outImage.getHeight());
                    tx = new AffineTransform();
                    tx.setToIdentity(); //use identity matrix so image is copied exactly
                    g2d.drawImage(inImage, tx, null);
                    g2d.dispose();
                }
                //JPEG-encode the image and write to file.
                File thumbnailsDir = new File(sourceFile.getParentFile(), "thumbnails");
                boolean dirExist = true;
                if (!thumbnailsDir.isDirectory()) {
                    dirExist = thumbnailsDir.mkdirs();
                }
//                File previewFile = new File(sourceFile.getParentFile(), outFilename);
                if (dirExist) {
                    File previewFile = new File(thumbnailsDir, outFilename);
                    OutputStream os = new FileOutputStream(previewFile);


                    JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
                    JPEGEncodeParam ep = encoder.getDefaultJPEGEncodeParam(outImage);
                    ep.setQuality(1f, true);
                    encoder.encode(outImage, ep);
                    os.close();
                }
            }
//			}
        }
        catch (Exception ex) {
            ex.printStackTrace();
            StringBuilder errorMsg = new StringBuilder("");
            errorMsg.append("<br/>Exception: ").append(ex.toString());
            errorMsg.append("<br/>Cause = ").append(ex.getCause());
            errorMsg.append("<br/>Stack Trace = ");
            StackTraceElement stackTrace[] = ex.getStackTrace();
            for (StackTraceElement aStackTrace : stackTrace) {
                errorMsg.append("<br/>").append(aStackTrace);
            }
            return errorMsg.toString();
        }

        return ""; //success
    }
}