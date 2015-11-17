package com.hidata.ad.web.dao;

import java.util.List;

import com.hidata.ad.web.dto.CrowdCalTaskDto;
import com.hidata.ad.web.dto.TaskAdRelDto;
import com.hidata.ad.web.dto.TaskCrowdRelDto;
import com.hidata.ad.web.dto.TaskDto;
import com.hidata.ad.web.model.Crowd;

public interface TaskDao {

   
    public int insertTask(TaskDto taskDto);
    
    public int insertTaskCrowdRel(TaskCrowdRelDto taskCrowdRelDto);
    
    public List<TaskDto> queryTaskListByConditions(CrowdCalTaskDto crowdCalTaskDto);

    /**
     * 查询 任务
     * @param Id
     * @return
     */
    public TaskDto queryTaskById(String Id);

    /**
     * 根据任务id查询人群
     * @param taskId
     * @return
     */
	public List<TaskCrowdRelDto> queryCrowdByTaskId(String taskId);

	/**
	 * 删除关联
	 * @param taskId
	 */
	public void delTaskCrowdRel(String taskId);

	/**
	 * 更新 任务
	 * @param taskDto
	 */
	public void updateTask(TaskDto taskDto);
	
	/**
	 * 查询任务 ad详情 
	 * @param taskId
	 * @return
	 */
	public List<TaskAdRelDto> findTaskAdDetailListByTaskId(String taskId);

	/**
	 * 根据条件  查询任务
	 * @param condition
	 * @return
	 */
	public List<TaskDto> queryTaskByCondition(TaskDto condition);

	/**
	 * 添加 任务ad关联
	 * @param taskAdRelDto
	 * @return
	 */
	public int insertTaskAdRelDto(TaskAdRelDto taskAdRelDto);

	/**
	 * 删除任务ad关联
	 * @param taskId
	 */
	public void delTaskAdRel(String taskId);
	

}
