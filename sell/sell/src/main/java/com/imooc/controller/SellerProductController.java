package com.imooc.controller;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.imooc.dataobject.ProductCategory;
import com.imooc.dataobject.ProductInfo;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.form.ProductForm;
import com.imooc.service.CategoryService;
import com.imooc.service.ProductService;
import com.imooc.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/seller/product")
@Slf4j
public class SellerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,// 这里modelandView默认从第一页开始查
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map){
        PageRequest pageRequest = new PageRequest(page-1, size); //默认从第零页开始
        Page<ProductInfo> productInfoPage = productService.findAll(pageRequest);

        List<ProductCategory> categoryList = categoryService.findAll();

        map.put("productInfoPage", productInfoPage);
        map.put("currentPage", page);
        map.put("size", size);
        map.put("categoryList", categoryList);
        return new ModelAndView("product/list", map);
    }

    @GetMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId,
                               Map<String, Object> map){
        ProductInfo productInfo = new ProductInfo();
         try{
             productInfo = productService.onSale(productId);
         }catch (SellException e){
             log.error("【卖家端上架】出现异常{}", e);
             map.put("msg", e.getMessage());
             map.put("url", "/sell/seller/product/list");
             return new ModelAndView("common/error");
         }
         map.put("msg", ResultEnum.PRODUCT_ONSALE_SUCCESS.getMsg());
         map.put("url", "/sell/seller/product/list");
         return new ModelAndView("common/success");
    }

    @GetMapping("/off_sale")
    public ModelAndView OffSale(@RequestParam("productId") String productId,
                                Map<String, Object> map){
        ProductInfo productInfo = new ProductInfo();
        try{
            productInfo = productService.offSale(productId);
        }catch (SellException e){
            log.error("【卖家端下架】出现异常{}", e);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }
        map.put("msg", ResultEnum.PRODUCT_OFFSALE_SUCCESS.getMsg());
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success", map);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId,
                              Map<String, Object> map){
        if(!StringUtils.isEmpty(productId)){
            ProductInfo productInfo = productService.findOne(productId);
            map.put("productInfo", productInfo);
        }

        List<ProductCategory> productCategoryList = categoryService.findAll();
        map.put("productCategoryList", productCategoryList);
        return new ModelAndView("product/index", map);

    }

    /**
     * 保存与更新
     * @param form
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("/save") //这里需要传入的参数过多 使用表单校验 @valid   BindingResult
    public ModelAndView save(@Valid ProductForm form,
                             BindingResult bindingResult,
                             Map<String, Object> map){
        if(bindingResult.hasErrors()){
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/sell/seller/product/index");
            return new ModelAndView("common/error", map);
        }
        ProductInfo productInfo = new ProductInfo();
        try {
            if(!StringUtils.isEmpty(form.getProductId())) {   //productId为空说明正在新增商品！所以判断不为空时才findone 修改商品
                productInfo = productService.findOne(form.getProductId());
            }else{
                form.setProductId(KeyUtil.getUniqueKey());
            }
            BeanUtils.copyProperties(form, productInfo);
            productService.save(productInfo);
        }catch (SellException e){
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/index");
            return new ModelAndView("common/error", map);
        }

        map.put("msg", ResultEnum.PRODUCT_SUBMIT_SUCCESS.getMsg());
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success", map);


    }

}
