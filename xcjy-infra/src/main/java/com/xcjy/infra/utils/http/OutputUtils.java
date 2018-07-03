package com.xcjy.infra.utils.http;

/**
 * 内容输出工具集合
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xcjy.infra.utils.json.JsonUtils;

/**
 * 内容输出工具集合
 */
public class OutputUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(OutputUtils.class);

	public static final String CONTENTTYPE_XML = "text/xml;charset=UTF-8";
	public static final String CONTENTTYPE_JSON = "text/x-json;charset=UTF-8";
	public static final String CONTENTTYPE_TEXT = "text/plain;charset=UTF-8";
	public static final String CONTENTTYPE_HTML = "text/html;charset=UTF-8";
	public static final String CONTENTTYPE_IMAGE = "image/*;charset=UTF-8";

	/**
	 * 直接输出.
	 * 
	 * @param contentType
	 *            内容的类型.html,text,xml的值见后，json为"text/x-json;charset=UTF-8"
	 */
	public static void write(HttpServletResponse response, String text, String contentType) {
		PrintWriter out = null;
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType(contentType);

		try {
			out = response.getWriter();
			out.write(text);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}

	/**
	 * Ajax请求时向页面写入数据
	 * 
	 * @author LCC
	 * @param 输出内容,mime类型,mime字符集,页面字符集
	 * @param ajax
	 *            string,mime type,mime character encoding,character encoding
	 */
	public static void doAjaxObjectWrite(HttpServletResponse response, String ajaxStr, String mime, String mimeCE,
			String ce) {
		if (ajaxStr != null && ajaxStr.length() > 0) {
			mime = mime == null ? MimeUtil.DEFAULT : mime;
			mimeCE = mimeCE == null ? MimeUtil.DEFAULT_MIME_CE : mimeCE;
			ce = ce == null ? MimeUtil.DEFAULT_CHARACTER_ENCODING : ce;

			String ct = mime + ";charset=" + mimeCE;

			response.setContentType(ct);
			response.setHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding(ce);
			try {
				response.getWriter().write(ajaxStr);
				response.getWriter().flush();
			} catch (IOException e) {
				LOGGER.error("IO异常: ", e);
			}
		}
	}

	public static void doAjaxObjectWrite(HttpServletResponse response, String ajaxStr, String mime) {
		doAjaxObjectWrite(response, ajaxStr, mime, null, null);
	}

	/**
	 * 向页面输出JSON
	 * 
	 * @author LCC
	 * @param json
	 */
	public static void doAjaxJsonWrite(HttpServletResponse response, String json) {
		doAjaxObjectWrite(response, json, MimeUtil.JSON);
	}

	/**
	 * 传入一个对象, 然后转换成为一个Json字符串打印在页面上<br/>
	 * 日期会格式化为 "yyyy-MM-dd HH:mm:ss"
	 * 
	 * @author LCC
	 * @param Object
	 * @throws IOException
	 */
	public static void doAjaxJsonWriteObj(HttpServletResponse response, Object obj) {
		String jsonStr = JsonUtils.toJSONString(obj, "yyyy-MM-dd HH:mm:ss");
		doAjaxJsonWrite(response, jsonStr);
	}

	/**
	 * 向页面输出验证的结果
	 * 
	 * @author LCC
	 */
	public static void doAjaxJsonWriteResult(HttpServletResponse response, Object result) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("success", result);
		doAjaxJsonWriteObj(response, m);
	}
}
