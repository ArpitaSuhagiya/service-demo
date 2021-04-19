package com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.demo.domain.ProductMaster;


public interface ProductMasterRepo extends JpaRepository<ProductMaster, Long> {
	

	public List<ProductMaster> findByUserIdAndIsActive(Long userId, Boolean isActive);
	
}
