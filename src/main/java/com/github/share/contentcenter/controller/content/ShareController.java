package com.github.share.contentcenter.controller.content;

import com.github.share.contentcenter.domain.dto.content.ShareDTO;
import com.github.share.contentcenter.service.content.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shares")
public class ShareController {

    @Autowired
    private ShareService shareService;

    @GetMapping("/{id}")
    public ShareDTO findById(@PathVariable Integer id) {
        return shareService.findShareById(id);
    }
}
