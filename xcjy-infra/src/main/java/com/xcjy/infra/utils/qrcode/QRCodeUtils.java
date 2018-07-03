package com.xcjy.infra.utils.qrcode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.xcjy.infra.exception.BaseException;

/**
 * @author Nick Zhang
 *
 * @createdOn:2013年11月13日 下午6:03:18
 */
public class QRCodeUtils {
	
	/**
	 * 生成二维码，以输出流的方式输出
	 * @param content 二维码的内容
	 * @param format　二维码的格式
	 * @param width　二维码的宽度
	 * @param height　二维码的高度
	 * @param stream　指定的输出流
	 */
	public static void writeToStream(String content, String format, int width, int height, OutputStream stream) {
		try {
			BitMatrix bitMatrix = generateBitMatrix(content, BarcodeFormat.QR_CODE, width, height);
			MatrixToImageWriter.writeToStream(bitMatrix, format, stream);
		} catch (Exception e) {
			throw new BaseException("生成二维码异常！", e);
		}
	}
	
	/**
	 * 生成二维码，写入到指定文件
	 * @param content 二维码的内容
	 * @param format 二维码的格式
	 * @param width 二维码的宽度
	 * @param height 二维码的高度
	 * @param filePath 指定文件路径
	 */
	public static void writeToFile(String content, String format, int width, int height, String filePath) {
		try {
			File file = new File(filePath);
			BitMatrix bitMatrix = generateBitMatrix(content, BarcodeFormat.QR_CODE, width, height);
			MatrixToImageWriter.writeToFile(bitMatrix, format, file);
		} catch (Exception e) {
			throw new BaseException("生成二维码异常！", e);
		}
	}
	
	private static BitMatrix generateBitMatrix(String content, BarcodeFormat barcodeFormat, int width, int height) throws WriterException {
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		Map<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		BitMatrix bitMatrix = multiFormatWriter.encode(content, barcodeFormat, width, height, hints);
		return bitMatrix;
	}
	
	/**
	 * 由指定文件读取二维码
	 * @param filePath 指定文件路径
	 * @return 二维码的内容
	 */
	public static String readQRCode(String filePath) {
		Result result = null;
		try {
			MultiFormatReader formatReader = new MultiFormatReader();
			File file = new File(filePath);
			BufferedImage image = ImageIO.read(file);
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			Binarizer binarizer = new HybridBinarizer(source);
			BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
			Map<DecodeHintType, String> hints = new HashMap<DecodeHintType, String>();
			hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
			result = formatReader.decode(binaryBitmap, hints);
		} catch (Exception e) {
			throw new BaseException("读取二维码异常！", e);
		}
		return result.getText();
	}
	
	/**
	 * 从指定输入流读取二维码
	 * @param inputStream 输入流读取
	 * @return 二维码的内容
	 */
	public static String readQRCode(InputStream inputStream) {
		Result result = null;
		try {
			MultiFormatReader formatReader = new MultiFormatReader();
			BufferedImage image = ImageIO.read(inputStream);
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			Binarizer binarizer = new HybridBinarizer(source);
			BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
			Map<DecodeHintType, String> hints = new HashMap<DecodeHintType, String>();
			hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
			result = formatReader.decode(binaryBitmap, hints);
		} catch (Exception e) {
			throw new BaseException("读取二维码异常！", e);
		}
		return result.getText();
	}
	
}
