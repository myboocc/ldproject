package it.ldlife.mongo.dao;

import java.util.List;

import it.ldlife.base.MongoBaseDao;
import it.ldlife.pojo.Cart;

public interface CartDao extends MongoBaseDao<Cart>{

	Cart selectCartByUserIdProductId(String userId, String productId);

	List<Cart> selectCartByUserId(String userId);

	int selectCartProductCheckedStatusByUserId(String userId);

	void deleteByUserIdProductIds(String userId, List<String> productList);

	void checkedOrUncheckedProduct(String userId, String productId, Integer checked);

	Integer selectCartProductCount(String userId);

	List<Cart> selectCheckedCartByUserId(String userId);

	void deleteByPrimaryKey(String id);

}
