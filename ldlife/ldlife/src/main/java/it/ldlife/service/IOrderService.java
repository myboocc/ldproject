package it.ldlife.service;

import java.util.Map;

import it.ldlife.common.ServiceResponse;
import it.ldlife.util.PageInfo;
import it.ldlife.vo.OrderVo;

public interface IOrderService {
    ServiceResponse pay(Long orderNo, String userId, String path);
    ServiceResponse aliCallback(Map<String,String> params);
    ServiceResponse queryOrderPayStatus(String userId,Long orderNo);
    ServiceResponse createOrder(String userId,String shippingId);
    ServiceResponse<String> cancel(String userId,Long orderNo);
    ServiceResponse getOrderCartProduct(String userId);
    ServiceResponse<OrderVo> getOrderDetail(String userId, Long orderNo);
    ServiceResponse<PageInfo> getOrderList(String userId, PageInfo pageInfo);



    //backend
    ServiceResponse<PageInfo> manageList(PageInfo pageInfo);
    ServiceResponse<OrderVo> manageDetail(Long orderNo);
    ServiceResponse<PageInfo> manageSearch(Long orderNo, PageInfo pageInfo);
    ServiceResponse<String> manageSendGoods(Long orderNo);


}
