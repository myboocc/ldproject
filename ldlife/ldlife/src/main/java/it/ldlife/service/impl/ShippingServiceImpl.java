package it.ldlife.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

import it.ldlife.common.ServiceResponse;
import it.ldlife.mongo.dao.ShippingDao;
import it.ldlife.pojo.Shipping;
import it.ldlife.service.IShippingService;
import it.ldlife.util.PageInfo;

@Service("iShippingService")
public class ShippingServiceImpl implements IShippingService{
	
	@Resource
	private ShippingDao shippingDao;
	
	public ServiceResponse add(String userId, Shipping shipping){
        shipping.setUserId(userId);
        Shipping saveShipping = shippingDao.save(shipping);
        if(saveShipping.getId() != null){
            Map result = Maps.newHashMap();
            result.put("shippingId",shipping.getId());
            return ServiceResponse.createBySuccess("新建地址成功",result);
        }
        return ServiceResponse.createByErrorMessage("新建地址失败");
    }

    public ServiceResponse<String> del(String userId,String shippingId){
        int resultCount = shippingDao.deleteByShippingIdUserId(userId,shippingId);
        if(resultCount > 0){
            return ServiceResponse.createBySuccess("删除地址成功");
        }
        return ServiceResponse.createByErrorMessage("删除地址失败");
    }

    public ServiceResponse update(String userId, Shipping shipping){
        shipping.setUserId(userId);
        int rowCount = shippingDao.updateByShipping(shipping);
        if(rowCount > 0){
            return ServiceResponse.createBySuccess("更新地址成功");
        }
        return ServiceResponse.createByErrorMessage("更新地址失败");
    }

    public ServiceResponse<Shipping> select(String userId, String shippingId){
        Shipping shipping = shippingDao.selectByShippingIdUserId(userId,shippingId);
        if(shipping == null){
            return ServiceResponse.createByErrorMessage("无法查询到该地址");
        }
        return ServiceResponse.createBySuccess(shipping);
    }


    public ServiceResponse<PageInfo> list(String userId,PageInfo pageInfo){
        List<Shipping> shippingList = shippingDao.selectByUserId(userId,pageInfo);
        pageInfo.setDataList(shippingList);
        return ServiceResponse.createBySuccess(pageInfo);
    }

}
