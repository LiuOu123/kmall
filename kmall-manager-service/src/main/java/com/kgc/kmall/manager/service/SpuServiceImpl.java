package com.kgc.kmall.manager.service;

import com.kgc.kmall.bean.*;
import com.kgc.kmall.manager.mapper.*;
import com.kgc.kmall.service.SpuService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;


import com.kgc.kmall.service.SpuService;

import javax.annotation.Resource;
import java.util.List;

@Component
@Service
public class SpuServiceImpl implements SpuService {

    @Resource
    PmsProductInfoMapper pmsProductInfoMapper;
    @Resource
    PmsBaseSaleAttrMapper pmsBaseSaleAttrMapper;
    @Resource
    PmsProductImageMapper pmsProductImageMapper;
    @Resource
    PmsProductSaleAttrMapper pmsProductSaleAttrMapper;
    @Resource
    pmsProductSaleAttrValueMapper pmsProductSaleAttrValueMapper;
    @Override
    public List<PmsProductInfo> spuList(Long Catalog3Id) {
        PmsProductInfoExample example=new PmsProductInfoExample();
        PmsProductInfoExample.Criteria criteria = example.createCriteria();
        criteria.andCatalog3IdEqualTo(Catalog3Id);
        List<PmsProductInfo> infoList = pmsProductInfoMapper.selectByExample(example);
        return infoList;
    }
    @Override
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        List<PmsBaseSaleAttr> saleAttrList = pmsBaseSaleAttrMapper.selectByExample(null);
        return saleAttrList;
    }

    @Override
    public Integer saveSpuInfo(PmsProductInfo pmsProductInfo) {
        int result=0;
        try {
            int insert = pmsProductInfoMapper.insert(pmsProductInfo);
            Long id = pmsProductInfo.getId();
            //保存图片
            for (PmsProductImage pmsProductImage : pmsProductInfo.getSpuImageList()) {
                pmsProductImage.setProductId(id);
                pmsProductImageMapper.insert(pmsProductImage);
            }
            //保存销售属性
            for (PmsProductSaleAttr pmsProductSaleAttr : pmsProductInfo.getSpuSaleAttrList()) {
                pmsProductSaleAttr.setProductId(id);
                for (pmsProductSaleAttrValue pmsProductSaleAttrValue : pmsProductSaleAttr.getSpuSaleAttrValueList()) {
                    pmsProductSaleAttrValue.setProductId(id);
                    pmsProductSaleAttrValueMapper.insert(pmsProductSaleAttrValue);
                }
                pmsProductSaleAttrMapper.insert(pmsProductSaleAttr);
            }
            result=1;
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return result;
    }

    @Override
    public List<PmsProductSaleAttr> spuSaleAttrList(Long spuId) {
        PmsProductSaleAttrExample example=new PmsProductSaleAttrExample();
        PmsProductSaleAttrExample.Criteria criteria = example.createCriteria();
        criteria.andProductIdEqualTo(spuId);
        List<PmsProductSaleAttr> pmsProductSaleAttrList = pmsProductSaleAttrMapper.selectByExample(example);
        for (PmsProductSaleAttr pmsProductSaleAttr : pmsProductSaleAttrList) {
            pmsProductSaleAttrValueExample example1=new pmsProductSaleAttrValueExample();
            pmsProductSaleAttrValueExample.Criteria criteria1 = example1.createCriteria();
            criteria1.andSaleAttrIdEqualTo(pmsProductSaleAttr.getSaleAttrId());
            criteria1.andProductIdEqualTo(spuId);

            List<pmsProductSaleAttrValue> pmsProductSaleAttrValueList = pmsProductSaleAttrValueMapper.selectByExample(example1);
            pmsProductSaleAttr.setSpuSaleAttrValueList(pmsProductSaleAttrValueList);
        }
        return pmsProductSaleAttrList;
    }
    @Override
    public List<PmsProductImage> spuImageList(Long spuId) {
        PmsProductImageExample example=new PmsProductImageExample();
        PmsProductImageExample.Criteria criteria = example.createCriteria();
        criteria.andProductIdEqualTo(spuId);
        List<PmsProductImage> pmsProductImageList = pmsProductImageMapper.selectByExample(example);
        return pmsProductImageList;
    }
}

