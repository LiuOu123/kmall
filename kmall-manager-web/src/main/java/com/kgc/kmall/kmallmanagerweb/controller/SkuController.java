package com.kgc.kmall.kmallmanagerweb.controller;

import com.kgc.kmall.bean.PmsSkuInfo;
import com.kgc.kmall.service.SkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@Api(tags = "保存sku销售属性",description = "提供商品信息管理")
public class SkuController {

    @Reference
    SkuService skuService;
    @ApiOperation("保存sku商品销售属性")
    @PostMapping("/saveSkuInfo")
    @ApiImplicitParam(name = "PmsSkuInfo",value = "skuInfo实体类对象",required = true)
    /* @RequestMapping("/saveSkuInfo")*/
    public String saveSkuInfo(@RequestBody PmsSkuInfo skuInfo){
        String result = skuService.saveSkuInfo(skuInfo);
        return result;
    }
}