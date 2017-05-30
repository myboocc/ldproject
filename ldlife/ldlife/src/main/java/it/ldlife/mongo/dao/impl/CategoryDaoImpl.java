package it.ldlife.mongo.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import it.ldlife.base.MongoBaseDaoImpl;
import it.ldlife.mongo.dao.CategoryDao;
import it.ldlife.pojo.Category;

@Repository("categoryDaoImpl")
public class CategoryDaoImpl extends MongoBaseDaoImpl<Category> implements CategoryDao{

	@Override
	public List<Category> selectCategoryChildrenByParentId(String categoryId) {
		Query query = new Query();
    	query.addCriteria(Criteria.where("parentId").is(categoryId));
		return this.mongoTemplate.find(query, Category.class);
	}

}
