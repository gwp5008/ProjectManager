package com.fp.code.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fp.code.exceptions.NotFoundException;
import com.fp.code.model.ParentTask;
import com.fp.code.repository.ParentTaskRepository;

@Component
public class ParentTaskDAO {

	@Autowired
	ParentTaskRepository parentTaskRepository;
	
	public void saveParentTask(ParentTask parentTask) {
		parentTaskRepository.save(parentTask);
	}
	public ParentTask findParent(long parentId) {
		return parentTaskRepository.getOne(parentId);
	}
	
	public List<ParentTask> getAll() {
		return parentTaskRepository.findAll();
	}
	
	public long getParentNumberByName(String parentName) {
//		return parentTaskRepository.getParentNumberByName(parentName).orElseThrow(() -> new NotFoundException("Parent task not found")).longValue();
		return parentTaskRepository.getParentNumberByName(parentName);
	}
	
	public ParentTask getParentByName(String parentName) {
		return parentTaskRepository.findByParentTask(parentName);
	}
}
