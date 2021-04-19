
package com.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.proxy.CommonProxy;
import com.demo.proxy.ProductMasterProxy;
import com.demo.proxy.UserLoginAuditProxy;
import com.demo.service.ProductMasterService;
import com.demo.service.UserLoginService;
import com.demo.service.UserService;

@RestController
@RequestMapping("/product")
public class ProductMasterController {

	private static final Logger logger = LoggerFactory.getLogger(ProductMasterController.class);
	
	@Autowired
	private ProductMasterService pmService;
	
	@Autowired
	private UserLoginService userService;
	

	@GetMapping("/getAll")
	public ResponseEntity<CommonProxy> getAll(HttpServletRequest httpServletRequest) {
		try {
			UserLoginAuditProxy userDetails = userService.checkTokenValidOrNot(httpServletRequest.getHeader("token"));
			if(userDetails == null) {
				return new ResponseEntity<>(new CommonProxy("Unauthorzed Request",  HttpStatus.UNAUTHORIZED.value(), Boolean.FALSE), 
						HttpStatus.OK);
			}
			
			List<ProductMasterProxy> productList = pmService.getAll(userDetails.getUserId());
			if(productList != null) {
				return new ResponseEntity<>(new CommonProxy("Successfully Get Data !!", productList, HttpStatus.OK.value(), Boolean.TRUE), 
						HttpStatus.OK);
			}
			return new ResponseEntity<>(new CommonProxy("Error While get Data !!", HttpStatus.OK.value(), Boolean.FALSE), 
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception while get Product list", e);
			return new ResponseEntity<>(new CommonProxy("Exception while get product list", HttpStatus.INTERNAL_SERVER_ERROR.value(), Boolean.FALSE), 
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	@PostMapping("/saveOrUpdate")
	public ResponseEntity<CommonProxy> saveOrUpdate(@RequestBody ProductMasterProxy productMasterProxy, HttpServletRequest httpServletRequest) {
		try {
			UserLoginAuditProxy userDetails = userService.checkTokenValidOrNot(httpServletRequest.getHeader("token"));
			if(userDetails == null) {
				return new ResponseEntity<>(new CommonProxy("Unauthorzed Request",  HttpStatus.UNAUTHORIZED.value(), Boolean.FALSE), 
						HttpStatus.OK);
			}
			
			productMasterProxy.setUserId(userDetails.getUserId());
			boolean saveOrUpdate = pmService.saveOrUpdate(productMasterProxy);
			if(saveOrUpdate) {
				return new ResponseEntity<>(new CommonProxy("Saved Successfully Data !!", HttpStatus.OK.value(), Boolean.TRUE), 
						HttpStatus.OK);
			}	
			return new ResponseEntity<>(new CommonProxy("Data Not Saved Successfully!!", HttpStatus.OK.value(), Boolean.FALSE), 
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception while save Or Update Product ", e);
			return new ResponseEntity<>(new CommonProxy("Data Not Saved Successfully!!", HttpStatus.INTERNAL_SERVER_ERROR.value(), Boolean.FALSE), 
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/deleteById/{id}")
	public ResponseEntity<CommonProxy> delete(@PathVariable("id") Long id,HttpServletRequest httpServletRequest) {
		try {
			UserLoginAuditProxy userDetails = userService.checkTokenValidOrNot(httpServletRequest.getHeader("token"));
			if(userDetails == null) {
				return new ResponseEntity<>(new CommonProxy("Unauthorzed Request",  HttpStatus.UNAUTHORIZED.value(), Boolean.FALSE), 
						HttpStatus.OK);
			}
			
			boolean deleted = pmService.delete(id);
			if(deleted) {
				return new ResponseEntity<>(new CommonProxy("Delete Successfully Data !!", HttpStatus.OK.value(), Boolean.TRUE), 
						HttpStatus.OK);
			}	
			return new ResponseEntity<>(new CommonProxy("Data Not Deleted Successfully!!", HttpStatus.OK.value(), Boolean.FALSE), 
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception while Detele Product ", e);
			return new ResponseEntity<>(new CommonProxy("Data Not Deleted Successfully!!", HttpStatus.INTERNAL_SERVER_ERROR.value(), Boolean.FALSE), 
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
