package com.myou.service.security.Controller;

import com.myou.service.security.Common.Util.Result;
import com.myou.service.security.Domain.TbRole;
import com.myou.service.security.Service.TbRoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/*
 * @Time    : 2019/10/8 4:11 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : RoleController.java
 * @Software: IntelliJ IDEA
 */
@RestController
public class RoleController {

    @Autowired
    private TbRoleService tbRoleService;

    @PostMapping("/rbac/role")
    public Result addRole(@RequestBody Map<String, String> map) {
        Date date = new Date();
        TbRole role = TbRole.builder().name(map.get("name"))
                .description(map.get("description"))
                .enname(map.get("enname"))
                .parentId(StringUtils.isEmpty(map.get("pid")) ? 0 : Long.getLong(map.get("pid")))
                .created(date)
                .updated(date)
                .build();
        int i = tbRoleService.insertRole(role);
        return Result.success(i);
    }

    @GetMapping("/rbac/role/{rid}")
    public Result RoleInfoById(@PathVariable("rid") Long rid) {
        return Result.success(tbRoleService.selectRoleById(rid));
    }

    @GetMapping("/rbac/role")
    public Result RoleList() {
        return Result.success(tbRoleService.selectRoleById(null));
    }
}
