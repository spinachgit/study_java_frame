package com.spinach.utils;

import java.util.Date;

/**
 * <p>
 * 航班调配操作日志表
 * </p>
 * 
 * @version 1.0
 * @author xuyouliang
 * @date 2016年10月27日
 */
public class FmLog {

	private String code;// 功能编码（FMPC01代表岗位更新检查单内容；FMPC02代表每个检查项目的明细内容变更；）
	private String optEmpId;// 操作人
	private Date optTime;// 操作时间
	private String optType;// 操作类型（1：修改 0：删除）
	private String ip;// ip
	private String primaryData;// 原始数据
	private String masterId;// 主数据ID
	private String masterName;// 主数据名称
	private String slaveId;// 从数据ID
	private String slaveName;// 从数据名称
	private String batchId;// 批量唯一标示ID（用时间生成）
	private String extProp;// 扩展字段1
	private String extProp2;// 扩展字段2

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOptEmpId() {
		return optEmpId;
	}

	public void setOptEmpId(String optEmpId) {
		this.optEmpId = optEmpId;
	}

	public Date getOptTime() {
		return optTime;
	}

	public void setOptTime(Date optTime) {
		this.optTime = optTime;
	}

	public String getOptType() {
		return optType;
	}

	public void setOptType(String optType) {
		this.optType = optType;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPrimaryData() {
		return primaryData;
	}

	public void setPrimaryData(String primaryData) {
		this.primaryData = primaryData;
	}

	public String getMasterId() {
		return masterId;
	}

	public void setMasterId(String masterId) {
		this.masterId = masterId;
	}

	public String getMasterName() {
		return masterName;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}

	public String getSlaveId() {
		return slaveId;
	}

	public void setSlaveId(String slaveId) {
		this.slaveId = slaveId;
	}

	public String getSlaveName() {
		return slaveName;
	}

	public void setSlaveName(String slaveName) {
		this.slaveName = slaveName;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getExtProp() {
		return extProp;
	}

	public void setExtProp(String extProp) {
		this.extProp = extProp;
	}

	public String getExtProp2() {
		return extProp2;
	}

	public void setExtProp2(String extProp2) {
		this.extProp2 = extProp2;
	}

}
