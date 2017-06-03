package it.ldlife.mongo.dao;

import java.util.List;

import it.ldlife.base.MongoBaseDao;
import it.ldlife.pojo.Order;
import it.ldlife.util.PageInfo;

public interface OrderDao extends MongoBaseDao<Order>{

	Order selectByUserIdAndOrderNo(String userId, Long orderNo);

	Order selectByOrderNo(Long orderNo);

	List<Order> selectByUserId(String userId);

	List<Order> selectByUserId(String userId, PageInfo pageInfo);

	List<Order> selectAllOrder(PageInfo pageInfo);

}
