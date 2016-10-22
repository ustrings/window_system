package com.microsoft.Test_struts_jpa.tool;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class DateTag extends TagSupport {
	
	private static final long serialVersionUID = 6464168398214506236L;

    private String value;
    private String time;

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}





	@Override
    public int doStartTag() throws JspException {
        String vv = "" + value;

        try {
        	long times = 0;
        	if(time.equals("beginTime")){
        		times = Long.valueOf(vv.trim());
        	}else if(time.equals("endTime")){
        		times = Long.valueOf(vv.trim())-1;
        	}else{
        		times = Long.valueOf(vv.trim());
        	}
            
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(times);
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String s = dateformat.format(c.getTime());
            pageContext.getOut().write(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.doStartTag();
    }
	
}
