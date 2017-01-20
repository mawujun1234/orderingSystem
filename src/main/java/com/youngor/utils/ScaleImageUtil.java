package com.youngor.utils;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.mawujun.exception.BusinessException;

public class ScaleImageUtil {
	  private int width;  
	  
	    private int height;  
	  
	    private int scaleWidth;  
	  
	    private double support = (double) 0.75;
	  
	    private double PI = (double) 3.14159265358978;  
	  
	    private double[] contrib;  
	  
	    private double[] normContrib;  
	  
	    private double[] tmpContrib;  
	  
	    private int nDots;  
	  
	    private int nHalfDots;  
	    
	    public static ScaleImageUtil getInstance() {
	        
	        return new ScaleImageUtil();  
	    }
	  
	    /** *//** 
	     * Start: Use Lanczos filter to replace the original algorithm for image 
	     * scaling. Lanczos improves quality of the scaled image modify by :blade 
	     */  
	  
	    public static void main(String[] args) {  
	        ScaleImageUtil is = new ScaleImageUtil();  
	        try {  
	            long time = System.currentTimeMillis();
	            is.saveImageAsJpg("d:/p-15a.jpg", "d:/p-15a222222222222.jpg", 1024, 768);  
//	            is.saveImageAsJpg("d:/DSC_0693.jpg", "d:/DSC_06932222222.jpg", 1024, 768); 
//	            is.saveImageAsJpg("d:/20160510170956.png", "d:/201605101709562222322.png", 1024, 768); 
//	            is.saveImageAsJpg(new FileInputStream("d:/DSC_0693.jpg"), "d:/liu999-1.png", 1024, 768); 
//	            is.saveImageAsJpg(new FileInputStream("d:/liu999-1.png"), "d:/liu999-2.png", 120, 120); 
//	            is.saveImageAsJpg(new FileInputStream("d:/liu999-1.png"), "d:/liu999-3.png", 60, 60); 
	            System.out.println((System.currentTimeMillis() - time));
	        } catch (Exception e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }  
	    }  
	  
	    // fromFileStr原图片地址,saveToFileStr生成缩略图地址,formatWideth生成图片宽度,formatHeight高度  
	    public void saveImageAsJpg(String fromFileStr, String saveToFileStr,  
	            int formatWideth, int formatHeight) throws Exception {  
	        BufferedImage srcImage;  
	        File saveFile = new File(saveToFileStr);  
	        File parent = saveFile.getParentFile();
	        if (!parent.exists()) {
	            parent.mkdirs();
	        }
	        File fromFile = new File(fromFileStr);  
//	      srcImage = javax.imageio.ImageIO.read(fromFile); // construct image
	        srcImage = readImage(new FileInputStream(fromFile)); // construct image new by liul20161121
	        int imageWideth = srcImage.getWidth(null);  
	        int imageHeight = srcImage.getHeight(null);  
	        int changeToWideth = 0;  
	        int changeToHeight = 0;  
	        if (imageWideth > 0 && imageHeight > 0) {  
	            // flag=true;  
	            if (imageWideth / imageHeight >= formatWideth / formatHeight) {  
	                if (imageWideth > formatWideth) {  
	                    changeToWideth = formatWideth;  
	                    changeToHeight = (imageHeight * formatWideth) / imageWideth;  
	                } else {  
	                    changeToWideth = imageWideth;  
	                    changeToHeight = imageHeight;  
	                }  
	            } else {  
	                if (imageHeight > formatHeight) {  
	                    changeToHeight = formatHeight;  
	                    changeToWideth = (imageWideth * formatHeight) / imageHeight;  
	                } else {  
	                    changeToWideth = imageWideth;  
	                    changeToHeight = imageHeight;  
	                }  
	            }  
	        }  
	  
	        srcImage = imageZoomOut(srcImage, changeToWideth, changeToHeight);  
	        ImageIO.write(srcImage, "JPEG", saveFile); 
//	      ImageIO.write(srcImage, "PNG", saveFile); 
//	        FileOutputStream out = null;
//	        try {
//	            out = new FileOutputStream(saveFile);
//	            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
////	            JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(srcImage);
////	            int quality = Math.max(0, Math.min(90, 100));
////	            param.setQuality((float) quality / 100.0f, false);
////	            encoder.setJPEGEncodeParam(param);
//	            encoder.encode(srcImage);
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	            throw new Exception(e);
//	        } finally {
//	            if (out != null) {
//	                out.close();
//	            }
//	        }
	    } 
	    
	    // fromFileStr原图片地址,saveToFileStr生成缩略图地址,formatWideth生成图片宽度,formatHeight高度  
	    public void saveImageAsJpg(InputStream inputStream, String saveToFileStr,  
	            int formatWideth, int formatHeight) throws Exception {  
	        try {
	            File saveFile = new File(saveToFileStr);  
	            File parent = saveFile.getParentFile();
	            if (!parent.exists()) {
	                parent.mkdirs();
	            }
//	            BufferedImage srcImage = javax.imageio.ImageIO.read(inputStream); // construct image  
	            BufferedImage srcImage = readImage(inputStream); // construct image new update by liul20161121 
	            if (srcImage == null) {
	                throw new BusinessException("此文件可能已损坏、损毁！");
	            }
	            int imageWideth = srcImage.getWidth(null);  
	            int imageHeight = srcImage.getHeight(null);  
	            int changeToWideth = 0;  
	            int changeToHeight = 0;  
	            if (imageWideth > 0 && imageHeight > 0) {  
	                // flag=true;  
	                if (imageWideth / imageHeight >= formatWideth / formatHeight) {  
	                    if (imageWideth > formatWideth) {  
	                        changeToWideth = formatWideth;  
	                        changeToHeight = (imageHeight * formatWideth) / imageWideth;  
	                    } else {  
	                        changeToWideth = imageWideth;  
	                        changeToHeight = imageHeight;  
	                    }  
	                } else {  
	                    if (imageHeight > formatHeight) {  
	                        changeToHeight = formatHeight;  
	                        changeToWideth = (imageWideth * formatHeight) / imageHeight;  
	                    } else {  
	                        changeToWideth = imageWideth;  
	                        changeToHeight = imageHeight;  
	                    }  
	                }  
	            }  
	            srcImage = imageZoomOut(srcImage, changeToWideth, changeToHeight);
	            ImageIO.write(srcImage, "JPEG", saveFile); 
	        } catch (Exception e) {
	            throw e;
	        } finally {
	            if (inputStream != null) {
	                try {
	                    inputStream.close();
	                } catch (Exception e) {
	                    throw e;
	                }
	            }
	        }
//	      ImageIO.write(srcImage, "PNG", saveFile); 
//	        FileOutputStream out = null;
//	        try {
//	            out = new FileOutputStream(saveFile);
//	            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
////	            JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(srcImage);
////	            int quality = Math.max(0, Math.min(90, 100));
////	            param.setQuality((float) quality / 100.0f, false);
////	            encoder.setJPEGEncodeParam(param);
//	            encoder.encode(srcImage);
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	            throw new Exception(e);
//	        } finally {
//	            if (out != null) {
//	                out.close();
//	            }
//	        }
	    }
	    
	 // fromFileStr原图片地址,saveToFileStr生成缩略图地址,formatWideth生成图片宽度,formatHeight高度  
	    public ByteArrayOutputStream saveImageAsJpg(InputStream inputStream,  
	            int formatWideth, int formatHeight) throws Exception {  
	        try {
	            ByteArrayOutputStream out = new ByteArrayOutputStream();
//	            BufferedImage srcImage = javax.imageio.ImageIO.read(inputStream); // construct image  
	            BufferedImage srcImage = readImage(inputStream); // construct image new update by liul20161121 
	            if (srcImage == null) {
	                throw new BusinessException("此文件可能已损坏、损毁！");
	            }
	            int imageWideth = srcImage.getWidth(null);  
	            int imageHeight = srcImage.getHeight(null);  
	            int changeToWideth = 0;  
	            int changeToHeight = 0;  
	            if (imageWideth > 0 && imageHeight > 0) {  
	                // flag=true;  
	                if (imageWideth / imageHeight >= formatWideth / formatHeight) {  
	                    if (imageWideth > formatWideth) {  
	                        changeToWideth = formatWideth;  
	                        changeToHeight = (imageHeight * formatWideth) / imageWideth;  
	                    } else {  
	                        changeToWideth = imageWideth;  
	                        changeToHeight = imageHeight;  
	                    }  
	                } else {  
	                    if (imageHeight > formatHeight) {  
	                        changeToHeight = formatHeight;  
	                        changeToWideth = (imageWideth * formatHeight) / imageHeight;  
	                    } else {  
	                        changeToWideth = imageWideth;  
	                        changeToHeight = imageHeight;  
	                    }  
	                }  
	            }  
	            srcImage = imageZoomOut(srcImage, changeToWideth, changeToHeight);
	            ImageIO.write(srcImage, "JPEG", out); 
	            return out;
	        } catch (Exception e) {
	            throw e;
	        } finally {
	            if (inputStream != null) {
	                try {
	                    inputStream.close();
	                } catch (Exception e) {
	                    throw e;
	                }
	            }
	        }
//	      ImageIO.write(srcImage, "PNG", saveFile); 
//	        FileOutputStream out = null;
//	        try {
//	            out = new FileOutputStream(saveFile);
//	            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
////	            JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(srcImage);
////	            int quality = Math.max(0, Math.min(90, 100));
////	            param.setQuality((float) quality / 100.0f, false);
////	            encoder.setJPEGEncodeParam(param);
//	            encoder.encode(srcImage);
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	            throw new Exception(e);
//	        } finally {
//	            if (out != null) {
//	                out.close();
//	            }
//	        }
	    }
	  
	    public BufferedImage imageZoomOut(BufferedImage srcBufferImage, int w, int h) {  
	        width = srcBufferImage.getWidth();  
	        height = srcBufferImage.getHeight();  
	        scaleWidth = w;  
	  
	        if (DetermineResultSize(w, h) == 1) {  
	            return srcBufferImage;  
	        }  
	        CalContrib();  
	        BufferedImage pbOut = HorizontalFiltering(srcBufferImage, w);  
	        BufferedImage pbFinalOut = VerticalFiltering(pbOut, h);  
	        return pbFinalOut;  
	    }  
	  
	    /** *//** 
	     * 决定图像尺寸 
	     */  
	    private int DetermineResultSize(int w, int h) {  
	        double scaleH, scaleV;  
	        scaleH = (double) w / (double) width;  
	        scaleV = (double) h / (double) height;  
	        // 需要判断一下scaleH，scaleV，不做放大操作  
	        if (scaleH > 1.0 && scaleV > 1.0) {  
	            return 1;  
	        }  
	        return 0;  
	  
	    } // end of DetermineResultSize()  
	  
	    private double Lanczos(int i, int inWidth, int outWidth, double Support) {  
	        double x;  
	  
	        x = (double) i * (double) outWidth / (double) inWidth;  
	  
	        return Math.sin(x * PI) / (x * PI) * Math.sin(x * PI / Support)  
	                / (x * PI / Support);  
	  
	    }  
	  
	    private void CalContrib() {  
	        nHalfDots = (int) ((double) width * support / (double) scaleWidth);  
	        nDots = nHalfDots * 2 + 1;  
	        try {  
	            contrib = new double[nDots];  
	            normContrib = new double[nDots];  
	            tmpContrib = new double[nDots];  
	        } catch (Exception e) {  
	            System.out.println("init   contrib,normContrib,tmpContrib" + e);  
	        }  
	  
	        int center = nHalfDots;  
	        contrib[center] = 1.0;  
	  
	        double weight = 0.0;  
	        int i = 0;  
	        for (i = 1; i <= center; i++) {  
	            contrib[center + i] = Lanczos(i, width, scaleWidth, support);  
	            weight += contrib[center + i];  
	        }  
	  
	        for (i = center - 1; i >= 0; i--) {  
	            contrib[i] = contrib[center * 2 - i];  
	        }  
	  
	        weight = weight * 2 + 1.0;  
	  
	        for (i = 0; i <= center; i++) {  
	            normContrib[i] = contrib[i] / weight;  
	        }  
	  
	        for (i = center + 1; i < nDots; i++) {  
	            normContrib[i] = normContrib[center * 2 - i];  
	        }  
	    } // end of CalContrib()  
	  
	    // 处理边缘  
	    private void CalTempContrib(int start, int stop) {  
	        double weight = 0;  
	  
	        int i = 0;  
	        for (i = start; i <= stop; i++) {  
	            weight += contrib[i];  
	        }  
	  
	        for (i = start; i <= stop; i++) {  
	            tmpContrib[i] = contrib[i] / weight;  
	        }  
	  
	    } // end of CalTempContrib()  
	  
	    private int GetRedValue(int rgbValue) {  
	        int temp = rgbValue & 0x00ff0000;  
	        return temp >> 16;  
	    }  
	  
	    private int GetGreenValue(int rgbValue) {  
	        int temp = rgbValue & 0x0000ff00;  
	        return temp >> 8;  
	    }  
	  
	    private int GetBlueValue(int rgbValue) {  
	        return rgbValue & 0x000000ff;  
	    }  
	  
	    private int ComRGB(int redValue, int greenValue, int blueValue) {  
	  
	        return (redValue << 16) + (greenValue << 8) + blueValue;  
	    }  
	  
	    // 行水平滤波  
	    private int HorizontalFilter(BufferedImage bufImg, int startX, int stopX,  
	            int start, int stop, int y, double[] pContrib) {  
	        double valueRed = 0.0;  
	        double valueGreen = 0.0;  
	        double valueBlue = 0.0;  
	        int valueRGB = 0;  
	        int i, j;  
	  
	        for (i = startX, j = start; i <= stopX; i++, j++) {  
	            valueRGB = bufImg.getRGB(i, y);  
	  
	            valueRed += GetRedValue(valueRGB) * pContrib[j];  
	            valueGreen += GetGreenValue(valueRGB) * pContrib[j];  
	            valueBlue += GetBlueValue(valueRGB) * pContrib[j];  
	        }  
	  
	        valueRGB = ComRGB(Clip((int) valueRed), Clip((int) valueGreen),  
	                Clip((int) valueBlue));  
	        return valueRGB;  
	  
	    } // end of HorizontalFilter()  
	  
	    // 图片水平滤波  
	    private BufferedImage HorizontalFiltering(BufferedImage bufImage, int iOutW) {  
	        int dwInW = bufImage.getWidth();  
	        int dwInH = bufImage.getHeight();  
	        int value = 0;  
	        BufferedImage pbOut = new BufferedImage(iOutW, dwInH,  
	                BufferedImage.TYPE_INT_RGB);  
	  
	        for (int x = 0; x < iOutW; x++) {  
	  
	            int startX;  
	            int start;  
	            int X = (int) (((double) x) * ((double) dwInW) / ((double) iOutW) + 0.5);  
	            int y = 0;  
	  
	            startX = X - nHalfDots;  
	            if (startX < 0) {  
	                startX = 0;  
	                start = nHalfDots - X;  
	            } else {  
	                start = 0;  
	            }  
	  
	            int stop;  
	            int stopX = X + nHalfDots;  
	            if (stopX > (dwInW - 1)) {  
	                stopX = dwInW - 1;  
	                stop = nHalfDots + (dwInW - 1 - X);  
	            } else {  
	                stop = nHalfDots * 2;  
	            }  
	  
	            if (start > 0 || stop < nDots - 1) {  
	                CalTempContrib(start, stop);  
	                for (y = 0; y < dwInH; y++) {  
	                    value = HorizontalFilter(bufImage, startX, stopX, start,  
	                            stop, y, tmpContrib);  
	                    pbOut.setRGB(x, y, value);  
	                }  
	            } else {  
	                for (y = 0; y < dwInH; y++) {  
	                    value = HorizontalFilter(bufImage, startX, stopX, start,  
	                            stop, y, normContrib);  
	                    pbOut.setRGB(x, y, value);  
	                }  
	            }  
	        }  
	  
	        return pbOut;  
	  
	    } // end of HorizontalFiltering()  
	  
	    private int VerticalFilter(BufferedImage pbInImage, int startY, int stopY,  
	            int start, int stop, int x, double[] pContrib) {  
	        double valueRed = 0.0;  
	        double valueGreen = 0.0;  
	        double valueBlue = 0.0;  
	        int valueRGB = 0;  
	        int i, j;  
	  
	        for (i = startY, j = start; i <= stopY; i++, j++) {  
	            valueRGB = pbInImage.getRGB(x, i);  
	  
	            valueRed += GetRedValue(valueRGB) * pContrib[j];  
	            valueGreen += GetGreenValue(valueRGB) * pContrib[j];  
	            valueBlue += GetBlueValue(valueRGB) * pContrib[j];  
	            // System.out.println(valueRed+"->"+Clip((int)valueRed)+"<-");  
	            //     
	            // System.out.println(valueGreen+"->"+Clip((int)valueGreen)+"<-");  
	            // System.out.println(valueBlue+"->"+Clip((int)valueBlue)+"<-"+"-->");  
	        }  
	  
	        valueRGB = ComRGB(Clip((int) valueRed), Clip((int) valueGreen),  
	                Clip((int) valueBlue));  
	        // System.out.println(valueRGB);  
	        return valueRGB;  
	  
	    } // end of VerticalFilter()  
	  
	    private BufferedImage VerticalFiltering(BufferedImage pbImage, int iOutH) {  
	        int iW = pbImage.getWidth();  
	        int iH = pbImage.getHeight();  
	        int value = 0;  
	        BufferedImage pbOut = new BufferedImage(iW, iOutH,  
	                BufferedImage.TYPE_INT_RGB);  
	  
	        for (int y = 0; y < iOutH; y++) {  
	  
	            int startY;  
	            int start;  
	            int Y = (int) (((double) y) * ((double) iH) / ((double) iOutH) + 0.5);  
	  
	            startY = Y - nHalfDots;  
	            if (startY < 0) {  
	                startY = 0;  
	                start = nHalfDots - Y;  
	            } else {  
	                start = 0;  
	            }  
	  
	            int stop;  
	            int stopY = Y + nHalfDots;  
	            if (stopY > (int) (iH - 1)) {  
	                stopY = iH - 1;  
	                stop = nHalfDots + (iH - 1 - Y);  
	            } else {  
	                stop = nHalfDots * 2;  
	            }  
	  
	            if (start > 0 || stop < nDots - 1) {  
	                CalTempContrib(start, stop);  
	                for (int x = 0; x < iW; x++) {  
	                    value = VerticalFilter(pbImage, startY, stopY, start, stop,  
	                            x, tmpContrib);  
	                    pbOut.setRGB(x, y, value);  
	                }  
	            } else {  
	                for (int x = 0; x < iW; x++) {  
	                    value = VerticalFilter(pbImage, startY, stopY, start, stop,  
	                            x, normContrib);  
	                    pbOut.setRGB(x, y, value);  
	                }  
	            }  
	  
	        }  
	  
	        return pbOut;  
	  
	    } // end of VerticalFiltering()  
	  
	    int Clip(int x) {  
	        if (x < 0)  
	            return 0;  
	        if (x > 255)  
	            return 255;  
	        return x;  
	    } 
	    
	    private BufferedImage readImage(InputStream stream) throws IOException {
	        ImageInputStream iis = ImageIO.createImageInputStream(stream);
	        Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(iis);
	        ImageReader imageReader = imageReaders.next();
	        imageReader.setInput(iis, true, true);
	        String format = imageReader.getFormatName();
	        BufferedImage result = null;
	        if ("JPEG".equalsIgnoreCase(format) || "JPG".equalsIgnoreCase(format)) {
	            Raster raster = imageReader.readRaster(0, null);
	            int w = raster.getWidth();
	            int h = raster.getHeight();
	            result = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	            int rgb[] = new int[3];
	            int pixel[] = new int[3];
	            for (int x = 0; x < w; x++) {
	                for (int y = 0; y < h; y++) {
	                    raster.getPixel(x, y, pixel);
	                    int Y = pixel[0];
	                    int CR = pixel[1];
	                    int CB = pixel[2];
	                    toRGB(Y, CB, CR, rgb);
	                    int r = rgb[0];
	                    int g = rgb[1];
	                    int b = rgb[2];
	                    int bgr = ((b & 0xFF) << 16) | ((g & 0xFF) << 8)
	                            | (r & 0xFF);
	                    result.setRGB(x, y, bgr);
	                }
	            }
	        }else{
	            result = imageReader.read(0);
	        }
	        return result;
	    }
	    // Based on http://www.equasys.de/colorconversion.html
	    private  void toRGB(int y, int cb, int cr, int rgb[]) {
	        float Y = y / 255.0f;
	        float Cb = (cb - 128) / 255.0f;
	        float Cr = (cr - 128) / 255.0f;
	        float R = Y + 1.4f * Cr;
	        float G = Y - 0.343f * Cb - 0.711f * Cr;
	        float B = Y + 1.765f * Cb;
	        R = Math.min(1.0f, Math.max(0.0f, R));
	        G = Math.min(1.0f, Math.max(0.0f, G));
	        B = Math.min(1.0f, Math.max(0.0f, B));
	        int r = (int) (R * 255);
	        int g = (int) (G * 255);
	        int b = (int) (B * 255);
	        rgb[0] = r;
	        rgb[1] = g;
	        rgb[2] = b;
	    }
}
