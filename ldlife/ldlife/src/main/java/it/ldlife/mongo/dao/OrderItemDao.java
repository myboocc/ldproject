package it.ldlife.mongo.dao;

import java.util.List;

import it.ldlife.base.MongoBaseDao;
import it.ldlife.pojo.OrderItem;

public interface OrderItemDao extends MongoBaseDao<OrderItem>{

	List<OrderItem> getByOrderNoUserId(Long orderNo, String userId);

	void batchInsert(List<OrderItem> orderItemList);

	List<OrderItem> getByOrderNo(Long orderNo);

}
