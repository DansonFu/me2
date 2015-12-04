package com.lettucetech.me2.common.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用于生成验证码的工具类，验证码字符串放在session的verifyCode中
 * @author pwc
 *
 */
public class VerifyCodeUtil {
	static int width=100;
	static int height=40;
	static String str="AaBbCc1DdEe2FfGg3Hh4JjKk5LMm6Nn7PpQq8RrSs9TtUuVvWwXxYyZz";
	
	/**
	 * 验证码字符串放在session的verifyCode中
	 * @param request
	 * @param response
	 */
	public static void outputVerifyCode(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		BufferedImage image=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g=image.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.WHITE);
		g.fillRect(1, 1, width-2, height-2);
		String outStr="";
		g.setColor(Color.BLUE);
		g.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 20));
		for(int i=0;i<4;++i){
			outStr=outStr+str.charAt((int)(Math.random()*str.length()));
			g.drawString(String.valueOf(outStr.charAt(i)), 10+i*20, 25);
		}
		request.getSession().setAttribute("verifyCode", outStr);
		g.dispose();
		OutputStream os;
		try {
			os = response.getOutputStream();
			ImageIO.write(image,"JPEG",os);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
