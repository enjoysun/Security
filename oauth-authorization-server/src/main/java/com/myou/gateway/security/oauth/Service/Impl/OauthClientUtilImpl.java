package com.myou.gateway.security.oauth.Service.Impl;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.myou.gateway.security.oauth.Service.OauthClientUtil;
import org.springframework.stereotype.Service;

import java.util.UUID;

/*
 * @Time    : 2019/10/23 9:49 AM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : OauthClientUtilImpl.java
 * @Software: IntelliJ IDEA
 */
@Service
public class OauthClientUtilImpl implements OauthClientUtil {
    /**
     * client-id:第三方注册名+当前时间戳
     */
    @Override
    public String createClientID(String name) {
        String s = name + System.currentTimeMillis();
        UUID uuid = UUID.nameUUIDFromBytes(s.getBytes());
        return uuid.toString().replace("-", "").toLowerCase();
    }

    @Override
    public String createSecret(String userName, String clientID) {
        String s = userName + clientID;
        HashFunction hashFunction = Hashing.hmacMd5(s.getBytes());
        String secret = hashFunction.newHasher().hash().toString();
        return secret;
    }
}
