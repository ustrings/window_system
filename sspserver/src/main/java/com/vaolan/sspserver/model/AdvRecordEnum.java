package com.vaolan.sspserver.model;

public enum AdvRecordEnum
{
	NONE(0), ONE(1), TWO(2), THREE(3), FOUR(4);
	private int value;
	private AdvRecordEnum(){};
	private AdvRecordEnum(int val){
		this.value =  val;
	}
	public int getValue(){
		return this.value;
	};
}
