package com.kgc.kmall.searchservice;

import com.kgc.kmall.bean.PmsSearchSkuInfo;
import com.kgc.kmall.bean.PmsSkuInfo;
import com.kgc.kmall.service.SkuService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class KmallSearchServiceApplicationTests {
    @Reference
    SkuService skuService;
    @Resource
    JestClient jestClient;
    @Resource

    @Test
    void contextLoads() {
        List<PmsSkuInfo> allSku = skuService.selectAll();
        List<PmsSearchSkuInfo> pmsSearchSkuInfos=new ArrayList<>();
        for (PmsSkuInfo pmsSkuInfo : allSku) {
            PmsSearchSkuInfo pmsSearchSkuInfo = new PmsSearchSkuInfo();
            BeanUtils.copyProperties(pmsSkuInfo,pmsSearchSkuInfo);
            pmsSearchSkuInfo.setProductId(pmsSkuInfo.getSpuId());
            pmsSearchSkuInfos.add(pmsSearchSkuInfo);
        }
        for (PmsSearchSkuInfo pmsSearchSkuInfo : pmsSearchSkuInfos) {
            Index index=new Index.Builder(pmsSearchSkuInfo).index("kmall").type("PmsSkuInfo").id(pmsSearchSkuInfo.getId()+"").build();
            try {
                jestClient.execute(index);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void Test01(){
        List<PmsSearchSkuInfo> pmsSearchSkuInfos=new ArrayList<>();
        //查询条件
        String json="{\n" +
                "  \"query\": {\n" +
                "    \"bool\": {\n" +
                "      \"filter\": [\n" +
                "          {\"terms\":{\"skuAttrValueList.valueId\":[\"39\",\"40\",\"41\",\"42\"]}},\n" +
                "          {\"term\":{\"skuAttrValueList.valueId\":\"43\"}}\n" +
                "        ], \n" +
                "      \"must\": \n" +
                "        {\n" +
                "          \"match\": {\n" +
                "            \"skuName\": \"iphone\"\n" +
                "          }\n" +
                "        }\n" +
                "      \n" +
                "    }\n" +
                "  }\n" +
                "}";
        Search search=new Search.Builder(json).addIndex("kmall").addType("PmsSkuInfo").build();
        try {
            SearchResult searchResult = jestClient.execute(search);
            List<SearchResult.Hit<PmsSearchSkuInfo, Void>> hits = searchResult.getHits(PmsSearchSkuInfo.class);
            for (SearchResult.Hit<PmsSearchSkuInfo, Void> hit : hits) {
                PmsSearchSkuInfo pmsSearchSkuInfo = hit.source;
                pmsSearchSkuInfos.add(pmsSearchSkuInfo);
                System.out.println(pmsSearchSkuInfo.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSearch(){
        List<PmsSearchSkuInfo> pmsSearchSkuInfos=new ArrayList<>();
        //查询条件
        String json="{\n" +
                "  \"query\": {\n" +
                "    \"bool\": {\n" +
                "      \"filter\": [\n" +
                "          {\"terms\":{\"skuAttrValueList.valueId\":[\"39\",\"40\",\"41\",\"42\"]}},\n" +
                "          {\"term\":{\"skuAttrValueList.valueId\":\"43\"}}\n" +
                "        ], \n" +
                "      \"must\": \n" +
                "        {\n" +
                "          \"match\": {\n" +
                "            \"skuName\": \"iphone\"\n" +
                "          }\n" +
                "        }\n" +
                "      \n" +
                "    }\n" +
                "  }\n" +
                "}";
        Search search=new Search.Builder(json).addIndex("kmall").addType("PmsSkuInfo").build();
        try {
           SearchResult searchResult=jestClient.execute(search);
           List<SearchResult.Hit<PmsSearchSkuInfo,Void>> hits=searchResult.getHits(PmsSearchSkuInfo.class);
            for (SearchResult.Hit<PmsSearchSkuInfo, Void> hit : hits) {
                PmsSearchSkuInfo pmsSearchSkuInfo=hit.source;
                pmsSearchSkuInfos.add(pmsSearchSkuInfo);
                System.out.println(pmsSearchSkuInfo.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
