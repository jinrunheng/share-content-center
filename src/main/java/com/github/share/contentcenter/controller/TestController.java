package com.github.share.contentcenter.controller;

import com.github.share.contentcenter.dao.content.ShareMapper;
import com.github.share.contentcenter.domain.entity.content.Share;
import com.github.share.contentcenter.service.content.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class TestController {

    @Autowired
    private ShareService shareService;

    @Autowired
    private ShareMapper shareMapper;


    @Autowired
    private DiscoveryClient discoveryClient;


    @GetMapping("/test")
    public List<Share> testInsert() {
        Share share = new Share();
        share.setUserId(1);
        share.setTitle("xxx");
        share.setCover("xxx");
        share.setAuthor("jrh");
        share.setBuyCount(1);
        share.setCreateTime(new Date());
        share.setUpdateTime(new Date());

        // 插入
        shareMapper.insertSelective(share);
        // 查询
        List<Share> shares = shareMapper.selectAll();
        return shares;
    }

    /**
     * 测试 服务发现，证明内容中心可以找到用户中心
     * @return 用户中心所有实例的信息
     */
    @GetMapping("/nacosTest")
    public List<ServiceInstance> testNacos(){
        // 查询指定服务的所有实例信息
        return discoveryClient.getInstances("user-center");
    }
}
