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
import java.io.OutputStream;
import java.util.Random;
import java.util.logging.Logger;

/**
 * @program:
 * @Description:
 * @Author: liweihai
 * @Date: Created in 2018/9/11 14:08
 */
public class ValidateImageServlet5 extends HttpServlet {
    private static final long serialVersionUID = -5051097528828603895L;
    /**
     * 验证码session的名称。
     */
    private static final String SESSION_ATT_NAME = "validateCode";
    /**
     * @width 验证码图片的宽度
     * @height 验证码图片的高度
     * @codeCount 验证码字符个数
     * @fontHeight 字体高度
     * @codeX 第一个字符的x轴值，因为后面的字符坐标依次递增，所以它们的x轴值是codeX的倍数
     * @codeY验证字符的y轴值，因为并行所以值一样 @
     */
    private int width = 100, height = 30, codeCount = 4, fontHeight, codeX,
            codeY;
    /**
     * codeSequence 表示字符允许出现的序列值
     */
    char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String strWidth = this.getInitParameter("width");// 从web.xml中获取初始信息
        String strHeight = this.getInitParameter("height");
        String strCodeCount = this.getInitParameter("codeCount");// 将配置的信息转换成数值
        try {
            if (strWidth != null && strWidth.length() != 0) {
                width = Integer.parseInt(strWidth);
            }
            if (strHeight != null && strHeight.length() != 0) {
                height = Integer.parseInt(strHeight);
            }
            if (strCodeCount != null && strCodeCount.length() != 0) {
                codeCount = Integer.parseInt(strCodeCount);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        // width-4 除去左右多余的位置，使验证码更加集中显示，减得越多越集中。
        // codeCount+1 //等比分配显示的宽度，包括左右两边的空格

        codeX = (width - 4) / (codeCount + 1);
        fontHeight = height - 1;// height - 10 集中显示验证码
        codeY = height - 7;

        BufferedImage buffImg = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);// 定义图像buffer

        Graphics2D gd = buffImg.createGraphics();
        Random random = new Random();// 创建一个随机数生成器类
        gd.setColor(Color.LIGHT_GRAY);// 将图像填充为白色
        gd.fillRect(0, 0, width, height);
        Font font = new Font("Fixedsys", Font.PLAIN, fontHeight);// 创建字体，字体的大小应该根据图片的高度来定。
        gd.setFont(font); // 设置字体。
        gd.setColor(Color.BLACK);// 画边框。
        gd.drawRect(0, 0, width - 1, height - 1);
        gd.setColor(Color.gray);// 随机产生160条干扰线，使图象中的认证码不易被其它程序探测到。
        for (int i = 0; i < 16; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            gd.drawLine(x, y, x + xl, y + yl);
        }
        StringBuffer randomCode = new StringBuffer();// randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        int red = 0, green = 0, blue = 0;
        for (int i = 0; i < codeCount; i++) {// 随机产生codeCount数字的验证码。
            String strRand = String.valueOf(codeSequence[random.nextInt(36)]);// 得到随机产生的验证码数字。
            red = random.nextInt(255);// 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
            green = random.nextInt(255);
            blue = random.nextInt(255);
            gd.setColor(new Color(red, green, blue));// 用随机产生的颜色将验证码绘制到图像中。
            gd.drawString(strRand, (i + 1) * codeX, codeY);
            randomCode.append(strRand);// 将产生的四个随机数组合在一起。
        }
        HttpSession session = request.getSession();// 将四位数字的验证码保存到Session中。
        session.setAttribute(SESSION_ATT_NAME, randomCode.toString());
        response.setHeader("Pragma", "no-cache");// 禁止图像缓存。
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        OutputStream sos = response.getOutputStream();// 将图像输出到Servlet输出流中。
        ImageIO.write(buffImg, "jpeg", sos);
        sos.close();
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
    public static String getValidateCode(HttpServletRequest request) {

        HttpSession session = request.getSession();
        Object obj = session.getAttribute(SESSION_ATT_NAME);
        if (obj != null) {
            return obj.toString();
        }
        return "";
    }
    public static void removeValidateCode(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(SESSION_ATT_NAME);

    }
}
