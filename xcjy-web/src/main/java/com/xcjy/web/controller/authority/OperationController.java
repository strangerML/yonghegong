package com.xcjy.web.controller.authority;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.xcjy.application.authority.IOperationApplication;
import com.xcjy.entity.authority.Operation;
import com.xcjy.infra.utils.page.PageModel;
import com.xcjy.web.utils.DataTablesPageModel;

/**
 * 操作管理
 * 
 * @author binbinccut@163.com
 *
 */
@Controller
@RequestMapping("/operation")
public class OperationController {

	@Autowired
	private IOperationApplication operationApplication;

	@RequestMapping("/goOperationPage")
	public String goOperationPage() {
		return "authority/operation-page";
	}

	@RequestMapping("/query")
	@ResponseBody
	public PageModel<Operation> query(DataTablesPageModel<Operation> pm, Operation operation,
			HttpServletRequest request) {
		pm.setOrderMap(request);

		if (StringUtils.isNotEmpty(operation.getName())) {
			pm.getLikeMap().put("name", operation.getName());
		}
		if (StringUtils.isNotEmpty(operation.getSn())) {
			pm.getLikeMap().put("sn", operation.getSn());

		}
		pm = (DataTablesPageModel<Operation>) operationApplication.query(pm);
		return pm;
	}

	@RequestMapping("/goMergePage")
	public String goMergePage(Operation operation, Model model) {
		Long id = operation.getId();
		if (id != null) {
			operation = operationApplication.get(id);
			model.addAttribute("operation", operation);
		}
		return "authority/operation-merge";
	}

	@RequestMapping("merge")
	@ResponseBody
	public JSONObject merge(Operation operation) {
		JSONObject jsonObject = new JSONObject();
		Long id = operation.getId();
		String sn = operation.getSn();
		List<Operation> snList = operationApplication.findByField("sn", sn);
		if (snList != null && !snList.isEmpty()) {
			for (Operation op : snList) {
				if (!op.getId().equals(id)) {
					jsonObject.put("result", false);
					jsonObject.put("msg", "该操作标识符已存在！");
					return jsonObject;
				}
			}
		}
		if (id != null) {
			Operation oldOperation = operationApplication.get(id);
			if (oldOperation != null) {
				oldOperation.setName(operation.getName());
				oldOperation.setSn(sn);
				oldOperation.setRemark(operation.getRemark());
				oldOperation.setUpdateTime(new Date());
				operationApplication.update(oldOperation);
				jsonObject.put("result", true);
				return jsonObject;
			} else {
				jsonObject.put("result", false);
				jsonObject.put("msg", "未选择要修改的数据！");
				return jsonObject;
			}
		} else {
			operation.setCreateTime(new Date());
			operation.setUpdateTime(operation.getCreateTime());
			operationApplication.insert(operation);
			jsonObject.put("result", true);
			return jsonObject;
		}
	}

	@RequestMapping("del")
	@ResponseBody
	public JSONObject del(Operation operation) {
		JSONObject jsonObject = new JSONObject();
		Long id = operation.getId();
		if (id != null) {
			operationApplication.delete(id);
			jsonObject.put("result", true);
			return jsonObject;
		}
		jsonObject.put("result", false);
		jsonObject.put("msg", "未选择要删除的数据！");
		return jsonObject;
	}

	public String datablesDemo() {
		return "authority/datatables_demo";
	}

}
