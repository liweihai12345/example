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
public class ValidateImageServlet2 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Pragma", "No-cache");// 设置页面不缓存
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        int width = 90, height = 35;// 在内存中创建图象
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();// 获取图形上下文
        Random random = new Random();// 生成随机类
        g.setColor(getRandColor(220, 250));// 设定背景色
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.PLAIN, 28));// 设定字体
        g.draw3DRect(0, 0, width - 1, height - 1, true);// 画边框//
        // g.drawRect(0,0,width-1,height-1);
        g.setColor(getRandColor(160, 200)); // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }
        // 取随机产生的认证码(6位数字)
        String sRand = "";
        String s = "012345678901234567890123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ012345678901234567890123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        for (int i = 0, a = s.length(); i < 4; i++) {
            char rand = s.charAt(random.nextInt(a));
            sRand += rand;
            g.setColor(new Color(20 + random.nextInt(110), 20 + random
                    .nextInt(110), 20 + random.nextInt(110)));// 将认证码显示到图象中
            g.drawString(String.valueOf(rand), 18 * i + 8, 25);// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
        }
        g.drawOval(0, 12, 60, 11);
        request.getSession().setAttribute("rand", sRand);// 将认证码存入SESSION
        g.dispose();// 图象生效
        ServletOutputStream output = null;
        try {
            output = response.getOutputStream();
            ImageIO.write(image, "JPEG", output);// 输出图象到页面
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            output.close();
        }

    }
    /**
     * 生成随机颜色
     */
    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
