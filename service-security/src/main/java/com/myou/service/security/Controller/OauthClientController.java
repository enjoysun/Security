package com.myou.service.security.Controller;

import com.myou.service.security.Common.Util.Result;
import com.myou.service.security.Domain.OauthClientDetails;
import com.myou.service.security.Service.OauthClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/*
 * @Time    : 2019/9/29 10:44 AM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : OauthClientController.java
 * @Software: IntelliJ IDEA
 */
@RestController
public class OauthClientController {

    @Autowired
    private OauthClientDetailsService oauthClientDetailsService;

    @GetMapping("/client/{app_id}")
    public Result<OauthClientDetails> select(@PathVariable("app_id") String aid) {
        return Result.success(oauthClientDetailsService.selectOauthByAppId(aid));
    }
}
