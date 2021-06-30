package com.github.share.contentcenter.service.content;

import com.github.share.contentcenter.dao.content.ShareMapper;
import com.github.share.contentcenter.domain.dto.content.ShareDTO;
import com.github.share.contentcenter.domain.dto.user.UserDTO;
import com.github.share.contentcenter.domain.entity.content.Share;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ShareService {

    @Autowired
    private ShareMapper shareMapper;

    @Autowired
    private RestTemplate restTemplate;

    public ShareDTO findShareById(Integer id) {
        // 获取分享详情
        Share share = shareMapper.selectByPrimaryKey(id);
        Integer userId = share.getUserId();
        // 调用用户微服务的 /users/{userId}
        String url = "http://localhost:8080/users/{id}";

        UserDTO userDTO = restTemplate.getForObject(url, UserDTO.class, userId);
        // 消息的装配
        ShareDTO shareDTO = new ShareDTO();
        BeanUtils.copyProperties(share, shareDTO);
        shareDTO.setWxNickName(userDTO.getWxNickname());
        return shareDTO;

    }

}
