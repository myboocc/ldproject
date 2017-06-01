package it.ldlife.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

import it.ldlife.common.ServiceResponse;
import it.ldlife.service.IOrderService;

@Service("iOrderService")
public class OrderServiceImpl implements IOrderService{

	@Override
	public ServiceResponse pay(Long orderNo, String userId, String path) {
		Map<String,String> resutlMap = Maps.newHashMap();
		
		return null;
	}

}
