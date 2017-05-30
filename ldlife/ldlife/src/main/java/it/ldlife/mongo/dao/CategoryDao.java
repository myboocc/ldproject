package it.ldlife.mongo.dao;

import java.util.List;

import it.ldlife.base.MongoBaseDao;
import it.ldlife.pojo.Category;

public interface CategoryDao extends MongoBaseDao<Category>{

	List<Category> selectCategoryChildrenByParentId(String categoryId);

}
