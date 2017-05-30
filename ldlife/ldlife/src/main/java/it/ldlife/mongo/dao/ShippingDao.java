package it.ldlife.mongo.dao;

import java.util.List;

import it.ldlife.base.MongoBaseDao;
import it.ldlife.pojo.Shipping;
import it.ldlife.util.PageInfo;

public interface ShippingDao extends MongoBaseDao<Shipping>{

	int deleteByShippingIdUserId(String userId, String shippingId);

	int updateByShipping(Shipping shipping);

	Shipping selectByShippingIdUserId(String userId, String shippingId);

	List<Shipping> selectByUserId(String userId, PageInfo pageInfo);

}
