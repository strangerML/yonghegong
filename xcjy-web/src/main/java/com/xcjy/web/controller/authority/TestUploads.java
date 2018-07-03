package com.xcjy.web.controller.authority;

import com.alibaba.fastjson.JSONObject;
import com.xcjy.infra.utils.datetime.DateUtils;
import com.xcjy.infra.utils.http.OutputUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

/**
 * Created by lishan on 2018/4/18.
 */
@Controller
public class TestUploads {


    //定义一个文件的储存路径
    private static final String filePathQ = "E:\\test\\xcjb-web";

    @RequestMapping(value = "/test/uploads",method = RequestMethod.POST)
    public void uploadss(HttpServletRequest request,HttpServletResponse response, String  isSimditorUpload, MultipartFile file ){

        StringBuilder builder = new StringBuilder();

        //判断是否 为1
        if(!"1".equals(isSimditorUpload)){
            System.out.println("不为1");
            builder.append("<script type=\"text/javascript\">");
            builder.append("alert('上传文件出现异常，请检查输入是否有误！');");
            builder.append("window.history.back();");
            builder.append("</script>");
            OutputUtils.write(response, builder.toString(), OutputUtils.CONTENTTYPE_HTML);
            return;
        }

        //查看是否有文件
        if(file.isEmpty()){
            System.out.println("图片为空");
            JSONObject result = new JSONObject();
            result.put("success", false);
            result.put("msg", "上传图片为空，请检查输入是否有误！");
            OutputUtils.write(response, result.toJSONString(), OutputUtils.CONTENTTYPE_JSON);
            return;
        }

        //获取图片的名称
        String fileName = file.getOriginalFilename();

        //设置新图片的名称
        String newFilename = System.currentTimeMillis()+"."+ FilenameUtils.getExtension(fileName);

        //设置新的存放路径
        String folderPath = "pictures" + File.separator + DateUtils.dateToStr(new Date(), "yyyy" + File.separator + "MMddHHmmss")
                + (new Random().nextInt(8999) + 1000);

        //设置要创建的目录
        String createM = filePathQ+File.separator+folderPath;
        File cfile = new File(createM,newFilename);

        if(!cfile.exists()||!cfile.isDirectory()){
            cfile.mkdirs();
        }

        //把图片写到目录中
        String newtu = createM+File.separator+newFilename;


        try {
            file.transferTo(cfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("msg", "上传成功");
        result.put("file_path", "download?filePath=" + (folderPath + File.separator + newFilename).replace(File.separator, "/"));
        result.put("img_path", (folderPath + File.separator + newFilename).replace(File.separator, "/"));
        result.put("file_name", fileName);
        OutputUtils.write(response, result.toJSONString(), OutputUtils.CONTENTTYPE_JSON);

        System.out.println("哈哈");

    }
}
