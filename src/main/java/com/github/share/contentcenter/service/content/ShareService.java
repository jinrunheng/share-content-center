package com.github.share.contentcenter.service.content;

import com.github.share.contentcenter.dao.content.ShareMapper;
import com.github.share.contentcenter.domain.dto.content.ShareDTO;
import com.github.share.contentcenter.domain.dto.user.UserDTO;
import com.github.share.contentcenter.domain.entity.content.Share;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
public class ShareService {

    @Autowired
    private ShareMapper shareMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    public ShareDTO findShareById(Integer id) {

        // 获取分享详情
        Share share = shareMapper.selectByPrimaryKey(id);
        Integer userId = share.getUserId();

        // 用户中心所有实例信息
        List<ServiceInstance> instances = discoveryClient.getInstances("user-center");
        // 调用用户微服务的 /users/{userId}
        String targetUrl = instances.stream()
                .map(instance -> instance.getUri().toString() + "/users/{id}")
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("当前没有实例！"));
        //"/users/{id}";
        log.info("请求的目标地址：{}", targetUrl);
        UserDTO userDTO = restTemplate.getForObject(targetUrl, UserDTO.class, userId);
        // 消息的装配
        ShareDTO shareDTO = new ShareDTO();
        BeanUtils.copyProperties(share, shareDTO);
        shareDTO.setWxNickName(userDTO.getWxNickname());
        return shareDTO;

    }

}
