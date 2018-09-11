package com.example.demo.validate;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @program:
 * @Description:
 * @Author: liweihai
 * @Date: Created in 2018/9/11 14:08
 */
public class ValidateImageServlet1 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Pragma", "nocache");// 禁止图像缓存,使得单击验证码可以刷新验证码图片
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", 0);
        resp.setContentType("image/jpeg");
        BufferedImage bim = new BufferedImage(85, 35, BufferedImage.TYPE_INT_RGB);
        Graphics2D gc = bim.createGraphics();
        Font font = new Font("Times New Roman", Font.PLAIN, 28);
        gc.setColor(Color.yellow);// 设置图片填充颜色
        gc.fillRect(0, 0, 85, 35);

        gc.setColor(Color.pink);// 设置边框颜色
        gc.drawRect(0, 0, 69, 19);
        gc.setFont(font);
        Random rand = new Random();// 产生4位随机数
        StringBuffer sb = new StringBuffer();
        gc.setColor(Color.cyan);// 设置干扰线颜色
        for (int j = 0; j < 30; j++) {
            int x = rand.nextInt(70);
            int y = rand.nextInt(70);
            int x1 = rand.nextInt(60);
            int y1 = rand.nextInt(60);

            gc.drawLine(x, y, x + x1, y + y1);// 往图片里面画干扰线
        }

        String sRand = "";
        String ctmp = "";
        int itmp = 0;
        for (int i = 0; i < 4; i++) {
            switch (rand.nextInt(2)) {
                case 1:
                    itmp = rand.nextInt(26) + 65; // 生成A~Z的字母
                    ctmp = String.valueOf((char) itmp);
                    break;
                default:
                    itmp = rand.nextInt(10) + 48; // 生成0~9的数字
                    ctmp = String.valueOf((char) itmp);
                    break;
            }

            gc.setColor(Color.RED);// 设置字体颜色
            gc.drawString(ctmp, i * 18 + 8, 25);//上下左右居中位置
            sb.append(ctmp);
        }
        String sb1 = String.valueOf(sb);// 将stringbuffer转成string

        req.getSession().setAttribute("code", sb1);// 将生成的验证码的值放到session中去

        ServletOutputStream sos = resp.getOutputStream();// 将图片以流的形式输出
        ImageIO.write(bim, "jpg", sos);
        sos.close();
    }
}
