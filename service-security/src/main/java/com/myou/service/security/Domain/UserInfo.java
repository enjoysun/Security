package com.myou.service.security.Domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*
 * @Time    : 2019/9/27 5:02 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : UserInfo.java
 * @Software: IntelliJ IDEA
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private TbUser user;
    private TbRole role;
    private List<TbPermission> permission;
}
