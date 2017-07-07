package com.spinach.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 枚举常量定义
 * 
 * @version 1.0
 * @author Shang Jingmin
 * @date 2016-8-11
 */
public class Constants {

	/** 航班调整类型 0:新增 1:机型 2:机号 3:航线 4:修改时间 5:时刻调整 6:备降 7:取消 8:返航 9:取消恢复 10:顺延 11:起降桥位 常量值需与T_FM_DICTIONARY_DETAIL保持一致，慎改 **/
	public static final String ADJUST_TYPE_NEW = "0";
	public static final String ADJUST_TYPE_NEW_NAME = "新增航班";
	public static final String ADJUST_TYPE_FLIGHT_TYPE = "1";
	public static final String ADJUST_TYPE_FLIGHT_TYPE_NAME = "机型";
	public static final String ADJUST_TYPE_ACNO = "2";
	public static final String ADJUST_TYPE_ACNO_NAME = "机号调整";
	public static final String ADJUST_TYPE_LINE = "3";
	public static final String ADJUST_TYPE_LINE_NAME = "航线调整";
	public static final String ADJUST_TYPE_TIME = "4";
	public static final String ADJUST_TYPE_TIME_NAME = "修改时间";
	public static final String ADJUST_TYPE_DELAY = "5";
	public static final String ADJUST_TYPE_DELAY_NAME = "时刻调整";
	public static final String ADJUST_TYPE_COM = "6";
	public static final String ADJUST_TYPE_COM_NAME = "航班备降";
	public static final String ADJUST_TYPE_CAN = "7";
	public static final String ADJUST_TYPE_CAN_NAME = "取消处理";
	public static final String ADJUST_TYPE_RETURN = "8";
	public static final String ADJUST_TYPE_RETURN_NAME = "返航";
	public static final String ADJUST_TYPE_RESUME = "9";
	public static final String ADJUST_TYPE_RESUME_NAME = "恢复处理";
	public static final String DAY_ADJUST_EXTENSION_DELAY = "10";
	public static final String DAY_ADJUST_EXTENSION_DELAY_NAME = "顺延处理";
	public static final String ADJUST_TYPE_BRIDGE = "11";
	public static final String ADJUST_TYPE_BRIDGE_NAME = "起降桥位";

	/** 调整类型 **/
	public static final Map<String, String> ADJUST_TYPE = new HashMap<String, String>();
	static {
		ADJUST_TYPE.put(ADJUST_TYPE_NEW, ADJUST_TYPE_NEW_NAME);
		ADJUST_TYPE.put(ADJUST_TYPE_FLIGHT_TYPE, ADJUST_TYPE_FLIGHT_TYPE_NAME);
		ADJUST_TYPE.put(ADJUST_TYPE_ACNO, ADJUST_TYPE_ACNO_NAME);
		ADJUST_TYPE.put(ADJUST_TYPE_LINE, ADJUST_TYPE_LINE_NAME);
		ADJUST_TYPE.put(ADJUST_TYPE_TIME, ADJUST_TYPE_TIME_NAME);
		ADJUST_TYPE.put(ADJUST_TYPE_DELAY, ADJUST_TYPE_DELAY_NAME);
		ADJUST_TYPE.put(ADJUST_TYPE_COM, ADJUST_TYPE_COM_NAME);
		ADJUST_TYPE.put(ADJUST_TYPE_CAN, ADJUST_TYPE_CAN_NAME);
		ADJUST_TYPE.put(ADJUST_TYPE_RETURN, ADJUST_TYPE_RETURN_NAME);
		ADJUST_TYPE.put(ADJUST_TYPE_RESUME, ADJUST_TYPE_RESUME_NAME);
		ADJUST_TYPE.put(DAY_ADJUST_EXTENSION_DELAY, DAY_ADJUST_EXTENSION_DELAY_NAME);
		ADJUST_TYPE.put(ADJUST_TYPE_BRIDGE, ADJUST_TYPE_BRIDGE_NAME);
	}

	/** 修改类型 1，周计划保障 2，周计划编辑 3，周计划调整 4,update 5，删除 **/
	public static final String DAYFLIGHT_MODIFY_UPDATE = "4";
	public static final String DAYFLIGHT_MODIFY_DELETE = "5";

	/** 方案类型（0：快捷 1:模拟） */
	public static final String PLAN_TYPE_SHORTCUT = "0";
	public static final String PLAN_TYPE_SIMULATION = "1";

	/** 操作步骤(0:原始航班数据) */
	public static final int OPT_STEP_1 = 0;

	/** 方案状态(0:草稿 1:确认中 2:已发布 3:发送至值班经理 4：退回 5:作废6:保存) */
	public static final String PLAN_STS_DRAFT = "0";
	public static final String PLAN_STS_COMFIRM = "1";
	public static final String PLAN_STS_RELEASE = "2";
	public static final String PLAN_STS_SENDTO_MANAGER = "3";
	public static final String PLAN_STS_RETURN = "4";
	public static final String PLAN_STS_DELETE = "5";
	public static final String PLAN_STS_SAVE = "6";

	/** STS 状态(0:有效 1：作废) */
	public static final String STS_VALID = "0";
	public static final String STS_DELETE = "1";

	/** STS 状态(0:发起确认 1：发布 2：保存) */
	public static final String OPTSTS_COMFIRM = "0";
	public static final String OPTSTS_RELEASE = "1";
	public static final String OPTSTS_SAVE = "2";

	/** SMS发送 状态(0:失败 1：成功) */
	public static final String SMS_SEND_FAIL = "0";
	public static final String SMS_SEND_SUCCESS = "1";

	/** 是or否下拉列表数据 **/
	public static final Map<String, String> YES_OR_NO_FUNCTION = new LinkedHashMap<String, String>();
	static {
		YES_OR_NO_FUNCTION.put("", "");
		YES_OR_NO_FUNCTION.put("0", "否");
		YES_OR_NO_FUNCTION.put("1", "是");
	}

	/** 有or无下拉列表数据 **/
	public static final Map<String, String> HAS_OR_NOT_FUNCTION = new LinkedHashMap<String, String>();
	static {
		HAS_OR_NOT_FUNCTION.put("", "");
		HAS_OR_NOT_FUNCTION.put("0", "无");
		HAS_OR_NOT_FUNCTION.put("1", "有");
	}

	/** 限制、禁止、建议、必须下拉列表数据 **/
	public static final Map<String, String> LIMIT_OR_FORBBIT_FUNCTION = new LinkedHashMap<String, String>();
	static {
		LIMIT_OR_FORBBIT_FUNCTION.put("", "");
		LIMIT_OR_FORBBIT_FUNCTION.put("0", "无限制");
		LIMIT_OR_FORBBIT_FUNCTION.put("1", "限制");
		LIMIT_OR_FORBBIT_FUNCTION.put("2", "禁止");
		LIMIT_OR_FORBBIT_FUNCTION.put("3", "建议");
		LIMIT_OR_FORBBIT_FUNCTION.put("4", "必须");
	}
	
	/** 航班类型下拉列表数据 1 正班、2 补班、3 加班、4 包机、5 调机、 6 试飞、 7 训练、 8 本场训练 **/
	public static final Map<String, String> FLIGHT_TYPE = new LinkedHashMap<String, String>();
	static {
		FLIGHT_TYPE.put("1", "正班");
		FLIGHT_TYPE.put("2", "补班");
		FLIGHT_TYPE.put("3", "加班");
		FLIGHT_TYPE.put("4", "包机");
		FLIGHT_TYPE.put("5", "调机");
		FLIGHT_TYPE.put("6", "试飞");
		FLIGHT_TYPE.put("7", "训练");
		FLIGHT_TYPE.put("8", "本场训练");
		// FLIGHT_TYPE.put("9", "正班");
	}

	/** 国内国际下拉列表数据 0 国内、1 国际 **/
	public static final Map<String, String> DORI_TYPE = new LinkedHashMap<String, String>();
	static {
		DORI_TYPE.put("0", "国内");
		DORI_TYPE.put("1", "国际");

	}

	/** 客运货运下拉列表数据 0 客运、1 货运 **/
	public static final Map<String, String> PORC_TYPE = new LinkedHashMap<String, String>();
	static {
		PORC_TYPE.put("0", "客运");
		PORC_TYPE.put("1", "货运");

	}

	/** 仓位布局下拉列表数据 180、186 **/
	public static final Map<String, String> LAYOUT_TYPE = new LinkedHashMap<String, String>();
	static {
		LAYOUT_TYPE.put("180", "180");
		LAYOUT_TYPE.put("186", "186");

	}

	/** 考勤类型 **/
	public static final Map<String, String> SIGN_IN_STATUS = new HashMap<String, String>();
	static {
		SIGN_IN_STATUS.put("0", "未打");
		SIGN_IN_STATUS.put("1", "正常");
		SIGN_IN_STATUS.put("2", "迟到");
		SIGN_IN_STATUS.put("3", "误机");
	}
	/** 考勤备注 **/
	public static final Map<String, String> SIGN_IN_STATUS_SHOW = new HashMap<String, String>();
	static {
		SIGN_IN_STATUS_SHOW.put("误机未到公司备份", "误机未到公司备份");
		SIGN_IN_STATUS_SHOW.put("准时参加准备会但忘记打卡", "准时参加准备会但忘记打卡");
		SIGN_IN_STATUS_SHOW.put("准时参加准备会但忘记带卡", "准时参加准备会但忘记带卡");
		SIGN_IN_STATUS_SHOW.put("迟到后未打卡", "迟到后未打卡");
		SIGN_IN_STATUS_SHOW.put("误机后未打卡", "误机后未打卡");
		SIGN_IN_STATUS_SHOW.put("其他原因", "其他原因");
	}

	/** 航班恢复与航班取消的部门 OPT_DEPART 0:市场部，1：运维部 */
	public static final Map<String, String> OPT_DEPAT = new LinkedHashMap<String, String>();
	static {
		OPT_DEPAT.put("0", "市场部");
		OPT_DEPAT.put("1", "运控部");
	}

	/** 文件分隔符 **/
	public static final String FM_FILE_SEP = System.getProperty("file.separator");

	public static final String YES_STRING = "Y";
	public static final String NO_STRING = "N";

	/** confirmSts 岗位确认状态(0：同意 1：拒绝) */
	public static final String CONFIRM_STS_AGREE = "0";
	public static final String CONFIRM_STS_REFUSE = "1";
	public static final String CONFIRM_STS_OVERTIME = "2";

	/** 检查单确认状态("0":未确认 "1":已确认) */
	public static final String CONFIRM_STS_NO = "0";
	public static final String CONFIRM_STS_YES = "1";

	/** AOC值班经理岗位编码 常量值需与T_FM_POSTION保持一致，慎改 */
	public static final String POSITION_DUTY_MANAGER = "DUTY_MANAGER";
	/** 带班主任岗位编码 */
	public static final String POSITION_DUTY_DIRECTOR = "DUTY_DIRECTOR";
	/** 责任签派岗位编码 */
	public static final String POSITION_DUTY_DISPATCHER = "DUTY_DISPATCHER";
	/** 助理签派岗位编码 */
	public static final String POSITION_ASSISTANT_DISPATCHER = "ASSISTANT_DISPATCHER";
	/** 机务调度岗位编码 */
	public static final String POSITION_AIRCRAFT_DISPATCHER = "AIRCRAFT_DISPATCHER";
	/** 飞行调度岗位编码 */
	public static final String POSITION_FLIGHT_DISPATCHER = "FLIGHT_DISPATCHER";
	/** 乘务调度岗位编码 */
	public static final String POSITION_CREW_DISPATCHER = "CREW_DISPATCHER";
	/** 商务调度岗位编码 */
	public static final String POSITION_BUSINESS_DISPATCHER = "BUSINESS_DISPATCHER";
	/** 技术支援岗位编码 */
	public static final String POSITION_TECHNICAL_SUPPORT = "TECHNICAL_SUPPORT";
	/** 勤务保障岗位编码 */
	public static final String POSITION_SERVICE_SUPPORT = "SERVICE_SUPPORT";
	/** 市场部岗位编码 */
	public static final String POSITION_MARKET_DEPARTMENT = "MARKET_DEPARTMENT";
	/** 其他岗位编码 */
	public static final String POSITION_OTHER = "OTHER";

	/** 岗位编码 **/
	public static final Map<String, String> POSITION_CODE = new HashMap<String, String>();
	static {
		POSITION_CODE.put(POSITION_DUTY_MANAGER, "AOC值班经理");
		POSITION_CODE.put(POSITION_DUTY_DIRECTOR, "带班主任");
		POSITION_CODE.put(POSITION_DUTY_DISPATCHER, "责任签派");
		POSITION_CODE.put(POSITION_ASSISTANT_DISPATCHER, "助理签派");
		POSITION_CODE.put(POSITION_AIRCRAFT_DISPATCHER, "机务调度");
		POSITION_CODE.put(POSITION_FLIGHT_DISPATCHER, "飞行调度");
		POSITION_CODE.put(POSITION_CREW_DISPATCHER, "乘务调度");
		POSITION_CODE.put(POSITION_BUSINESS_DISPATCHER, "商务调度");
		POSITION_CODE.put(POSITION_TECHNICAL_SUPPORT, "技术支援");
		POSITION_CODE.put(POSITION_SERVICE_SUPPORT, "勤务保障");
		POSITION_CODE.put(POSITION_MARKET_DEPARTMENT, "市场部");
		POSITION_CODE.put(POSITION_OTHER, "其他");
	}

	/** 调配方案确认状态（确认中：1:未完成，2：全部完成但全部同意，3：全部完成但有拒绝） */
	public static final String CONFIRM_TYPE_UNFINISHED = "1";
	public static final String CONFIRM_TYPE_ALLAGREE = "2";
	public static final String CONFIRM_TYPE_REFUSE = "3";

	/**
	 * 订座权限KEY
	 */
	public static final String SALE_KEY = "check_pass=foc2sale";

	/** 次日航班计划检查岗位编码 **/
	public static final Map<String, String> CHECK_POSITION_CODE_MAP = new HashMap<String, String>();
	static {
		CHECK_POSITION_CODE_MAP.put(POSITION_DUTY_DIRECTOR, "dutyDirector");// 带班主任
		CHECK_POSITION_CODE_MAP.put(POSITION_ASSISTANT_DISPATCHER, "assistantDispatcher");// 助理签派
		CHECK_POSITION_CODE_MAP.put(POSITION_AIRCRAFT_DISPATCHER, "aircraftDispatcher");// 机务调度
		CHECK_POSITION_CODE_MAP.put(POSITION_FLIGHT_DISPATCHER, "flightDispatcher");// 飞行调度
		CHECK_POSITION_CODE_MAP.put(POSITION_CREW_DISPATCHER, "crewDispatcher");// 乘务调度
	}

	/** 次日计划检查结果: 是否提交,0是未提交，1是已提交 */
	public static final String COMMIT_STATUS_YES = "1";
	public static final String COMMIT_STATUS_NO = "0";

	/** 方案状态 (0:草稿 1:确认中 2:已发布 3:发送至值班经理 4：退回 5:作废) **/
	public static final Map<String, String> PLAN_STS_MAP = new HashMap<String, String>();
	static {
		PLAN_STS_MAP.put(PLAN_STS_DRAFT, "草稿");
		PLAN_STS_MAP.put(PLAN_STS_COMFIRM, "确认中");
		PLAN_STS_MAP.put(PLAN_STS_RELEASE, "已发布");
		PLAN_STS_MAP.put(PLAN_STS_SENDTO_MANAGER, "发送至值班经理");
		PLAN_STS_MAP.put(PLAN_STS_RETURN, "退回");
		PLAN_STS_MAP.put(PLAN_STS_DELETE, "作废");
	}

	/** 次日计划岗位检查项目: 能否编辑子项内容,0:不可编辑,1:可编辑 */
	public static final String CAN_EDIT_RS_ITEM_YES = "1";
	public static final String CAN_EDIT_RS_ITEM_NO = "0";

	/** 次日计划岗位检查内容: 初始行数 */
	public static final int INIT_ROW_NUM = 3;

	/** 次日计划岗位检查项目: 题头对应列中标签的类型（radio表示单选框，textarea或空表示文本框） */
	public static final String CONTENT_TYPE_RADIO = "radio";
	public static final String CONTENT_TYPE_TEXTAREA = "textarea";

	/** 检查单状态（0表示已发起，1表示带班主任已完成总体确认，2表示助理签派完成了PLN报文发送（即检查完成）） */
	public static final String CHECK_STATUS_START = "0";
	public static final String CHECK_STATUS_MANAGERFINISH = "1";
	public static final String CHECK_STATUS_PLNFINISH = "2";

	/** 消息中心-消息 等级下拉列表数据 **/
	public static final Map<String, String> MESSAGE_CENTER_GRADE = new LinkedHashMap<String, String>();
	static {
		MESSAGE_CENTER_GRADE.put("", "");
		MESSAGE_CENTER_GRADE.put("0", "不重要");
		MESSAGE_CENTER_GRADE.put("1", "重要");
	}

	/** 消息中心-消息 消息类型:"1",延误信息未录入;"2",航班时间录入;"3",MVT报发送;"4",航班状态,"5":CLR报发送,"6":关门时刻未录入,"7":晚关门原因,"8":位置报缺报 ,"9":CDM时间变更 **/
	public static final Map<String, String> MESSAGE_CENTER_TYPE = new HashMap<String, String>();
	static {
		MESSAGE_CENTER_TYPE.put("", "");
		MESSAGE_CENTER_TYPE.put("1", "延误信息未录入");
		MESSAGE_CENTER_TYPE.put("2", "航班时间录入");
		MESSAGE_CENTER_TYPE.put("3", "MVT报发送");
		MESSAGE_CENTER_TYPE.put("4", "航班状态");
		MESSAGE_CENTER_TYPE.put("5", "CLR报发送");
		MESSAGE_CENTER_TYPE.put("6", "关门时刻未录入");
		MESSAGE_CENTER_TYPE.put("7", "晚关门原因");
		MESSAGE_CENTER_TYPE.put("8", "位置报缺报");
		MESSAGE_CENTER_TYPE.put("9", "CDM时间变更");
		MESSAGE_CENTER_TYPE.put("10", "Acars报文与FOC航班不匹配");
		MESSAGE_CENTER_TYPE.put("11", "航班延误");
		MESSAGE_CENTER_TYPE.put("12", "未推出提醒");
		MESSAGE_CENTER_TYPE.put("13", "未起飞提醒");
	}
	public static final String MESSAGE_TYPE_DELAY = "1";// 延误信息未录入
	public static final String MESSAGE_TYPE_FLIGHT_TIME = "2";// 航班时间录入
	public static final String MESSAGE_TYPE_MVT = "3";// MVT报发送
	public static final String MESSAGE_TYPE_FLIGHT_STATUS = "4";// 航班状态
	public static final String MESSAGE_TYPE_CLR = "5";// CLR报发送
	public static final String MESSAGE_TYPE_CLOSE_TIME = "6";// 关门时刻未录入
	public static final String MESSAGE_TYPE_DELAY_CLOSE = "7";// 晚关门原因
	public static final String MESSAGE_TYPE_ACARS_MISS = "8";// 位置报缺报
	public static final String MESSAGE_TYPE_CDM = "9";// CDM时间变更
	public static final String MESSAGE_TYPE_ACARSMATCHFAIELD = "10";// Acars报文与FOC航班不匹配
	public static final String MESSAGE_TYPE_FLIGHT_DELAY = "11";// 航班延误
	public static final String MESSAGE_TYPE_NOT_LAUNCH = "12";// 未推出提醒
	public static final String MESSAGE_TYPE_NOT_TAKE_OFF = "13";// 未起飞提醒
	/** 消息中心-气象 "0",未读;"1",已读;"NULL",全部 **/
	public static final Map<String, String> MESSAGE_METEOROLOGIAL = new HashMap<String, String>();
	static {
		MESSAGE_METEOROLOGIAL.put("", "");
		MESSAGE_METEOROLOGIAL.put("0", "未读");
		MESSAGE_METEOROLOGIAL.put("1", "已读");
	}

	/** 航班调整类型 0:新增 1:机型 2:机号 3:航线 4:修改时间 5:时刻调整 6:备降 7:取消 8:返航 9:取消恢复 10:顺延 11:起降桥位 **/
	public static final Map<String, String> MESSAGE_FLIGHT_TYPE = new HashMap<String, String>();
	static {
		MESSAGE_FLIGHT_TYPE.put("", "");
		MESSAGE_FLIGHT_TYPE.put("0", "新增航班");
		MESSAGE_FLIGHT_TYPE.put("1", "机型调整");
		MESSAGE_FLIGHT_TYPE.put("2", "机号调整");
		MESSAGE_FLIGHT_TYPE.put("3", "航线调整");
		MESSAGE_FLIGHT_TYPE.put("4", "修改时间");
		MESSAGE_FLIGHT_TYPE.put("5", "时刻调整");
		MESSAGE_FLIGHT_TYPE.put("6", "航班备降");
		MESSAGE_FLIGHT_TYPE.put("7", "取消处理");
		MESSAGE_FLIGHT_TYPE.put("8", "返航处理");
		MESSAGE_FLIGHT_TYPE.put("9", "恢复处理");
		MESSAGE_FLIGHT_TYPE.put("10", "顺延处理");
		MESSAGE_FLIGHT_TYPE.put("11", "起降桥位");
	}

	/**
	 * 消息类型
	 */
	/*
	 * standDeparture 停机位（出港） <br> standArrival 停机位(进港） <br> gate登机口 <br> luggagePickUp行李提取转盘 <br> collaborativeDecisionMakingTimeCDM时间 <br> luggageBelt装卸行李带
	 * <br> aircraftNumberAdjust机号调整 <br> estimatedTimeAdjust 预计时刻调整 <br> flightCrewAdjust 飞行组名单变更 <br> cabinCrewAdjust 乘务组名单变更 <br> additionalFlight 航班新增 <br>
	 * flightCancel 航班取消 <br> routeAdjust 航线调整 <br> plannedTimeAdjust 计划时刻调整 <br> flightAlternate 航班备降 <br> flightReturn 航班返航 <br> estimatedTimeArrival 预计落地时间
	 * <br> flightCrewCheckingTimeAdjust 飞行组签到时间变更 <br> flightRecovery 航班恢复
	 */
	public static final Map<String, String> MESSAGE_FLIGHT_TYPE_EN = new HashMap<String, String>();
	static {
		MESSAGE_FLIGHT_TYPE_EN.put("", "");
		MESSAGE_FLIGHT_TYPE_EN.put("0", "additionalFlight");
		// MESSAGE_FLIGHT_TYPE_EN.put("1", "机型调整");
		MESSAGE_FLIGHT_TYPE_EN.put("2", "aircraftNumberAdjust");
		MESSAGE_FLIGHT_TYPE_EN.put("3", "routeAdjust");
		// MESSAGE_FLIGHT_TYPE_EN.put("4", "修改时间");
		MESSAGE_FLIGHT_TYPE_EN.put("5", "estimatedTimeAdjust");
		MESSAGE_FLIGHT_TYPE_EN.put("6", "flightAlternate");
		MESSAGE_FLIGHT_TYPE_EN.put("7", "flightCancel");
		MESSAGE_FLIGHT_TYPE_EN.put("8", "flightReturn");
		MESSAGE_FLIGHT_TYPE_EN.put("9", "flightRecovery");
		MESSAGE_FLIGHT_TYPE_EN.put("10", "plannedTimeAdjust");
		// MESSAGE_FLIGHT_TYPE_EN.put("11", "起降桥位");
	}

	/** 消息中心 -系统、岗位 推送时间下拉数据 0：无，1：起飞前 ， 2：降落前 ,3:起飞后，4：飞行组签到后 ，5：乘务组签到后，6：飞行组签到前 ，7：乘务组签到前 **/
	public static final Map<String, String> MESSAGE_CENTER_PUSH_TIME = new HashMap<String, String>();
	static {
		MESSAGE_CENTER_PUSH_TIME.put("0", "无");
		MESSAGE_CENTER_PUSH_TIME.put("1", "起飞前");
		/* MESSAGE_CENTER_PUSH_TIME.put("2", "降落前"); */
		MESSAGE_CENTER_PUSH_TIME.put("3", "起飞后");
		MESSAGE_CENTER_PUSH_TIME.put("4", "飞行组签到后");
		MESSAGE_CENTER_PUSH_TIME.put("5", "乘务组签到后");
		MESSAGE_CENTER_PUSH_TIME.put("6", "飞行组签到前");
		MESSAGE_CENTER_PUSH_TIME.put("7", "乘务组签到前");
	}
	/** 消息中心 -系统、岗位 推送时间下拉数据 0：无，1：起飞前 ， 2：降落前,3:起飞后，4：飞行组签到后 ，5：乘务组签到后，6：飞行组签到前 ，7：乘务组签到前 **/
	public static final String MESSAGE_CENTER_PUSH_TIME_NO = "0";
	public static final String MESSAGE_CENTER_PUSH_TIME_BEFOR_FLY = "1";
	public static final String MESSAGE_CENTER_PUSH_TIME_BEFOR_LAND = "2";
	public static final String MESSAGE_CENTER_PUSH_TIME_AFTER_FLY = "3";
	public static final String MESSAGE_CENTER_PUSH_TIME_AFTER_SIGN = "4";
	public static final String MESSAGE_CENTER_PUSH_TIME_AFTER_STEW_SIGN = "5";
	public static final String MESSAGE_CENTER_PUSH_TIME_BEFOR_SIGN = "6";
	public static final String MESSAGE_CENTER_PUSH_TIME_BEFOR_STEW_SIGN = "7";

	/** 消息中心 -推送类型：0:定时,1:变更时,2:首次 **/
	public static final String MESSAGE_CENTER_SEND_TYPE_TIMER = "0";
	public static final String MESSAGE_CENTER_SEND_TYPE_CHANGE = "1";
	public static final String MESSAGE_CENTER_SEND_TYPE_FIRST = "2";

	/** 消息中心 -部门：1:AOC,2:机组,3:地服,4:机务 **/
	public static final Map<String, String> MESSAGE_CENTER_DEPT = new HashMap<String, String>();
	static {
		MESSAGE_CENTER_DEPT.put("1", "AOC");
		MESSAGE_CENTER_DEPT.put("2", "机组");
		MESSAGE_CENTER_DEPT.put("3", "地服");
		MESSAGE_CENTER_DEPT.put("4", "机务");
	}

	/** 消息中心 -系统or岗位(1:系统，2：岗位) **/
	public static final String MESSAGE_CENTER_SYS = "1";
	public static final String MESSAGE_CENTER_POST = "2";

	/** Redis Keys **/
	// 消息中心所有的岗位
	public static final String REDISKEY_MSG_CENTER_POSITIONS = "MSG_CENTER_POSITIONS";
	// 消息中心所有消息类型配置
	public static final String REDISKEY_MSG_CENTER_TYPES = "MSG_CENTER_TYPES";
	// 消息中心所有的消息推送系统
	public static final String REDISKEY_MSG_CENTER_SYSTEMS = "MSG_CENTER_SYSTEMS";
	// 消息中心PSN、EMPID关联
	public static final String REDISKEY_MSG_CENTER_PSN_EMPID = "MSG_CENTER_PSN_EMPID";
	// 消息中心PSN、ACCOUNT关联
	public static final String REDISKEY_MSG_CENTER_PSN_ACCOUNT = "MSG_CENTER_PSN_ACCOUNT";
	// 消息中心PSN、是否外籍关联
	public static final String REDISKEY_MSG_CENTER_PSN_FOREIGNER = "MSG_CENTER_PSN_FOREIGNER";
}
