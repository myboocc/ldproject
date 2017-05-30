package it.ldlife.mongo.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import it.ldlife.base.MongoBaseDaoImpl;
import it.ldlife.mongo.dao.CartDao;
import it.ldlife.pojo.Cart;

@Repository("cartDaoImpl")
public class CartDaoImpl extends MongoBaseDaoImpl<Cart> implements CartDao{

	@Override
	public Cart selectCartByUserIdProductId(String userId, String productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cart> selectCartByUserId(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int selectCartProductCheckedStatusByUserId(String userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteByUserIdProductIds(String userId, List<String> productList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkedOrUncheckedProduct(String userId, String productId, Integer checked) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer selectCartProductCount(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
