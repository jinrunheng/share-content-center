package com.github.share.contentcenter.controller;

import com.github.share.contentcenter.dao.content.ShareMapper;
import com.github.share.contentcenter.domain.entity.content.Share;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class TestController {

    @Autowired
    private ShareMapper shareMapper;

    @GetMapping("/test")
    public List<Share> testInsert() {
        Share share = new Share();
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
}
