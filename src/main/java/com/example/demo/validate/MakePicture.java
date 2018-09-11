package com.ordinarilyValidate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

public class MakePicture // 产生识别验证图像
{
	private char charTable[] = { 'a', 'A', 'b', 'B', 'c', 'C', 'd', 'D', 'e',
			'E', 'f', 'F', 'g', 'G', 'h', 'H', 'i', 'I', 'j', 'J', '0', '1',
			'2', '3', '4', '5', '6', '7', '8', '9' };

	public String drawPicture(int width, int height, OutputStream os) {
		if (width <= 0)
			width = 150;
		if (height <= 0)
			height = 100;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		g.setColor(Color.LIGHT_GRAY);//背景颜色
		g.fillRect(0, 0, width, height);
		g.setColor(new Color(0xcc007d52));
		Font f = new Font("黑体", Font.PLAIN, 28);//设置字体大小
		g.setFont(f);
		g.drawRect(0, 0, width, height);
		String str = "";
		for (int x = 0; x < 4; x++) {
			str += charTable[(int) (Math.random() * charTable.length)];
		}

		g.drawString(str.substring(0, 1),5,24);//（截取随机4位数的第一位,左右,上下）
		g.drawString(str.substring(1, 2), 25, 24);
		g.drawString(str.substring(2, 3), 45, 24);
		g.drawString(str.substring(3, 4), 65, 24);
		
		Random rand = new Random();
		for (int i = 0; i < 10; i++) {
			int x = rand.nextInt(width);
			int y = rand.nextInt(height);
			g.drawOval(x, y, 10, 5);//干扰大小
		}
		g.dispose();
		try {
			ImageIO.write(image, "JPEG", os);
		} catch (IOException e) {

			e.printStackTrace();
			return "";
		}

		return str;
	}
}
