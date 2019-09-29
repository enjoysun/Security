package com.myou.service.security.Controller;

import com.myou.service.security.Common.Util.Result;
import com.myou.service.security.Domain.TbUser;
import com.myou.service.security.Service.TbUserService;
import com.myou.service.security.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.Map;

/*
 * @Time    : 2019/9/27 10:44 AM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : UserController.java
 * @Software: IntelliJ IDEA
 */
@RestController
public class UserController {
    @Autowired
    private TbUserService userService;

    @Autowired
    private UserService userCenter;

    @GetMapping("/test/user/{name}")
    public ResponseEntity<TbUser> getUerById(@PathVariable String name) {
        return ResponseEntity.ok(userService.findByUserName(name));
    }

    @PostMapping("/rbac/auth/login")
    public Result login(@RequestBody Map<String, String> map) throws AuthenticationException {
        TbUser builder = TbUser.builder().username(map.get("username")).password(map.get("password")).build();
        String login = userCenter.login(builder);
        return Result.success(login);
    }

    @PostMapping("/rbac/auth/refresh")
    public Result refresh(@RequestBody Map<String, String> map) {
        return Result.success(userCenter.refresh_token(map.get("old_key")));
    }
}
