package com.fp.code.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import com.fp.code.dao.ParentTaskDAO;
import com.fp.code.model.ParentTask;

@Service
@Transactional
public class ParentTaskService {

	@Autowired
	ParentTaskDAO parentTaskDAO;

	public void saveParentTask(ParentTask parentTask) {
		parentTaskDAO.saveParentTask(parentTask);
	}

	public ParentTask findParent(long parentId) {
		return parentTaskDAO.findParent(parentId);
	}

	public List<ParentTask> getAll() {
		return parentTaskDAO.getAll();
	}
	
	public long getParentNumberByName(String parentName) {
		return parentTaskDAO.getParentNumberByName(parentName);
	}
	
	public ParentTask getParentByName(String parentName) {
		return parentTaskDAO.getParentByName(parentName);
	}
}
