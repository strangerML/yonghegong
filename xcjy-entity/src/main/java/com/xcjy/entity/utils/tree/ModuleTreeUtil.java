package com.xcjy.entity.utils.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.xcjy.entity.authority.Module;


/**
 * 菜单树工具类
 * 
 * @author 支亚州
 *
 */
public class ModuleTreeUtil {

	/**
	 * 将乱序的资源菜单整理成树
	 * 
	 * @param modelList
	 * @return
	 */
	public static List<Module> generateTree(List<Module> modelList) {
		if (modelList == null || modelList.isEmpty()) {
			return modelList;
		}
		List<Module> parents = new ArrayList<Module>();
		List<Module> others = new ArrayList<Module>();
		for (Module module : modelList) {
			Long parentId = module.getParentId();
			if (null == parentId || parentId <= 0) {// 一级节点
				Integer isLeaf = module.getIsLeaf();// 是否为叶子节点。0否，1是。
				if (1 == isLeaf) {// 是叶子节点
					module.setChildren(null);
				} else {// 不是叶子节点
					module.setChildren(new ArrayList<Module>());
				}
				parents.add(module);
			} else {
				others.add(module);
			}
		}
		buildTree(parents, others);
		return parents;
	}

	private static void buildTree(List<Module> parents, List<Module> others) {
		if (parents.isEmpty() || others.isEmpty()) {
			return;
		}
		List<Module> record = new ArrayList<Module>();
		for (Module parent : parents) {
			if (1 == parent.getIsLeaf()) {
				continue;
			}
			Iterator<Module> oit = others.iterator();
			while (oit.hasNext()) {
				Module ot = oit.next();
				if (parent.getId().equals(ot.getParentId())) {
					if (1 == ot.getIsLeaf()) {// 是叶子节点
						ot.setChildren(null);
					} else {// 不是叶子节点
						ot.setChildren(new ArrayList<Module>());
						record.add(ot);
					}
					parent.getChildren().add(ot);
					oit.remove();
				}
			}
		}
		buildTree(record, others);
	}

}
