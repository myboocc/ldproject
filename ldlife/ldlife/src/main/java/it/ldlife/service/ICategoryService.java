package it.ldlife.service;

import it.ldlife.common.ServiceResponse;
import it.ldlife.pojo.Category;

import java.util.List;

public interface ICategoryService {
	ServiceResponse addCategory(String categoryName, String parentId);
	ServiceResponse updateCategoryName(String categoryId,String categoryName);
	ServiceResponse<List<Category>> getChildrenParallelCategory(String categoryId);
	ServiceResponse<List<String>> selectCategoryAndChildrenById(String categoryId);

}
