package com.kgc.kmall.kmallmanagerweb.controller;

import com.kgc.kmall.bean.PmsBaseCatalog1;
import com.kgc.kmall.bean.PmsBaseCatalog2;
import com.kgc.kmall.bean.PmsBaseCatalog3;
import com.kgc.kmall.service.CatalogService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@CrossOrigin
@RestController
@Api(tags = "三级分类" , description = "查看三个下拉框")
public class CatalogController {
    @Reference
    CatalogService catalogService;
    @ApiOperation( value ="一级分类",produces = "application/json;charset=UTF-8")
    @PostMapping("/getCatalog1")
    public List<PmsBaseCatalog1> getCatalog1(){
        List<PmsBaseCatalog1> catalog1List = catalogService.getCatalog1();
        return catalog1List;
    }

    @ApiOperation(value = "二级分类" , produces = "application/json;charset=UTF-8")
    @ApiImplicitParam(name = "catalog1Id",value = "一级分类id")
    @PostMapping("/getCatalog2")
    public List<PmsBaseCatalog2> getCatalog2(Integer catalog1Id){
        List<PmsBaseCatalog2> catalog2List = catalogService.getCatalog2(catalog1Id);
        return catalog2List;
    }


    @ApiOperation(value = "三级分类" , produces = "application/json;charset=UTF-8")
    @ApiImplicitParam(name = "catalog2Id" , value = "二级分类id")
    @PostMapping("/getCatalog3")
    public List<PmsBaseCatalog3> getCatalog3(Long catalog2Id){
        List<PmsBaseCatalog3> catalog3List = catalogService.getCatalog3(catalog2Id);
        return catalog3List;
    }
}
