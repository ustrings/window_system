package com.hidata.ad.web.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.DBExclude;
import com.hidata.framework.annotation.db.Table;

@Table("task")
public class TaskDto implements Serializable {

	//人群任务状态
	@DBExclude
	public static final String TASK_STS_EXEC_NO="A";//待执行
	@DBExclude
	public static final String TASK_STS__EXEC_SUCCESS="B";//待执成功
    @DBExclude
    public static final String TASK_STS_EXEC_FAIL="C";//执行失败
    @DBExclude
    public static final String TASK_STS_EXEC_NOW="D";//执行中
    @DBExclude
    public static final String TASK_STS_EXEC_DEL="E";//已删除
    @DBExclude
    public static final Map<String,String> CONSTANT_MAP =new HashMap<String,String>();
    @DBExclude
    public static final String TASK_STS_PREFIX="TASK_PREFIX";
    static {
    	CONSTANT_MAP.put(TASK_STS_PREFIX+TASK_STS_EXEC_NO, "待执行");
    	CONSTANT_MAP.put(TASK_STS_PREFIX+TASK_STS__EXEC_SUCCESS, "执行成功");
    	CONSTANT_MAP.put(TASK_STS_PREFIX+TASK_STS_EXEC_FAIL, "执行失败");
    	CONSTANT_MAP.put(TASK_STS_PREFIX+TASK_STS_EXEC_NOW, "执行中");
    	CONSTANT_MAP.put(TASK_STS_PREFIX+TASK_STS_EXEC_DEL, "已删除");
    }
	public  enum TaskType {
		TASDK_CAL("人群计算", 1), CROWD_COUNT("人群总数", 2), CROWD_DETAIL("人群明细", 3);
		
		private String name;
		private int index;
		
		private TaskType(String name, int index) {  
	        this.name = name;  
	        this.index = index;  
	    }  
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
		
		public static String getName(int index) {  
	        for (TaskType c : TaskType.values()) {  
	            if (c.getIndex() == index) {  
	                return c.name;  
	            }  
	        }  
	        return null;  
	    }
	}

	/**
	 * 
	 */
	@DBExclude
	private static final long serialVersionUID = -5589778425265040648L;

	@Column("task_id")
	private Integer id;
	
	@Column("task_name")
	private String name;
	
	//任务类型
	@Column("type")
	private Integer type;
	
	private String typeName;
	
	public String getTypeName() {
		return typeName;
	}


	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Column("sts")
	private String sts;
	
	@Column("start_time")
	private String start_time;
	
	@Column("end_time")
	private String end_time;
	
	@Column("creator")
	private Integer creator;
	
	@Column("create_time")
	private String create_time;
	
	@Column("update_time")
	private String update_time;
	
	@Column("exec_start_time")
	private String exec_start_time;
	
	@Column("exec_end_time")
	private String exec_end_time;
	@Column("count")
	private Integer count;

	//扩展字段 start
	@Column("crowd_id")
	private String crowdId;
	private String crowdName; 
	
	private String crowdids;
	//扩展字段 end
	
	public String getCrowdids() {
		return crowdids;
	}


	public void setCrowdids(String crowdids) {
		this.crowdids = crowdids;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	public Integer getCount() {
		return count;
	}


	public void setCount(Integer count) {
		this.count = count;
	}

	
	public String getCrowdName() {
		return crowdName;
	}


	public void setCrowdName(String crowdName) {
		this.crowdName = crowdName;
	}


	public String getCrowdId() {
		return crowdId;
	}

	
	public void setCrowdId(String crowdId) {
		this.crowdId = crowdId;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}


	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getExec_start_time() {
		return exec_start_time;
	}

	public void setExec_start_time(String exec_start_time) {
		this.exec_start_time = exec_start_time;
	}

	public String getExec_end_time() {
		return exec_end_time;
	}

	public void setExec_end_time(String exec_end_time) {
		this.exec_end_time = exec_end_time;
	}

    
}
