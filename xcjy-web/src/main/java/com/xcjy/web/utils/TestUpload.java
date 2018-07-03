package com.xcjy.web.utils;

import com.alibaba.fastjson.JSONObject;
import com.xcjy.infra.utils.datetime.DateUtils;
import com.xcjy.infra.utils.http.OutputUtils;
import com.xcjy.infra.utils.http.UploadUtils;
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
 * Created by lishan on 2018/4/17.
 */
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
@WebServlet("/uploadss")
public class TestUpload extends HttpServlet {


    private static final Logger LOGGER = LoggerFactory.getLogger(TestUpload.class);

    private static final String filePathQ = "E:\\test\\xcjb-web";

    //定义注解的对象
    private static MultipartConfig config = null;

    //静态代码块加载信息
    static {
        config = TestUpload.class.getAnnotation(MultipartConfig.class);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //设置请求的编码
        req.setCharacterEncoding("utf8");


        //定一个字符串缓存
        StringBuilder builder = new StringBuilder();


        //获取前台传来的参数
        String isUpload = req.getParameter("isSimditorUpload");
        //非法操作
        if (!"1".equals(isUpload)) {
            builder.append("<script type=\"text/javascript\">");
            builder.append("alert('上传文件出现异常，请检查输入是否有误！');");
            builder.append("window.history.back();");
            builder.append("</script>");
            OutputUtils.write(resp, builder.toString(), OutputUtils.CONTENTTYPE_HTML);
            return;
        }


        //把file转换成Part对象
        Part part = null;

        //从请求中获取到file

        try {
            part = req.getPart("file");
        } catch (Exception e) {


            e.printStackTrace();

            LOGGER.info("error 文件上传为空");

        }


        //判断是否为空
        if (part == null) {

            JSONObject result = new JSONObject();
            result.put("success", false);
            result.put("msg", "上传文件出现异常，请检查输入是否有误！");
            OutputUtils.write(resp, result.toJSONString(), OutputUtils.CONTENTTYPE_JSON);
            return;


        }

        //获取文件的名称

        String fileName = UploadUtils.getFileName(part);


        //给图片设置新名字

        String saveName = System.currentTimeMillis() + "." + FilenameUtils.getExtension(fileName);

        //给新的图片设置目录
        String filePath = "picture" + File.separator + DateUtils.dateToStr(new Date(), "yyyy" + File.separator + "MMddHHmmss")
                + (new Random().nextInt(8999) + 1000);

        File file = new File(filePathQ + File.separator + filePath);
        //设置完成之后进行判断创建
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }


        //把路径和名称进行全部write输出
        String write = filePathQ + File.separator + filePath + File.separator + saveName;


        part.write(write);


        //进行返回的处理
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("msg", "上传成功");
        result.put("file_path", "download?filePath=" + (filePath + File.separator + saveName).replace(File.separator, "/"));
        result.put("img_path", (filePath + File.separator + saveName).replace(File.separator, "/"));
        result.put("file_name", UploadUtils.getFileName(part));
        OutputUtils.write(resp, result.toJSONString(), OutputUtils.CONTENTTYPE_JSON);
        return;


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
