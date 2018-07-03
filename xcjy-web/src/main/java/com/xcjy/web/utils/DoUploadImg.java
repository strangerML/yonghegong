package com.xcjy.web.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.xcjy.infra.utils.datetime.DateUtils;
import com.xcjy.infra.utils.http.OutputUtils;
import com.xcjy.infra.utils.http.UploadUtils;
import com.xcjy.infra.utils.resources.Configuration;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@WebServlet("/doUploadImg")
public class DoUploadImg extends HttpServlet{
		
	private static final long serialVersionUID = 1L;
	
	private String filePath = Configuration.getConfigValue("file.path");

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        // 获取参数
        String imgData = request.getParameter("imgData");

        // 绝对路径
        //String path = request.getSession().getServletContext().getRealPath("upload");
        
        String folderPath = "pictures" + File.separator + DateUtils.dateToStr(new Date(),"yyyy" + File.separator + "MMddHHmmss")
		+ (new Random().nextInt(8999) + 1000);
        String path = filePath + File.separator + folderPath;
        File targetFile = new File(path);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }

        // 生成新的图片名称（入库）
        String imgSuffix = imgData.split(",")[0];
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + imgSuffix;

        boolean flag = GenerateImage(imgData.split(",")[2], fileName, path);
        JSONObject result = new JSONObject();
		result.put("success", true);
		result.put("msg", "上传成功！");
		result.put("file_path",
				"download?filePath=" + (folderPath + File.separator + fileName).replace(File.separator, "/"));
		result.put("img_path", (folderPath + File.separator + fileName).replace(File.separator, "/"));
        out.print(result.toJSONString());
        out.flush();
        out.close();
       
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }


    // 将base64字符文件输出图像
    public static boolean GenerateImage(String imgStr, String fileName, String path) {
        // 对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) // 图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            // 生成jpeg图片
            String imgFilePath = path + "\\" + fileName;// 新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 图片转化成base64字符串
    public static String GetImageStr() {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        String imgFile = "d://test.jpg";// 待处理的图片
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);// 返回Base64编码过的字节数组字符串
    }
	
}
