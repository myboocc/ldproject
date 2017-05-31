package it.ldlife.mongo.dao.impl;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import it.ldlife.base.MongoBaseDaoImpl;
import it.ldlife.mongo.dao.CartDao;
import it.ldlife.pojo.Cart;

@Repository("cartDaoImpl")
public class CartDaoImpl extends MongoBaseDaoImpl<Cart> implements CartDao{

	@Override
	public Cart selectCartByUserIdProductId(String userId, String productId) {
		Query query = new Query();
    	query.addCriteria(Criteria.where("userId").is(userId).and("productId").is(productId));
		return this.mongoTemplate.findOne(query, this.getEntityClass());
	}

	@Override
	public List<Cart> selectCartByUserId(String userId) {
		Query query = new Query();
    	query.addCriteria(Criteria.where("userId").is(userId));
		return this.mongoTemplate.find(query, this.getEntityClass());
	}

	@Override
	public int selectCartProductCheckedStatusByUserId(String userId) {
		Query query = new Query();
    	query.addCriteria(Criteria.where("userId").is(userId).and("checked").is(0));
    	Cart cart = this.mongoTemplate.findOne(query, this.getEntityClass());
    	if(cart != null){
    		return 1;
    	}
		return 0;
	}

	@Override
	public void deleteByUserIdProductIds(String userId, List<String> productList) {
		Query query = new Query();
    	query.addCriteria(Criteria.where("userId").is(userId));
    	if(productList.size() > 0){
    		query.addCriteria(Criteria.where("productId").in(productList));
    	}
		this.mongoTemplate.remove(query, this.getEntityClass());
	}

	@Override
	public void checkedOrUncheckedProduct(String userId, String productId, Integer checked) {
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(userId));
		if(productId != null){
			query.addCriteria(Criteria.where("productId").is(productId));
		}
		Update update = new Update();
		update.set("checked", checked);
		update.set("updateTime", DateTime.now());
		this.mongoTemplate.updateFirst(query, update, this.getEntityClass());
	}

	@Override
	public Integer selectCartProductCount(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
