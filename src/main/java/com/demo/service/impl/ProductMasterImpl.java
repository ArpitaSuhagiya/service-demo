package com.demo.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.domain.ProductMaster;
import com.demo.proxy.ProductMasterProxy;
import com.demo.repository.ProductMasterRepo;
import com.demo.service.ProductMasterService;

@Service
@Transactional
public class ProductMasterImpl implements ProductMasterService{

	@Autowired
	private ProductMasterRepo pmRepo;
	
	@Override
	public List<ProductMasterProxy> getAll(Long userId) {
		List<ProductMaster> pmList = pmRepo.findByUserIdAndIsActive(userId, Boolean.TRUE);
		if(pmList.isEmpty()) {
			return Collections.emptyList();
		}
		List<ProductMasterProxy> resList = new ArrayList<ProductMasterProxy>(pmList.size());
		ProductMasterProxy res = null;
		for(ProductMaster usd : pmList) {
			res = new ProductMasterProxy();
			BeanUtils.copyProperties(usd, res);
			resList.add(res);
		}
		return resList;
	}
	
	@Override
	public boolean saveOrUpdate(ProductMasterProxy productMasterProxy) {
		ProductMaster productMaster = null;
		if(productMasterProxy.getId() != null) {
			productMaster = pmRepo.findById(productMasterProxy.getId()).orElse(null);
		}
		
		if(productMaster == null) {
			productMaster = new ProductMaster();
			BeanUtils.copyProperties(productMasterProxy, productMaster);
			productMaster.setCreatedDate(new Date());
			productMaster.setCreatedBy(productMasterProxy.getUserId());
			productMaster.setIsActive(Boolean.TRUE);
		} else {
			BeanUtils.copyProperties(productMasterProxy, productMaster);
			productMaster.setModifiedDate(new Date());
			productMaster.setModifiedBy(productMasterProxy.getUserId());
		}
		pmRepo.save(productMaster);
		return true;
	}
	
	
	@Override
	public boolean delete(Long id){
		ProductMaster usd = pmRepo.findById(id).get();
		if(usd != null) {
			usd.setIsActive(Boolean.FALSE);
			pmRepo.save(usd);
			return true;
		}
		return false;
	}
	
	@Override
	public ProductMasterProxy get(Long id) {
		ProductMaster p = pmRepo.findById(id).orElse(null);
		if(p != null) {
			ProductMasterProxy res = new ProductMasterProxy();
			BeanUtils.copyProperties(p, res);
			return res;
		
		}
		return null;
	}
	
}
