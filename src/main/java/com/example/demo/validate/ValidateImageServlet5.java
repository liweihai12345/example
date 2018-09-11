package com.example.demo.validate;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Logger;

/**
 * @program:
 * @Description:
 * @Author: liweihai
 * @Date: Created in 2018/9/11 14:08
 */
public class ValidateImageServlet4 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger("ValidateImageServlet3");
    // 设置图形验证码中的字符串的字体的大小
    private Font mFont = new Font("Arial Black", Font.PLAIN, 26);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "No-cache");
        response.setDateHeader("Expires", 0);

        response.setContentType("image/jpeg");// 指定生成的响应图片
        int width = 80; // 指定生成验证码图片的宽度
        int heigh = 35; // 指定生成验证码图片的高度

        BufferedImage bi = new BufferedImage(width, heigh, BufferedImage.TYPE_INT_RGB); // 获得图像数据缓冲对象
        Graphics g = bi.getGraphics();// 获得图形上下文的抽象基类
        Random r = new Random();
        Font f = new Font("黑体", Font.PLAIN, 35); // 定义字体样式
        g.setColor(getRandColor(200, 250)); // 设置背景颜色
        g.fillRect(0, 0, width, heigh); // 绘制背景
        g.setFont(f); // 设置字体
        g.setColor(getRandColor(180, 200)); // 设置前景颜色

        Graphics2D g2d = (Graphics2D) g;
        // 画一条折线
        // BasicStroke bs=new
        // BasicStroke(5f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL);
        // //创建一个供画笔选择线条粗细的对象
        // g2d.setStroke(bs); //改变线条的粗细
        g2d.setColor(Color.DARK_GRAY); // 设置当前颜色为预定义颜色中的深灰色
        int[] xPoints = new int[4];
        int[] yPoints = new int[4];
        for (int j = 0; j < 4; j++) {
            xPoints[j] = r.nextInt(width - 1);
            yPoints[j] = r.nextInt(heigh - 1);
        }
        g2d.drawPolyline(xPoints, yPoints, 4);
        String sRand = "";
        String ctmp = "";
        int itmp = 0;
        for (int i = 0; i < 4; i++) {
            switch (r.nextInt(2)) {
                case 1:
                    itmp = r.nextInt(26) + 65; // 生成A~Z的字母
                    ctmp = String.valueOf((char) itmp);
                    break;
                default:
                    itmp = r.nextInt(10) + 48; // 生成0~9的数字
                    ctmp = String.valueOf((char) itmp);
                    break;
            }
            sRand += ctmp;

            Color color = new Color(20 + r.nextInt(210), 20 + r.nextInt(210),// 设置每个字符的随机颜色
                    20 + r.nextInt(210));
            g2d.setColor(color);

            g2d.setFont(this.getRandomFont());// 设置每个字符的随机字体

            AffineTransform at = new AffineTransform();
            int number = r.nextInt(3) - 1;
            double role = number * r.nextDouble() * (Math.PI / 100);

            //at.setToRotation(role, 25 * i + 5, 55 - r.nextInt(20)); // role:旋转角度,后面两个参数是设置围绕坐标点旋转
            at.setToRotation(role, 30 , 30);

            g2d.setTransform(at);

            // g2d.drawString(ctmp, 25 * i + 5, 55 - r.nextInt(20));//随机上下左右位置
            g2d.drawString(ctmp, 19 * i+5 , 30 );
        }
        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(5 * 60);// 设置session对象5分钟失效
        session.setAttribute("code", sRand.toString());

        g2d.dispose();

        System.out.println(sRand);

        session.setAttribute("code", sRand.toString());

        ImageIO.write(bi, "JPEG", response.getOutputStream());
    }
    private Color getRandColor(int s, int e) {
        Random random = new Random();
        if (s > 255)
            s = 255;
        if (e > 255)
            e = 255;

        int r = random.nextInt(e - s) + s; // 随机生成RGB中的R值
        int g = random.nextInt(e - s) + s; // 随机生成RGB中的G值
        int b = random.nextInt(e - s) + s; // 随机生成RGB中的B值
        return new Color(r, g, b);
    }

    public static Font getRandomFont() {
        // String[] fonts = {"Georgia", "Verdana", "Arial", "Tahoma",
        // "Time News Roman", "Courier New", "Arial Black", "Quantzite"};
        String[] fonts = { "a_TrianglerCmUp", "Astarisborn", "WishfulWaves" };
        Random r = new Random();
        int fontIndex = r.nextInt(fonts.length);
        return new Font(fonts[fontIndex], r.nextInt(3), 35 - r.nextInt(8));//设置随机字体样式大小

    }
}
