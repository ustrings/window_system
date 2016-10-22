package com.microsoft.Test_struts_jpa.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.microsoft.Test_struts_jpa.biz.DspAdBiz;
import com.microsoft.Test_struts_jpa.entity.Dsp_Ad;
import com.microsoft.Test_struts_jpa.entity.Dsp_Domain;

public class DspAdAction  extends BaseAction{
	private DspAdBiz dspadBiz = new DspAdBiz();
	
	private int id;
	private int push_status;
	private Dsp_Ad ad;
	private String domain;
	private String time;
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Dsp_Ad getAd() {
		return ad;
	}
	public void setAd(Dsp_Ad ad) {
		this.ad = ad;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPush_status() {
		return push_status;
	}

	public void setPush_status(int pushStatus) {
		push_status = pushStatus;
	}

	//需求查询
	public String doSelectAd(){
		List<Dsp_Ad> adlist = dspadBiz.getSelectAd();
		if(adlist != null && adlist.size() >=1){
			
			this.getRequest().put("adlist", adlist);
			return "success";
		}else{
			return "error";
		}
	}
	
	//需求统计查询
	public String doSelectCountAd(){
		List<Dsp_Ad> adCountlist = dspadBiz.getSelectAd();
		if(adCountlist != null && adCountlist.size() >=1){
			this.getRequest().put("adCountlist", adCountlist);
			return "needCount";
		}else{
			return "error";
		}
		
	}
	
	//需求推送状态修改
	public String doUpdateAd(){
		boolean b = dspadBiz.getUpdateStatus(this.getId(),this.getPush_status());
		if(b){
			doSelectAd();
			return "success";
		}else{
			return "error";
		}	
	}
	
	//需求详情
	public String doDetailAd(){
		Dsp_Ad adDetaillist = dspadBiz.getSelectDetailAd(this.getId());
		
		StringBuffer domain = new StringBuffer();
		List<Dsp_Domain> domainList = dspadBiz.getQueryDomain(this.getId());
		if(domainList != null){
			for(int i =0;i<domainList.size();i++){
				domain.append(domainList.get(i).getDomain());
				if(i<domainList.size()-1){
					domain.append("\r\n");
				}
			}
		}
		if(adDetaillist != null){
			this.getRequest().put("domain", domain);
			this.getRequest().put("adDetaillist", adDetaillist);
			return "adDetail";
		}else{
			return "error";
		}
	}
	
	//删除
	public String doDeleteAd(){
		boolean b = dspadBiz.getDeleteAd(this.getId());
		if(b){
			doSelectAd();
			return "success";
		}else{
			return "error";
		}	
	}
	//添加
	public String doInsterAd(){
		//添加ad表信息，且优先级不能重复，
		//查询优先级是否重复
		String[] timeStr = this.getTime().split("-");
		long tBegin = getStringToDate(timeStr[0].trim());
		long tEnd = getStringToDate(timeStr[1].trim());

		ad.setBegin_time(tBegin);
		ad.setEnd_time(tEnd);

		List<Dsp_Ad> adlist = dspadBiz.getSelectPrio(this.getAd().getPrio());
		if(adlist != null && adlist.size() >=1){
			this.getRequest().put("ad", ad);
			this.getRequest().put("domain", domain);
			this.getRequest().put("time", this.getTime());
			
			this.getRequest().put("message", "alert('优先级已存在，请修改！')");
			return "errorPrio";
		}else{
			boolean b = dspadBiz.getInsterAd(this.getAd(), this.getDomain());
			if(b){
				doSelectAd();
				return "success";
			}
			return "error";
		}
	}
	
	//把时间转换为时间戳
	public static long getStringToDate(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		//SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		try{
			date = sdf.parse(time);
			
		}catch(Exception e) {
			// TODO Auto-generated catch block　　
			e.printStackTrace();
		}
		return date.getTime();
	}
	/*时间戳转换成字符窜*/
	public static String getDateToString(long time) {
		Date d = new Date(time);
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		return sdf.format(d);
	}
	
	
}
