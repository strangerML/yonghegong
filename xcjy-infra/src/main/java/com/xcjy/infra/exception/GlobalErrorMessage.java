package com.xcjy.infra.exception;

/**
 * Copyright (C) 2012 北京国信普瑞德科技发展有限公司
 *
 * @className:org.oss.airchina.energym.exception.GlobalErrorMessage
 * @description:
 * 
 * @version:v1.0.0 
 * @author:liuliang
 * 
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2012-10-11     huangsong       v1.0.0        create
 *
 *
 */

/**
 * @Description:
 * @author:liuliang
 * @version:v1.0.0
 * @Created:2012-10-11上午9:42:04
 * @Modified:
 */
public class GlobalErrorMessage {

	/** 账户登录失败 */
	public static final String ERROR_ACCOUNT_LOGIN_FAIL = "ACCOUNT_LOGIN_FAIL";
	/** 账户编号不能为空 */
	public static final String ERROR_ACCOUNT_NUM_CANNOT_NULL = "ACCOUNT_NUM_CANNOT_NULL";
	/** 账户编号已经存在，不能重复 */
	public static final String ERROR_ACCOUNT_NUM_EXIST = "ACCOUNT_NUM_EXIST";
	/** 账户不存在 */
	public static final String ERROR_ACCOUNT_NUM_NOT_EXIST = "ACCOUNT_NUM_NOT_EXIST";
	/** 原密码不正确*/
	public static final String ERROR_ACCOUNT_PASSWORD_ERROR = "ACCOUNT_PASSWORD_ERROR";
	/** 角色名称不能为空 */
	public static final String ERROR_ROLE_NAME_CANNOT_NULL = "ROLE_NAME_CANNOT_NULL";
	/** 角色已经存在，不能重复 */
	public static final String ERROR_ROLE_NAME_EXIST = "ROLE_NAME_EXIST";
	/** 角色不存在 */
	public static final String ERROR_ROLE_NAME_NOT_EXIST = "ROLE_NAME_NOT_EXIST";
	/** 角色已关联用户 */
	public static final String ERROR_ACCOUNT_EXIST_ROLE = "ACCOUNT_EXIST_ROLE";
	/** 权限名称不能为空 */
	public static final String ERROR_AUTHORITY_NAME_CANNOT_NULL = "AUTHORITY_NAME_CANNOT_NULL";
	/** 权限已经存在，不能重复 */
	public static final String ERROR_AUTHORITY_NAME_EXIST = "AUTHORITY_NAME_EXIST";
	/** 权限根节点不存在 */
	public static final String ERROR_AUTHORITY_ROOT_IS_NULL = "AUTHORITY_ROOT_IS_NULL";
	/** 权限不能添加第三级 */
	public static final String ERROR_AUTHORITY_IS_NOT_FORE = "AUTHORITY_IS_NOT_FORE";
	/** 该条目存在子表，不允许删除   ---ls---*/
	public static final String ERROR_ITEM_EXISTS_CHILDREN_CANNOT_DEL = "ERROR_ITEM_EXISTS_CHILDREN_CANNOT_DEL";
	
	/** 楼宇基础信息名称不能为空 */
	public static final String ERROR_BUILDING_NAME_CANNOT_NULL = "BUILDING_NAME_CANNOT_NULL";
	/** 楼宇基础信息名称已经存在，不能重复 */
	public static final String ERROR_BUILDING_NAME_EXIST = "BUILDING_NAME_EXIST";
	
	/** 楼宇企业信息名称不能为空 */
	public static final String ERROR_COMPANYINFO_NAME_CANNOT_NULL = "COMPANYINFO_NAME_CANNOT_NULL";
	/** 楼宇企业信息名称已经存在，不能重复 */
	public static final String ERROR_COMPANYINFO_NAME_EXIST = "COMPANYINFO_NAME_EXIST";
	
	/** 捐助站名称不能为空 */
	public static final String ERROR_DONATION_STATION_NAME_CANNOT_NULL = "DONATION_STATION_NAME_CANNOT_NULL";
	/** 捐助站名称已经存在，不能重复 */
	public static final String ERROR_DONATION_STATION_NAME_EXIST = "DONATION_STATION_NAME_EXIST";
	
	/** 捐助站不存在，不能修改 */
	public static final String ERROR_DONATION_STATION_NAME_CANNOT_MODIFY = "ERROR_DONATION_STATION_NAME_CANNOT_MODIFY";
	
	/** 捐助站不存在，不能删除 */
	public static final String ERROR_DONATION_STATION_CANNOT_REMOVE = "ERROR_DONATION_STATION_NAME_CANNOT_REMOVE";
	
	/** 捐助站子表中存在数据，不能删除 */
	public static final String ERROR_DONATION_STATION_CHIRD_TABLE_HAS_DATA = "ERROR_DONATION_STATION_CHIRD_TABLE_HAS_DATA";
	
	/** 物资名称已存在，不能重复添加 */
	public static final String ERROR_DONATION_STOREROOM_BASE_GOODS_CANNOT_SAVE = "ERROR_DONATION_STOREROOM_BASE_GOODS_CANNOT_SAVE";
	
	/** 物资不存在数据，不能修改 */
	public static final String ERROR_DONATION_STOREROOM_BASE_GOODS_CANNOT_MODIFY = "ERROR_DONATION_STOREROOM_BASE_GOODS_CANNOT_MODIFY";
	
	/** 物资不存在数据，不能删除 */
	public static final String ERROR_DONATION_STOREROOM_BASE_GOODS_CANNOT_REMOVE = "ERROR_DONATION_STOREROOM_BASE_GOODS_CANNOT_REMOVE";
	
	/** 街道id不能为空 */
	public static final String ERROR_DONATION_SYS_STREET_ID_IS_NULL = "ERROR_DONATION_SYS_STREET_ID_IS_NULL";
	
	/** 社区id不能为空 */
	public static final String ERROR_DONATION_SYS_COMMUNITY_ID_IS_NULL = "ERROR_DONATION_SYS_COMMUNITY_ID_IS_NULL";

	/** 计量单位id不能为空 */
	public static final String ERROR_DONATION_SYS_CALCULATE_UNIT_ID_IS_NULL = "ERROR_DONATION_SYS_CALCULATE_UNIT_ID_IS_NULL";
	
	/** 物资类型id不能为空 */
	public static final String ERROR_DONATION_SYS_GOODS_TYPE_ID_IS_NULL = "ERROR_DONATION_SYS_GOODS_TYPE_ID_IS_NULL";
	
	/** 物资基础信息子表中存在数据，不能删除 */
	public static final String ERROR_DONATION_STOREROOM_BASE_GOODS_TABLE_HAS_DATA = "ERROR_DONATION_STOREROOM_BASE_GOODS_TABLE_HAS_DATA";
	/** 派单已入库，不允许删除*/
	public static final String ERROR_DON_GOODS_LIBRARY_HAS_JOIN ="ERROR_DON_GOODS_LIBRARY_HAS_JOIN";
	/**义工社id不能为空 ， */
	public static final String ERROR_VOLYGORG_IS_NOT_NULL = "ERROR_VOLYGORG_IS_NOT_NULL";
	/**义工社id不能为空 ， */
	public static final String ERROR_VOLYGINFO_NAME_IS_SAME = "ERROR_VOLYGINFO_NAME_IS_SAME";
	
	/**义工社存在子表，不能删除 ， */
	public static final String ERROR_VOLYGORG_CHIRD_TABLE_CANNOT_DEL = "ERROR_VOLYGORG_CHIRD_TABLE_CANNOT_DEL";
	
	/**义工社不存在 ， */
	public static final String ERROR_VOLYGORG_IS_NULL = "ERROR_VOLYGORG_IS_NULL";
	
	/**义工社名字相同，不能新增 ， */
	public static final String ERROR_VOLYGORG_NAME_IS_SAME = "ERROR_VOLYGORG_NAME_IS_SAME";	
	/**义工存在子表，不能删除 ， */
	public static final String ERROR_VOLYGINFO_CHILDREN_CANNOT_DEL = "ERROR_VOLYGINFO_CHILDREN_CANNOT_DEL";
	
	/**义工关联的街道不存在，不能新增 ， */
	public static final String ERROR_VOLYGINFO_STREET_ID_IS_NULL = "ERROR_VOLYGINFO_STREET_ID_IS_NULL";
	
	/**义工关联的社区不存在，不能新增 ， */
	public static final String ERROR_VOLYGINFO_COMMUNITY_ID_IS_NULL = "ERROR_VOLYGINFO_COMMUNITY_ID_IS_NULL";
	
	/**义工关联的社区不存在，不能修改 ， */
	public static final String ERROR_VOLYGINFO_VOLYGORG_ID_IS_NULL = "ERROR_VOLYGINFO_VOLYGORG_ID_IS_NULL";
	
	/**义工所属组织ID不存在，不能新增 ， */
	public static final String ERROR_VOLYGINFOORG_ID_IS_NULL = "ERROR_VOLYGINFOORG_ID_IS_NULL";
	
	/**义工服务实体不存在，不能删除 ， */
	public static final String ERROR_VOLYGSERVICE_IS_NULL = "ERROR_VOLYGSERVICE_IS_NULL";
	
	/**义工服务不是同一条记录，不能修改， */
	public static final String ERROR_VOLYGSERVICE_CHIRD_TABLE_HAS_DATA = "ERROR_VOLYGSERVICE_CHIRD_TABLE_HAS_DATA";

	/**志愿者组织不存在， ， */
	public static final String ERROR_VOLZYZORGINFO_IS_NULL = "ERROR_VOLZYZORGINFO_IS_NULL";
	
	/**志愿者组织已存在，不能重复 ， */
	public static final String ERROR_VOLZYZORGINFO_IS_CONNOT_SAME = "ERROR_VOLZYZORGINFO_IS_CONNOT_SAME";
	
	/**志愿者不存在， */
	public static final String ERROR_VOLZYZINFO_IS_NULL = "ERROR_VOLZYZINFO_IS_NULL";
	
	/**志愿者名字已存在，不能重复 */
	public static final String ERROR_VOLZYZNAME_IS_CONNOT_SAME = "ERROR_VOLZYZNAME_IS_CONNOT_SAME";
	
	/**志愿者关联的街道不存在，不能新增 */
	public static final String ERROR_VOLZYZINFO_STREET_ID_IS_NULL = "ERROR_VOLZYZINFO_STREET_ID_IS_NULL";
	
	/**志愿者关联的社区不存在，不能新增 */
	public static final String ERROR_VOLZYZINFO_COMMUNITY_ID_IS_NULL = "ERROR_VOLZYZINFO_COMMUNITY_ID_IS_NULL";
	
	/**志愿者关联的组织不存在，不能新增 */
	public static final String ERROR_VOLZYZINFO_ORG_ID_IS_NULL = "ERROR_VOLZYZINFO_ORG_ID_IS_NULL";
	
	/**志愿者服务信息不存在，*/
	public static final String ERROR_VOLZYZSERVICE_IS_NULL = "ERROR_VOLZYZSERVICE_IS_NULL";
	
	/**志愿者培训信息不存在，*/
	public static final String ERROR_VOLZYZTRAIN_IS_NULL = "ERROR_VOLZYZTRAIN_IS_NULL";
	
	/**志愿者表彰奖励信息实体不存在，*/
	public static final String ERROR_VOLZYZPRAISE_IS_NULL = "ERROR_VOLZYZPRAISE_IS_NULL";
	
	/**志愿者被投诉信息实体不存在，*/
	public static final String ERROR_VOLZYZCOMPLAINT_IS_NULL = "ERROR_VOLZYZCOMPLAINT_IS_NULL";
	
	/**志愿者星级评定实体不存在，*/
	public static final String ERROR_VOLZYZSTARS_IS_NULL = "ERROR_VOLZYZSTARS_IS_NULL";
	
	/**志愿者服务时间储备实体不存在，*/
	public static final String ERROR_VOLZYZRESERVE_IS_NULL = "ERROR_VOLZYZRESERVE_IS_NULL";
	
	/**盘点信息不存在，不能删除 ， */
	public static final String ERROR_STOREROOM_CHECK_IS_NULL = "ERROR_STOREROOM_CHECK_IS_NULL";
	
	/**公益人员信息不存在，， */
	public static final String ERROR_VOLGYINFO_IS_NULL = "ERROR_VOLGYINFO_IS_NULL";
	
	/**公益人员已存在。不能新增，， */
	public static final String ERROR_VOLZYZGYINFO_NAME_CANNOT_SAME = "ERROR_VOLZYZGYINFO_NAME_CANNOT_SAME";
	
	/**公益人员所属街道不存在。， */
	public static final String ERROR_VOLGYINFO_STREET_ID_IS_NULL = "ERROR_VOLGYINFO_STREET_ID_IS_NULL";
	
	/**公益人员所属社区不存在。， */
	public static final String ERROR_VOLGYINFO_COMMUNITY_ID_IS_NULL = "ERROR_VOLGYINFO_COMMUNITY_ID_IS_NULL";
	
	/**公益人员服务信息不存在。， */
	public static final String ERROR_VOLGYSERVICE_IS_NULL = "ERROR_VOLGYSERVICE_IS_NULL";
	
	/**日志实体不存在。， */
	public static final String ERROR_LOG_IS_NULL = "ERROR_LOG_IS_NULL";
	
	/**社区属性管理实体不存在。， */
	public static final String ERROR_COMMUNITTY_IS_NULL = "ERROR_COMMUNITTY_IS_NULL";
	
	/**社区属性名字不能重复 */
	public static final String ERROR_COMMUNITTY_NAME_CANNOT_SAME = "ERROR_COMMUNITTY_NAME_CANNOT_SAME";
	
	/**社区所属街道实体不存在 */
	public static final String ERROR_COMMUNITY_IS_NULL = "ERROR_COMMUNITY_IS_NULL";
	
	/**街道实体不存在 */
	public static final String ERROR_STREET_IS_NULL = "ERROR_STREET_IS_NULL";
	
	/**街道名字相同， */
	public static final String ERROR_STREETATTR_CHIRD_TABLE_HAS_DATA = "ERROR_STREETATTR_CHIRD_TABLE_HAS_DATA";
	
	/**计量单位实体不存在， */
	public static final String ERROR_CALCULATE_IS_NULL = "ERROR_CALCULATE_IS_NULL";
	
	/**计量单位不是同一条记录， */
	public static final String ERROR_CALCULATE_NAME_EXIST = "ERROR_CALCULATE_NAME_EXIST";
	
	
	/**计量单位已存在， 不能新增*/
	public static final String ERROR_CALCULATE_CHIRD_TABLE_HAS_DATA = "ERROR_CALCULATE_CHIRD_TABLE_HAS_DATA";
	
	/**物资实体不存在*/
	public static final String ERROR_GOODS_IS_NOT_NULL = "ERROR_GOODS_IS_NOT_NULL";
	
	/**物资实体不是同一条记录*/
	public static final String ERROR_GOODS_CHIRD_TABLE_HAS_DATA = "ERROR_GOODS_CHIRD_TABLE_HAS_DATA";
	
	/**消息记录不存在*/
	public static final String ERROR_MESSAGE_IS_NOT_NULL = "ERROR_MESSAGE_IS_NOT_NULL";

	/**消息实体不是同一条记录*/
	public static final String ERROR_MESSAGE_CHIRD_TABLE_HAS_DATA = "ERROR_MESSAGE_CHIRD_TABLE_HAS_DATA";
	/**爱心卡号不能为空*/
	public static final String ERROR_LOVE_CARD_NUMBER_CANNOT_NULL = "ERROR_LOVE_CARD_NUMBER_CANNOT_NULL";
	/**爱心卡号已存在*/
	public static final String ERROR_LOVE_CARD_NUMBER_EXISTS = "ERROR_LOVE_CARD_NUMBER_EXISTS";
	
	/**该物资库存不足，请重新操作*/
	public static final String ERROR_GOOD_STOCK_IS_NOT_FULL ="ERROR_GOOD_STOCK_IS_NOT_FULL";

}
