package com.xcjy.infra.utils.encrypt;

/*import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;*/
/**
 * <p>Title: 单例模式为数据加密工具类 </p>
 * <p>Description:提供base64和md5加密方式</p>
 * 使用规范：所有使用spring容器的类都需要实现此接口
 * <p>Copyright: Copyright (c) 2007-2009</p>
 * <p>Company: 恒拓开源信息科技有限公司版权所有 </p> 
 * @author huangsong
 * @version 1.0
 */
public class CodeUtil {
   private static CodeUtil code=new CodeUtil();
   private CodeUtil(){
	   
   }
   /*public static Code getInstance(){
	   Lock lock=new ReentrantLock();
	   lock.lock();
	   try{
	   if(code==null)
		   code=new Code();
	   }finally{
		   lock.unlock();
	   }
	   return code;
   }*/
   
   public synchronized static CodeUtil getInstance(){
	   return code;
   }
   /**
    * 数据加密
    * @param code 要加密的字符串
    * @param t 使用什么类型加密
    * @return 加密后的字符串
    */
   public String encode(String code,Type t){
	   switch(t){
	   case BASE64:
		    return Base64.encode(code.getBytes());
	   case MD5:   
		   MD5 m=new MD5();
		  return m.getMD5ofStr(code);
	   }
	   return null;
   }
   /**
    * 数据解密，只针对于Base64的加密
    * @param code 要解密的字符串
    * @return String 解密后的字符串
    */
   public String decode(String code){
	   return new String(Base64.decode(code));
   }
   
}

