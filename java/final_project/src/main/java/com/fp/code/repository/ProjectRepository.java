package com.fp.code.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.fp.code.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>{
	
//	Optional<Project> findByProjectName(String projectName);
//	
//	Optional<Project> findByProjectId(long projectId);
	
	Project findByProjectName(String projectName);
	
	Project findByProjectId(Long projectId);
	
	

//	@Modifying
//	@Query(value = "update project pt set pt.status = 'inactive' where pt.project_id = ?1", nativeQuery = true)
//	void removeProject(int id);
}
