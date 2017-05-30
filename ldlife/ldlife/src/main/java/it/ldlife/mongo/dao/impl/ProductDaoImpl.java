package it.ldlife.mongo.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import it.ldlife.base.MongoBaseDaoImpl;
import it.ldlife.common.Const;
import it.ldlife.mongo.dao.ProductDao;
import it.ldlife.pojo.Product;
import it.ldlife.util.PageInfo;

@Repository("productDaoImpl")
public class ProductDaoImpl extends MongoBaseDaoImpl<Product> implements ProductDao{

	@Override
	public List<Product> search(String productName, String productId, PageInfo pageInfo) {
		Query query = new Query();
    	if(StringUtils.isNotBlank(productName)){
    		query.addCriteria(Criteria.where("name").regex(productName));
    	}
    	if(StringUtils.isNotBlank(productId)){
    		query.addCriteria(Criteria.where("_id").is(productId));
    	}
    	if(StringUtils.isNotBlank(productName) || StringUtils.isNotBlank(productId)){
    		long count = mongoTemplate.count(query,this.getEntityClass());
    		pageInfo.setTotalResult(new Long(count).intValue());
    		query.with(new Sort(Direction.DESC,"createTime"));
    		query.skip(pageInfo.getCurrentResult());
    		query.limit(pageInfo.getShowCount());
    		return mongoTemplate.find(query,this.getEntityClass());
    	}
    	return null;
	}

	@Override
	public List<Product> selectByNameAndCategoryIds(PageInfo pageInfo, String keyword, List<String> categoryIdList,String orderBy) {
		Query query = new Query();
		if(StringUtils.isNotBlank(keyword)){
			query.addCriteria(Criteria.where("name").regex(keyword));
		}
		if(categoryIdList.size() > 0){
			query.addCriteria(Criteria.where("categoryId").in(categoryIdList));
		}
		if(StringUtils.isNotBlank(orderBy)){
			if(Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)){
				String[] orderByArray = orderBy.split("_");
				query.with(new Sort("desc".equals(orderByArray[1])?Direction.DESC:Direction.ASC, orderByArray[0]));
			}
		}
		long count = mongoTemplate.count(query,this.getEntityClass());
		pageInfo.setTotalResult(new Long(count).intValue());
		query.skip(pageInfo.getCurrentResult());
		query.limit(pageInfo.getShowCount());
		return mongoTemplate.find(query,this.getEntityClass());
	}
	

}
