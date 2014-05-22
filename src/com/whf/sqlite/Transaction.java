package com.whf.sqlite;

public class Transaction {
	int _id;
	public	String dataCenter;
	public	String dataType;
	public	String dataSubType;
	public	String description;
	public	String time;
	public	String totalCount;
	public	String failureCount;
	public	String failureRate;
	public	String average;
	public	String tile95;
	public	String min;
	public	String max;
	
	public Transaction(int id,String center,String type,String sub,String des,String time,String total,String failure,String fRate,String ave,String tile,String min,String max){
		this._id=id;
		this.dataCenter=center;
		this.dataType=type;
		this.dataSubType=sub;
		this.description=des;
		this.time=time;
		this.totalCount=total;
		this.failureCount=failure;
		this.failureRate=fRate;
		this.average=ave;
		this.tile95=tile;
		this.min=min;
		this.max=max;
	}
	
	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String getDataCenter() {
		return dataCenter;
	}

	public void setDataCenter(String dataCenter) {
		this.dataCenter = dataCenter;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDataSubType() {
		return dataSubType;
	}

	public void setDataSubType(String dataSubType) {
		this.dataSubType = dataSubType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getFailureCount() {
		return failureCount;
	}

	public void setFailureCount(String failureCount) {
		this.failureCount = failureCount;
	}

	public String getFailureRate() {
		return failureRate;
	}

	public void setFailureRate(String failureRate) {
		this.failureRate = failureRate;
	}

	public String getAverage() {
		return average;
	}

	public void setAverage(String average) {
		this.average = average;
	}

	public String getTile95() {
		return tile95;
	}

	public void setTile95(String tile95) {
		this.tile95 = tile95;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}
	
}