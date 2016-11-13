package com.mao.captcha;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * 验证码图片
 * @author Mao 2015年8月31日 下午1:34:09
 */
public class Captcha {

	Random r = new Random();

	// 验证码图片中可以出现的字符集，可根据需要修改
	private char mapTable[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'h', 'j', 'k',
			'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
			'0', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
			'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'R', 'S', 'T',
			'U', 'V', 'W', 'X', 'Y', 'Z' };

	public String getCertPic(int width, int height, OutputStream os) {
		if (width <= 0) {
			width = 80;
		}
		if (height <= 0) {
			height = 30;
		}

		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		// 获取图形上下文
		Graphics g = image.getGraphics();
		// 设定背景颜色
		g.setColor(new Color(255, 255, 255));
		g.fillRect(0, 0, width, height);
		// 画边框
		g.setColor(Color.GRAY);
		g.drawRect(0, 0, width - 1, height - 1);
		// 随机产生的验证码
		String strEnsure = "";
		// 4代表4位验证码，如果要生成等多位的验证码 ，则加大数值

		for (int i = 0; i < 4; i++) {
			strEnsure += mapTable[(int) (mapTable.length * Math.random())];
		}

		// 将验证码显示在图像中，如果要生成更多位的验证码，增加drawString语句

		g.setFont(new Font("Atlantic Inline", Font.BOLD, 22));

		String str = strEnsure.substring(0, 1);
		g.setColor(this.getFontColor((int) (Math.random() * 6)));
		g.drawString(str, 4, 19);

		str = strEnsure.substring(1, 2);
		g.setColor(this.getFontColor((int) (Math.random() * 6)));
		g.drawString(str, 20 + r.nextInt(2), 17);

		str = strEnsure.substring(2, 3);
		g.setColor(this.getFontColor((int) (Math.random() * 6)));
		g.drawString(str, 36 + r.nextInt(2), 20);

		str = strEnsure.substring(3, 4);
		g.setColor(this.getFontColor((int) (Math.random() * 6)));
		g.drawString(str, 52 + r.nextInt(2), 17);

		// 随机产生5个干扰点
		for (int i = 0; i < 5; i++) {
			int x = r.nextInt(width);
			int y = r.nextInt(height);
			g.setColor(this.getLineColor((int) (Math.random() * 8)));
			g.drawOval(x, y, 1, 1);
		}

		// 两条干扰线
		for (int i = 0; i < 2; i++) {
			g.setColor(this.getLineColor((int) (Math.random() * 8)));
			g.drawLine(r.nextInt(width), r.nextInt(height), r.nextInt(width),
					r.nextInt(height));
		}
		// 释放图形上下文
		g.dispose();
		try {
			// 输出图像到页面
			ImageIO.write(image, "JPEG", os);
		} catch (IOException e) {
			return "";
		}
		return strEnsure;
	}

	private Color getFontColor(int num) {
		switch (num) {
		case 1:
			return Color.black;
		case 2:
			return Color.blue;
		case 3:
			return Color.green;
		case 4:
			return Color.magenta;
		case 5:
			return Color.red;
		case 6:
			return Color.cyan;
		default:
			return Color.black;
		}
	}

	private Color getLineColor(int num) {
		switch (num) {
		case 1:
			return Color.pink;
		case 2:
			return Color.white;
		case 3:
			return Color.green;
		case 4:
			return Color.lightGray;
		case 5:
			return Color.orange;
		case 6:
			return Color.red;
		case 7:
			return Color.cyan;
		case 8:
			return Color.yellow;
		default:
			return Color.black;
		}
	}

}
