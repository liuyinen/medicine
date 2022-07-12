package com.client.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import com.sun.prism.Graphics;

public class Print {
//	public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
//		Graphics2D g2 = (Graphics2D) g;
//		g2.setPaint(Color.black); // 设置打印颜色为黑色
//		if (page >= PAGES) // 当打印页号大于需要打印的总页数时，打印工作结束
//			return Printable.NO_SUCH_PAGE;
//		g2.translate(pf.getImageableX(), pf.getImageableY());// 转换坐标，确定打印边界
//		drawCurrentPageText(g2, pf, page); // 打印当前页文本
//		return Printable.PAGE_EXISTS; // 存在打印页时，继续打印工作
//	}
//
//	/* 打印指定页号的具体文本内容 */
//	private void drawCurrentPageText(Graphics2D g2, PageFormat pf, int page) {
//		String s = getDrawText(printStr)[page];// 获取当前页的待打印文本内容
//		// 获取默认字体及相应的尺寸
//		FontRenderContext context = g2.getFontRenderContext();
//		Font f = area.getFont();
//		String drawText;
//		float ascent = 16; // 给定字符点阵
//		int k, i = f.getSize(), lines = 0;
//		while (s.length() > 0 && lines < 54) { // 每页限定在 54 行以内
//			k = s.indexOf('\n'); // 获取每一个回车符的位置
//			if (k != -1) { // 存在回车符 
//				lines += 1; // 计算行数
//				drawText = s.substring(0, k); // 获取每一行文本
//				g2.drawString(drawText, 0, ascent); // 具体打印每一行文本，同时走纸移位
//				if (s.substring(k + 1).length() > 0) {
//					s = s.substring(k + 1); // 截取尚未打印的文本
//					ascent += i;
//				}
//			} else {// 不存在回车符
//				lines += 1; // 计算行数
//				drawText = s; // 获取每一行文本
//				g2.drawString(drawText, 0, ascent); // 具体打印每一行文本，同时走纸移位
//				s = ""; // 文本已结束
//			}
//		}
//	}
//
//	/* 将打印目标文本按页存放为字符串数组 */
//	public String[] getDrawText(String s) {
//		String[] drawText = new String[PAGES];// 根据页数初始化数组
//		for (int i = 0; i < PAGES; i++)
//			drawText[i] = ""; // 数组元素初始化为空字符串
//		int k, suffix = 0, lines = 0;
//		while (s.length() > 0) {
//			if (lines < 54) { // 不够一页时
//				k = s.indexOf('\n');
//				if (k != -1) {// 存在回车符
//					lines += 1; // 行数累加
//					// 计算该页的具体文本内容，存放到相应下标的数组元素
//					drawText[suffix] = drawText[suffix] + s.substring(0, k + 1);
//					if (s.substring(k + 1).length() > 0)
//						s = s.substring(k + 1);
//				} else {
//					lines += 1; // 行数累加
//					// 将文本内容存放到相应的数组元素
//					drawText[suffix] = drawText[suffix] + s;
//					s = "";
//				}
//			} else { // 已满一页时
//				lines = 0; // 行数统计清零
//				suffix++; // 数组下标加 1
//			}
//		}
//		return drawText;
//	}
}
