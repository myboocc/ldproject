package it.ldlife.service;

import it.ldlife.common.ServiceResponse;
import it.ldlife.pojo.Shipping;
import it.ldlife.util.PageInfo;

public interface IShippingService {
	
	ServiceResponse add(String userId, Shipping shipping);
    ServiceResponse<String> del(String userId,String shippingId);
    ServiceResponse update(String userId, Shipping shipping);
    ServiceResponse<Shipping> select(String userId, String shippingId);
    ServiceResponse<PageInfo> list(String userId, PageInfo pageInfo);

}
