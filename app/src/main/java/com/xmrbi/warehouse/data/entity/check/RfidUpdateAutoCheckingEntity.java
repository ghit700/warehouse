package com.xmrbi.warehouse.data.entity.check;

public class RfidUpdateAutoCheckingEntity {
	private String rfids;
	private boolean success;
	private int checkCount;
	private int unCheckCount;
	private String errorMsg;

	public int getUnCheckCount() {
		return unCheckCount;
	}

	public void setUnCheckCount(int unCheckCount) {
		this.unCheckCount = unCheckCount;
	}

	public String getRfids() {
		return rfids;
	}

	public void setRfids(String rfids) {
		this.rfids = rfids;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getCheckCount() {
		return checkCount;
	}

	public void setCheckCount(int checkCount) {
		this.checkCount = checkCount;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
