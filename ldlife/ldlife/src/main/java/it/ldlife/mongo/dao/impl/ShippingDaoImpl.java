package it.ldlife.mongo.dao.impl;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import it.ldlife.base.MongoBaseDaoImpl;
import it.ldlife.mongo.dao.ShippingDao;
import it.ldlife.pojo.Shipping;
import it.ldlife.util.DateTimeUtil;
import it.ldlife.util.PageInfo;

@Repository("shippingDaoImpl")
public class ShippingDaoImpl extends MongoBaseDaoImpl<Shipping> implements ShippingDao{

	@Override
	public int deleteByShippingIdUserId(String userId, String shippingId) {
		Query query = new Query();
    	query.addCriteria(Criteria.where("userId").is(userId).and("_id").is(shippingId));
		return this.mongoTemplate.remove(query, this.getEntityClass()).getN();
	}

	@Override
	public int updateByShipping(Shipping shipping) {
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(shipping.getUserId()).and("_id").is(shipping.getId()));
		Update update = new Update();
		update.set("receiverName", shipping.getReceiverName());
		update.set("receiverPhone", shipping.getReceiverPhone());
		update.set("receiverMobile", shipping.getReceiverMobile());
		update.set("receiverProvince", shipping.getReceiverProvince());
		update.set("receiverCity", shipping.getReceiverCity());
		update.set("receiverDistrict", shipping.getReceiverDistrict());
		update.set("receiverAddress", shipping.getReceiverAddress());
		update.set("receiverZip", shipping.getReceiverZip());
		update.set("updateTime", DateTime.now());
		return this.mongoTemplate.updateFirst(query, update, this.getEntityClass()).getN();
	}

	@Override
	public Shipping selectByShippingIdUserId(String userId, String shippingId) {
		Query query = new Query();
    	query.addCriteria(Criteria.where("userId").is(userId).and("_id").is(shippingId));
		return this.mongoTemplate.findOne(query, this.getEntityClass());
	}

	@Override
	public List<Shipping> selectByUserId(String userId, PageInfo pageInfo) {
		Query query = new Query();
    	query.addCriteria(Criteria.where("userId").is(userId));
    	long count = mongoTemplate.count(query,this.getEntityClass());
		pageInfo.setTotalResult(new Long(count).intValue());
		query.with(new Sort(Direction.DESC,"createTime"));
		query.skip(pageInfo.getCurrentResult());
		query.limit(pageInfo.getShowCount());
		
		return mongoTemplate.find(query,this.getEntityClass());
	}

}
