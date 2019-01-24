package com.fp.code.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fp.code.model.ParentTask;

public interface ParentTaskRepository extends JpaRepository<ParentTask, Long> {

//	@Query(value = "select * from parent_task pt where pt.parent_id = ?1", nativeQuery = true)
//	public ParentTask getParent(long parentId);
	
	@Query(value = "select parent_id from parent_task pt where pt.parent_task = ?1 and pt.status = 'active'", nativeQuery = true)
//	public Optional<Integer> getParentNumberByName(String parentName);
//	public Integer getParentNumberByName(String parentName);
	public Long getParentNumberByName(String parentName);
	
	public ParentTask findByParentTask(String parentName);
}
