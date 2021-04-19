package com.demo.service;

import java.util.List;

import com.demo.proxy.ProductMasterProxy;

public interface ProductMasterService {

	List<ProductMasterProxy> getAll(Long userId);
	
	boolean delete(Long id);

	ProductMasterProxy get(Long id);

	boolean saveOrUpdate(ProductMasterProxy productMasterProxy);

	
}
