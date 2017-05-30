package it.ldlife.service;

import it.ldlife.common.ServiceResponse;
import it.ldlife.pojo.Product;
import it.ldlife.util.Page;
import it.ldlife.util.PageInfo;
import it.ldlife.vo.ProductDetailVo;

public interface IProductService {
	
	ServiceResponse saveOrUpdateProduct(Product product);

	ServiceResponse<String> setSaleStatus(String productId,Integer status);

    ServiceResponse<ProductDetailVo> manageProductDetail(String productId);

    ServiceResponse getProductList(Page<Product> page);

    ServiceResponse searchProduct(String productName,String productId, PageInfo pageInfo);

    ServiceResponse<ProductDetailVo> getProductDetail(String productId);

    ServiceResponse<PageInfo> getProductByKeywordCategory(String keyword,String categoryId,PageInfo pageInfo,String orderBy);

}
