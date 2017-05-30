package it.ldlife.service.impl;

import it.ldlife.common.ServiceResponse;
import it.ldlife.mongo.dao.CategoryDao;
import it.ldlife.pojo.Category;
import it.ldlife.service.ICategoryService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService {

    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Resource
    private CategoryDao categoryDao;

    @Override
    public ServiceResponse addCategory(String categoryName,String parentId){
        if(parentId == null || StringUtils.isBlank(categoryName)){
            return ServiceResponse.createByErrorMessage("添加品类参数错误");
        }

        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);//这个分类是可用的

        Category saveCategory = categoryDao.save(category);
        if(saveCategory.getId() != null){
            return ServiceResponse.createBySuccess("添加品类成功");
        }
        return ServiceResponse.createByErrorMessage("添加品类失败");
    }

    public ServiceResponse updateCategoryName(String categoryId,String categoryName){
        if(categoryId == null || StringUtils.isBlank(categoryName)){
            return ServiceResponse.createByErrorMessage("更新品类参数错误");
        }
        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);

        int rowCount = categoryDao.update(category).getN();
        if(rowCount > 0){
            return ServiceResponse.createBySuccess("更新品类名字成功");
        }
        return ServiceResponse.createByErrorMessage("更新品类名字失败");
    }

    public ServiceResponse<List<Category>> getChildrenParallelCategory(String categoryId){
        List<Category> categoryList = categoryDao.selectCategoryChildrenByParentId(categoryId);
        if(CollectionUtils.isEmpty(categoryList)){
            logger.info("未找到当前分类的子分类");
        }
        return ServiceResponse.createBySuccess(categoryList);
    }


    /**
     * 递归查询本节点的id及孩子节点的id
     * @param categoryId
     * @return
     */
    public ServiceResponse<List<String>> selectCategoryAndChildrenById(String categoryId){
        Set<Category> categorySet = Sets.newHashSet();
        findChildCategory(categorySet,categoryId);

        List<String> categoryIdList = Lists.newArrayList();
        if(categoryId != null){
            for(Category categoryItem : categorySet){
                categoryIdList.add(categoryItem.getId());
            }
        }
        return ServiceResponse.createBySuccess(categoryIdList);
    }


    //递归算法,算出子节点
    private Set<Category> findChildCategory(Set<Category> categorySet ,String categoryId){
        Category category = categoryDao.findById(categoryId);
        if(category != null){
            categorySet.add(category);
        }
        //查找子节点,递归算法一定要有一个退出的条件
        List<Category> categoryList = categoryDao.selectCategoryChildrenByParentId(categoryId);
        for(Category categoryItem : categoryList){
            findChildCategory(categorySet,categoryItem.getId());
        }
        return categorySet;
    }

}
