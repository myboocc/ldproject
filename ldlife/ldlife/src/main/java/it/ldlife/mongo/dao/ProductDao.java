package it.ldlife.mongo.dao;

import java.util.List;

import it.ldlife.base.MongoBaseDao;
import it.ldlife.pojo.Product;
import it.ldlife.util.PageInfo;

public interface ProductDao extends MongoBaseDao<Product>{

	List<Product> search(String productName, String productId, PageInfo pageInfo);

	List<Product> selectByNameAndCategoryIds(PageInfo pageInfo,String keyword, List<String> categoryIdList,String orderBy);

}
