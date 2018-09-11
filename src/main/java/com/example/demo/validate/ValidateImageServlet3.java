package com.example.demo.validate;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
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
public class ValidateImageServlet3 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger("ValidateImageServlet3");
    // 设置图形验证码中的字符串的字体的大小
    private Font mFont = new Font("Arial Black", Font.PLAIN, 26);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 生成服务器相应的service方法
        // 阻止生成的页面内容被缓存，保证每次重新生成随机验证码
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        // 指定图形验证码图片的大小；
        int width = 90;// 宽度
        int height = 40;// 高度
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();// 准备在图片中绘制内容
        Random random = new Random();
        g.setColor(getRandColor(200, 250));
        g.fillRect(1, 1, width - 1, height - 1);
        g.setColor(new Color(102, 102, 102));
        g.drawRect(0, 0, width - 1, height - 1);
        g.setFont(mFont);
        g.setColor(getRandColor(160, 200));
        // 生成随机线条
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            int xl = random.nextInt(6) + 1;
            int yl = random.nextInt(12) + 1;
            g.drawLine(x, y, x + xl, y + yl);
        }
        for (int i = 0; i < 70; i++) {
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            int xl = random.nextInt(12) + 1;
            int yl = random.nextInt(6) + 1;
            g.drawLine(x, y, x - xl, y - yl);
        }
        String sRand = "";
        int LEN = 4; // 控制随机码的长度
        for (int i = 0; i < LEN; i++) { // 生成随机的字符串并加入到图片中
            String tmp = getRandomChar();
            sRand += tmp;
            g.setColor(new Color(20 + random.nextInt(110), 20 + random
                    .nextInt(110), 20 + random.nextInt(110)));
            g.drawString(tmp, 18 * i + 8, 28);
        }
        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(10000);
        log.info("随机生成的字符串为" + sRand);
        System.out.println("随机生成的字符串为" + sRand);
        // 将其自动转换为小写。也就是说用户在输入验证码的时候，不需要区分大小写，方便输入。
        session.setAttribute("randCode", sRand.toLowerCase());
        log.info("从session中取出来" + session.getAttribute("randCode"));
        g.dispose();
        System.out.println("从session中取出来" + session.getAttribute("randCode"));
        ImageIO.write(image, "JPEG", response.getOutputStream());
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
    private String getRandomChar() {
        int rand = (int) Math.round(Math.random() * 2);
        long itmp = 0;
        char ctmp = '\u0000';
        switch (rand) {
            case 1:
                itmp = Math.round(Math.random() * 25 + 65);
                ctmp = (char) itmp;
                return String.valueOf(ctmp);
            case 2:
                itmp = Math.round(Math.random() * 25 + 97);
                ctmp = (char) itmp;
                return String.valueOf(ctmp);
            default:
                itmp = Math.round(Math.random() * 9);
                return String.valueOf(itmp);
        }
    }
}
