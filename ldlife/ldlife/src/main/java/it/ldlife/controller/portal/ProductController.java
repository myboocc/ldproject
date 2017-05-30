package it.ldlife.controller.portal;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import it.ldlife.common.ServiceResponse;
import it.ldlife.service.IProductService;
import it.ldlife.util.PageInfo;
import it.ldlife.vo.ProductDetailVo;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Resource
    private IProductService iProductService;

    @RequestMapping("/detail")
    @ResponseBody
    public ServiceResponse<ProductDetailVo> detail(String productId){
        return iProductService.getProductDetail(productId);
    }

    @RequestMapping("/list")
    @ResponseBody
    public ServiceResponse<PageInfo> list(@RequestParam(value = "keyword",required = false)String keyword,
                                         @RequestParam(value = "categoryId",required = false)String categoryId,
//                                         @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
//                                         @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                         PageInfo pageInfo,
                                         @RequestParam(value = "orderBy",defaultValue = "") String orderBy){
        return iProductService.getProductByKeywordCategory(keyword,categoryId,pageInfo,orderBy);
    }

}
