package com.xcjy.web.utils;

/*

1. 若是上传一个文件，仅仅需要设置maxFileSize熟悉即可。
2. 上传多个文件，可能需要设置maxRequestSize属性，设定一次上传数据的最大量。
3. 上传过程中无论是单个文件超过maxFileSize值，或者上传总的数据量大于maxRequestSize值都会抛出IllegalStateException异常;
4. location属性，既是保存路径(在写入的时候，可以忽略路径设定)，又是上传过程中临时文件的保存路径，一旦执行Part.write方法之后，临时文件将被自动清除。
5. 但Servlet 3.0规范同时也说明，不提供获取上传文件名的方法，尽管我们可以通过part.getHeader("content-disposition")方法间接获取得到。
6. 如何读取MultipartConfig注解属性值，API没有提供直接读取的方法，只能手动获取。
*/


import com.alibaba.fastjson.JSONObject;
import com.xcjy.infra.utils.datetime.DateUtils;
import com.xcjy.infra.utils.http.OutputUtils;
import com.xcjy.infra.utils.http.UploadUtils;
import com.xcjy.infra.utils.resources.Configuration;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

/**
 * @author 支亚州
 *
 */
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
@WebServlet("/upload")
public class UploadFileServlet extends HttpServlet {

	private static final long serialVersionUID = -177310897124393158L;

	private static final Logger LOGGER = LoggerFactory.getLogger(UploadFileServlet.class);

	// 得到注解信息
	private static final MultipartConfig config;

	static {
		config = UploadFileServlet.class.getAnnotation(MultipartConfig.class);
	}

	//获取到配置文件中 项目路径的前缀
	private String filePath = Configuration.getConfigValue("file.path");

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 为避免获取文件名称时出现乱码
		request.setCharacterEncoding("UTF-8");

		//可以根据？isSimditorUpload 获取到值，这个值为1 是前台传过来的
		String isSimditorUpload = request.getParameter("isSimditorUpload");

		StringBuilder builder = new StringBuilder();
		Part part = null;
		try {
			// <input name="file" size="50" type="file" />
			part = request.getPart("file");
		} catch (IllegalStateException ise) {
			// 上传文件超过注解所标注的maxRequestSize或maxFileSize值
			if (config.maxRequestSize() == -1L) {
				LOGGER.info("the Part in the request is larger than maxFileSize");
			} else if (config.maxFileSize() == -1L) {
				LOGGER.info("the request body is larger than maxRequestSize");
			} else {
				LOGGER.info(
						"the request body is larger than maxRequestSize, or any Part in the request is larger than maxFileSize");
			}
			if ("1".equals(isSimditorUpload)) {

				JSONObject result = new JSONObject();
				result.put("success", false);
				result.put("msg", "上传文件过大，请检查输入是否有误！");
				OutputUtils.write(response, result.toJSONString(), OutputUtils.CONTENTTYPE_JSON);
				return;
			}
			builder.append("<script type=\"text/javascript\">");
			builder.append("alert('上传文件过大，请检查输入是否有误！');");
			builder.append("window.history.back();");
			builder.append("</script>");
			OutputUtils.write(response, builder.toString(), OutputUtils.CONTENTTYPE_HTML);
			return;
		} catch (IOException ieo) {
			// 在接收数据时出现问题
			LOGGER.error("I/O error occurred during the retrieval of the requested Part");
		} catch (Exception e) {
			LOGGER.error(e.toString());
			e.printStackTrace();
		}

		if (part == null) {
			if ("1".equals(isSimditorUpload)) {
				JSONObject result = new JSONObject();
				result.put("success", false);
				result.put("msg", "上传文件出现异常，请检查输入是否有误！");
				OutputUtils.write(response, result.toJSONString(), OutputUtils.CONTENTTYPE_JSON);
				return;
			}
			builder.append("<script type=\"text/javascript\">");
			builder.append("alert('上传文件出现异常，请检查输入是否有误！');");
			builder.append("window.history.back();");
			builder.append("</script>");
			OutputUtils.write(response, builder.toString(), OutputUtils.CONTENTTYPE_HTML);
			return;
		}

		//lishan 获取到文件的名称
		String fileName = UploadUtils.getFileName(part);

		//重新生成图片的名称,后面的工具类切割刀只剩下JPG
		String saveName = System.currentTimeMillis() + "." + FilenameUtils.getExtension(fileName);


		//应该是分割多个目录 lishan  a\b\c
		String folderPath = "pictures" + File.separator + DateUtils.dateToStr(new Date(),"yyyy" + File.separator + "MMddHHmmss")
				+ (new Random().nextInt(8999) + 1000);
		String fileSaveFolder = filePath + File.separator + folderPath;
		//如果不存在则创建
		File file = new File(fileSaveFolder);
		if (!file.exists() || !file.isDirectory()) {
			file.mkdirs();
		}
		String fileSavePath = fileSaveFolder + File.separator + saveName;
		part.write(fileSavePath);

		if ("1".equals(isSimditorUpload)) {
			JSONObject result = new JSONObject();
			result.put("success", true);
			result.put("msg", "上传成功！");
			result.put("file_path",
					"download?filePath=" + (folderPath + File.separator + saveName).replace(File.separator, "/"));
			result.put("img_path", (folderPath + File.separator + saveName).replace(File.separator, "/"));
			result.put("file_name", UploadUtils.getFileName(part));
			OutputUtils.write(response, result.toJSONString(), OutputUtils.CONTENTTYPE_JSON);
			return;
		}
		JSONObject resultJson = new JSONObject();
		resultJson.put("result", true);
		resultJson.put("filePath", (folderPath + File.separator + saveName).replace(File.separator, "/"));
		resultJson.put("fileName", fileName);
		builder.append("<script type=\"text/javascript\">");
		builder.append("window.parent.parentContentInit('").append(resultJson.toJSONString()).append("');");
		builder.append("</script>");
		OutputUtils.write(response, builder.toString(), OutputUtils.CONTENTTYPE_HTML);
	}
}
