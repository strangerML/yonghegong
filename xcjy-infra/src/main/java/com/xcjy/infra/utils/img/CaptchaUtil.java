package com.xcjy.infra.utils.img;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

/**
 * 带干扰线的随机验证码图片生成类
 * 
 * @author binbinccut@163.com
 *
 */
public class CaptchaUtil {

	/**
	 * 随机产生的字符串
	 */
	private static final String VERIFY_CODES = "0123456789ACDEFGHKLMNPQRTUVWXYabcdefhikmnprtuvwxy";// "gjyvqpr";

	/**
	 * 字体
	 */
	private static final String FONT_NAME = "Verdana";// Arial, Times New Roman,
														// Verdana

	private static final int PADDING_X = 5;
	private static final int PADDING_Y = 5;

	private static final String CONTENTTYPE_IMAGE = "image/*;charset=UTF-8";

	/**
	 * 放到session中的验证码内容
	 */
	public static final String VERIFY_SESSION_VALUE = "captcha_value";

	/**
	 * 验证码内容放到session时的时间
	 */
	public static final String VERIFY_SESSION_TIME = "captcha_time";

	/**
	 * session中的验证码内容有效时间，单位（毫秒）
	 */
	public static final int VERIFY_SESSION_LIFE = 2 * 60 * 1000;

	/**
	 * 输出验证码到浏览器客户端
	 * 
	 * @param width
	 * @param height
	 * @param code
	 * @param response
	 */
	public static void write(int width, int height, String code, HttpServletResponse response) {

		response.reset();
		response.setContentType(CONTENTTYPE_IMAGE);
		response.addHeader("Content-Disposition",
				"attachment; filename=\"VERIFY_CODES" + System.currentTimeMillis() + ".jpg\"");
		// 不缓存此内容
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");

		OutputStream os;
		try {
			os = response.getOutputStream();
			outputImage(width, height, os, code);
			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 绘制验证码图片
	 * 
	 * @param width
	 *            图片宽度
	 * @param height
	 *            图片高度
	 * @param verifySize
	 *            验证码个数
	 * @param os
	 *            输出流
	 * @return 验证码内容
	 * @throws IOException
	 */
	public static String outputVerifyImage(int width, int height, int verifySize, OutputStream os) throws IOException {
		String verifyCode = generateVerifyCode(verifySize);
		outputImage(width, height, os, verifyCode);
		return verifyCode;
	}

	/**
	 * 生成随机验证码文件,并返回验证码值
	 * 
	 * @param w
	 * @param h
	 * @param outputFile
	 * @param verifySize
	 * @return
	 * @throws IOException
	 */
	public static String outputVerifyImage(int w, int h, File outputFile, int verifySize) throws IOException {
		String verifyCode = generateVerifyCode(verifySize);
		outputImage(w, h, outputFile, verifyCode);
		return verifyCode;
	}

	/**
	 * 生成指定验证码图像文件
	 * 
	 * @param w
	 * @param h
	 * @param outputFile
	 * @param code
	 * @throws IOException
	 */
	public static void outputImage(int w, int h, File outputFile, String code) throws IOException {
		if (outputFile == null) {
			return;
		}
		File dir = outputFile.getParentFile();
		if (!dir.exists()) {
			dir.mkdirs();
		}
		try {
			outputFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(outputFile);
			outputImage(w, h, fos, code);
			fos.close();
		} catch (IOException e) {
			throw e;
		}
	}

	/**
	 * 绘制验证码图片的核心方法
	 * 
	 * @param width
	 *            图片宽度
	 * @param height
	 *            图片高度
	 * @param os
	 *            输出流
	 * @param code
	 *            验证码图片的内容
	 * @throws IOException
	 */
	public static void outputImage(int width, int height, OutputStream os, String code) throws IOException {
		if (width <= 0 || height <= 0 || os == null || StringUtils.isEmpty(code)) {
			return;
		}

		// BufferedImage类是具有缓冲区的Image类
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		// 获取Graphics对象,便于对图像进行各种绘制操作
		Graphics g = image.getGraphics();
		// 设置背景色
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);

		// 设置干扰线的颜色
		g.setColor(getRandColor(110, 120));

		int lineNum = (int) Math.ceil(width * height * 0.0165);

		// 绘制干扰线
		for (int i = 0; i <= lineNum; i++) {
			drowLine(g, width, height);
		}

		char[] chars = code.toCharArray();
		int verifySize = code.length();
		int allFontBoxWith = width - PADDING_X * 2;
		int allFontBoxHeight = height - PADDING_Y * 2;
		int fontSize = Math.min(allFontBoxWith / verifySize, allFontBoxHeight);

		int fontBoxWidth = allFontBoxWith / verifySize;

		int fontBoxHeight = allFontBoxHeight;

		for (int i = 0; i < verifySize; i++) {

			Random random = new Random();

			// 绘制随机字符
			g.setFont(new Font(FONT_NAME, Font.BOLD, fontSize));

			g.setColor(new Color(random.nextInt(101), random.nextInt(111), random.nextInt(121)));

			String rand = String.valueOf(chars[i]);

			FontMetrics fm = g.getFontMetrics();
			int fontWidth = fm.stringWidth(rand);// 字体宽度
			// int fontHeight = fm.getHeight();// 字体高度=Ascent+Descent+Leading

			int ascent = fm.getAscent();// 字体顶端到baseLine的高度=Ascent
			int descent = fm.getDescent();// 字体baseLine到DescentLine的高度=Descent
			int leading = fm.getLeading();// 字体DescentLine到底端的高度=Leading

			int x = PADDING_X + fontBoxWidth * i;// 字体左边界起始位置
			int bfw = fontBoxWidth - fontWidth;// 字体宽度与外框宽度查
			if (bfw > 0) {
				x += random.nextInt(bfw);
			}

			int yStart = PADDING_Y + ascent;
			int yEnd = PADDING_Y + fontBoxHeight - descent - leading;
			int y = yStart;// baseLine的位置
			if (yEnd > yStart) {
				y += random.nextInt(yEnd - yStart + 1);
			}
			g.drawString(rand, x, y);
		}

		g.dispose();

		ImageIO.write(image, "jpg", os);

	}

	/**
	 * 使用系统默认字符源生成验证码
	 * 
	 * @param verifySize
	 *            验证码长度
	 * @return
	 */
	public static String generateVerifyCode(int verifySize) {
		return generateVerifyCode(verifySize, VERIFY_CODES);
	}

	/**
	 * 使用指定源生成验证码
	 * 
	 * @param verifySize
	 *            验证码长度
	 * @param sources
	 *            验证码字符源
	 * @return
	 */
	public static String generateVerifyCode(int verifySize, String sources) {
		if (verifySize <= 0) {
			return null;
		}
		if (StringUtils.isEmpty(sources)) {
			sources = VERIFY_CODES;
		}
		int codesLen = sources.length();
		StringBuilder verifyCode = new StringBuilder(verifySize);
		for (int i = 0; i < verifySize; i++) {
			Random random = new Random();
			verifyCode.append(sources.charAt(random.nextInt(codesLen)));
		}
		return verifyCode.toString();
	}

	/**
	 * 绘制干扰线
	 */
	private static void drowLine(Graphics g, int width, int height) {
		Random random = new Random();
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		int x0 = random.nextInt(width / 2);
		int y0 = random.nextInt(height / 2);
		g.drawLine(x, y, x + x0, y + y0);
	}

	/**
	 * 给定范围获得随机颜色
	 */
	private static Color getRandColor(int fc, int bc) {
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		Random random = new Random();
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

}
