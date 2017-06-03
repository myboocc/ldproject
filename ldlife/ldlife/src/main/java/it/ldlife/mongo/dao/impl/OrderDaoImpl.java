package it.ldlife.mongo.dao.impl;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import it.ldlife.base.MongoBaseDaoImpl;
import it.ldlife.mongo.dao.OrderDao;
import it.ldlife.pojo.Order;
import it.ldlife.util.PageInfo;

@Repository("orderDaoImpl")
public class OrderDaoImpl extends MongoBaseDaoImpl<Order> implements OrderDao{

	@Override
	public Order selectByUserIdAndOrderNo(String userId, Long orderNo) {
		Query query = new Query();
    	query.addCriteria(Criteria.where("userId").is(userId).and("orderNo").is(orderNo));
		return this.mongoTemplate.findOne(query, this.getEntityClass());
	}

	@Override
	public Order selectByOrderNo(Long orderNo) {
		Query query = new Query();
    	query.addCriteria(Criteria.where("orderNo").is(orderNo));
		return this.mongoTemplate.findOne(query, this.getEntityClass());
	}

	@Override
	public List<Order> selectByUserId(String userId) {
		Query query = new Query();
    	query.addCriteria(Criteria.where("userId").is(userId));
    	query.with(new Sort(Direction.DESC,"createTime"));
		return this.mongoTemplate.find(query, this.getEntityClass());
	}

	@Override
	public List<Order> selectByUserId(String userId, PageInfo pageInfo) {
		Query query = new Query();
    	query.addCriteria(Criteria.where("userId").is(userId));
    	long count = mongoTemplate.count(query,this.getEntityClass());
		pageInfo.setTotalResult(new Long(count).intValue());
		query.with(new Sort(Direction.DESC,"createTime"));
		query.skip(pageInfo.getCurrentResult());
		query.limit(pageInfo.getShowCount());
		return mongoTemplate.find(query,this.getEntityClass());
	}

	@Override
	public List<Order> selectAllOrder(PageInfo pageInfo) {
		Query query = new Query();
		long count = mongoTemplate.count(query,this.getEntityClass());
		pageInfo.setTotalResult(new Long(count).intValue());
		query.with(new Sort(Direction.DESC,"createTime"));
		query.skip(pageInfo.getCurrentResult());
		query.limit(pageInfo.getShowCount());
		return mongoTemplate.find(query,this.getEntityClass());
	}

}
