package com.myou.service.security.Mapper;

import com.myou.service.security.Domain.OauthClientDetails;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.Mymapper;

public interface OauthClientDetailsMapper extends Mymapper<OauthClientDetails> {
    OauthClientDetails selectOauthClientByAppId(@Param("aid") String aid);
}