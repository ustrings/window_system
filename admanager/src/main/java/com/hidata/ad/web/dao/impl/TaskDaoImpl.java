package com.hidata.ad.web.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.hidata.ad.web.dao.TaskDao;
import com.hidata.ad.web.dto.CrowdCalTaskDto;
import com.hidata.ad.web.dto.TaskAdRelDto;
import com.hidata.ad.web.dto.TaskCrowdRelDto;
import com.hidata.ad.web.dto.TaskDto;
import com.hidata.framework.db.DBManager;
@Component
public class TaskDaoImpl implements TaskDao {

	@Autowired
	private DBManager db;
	
    public int insertTask(TaskDto taskDto){
    	return db.insertObjectAndGetAutoIncreaseId(taskDto);
    }
    
    public int insertTaskCrowdRel(TaskCrowdRelDto taskCrowdRelDto){
    	
    	return db.insertObject(taskCrowdRelDto);
    }
    
    @Override
	public List<TaskDto> queryTaskByCondition(TaskDto condition){
		StringBuilder sqlBuilder = new StringBuilder();
    	sqlBuilder.append("select * from task a where  1=1");
    	List<Object> paramList = new ArrayList<Object>();
    	if(condition.getType()!=null){
    		sqlBuilder.append(" and a.type=?");
        	paramList.add(condition.getType());
    	}
    	if(!StringUtils.isEmpty(condition.getSts())){
    		sqlBuilder.append(" and a.sts=?");
        	paramList.add(condition.getSts());
    	}
    	Object[] args = paramList.toArray();	
    	return db.queryForListObject(sqlBuilder.toString(), args, TaskDto.class);
	}

    public List<TaskDto> queryTaskListByConditions(CrowdCalTaskDto crowdCalTaskDto){
    	StringBuilder sqlBuilder = new StringBuilder();
    	sqlBuilder.append("select a.*,b.crowd_id from task a,task_crowd_rel b where a.task_id=b.task_id ");
    	
    	List<Object> paramList = new ArrayList<Object>();
    	sqlBuilder.append(" and a.creator=?");
    	paramList.add(crowdCalTaskDto.getUserId());
    	if(!StringUtils.isEmpty(crowdCalTaskDto.getCrowdIds())){
    		//人群
    		sqlBuilder.append(" and b.crowd_id in (").append(crowdCalTaskDto.getCrowdIds()).append(")");
    	}
    	TaskDto taskDto = crowdCalTaskDto.getTaskDto();
    	
    	if(taskDto!=null){
    		if(!StringUtils.isEmpty(taskDto.getType())){
    			sqlBuilder.append(" and a.type=?");
    			paramList.add(taskDto.getType());
    		}
    		if(!StringUtils.isEmpty(taskDto.getSts())){
    			//状态
    			sqlBuilder.append(" and a.sts=?");
    			paramList.add(taskDto.getSts());
    		}
    	}
    	if(!StringUtils.isEmpty(crowdCalTaskDto.getStart_time())){
    		//开始时间
    		sqlBuilder.append(" and a.start_time>=?");
    		paramList.add(taskDto.getStart_time());
    	}
    	if(!StringUtils.isEmpty(crowdCalTaskDto.getEnd_time())){
    		//结束时间
    		sqlBuilder.append(" and a.end_time<=?");
    		paramList.add(taskDto.getEnd_time());
    	}
    	sqlBuilder.append(" and a.sts!=?");
		paramList.add(TaskDto.TASK_STS_EXEC_DEL);//查询没有删除的任务
    	Object[] args = paramList.toArray();	
    	return db.queryForListObject(sqlBuilder.toString(), args, TaskDto.class);
    }
    
    @Override
    public TaskDto queryTaskById(String Id){
    	StringBuilder sqlBuilder = new StringBuilder();
    	sqlBuilder.append("select * from task a where a.task_id=?");
    	
    	List<Object> paramList = new ArrayList<Object>();
    	paramList.add(Id);
    	Object[] args = paramList.toArray();	
    	return db.queryForObject(sqlBuilder.toString(), args, TaskDto.class);
    }
    
    @Override
    public List<TaskCrowdRelDto> queryCrowdByTaskId(String taskId){
    	StringBuilder sqlBuilder = new StringBuilder();
    	sqlBuilder.append("select * from task_crowd_rel b where b.task_id=?");
    	List<Object> paramList = new ArrayList<Object>();
    	paramList.add(taskId);
    	Object[] args = paramList.toArray();	
    	return db.queryForListObject(sqlBuilder.toString(), args, TaskCrowdRelDto.class);
    }
    
    @Override
	public void delTaskCrowdRel(String taskId) {
		
		String sql= "delete from task_crowd_rel where task_id = ?";
		db.update(sql,new Object[]{taskId});
	}
    
    /**
	 * 更新 任务
	 * @param taskDto
	 */
	public void updateTask(TaskDto taskDto){
		String sql = "update task set task_name= ?,sts= ?,start_time = ?,end_time = ?,"
				+ "update_time = ?,type=?  where task_id = ?";
		db.update(sql, new Object[]{taskDto.getName(),taskDto.getSts(),taskDto.getStart_time(),taskDto.getEnd_time()
				,taskDto.getUpdate_time(),taskDto.getType(),taskDto.getId()});
	}
	
	public List<TaskAdRelDto> findTaskAdDetailListByTaskId(String taskId){
		StringBuilder sqlBuilder = new StringBuilder();
    	sqlBuilder.append("select * from task_ad_rel  where task_id=?");
    	List<Object> paramList = new ArrayList<Object>();
    	paramList.add(taskId);
    	Object[] args = paramList.toArray();	
    	return db.queryForListObject(sqlBuilder.toString(), args, TaskAdRelDto.class);
	}
	
	@Override
	public int insertTaskAdRelDto(TaskAdRelDto taskAdRelDto) {
		return db.insertObject(taskAdRelDto);
	}
	@Override
	public void delTaskAdRel(String taskId) {
			String sql= "delete from task_ad_rel where task_id = ?";
			db.update(sql,new Object[]{taskId});
	}
}
