package com.common.controller;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

public class ImageUpload {

	/**
	 * 圖片上传
	 * 
	 * @param fileName
	 */
	
	public Object upload(MultipartFile file) {
		if (file != null) {
			String originalFileName = file.getOriginalFilename();
			String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));
			String fileName = UUID.randomUUID().toString() + suffix;
			String filePath = Constants.IMG_PATH + fileName;
			File saveFile = new File(filePath);
			
			try {
				file.transferTo(saveFile);// 将上传的文件保存到服务器文件系统
//				user.setAvatar(fileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;

	}

	/**
	 * 处理图片显示请求
	 * 
	 * @param fileName
	 */
	@RequestMapping("/showPic/{fileName}.{suffix}")
	public void showPicture(@PathVariable("fileName") String fileName, @PathVariable("suffix") String suffix,
			HttpServletResponse response) {
		File imgFile = new File(Constants.IMG_PATH + fileName + "." + suffix);
		responseFile(response, imgFile);
	}

	/**
	 * 处理图片下载请求
	 * 
	 * @param fileName
	 * @param response
	 */
	@RequestMapping("/downloadPic/{fileName}.{suffix}")
	public void downloadPicture(@PathVariable("fileName") String fileName, @PathVariable("suffix") String suffix,
			HttpServletResponse response) {
		// 设置下载的响应头信息
		response.setHeader("Content-Disposition", "attachment;fileName=" + "headPic.jpg");
		File imgFile = new File(Constants.IMG_PATH + fileName + "." + suffix);
		responseFile(response, imgFile);
	}

	/**
	 * 响应输出图片文件
	 * 
	 * @param response
	 * @param imgFile
	 */
	private void responseFile(HttpServletResponse response, File imgFile) {
		try (InputStream is = new FileInputStream(imgFile); OutputStream os = response.getOutputStream();) {
			byte[] buffer = new byte[1024]; // 图片文件流缓存池
			while (is.read(buffer) != -1) {
				os.write(buffer);
			}
			os.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
