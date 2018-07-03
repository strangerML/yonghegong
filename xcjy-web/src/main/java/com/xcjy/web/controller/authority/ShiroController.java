package com.xcjy.web.controller.authority;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xcjy.application.authority.IModuleApplication;
import com.xcjy.application.authority.IUserApplication;
import com.xcjy.entity.authority.Module;
import com.xcjy.entity.authority.User;
import com.xcjy.infra.utils.Constants;
import com.xcjy.infra.utils.encrypt.CodeUtil;
import com.xcjy.infra.utils.encrypt.Type;
import com.xcjy.infra.utils.http.MimeUtil;
import com.xcjy.infra.utils.img.CaptchaUtil;



/**
 * Shiro授权
 * 
 * @author binbinccut@163.com
 *
 */
@Controller
public class ShiroController {

	/**
	 * 超级用户的角色
	 */
	public static final String SUPER_ROLE_NAME = "administrator";

	@Autowired
	private IModuleApplication moduleApplication;

	@Autowired
	private IUserApplication userApplication;

	/**
	 * 登录页面的访问
	 * 
	 * @return
	 */
	@RequestMapping("/signIn")
	public String signIn() {
		Subject subject = SecurityUtils.getSubject();
		// 如果已经登录，则直接进入主界面
		if (subject.isAuthenticated()) {
			return "redirect:/index";
		}
		// 否则显示登录页面
		return "/timeout";
	}
	
	/**
	 * 进入登录页
	 * @return
	 */
	@RequestMapping("/gologinpage")
	public String goLoginPage(){
		return "/login";
	}

	/**
	 * 获取验证码图片
	 * 
	 * @param session
	 * @param response
	 */
	@RequestMapping("/kaptcha.jpg")
	public void getKaptcha(HttpServletResponse response) {
		String verifyCode = CaptchaUtil.generateVerifyCode(4);
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		session.setAttribute(CaptchaUtil.VERIFY_SESSION_VALUE, verifyCode.toUpperCase());
		session.setAttribute(CaptchaUtil.VERIFY_SESSION_TIME, System.currentTimeMillis());
		CaptchaUtil.write(200, 80, verifyCode, response);
	}

	/**
	 * 登录操作
	 * 
	 * @param userName
	 *            账户
	 * @param password
	 *            密码
	 * @param captcha
	 *            验证码
	 * @param rememberMe
	 *            记住我
	 * @return
	 */
	@RequestMapping("/login")
	public String login(@RequestParam("username") String userName, @RequestParam("password") String password,
			@RequestParam("captcha") String captcha,
			@RequestParam(value = "rememberme", required = false) String rememberMe) {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		if (StringUtils.isEmpty(userName)) {
			session.setAttribute("login_error_info", "用户名不能为空！");
			return "redirect:/signIn";
		}
		if (StringUtils.isEmpty(password)) {
			session.setAttribute("login_error_info", "密码不能为空！");
			return "redirect:/signIn";
		}
		if (StringUtils.isEmpty(captcha)) {
			session.setAttribute("login_error_info", "验证码不能为空！");
			return "redirect:/signIn";
		}
		String verifyCode = (String) session.getAttribute(CaptchaUtil.VERIFY_SESSION_VALUE);
		if (StringUtils.isEmpty(verifyCode)) {
			session.setAttribute("login_error_info", "验证码已失效！");
			return "redirect:/signIn";
		}
		long captcha_time = (long) session.getAttribute(CaptchaUtil.VERIFY_SESSION_TIME);
		if ((System.currentTimeMillis() - captcha_time) > CaptchaUtil.VERIFY_SESSION_LIFE) {
			session.setAttribute("login_error_info", "验证码已失效！");
			return "redirect:/signIn";
		}
		if (!verifyCode.equals(captcha.toUpperCase())) {
			session.setAttribute("login_error_info", "验证码错误！");
			return "redirect:/signIn";
		}

		password = passwordEncode(password, userName);
		UsernamePasswordToken upToken = new UsernamePasswordToken(userName, password);
		try {
			// 记住我
			if (rememberMe != null && rememberMe.equals("on")) {
				upToken.setRememberMe(true);
			}
			// 登录
			subject.login(upToken);
		} catch (AuthenticationException e) {
			session.setAttribute("login_error_info", e.getMessage());
			return "redirect:/signIn";
		}

		session.removeAttribute("login_error_info");
		// 重定向到主页面
		return "redirect:/index";

	}

	/**
	 * 密码加密
	 * 
	 * @param password
	 *            未加密密码
	 * @param salt
	 *            盐，可以为null
	 * @return 加密后的密码
	 */
	private String passwordEncode(String password, String salt) {
		CodeUtil code = CodeUtil.getInstance();
		return code.encode(password + (StringUtils.isEmpty(salt) ? "" : Constants.COLON + salt), Type.MD5);
	}

	/**
	 * 登出操作
	 * 
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "redirect:/";
	}

	/**
	 * 初始化菜单列表，返回json串
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getMenus", produces = MimeUtil.JSON)
	@ResponseBody
	public List<Module> getMenusByUserName() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.hasRole(SUPER_ROLE_NAME)) {
			return moduleApplication.getMenus();
		}
		return moduleApplication.getMenusByUser((String) subject.getPrincipal());
	}

	/**
	 * 修改密码
	 * @param oldPwd
	 * @param password
	 * @param confirmPassword
	 * @return
	 */
	@RequestMapping(value = "/changePassword", produces = MimeUtil.JSON)
	@ResponseBody
	public Map<String, Object> changePassword(@RequestParam("oldPassword") String oldPwd,
			@RequestParam("password") String password, @RequestParam("confirmPassword") String confirmPassword) {
		Map<String, Object> result = new HashMap<>();
		result.put("result", false);
		if (StringUtils.isEmpty(oldPwd)) {
			result.put("msg", "旧密码不能为空！");
			return result;
		}
		if (StringUtils.isEmpty(password)) {
			result.put("msg", "新密码不能为空！");
			return result;
		}
		if (StringUtils.isEmpty(confirmPassword)) {
			result.put("msg", "确认密码不能为空！");
			return result;
		}
		if (!password.equals(confirmPassword)) {
			result.put("msg", "新密码与确认密码不一致！");
			return result;
		}

		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		User seesionUser = (User) session.getAttribute(Constants.CURRENT_USER);
		User user = userApplication.get(seesionUser.getId());
		String userName = user.getUserName();
		oldPwd = passwordEncode(oldPwd, userName);
		if (!oldPwd.equals(user.getPassword())) {
			result.put("msg", "旧密码不正确！");
			return result;
		}
		password = passwordEncode(password, userName);
		user.setPassword(password);
		user.setUpdateTime(new Date());
		userApplication.update(user);
		result.put("result", true);
		return result;
	}

	/**
	 * demo 巨幕主页demo
	 * 
	 * @return
	 */
	@RequestMapping("/gojumbotron")
	public String goJumbotronPage() {
		return "index/jumbotron";
	}

}
