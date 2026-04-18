package com.telusko.tutor.repository;

import com.telusko.tutor.model.UserMemory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMemoryRepository extends JpaRepository<UserMemory, String> {
}
