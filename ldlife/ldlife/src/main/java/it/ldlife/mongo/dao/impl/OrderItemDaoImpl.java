package it.ldlife.mongo.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import it.ldlife.base.MongoBaseDaoImpl;
import it.ldlife.mongo.dao.OrderItemDao;
import it.ldlife.pojo.OrderItem;

@Repository("orderItemDaoImpl")
public class OrderItemDaoImpl extends MongoBaseDaoImpl<OrderItem> implements OrderItemDao{

	@Override
	public List<OrderItem> getByOrderNoUserId(Long orderNo, String userId) {
		Query query = new Query();
    	query.addCriteria(Criteria.where("userId").is(userId).and("orderNo").is(orderNo));
		return this.mongoTemplate.find(query, this.getEntityClass());
	}

	@Override
	public void batchInsert(List<OrderItem> orderItemList) {
		this.mongoTemplate.insert(orderItemList, this.getEntityClass());
	}

	@Override
	public List<OrderItem> getByOrderNo(Long orderNo) {
		Query query = new Query();
    	query.addCriteria(Criteria.where("orderNo").is(orderNo));
		return this.mongoTemplate.find(query, this.getEntityClass());
	}

}
