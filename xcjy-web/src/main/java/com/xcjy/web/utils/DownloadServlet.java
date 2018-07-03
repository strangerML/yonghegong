package com.xcjy.web.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xcjy.infra.utils.http.OutputUtils;
import com.xcjy.infra.utils.resources.Configuration;

/**
 * 下载servlet
 * 
 * @author 支亚州
 */
@WebServlet("/download")
public class DownloadServlet extends HttpServlet {

	private static final long serialVersionUID = 1752671703358017196L;

	private static final Logger LOGGER = LoggerFactory.getLogger(DownloadServlet.class);

	private String rootFilePath = Configuration.getConfigValue("file.path");

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 为避免获取文件名称时出现乱码
		request.setCharacterEncoding("UTF-8");

		String filePath = request.getParameter("filePath");
		if (StringUtils.isBlank(filePath)) {
			LOGGER.error("未指定要下载的文件！");
			return;
		}

		File file = new File(rootFilePath + File.separator + filePath);
		if (!file.exists() || !file.isFile()) {
			LOGGER.error("指定的文件不存在！");
			return;
		}
		String fileName = request.getParameter("fileName");
		if (StringUtils.isBlank(fileName)) {
			fileName = filePath.replace("\\", "/");
			int startIndex = fileName.lastIndexOf("/");
			if (startIndex != -1) {
				fileName = fileName.substring(startIndex + 1);
			}
		}

		response.reset();
		response.setContentType(OutputUtils.CONTENTTYPE_IMAGE);
		response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		int fileLength = (int) file.length();
		response.setContentLength(fileLength);
		if (fileLength != 0) {
			FileInputStream in = null;
			ServletOutputStream servletOS = null;
			try {
				in = new FileInputStream(file);
				byte[] buf = new byte[1024];
				servletOS = response.getOutputStream();
				int readLength;
				while (((readLength = in.read(buf)) != -1)) {
					servletOS.write(buf, 0, readLength);
				}
			} catch (Exception e) {
				LOGGER.error("下载文件失败！", e);
			}
			if (in != null) {
				in.close();
			}
			if (servletOS != null) {
				servletOS.flush();
				servletOS.close();
			}
		}
	}

}
