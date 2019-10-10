package com.myou.service.security.Server.Domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*
 * @Time    : 2019/10/9 4:31 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : Authority.java
 * @Software: IntelliJ IDEA
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Authority {
    private String role;
    private List<String> permission;
    private String enRole;
}
