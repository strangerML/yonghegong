package com.xcjy.infra.utils.enums.status;

import com.xcjy.infra.utils.enums.IDescription;

/**
 * 默认状态枚举
 * 
 * @author QuBinBin
 *
 */
public enum DefaultStatus implements IDescription {

	/** 正常 */
	STATUS_ON(1) {

		@Override
		public String getDesc() {
			return "正常";
		}

	},
	/** 未激活 */
	STATUS_NORMAL(0) {

		@Override
		public String getDesc() {
			return "未激活";
		}

	},
	/** 禁用 */
	STATUS_OFF(-1) {

		@Override
		public String getDesc() {
			return "禁用";
		}

	};

	private int value = 1;

	private DefaultStatus(int value) {
		this.value = value;
	}

	public static final DefaultStatus valueOf(int value) {
		switch (value) {
		case 1:
			return STATUS_ON;
		case 0:
			return STATUS_NORMAL;
		case -1:
			return STATUS_OFF;
		default:
			return null;
		}
	}

	public int value() {
		return this.value;
	}

	@Override
	public abstract String getDesc();

}
