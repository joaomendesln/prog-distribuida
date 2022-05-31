package com.authentication.repository;

import com.authentication.model.Good;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GoodRepository extends JpaRepository<Good, Long> {
}