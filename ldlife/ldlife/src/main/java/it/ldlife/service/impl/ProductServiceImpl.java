package it.ldlife.service.impl;

import com.google.common.collect.Lists;

import it.ldlife.common.Const;
import it.ldlife.common.ResponseCode;
import it.ldlife.common.ServiceResponse;
import it.ldlife.mongo.dao.CategoryDao;
import it.ldlife.mongo.dao.ProductDao;
import it.ldlife.pojo.Category;
import it.ldlife.pojo.Product;
import it.ldlife.service.ICategoryService;
import it.ldlife.service.IProductService;
import it.ldlife.util.DateTimeUtil;
import it.ldlife.util.Page;
import it.ldlife.util.PageInfo;
import it.ldlife.util.PropertiesUtil;
import it.ldlife.vo.ProductDetailVo;
import it.ldlife.vo.ProductListVo;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

@Service("iProductService")
public class ProductServiceImpl implements IProductService {

	@Resource
    private ProductDao productDao;

	@Resource
    private CategoryDao categoryDao;

	@Resource
    private ICategoryService iCategoryService;

    public ServiceResponse saveOrUpdateProduct(Product product){
        if(product != null){
            if(StringUtils.isNotBlank(product.getSubImages())){
                String[] subImageArray = product.getSubImages().split(",");
                if(subImageArray.length > 0){
                    product.setMainImage(subImageArray[0]);
                }
            }
            if(product.getId() != null){
                int rowCount = productDao.update(product).getN();
                if(rowCount > 0){
                    return ServiceResponse.createBySuccess("更新产品成功");
                }
                return ServiceResponse.createBySuccess("更新产品失败");
            }else{
                Product saveProduct = productDao.save(product);
                if(saveProduct.getId() != null){
                    return ServiceResponse.createBySuccess("新增产品成功");
                }
                return ServiceResponse.createBySuccess("新增产品失败");
            }
        }
        return ServiceResponse.createByErrorMessage("新增或更新产品参数不正确");
    }


    public ServiceResponse<String> setSaleStatus(String productId,Integer status){
        if(productId == null || status == null){
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int rowCount = productDao.update(product).getN();
        if(rowCount > 0){
            return ServiceResponse.createBySuccess("修改产品销售状态成功");
        }
        return ServiceResponse.createByErrorMessage("修改产品销售状态失败");
    }


    public ServiceResponse<ProductDetailVo> manageProductDetail(String productId){
        if(productId == null){
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productDao.findById(productId);
        if(product == null){
            return ServiceResponse.createByErrorMessage("产品已下架或者删除");
        }
        ProductDetailVo productDetailVo = assembleProductDetailVo(product);
        return ServiceResponse.createBySuccess(productDetailVo);
    }

    private ProductDetailVo assembleProductDetailVo(Product product){
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setName(product.getName());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setStock(product.getStock());

        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.manzhiyan.com/"));

        Category category = categoryDao.findById(product.getCategoryId());
        if(category == null){
            productDetailVo.setParentCategoryId("0");//默认根节点
        }else{
            productDetailVo.setParentCategoryId(category.getParentId());
        }

        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
        return productDetailVo;
    }

    public ServiceResponse getProductList(Page<Product> page){
        //startPage--start
        //填充自己的sql查询逻辑
        //pageHelper-收尾
    	Query query = new Query();
    	Page<Product> pageProduct = productDao.findPage(page, query);
    	List<Product> productList = pageProduct.getRows();
    	if(CollectionUtils.isNotEmpty(productList)){
    		List<ProductListVo> productListVoList = Lists.newArrayList();
    		for(Product productItem : productList){
    			ProductListVo productListVo = assembleProductListVo(productItem);
    			productListVoList.add(productListVo);
    		}
    		Page<ProductListVo> pageResult = new Page<ProductListVo>();
    		pageResult.setCurrentPage(page.getCurrentPage());
    		pageResult.setPageSize(page.getPageSize());
    		pageResult.setTotalCount(page.getTotalCount());
    		pageResult.setTotalPage(page.getTotalPage());
    		pageResult.build(productListVoList);
    		return ServiceResponse.createBySuccess(pageResult);
    	}
		return ServiceResponse.createByErrorMessage("获取信息错误");
    }

    private ProductListVo assembleProductListVo(Product product){
        ProductListVo productListVo = new ProductListVo();
        productListVo.setId(product.getId());
        productListVo.setName(product.getName());
        productListVo.setCategoryId(product.getCategoryId());
        productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.manzhiyan.com/"));
        productListVo.setMainImage(product.getMainImage());
        productListVo.setPrice(product.getPrice());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setStatus(product.getStatus());
        return productListVo;
    }

    public ServiceResponse<PageInfo> searchProduct(String productName,String productId, PageInfo pageInfo){
    	List<Product> productList = productDao.search(productName,productId,pageInfo);
    	if(CollectionUtils.isNotEmpty(productList)){
    		List<ProductListVo> productListVoList = Lists.newArrayList();
    		for(Product productItem : productList){
    			ProductListVo productListVo = assembleProductListVo(productItem);
    			productListVoList.add(productListVo);
    		}
    		pageInfo.setDataList(productListVoList);
    		return ServiceResponse.createBySuccess(pageInfo);
    	}
		return ServiceResponse.createByErrorMessage("没有查到相关结果");
    }


    public ServiceResponse<ProductDetailVo> getProductDetail(String productId){
        if(productId == null){
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productDao.findById(productId);
        if(product == null){
            return ServiceResponse.createByErrorMessage("产品已下架或者删除");
        }
        if(product.getStatus() != Const.ProductStatusEnum.ON_SALE.getCode()){
            return ServiceResponse.createByErrorMessage("产品已下架或者删除");
        }
        ProductDetailVo productDetailVo = assembleProductDetailVo(product);
        return ServiceResponse.createBySuccess(productDetailVo);
    }


    public ServiceResponse<PageInfo> getProductByKeywordCategory(String keyword,String categoryId,PageInfo pageInfo,String orderBy){
        if(StringUtils.isBlank(keyword) && categoryId == null){
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        List<String> categoryIdList = new ArrayList<String>();

        if(categoryId != null){
            Category category = categoryDao.findById(categoryId);
            if(category == null && StringUtils.isBlank(keyword)){
                //没有该分类,并且还没有关键字,这个时候返回一个空的结果集,不报错
                List<ProductListVo> productListVoList = Lists.newArrayList();
                pageInfo.setDataList(productListVoList);
                return ServiceResponse.createBySuccess(pageInfo);
            }
            categoryIdList = iCategoryService.selectCategoryAndChildrenById(category.getId()).getData();
        }
        List<Product> productList = productDao.selectByNameAndCategoryIds(pageInfo,StringUtils.isBlank(keyword)?null:keyword,categoryIdList.size()==0?null:categoryIdList,StringUtils.isBlank(orderBy)?null:orderBy);

        List<ProductListVo> productListVoList = Lists.newArrayList();
        for(Product product : productList){
            ProductListVo productListVo = assembleProductListVo(product);
            productListVoList.add(productListVo);
        }
        pageInfo.setDataList(productListVoList);
        return ServiceResponse.createBySuccess(pageInfo);
    }



}
