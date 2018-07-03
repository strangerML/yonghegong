package com.denet.infra.test.img;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import org.junit.Test;

/**
 * 验证码工具类
 *
 * @author &lt;a href="http://www.micmiu.com"&gt;Michael Sun&lt;/a&gt;
 */
public class CaptchaUtil {

	/**
	 * 随机产生的字符串
	 */
	private static final String RANDOM_STRS = "gjyvqpr";// "0123456789ACDEFGHKLMNPQRTUVWXYabcdefhikmnprtuvwxy";

	/**
	 * 字体
	 */
	private static final String FONT_NAME = "Arial";// Arial, Times New Roman,
													// Verdana

	private static final int PADDING_X = 5;
	private static final int PADDING_Y = 5;

	/**
	 * 随机产生字符数量
	 */
	private static final int STR_NUM = 4;

	private Random random = new Random();

	private int width = 200;// 图片宽
	private int height = 80;// 图片高
	private int lineNum = 260;// 干扰线数量

	/**
	 * 获取随机的字符
	 */
	private String getRandomString(int num) {
		return String.valueOf(RANDOM_STRS.charAt(num));
	}

	/**
	 * 生成随机图片
	 */
	public BufferedImage genRandomCodeImage(StringBuffer randomCode) {
		// BufferedImage类是具有缓冲区的Image类
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		// 获取Graphics对象,便于对图像进行各种绘制操作
		Graphics g = image.getGraphics();
		// 设置背景色
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);

		// 设置干扰线的颜色
		g.setColor(getRandColor(110, 120));

		// 绘制干扰线
		for (int i = 0; i <= lineNum; i++) {
			drowLine(g);
		}

		// 生成随机字符
		for (int i = 1; i <= STR_NUM; i++) {
			randomCode.append(drowString(g, i));
		}

		g.dispose();
		return image;
	}

	/**
	 * 绘制字符串
	 */
	private String drowString(Graphics g, int i) {

		int allFontBoxWith = width - PADDING_X * 2;
		int allFontBoxHeight = height - PADDING_Y * 2;
		int fontSize = Math.min(allFontBoxWith / STR_NUM, allFontBoxHeight);

		// 绘制随机字符
		g.setFont(new Font(FONT_NAME, Font.BOLD, fontSize));

		g.setColor(new Color(random.nextInt(101), random.nextInt(111), random.nextInt(121)));

		String rand = String.valueOf(getRandomString(random.nextInt(RANDOM_STRS.length())));

		FontMetrics fm = g.getFontMetrics();
		int fontWidth = fm.stringWidth(rand);// 字体宽度
		int fontHeight = fm.getHeight();// 字体高度=Ascent+Descent+Leading

		int ascent = fm.getAscent();// 字体顶端到baseLine的高度=Ascent
		int descent = fm.getDescent();// 字体baseLine到DescentLine的高度=Descent
		int leading = fm.getLeading();// 字体DescentLine到底端的高度=Leading

		System.out.println("" + fontWidth + "  " + fontHeight);

		int fontBoxWidth = allFontBoxWith / STR_NUM;
		int x = PADDING_X + fontBoxWidth * (i - 1);// 字体左边界起始位置
		int bfw = fontBoxWidth - fontWidth;// 字体宽度与外框宽度查
		if (bfw > 0) {
			x += random.nextInt(bfw);
		}

		int fontBoxHeight = allFontBoxHeight;
		int yStart = PADDING_Y + ascent;
		int yEnd = PADDING_Y + fontBoxHeight - descent - leading;
		int y = yStart;// baseLine的位置
		if (yEnd > yStart) {
			y += random.nextInt(yEnd - yStart + 1);
		}
		System.out.println("fontSize=" + fontSize + "  x=" + x + "  y=" + y);
		g.drawString(rand, x, y);
		return rand;
	}

	/**
	 * 绘制干扰线
	 */
	private void drowLine(Graphics g) {
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		int x0 = random.nextInt(width / 2);
		int y0 = random.nextInt(height / 2);
		g.drawLine(x, y, x + x0, y + y0);
	}

	/**
	 * 给定范围获得随机颜色
	 */
	private Color getRandColor(int fc, int bc) {
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	public static void main(String[] args) {
		CaptchaUtil tool = new CaptchaUtil();
		StringBuffer code = new StringBuffer();
		BufferedImage image = tool.genRandomCodeImage(code);
		System.out.println("&gt;&gt;&gt; random code =: " + code);
		try {
			// 将内存中的图片通过流动形式输出到客户端
			ImageIO.write(image, "JPEG", new FileOutputStream(new File("random-code.jpg")));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void CaptchaUtilTest() {
		File file = new File("F:/captchas/");
		if (!file.exists()) {
			file.mkdirs();
		}
		CaptchaUtil tool = new CaptchaUtil();
		try {
			// 将内存中的图片通过流动形式输出到客户端
			for (int i = 0; i < 1; i++) {
				StringBuffer code = new StringBuffer();
				BufferedImage image = tool.genRandomCodeImage(code);
				System.out.println("&gt;&gt;&gt; random code =: " + code);

				ImageIO.write(image, "JPEG", new FileOutputStream(new File("F:/captchas/" + code + ".jpg")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
