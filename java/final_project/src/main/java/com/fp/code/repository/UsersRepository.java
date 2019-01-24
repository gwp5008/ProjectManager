package com.fp.code.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.fp.code.model.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {
	
	Users findByUserId(Long userId);
//	Optional<Users> findByUserId(Long userId);
	
	@Query(value = "select * from users u where u.project_id = ?1", nativeQuery = true)
	Users findByProjectId(long projectId);
//	Optional<Users> findByProjectId(long projectId);

	@Query(value = "select * from users", nativeQuery = true)
	List<Users> findAll();

	@Modifying
	@Query(value = "update users us set us.project_id = ?1 where us.user_id = ?2", nativeQuery = true)
	void assignProject(Long projectId, Long userId);
	
	@Modifying
	@Query(value = "update users us set us.task_id = ?2 where us.user_id = ?1", nativeQuery = true)
	void assignTaskManager(Long userId, Long taskId);
}
