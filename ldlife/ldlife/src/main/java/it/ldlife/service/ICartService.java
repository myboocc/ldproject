package it.ldlife.service;

import it.ldlife.common.ServiceResponse;
import it.ldlife.vo.CartVo;

public interface ICartService {

	ServiceResponse<CartVo> add(String userId, String productId, Integer count);
    ServiceResponse<CartVo> update(String userId,String productId,Integer count);
    ServiceResponse<CartVo> deleteProduct(String userId,String productIds);

    ServiceResponse<CartVo> list (String userId);
    ServiceResponse<CartVo> selectOrUnSelect (String userId,String productId,Integer checked);
    ServiceResponse<Integer> getCartProductCount(String userId);
    
}
