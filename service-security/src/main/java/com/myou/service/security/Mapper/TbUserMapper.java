package com.myou.service.security.Mapper;

import com.myou.service.security.Domain.TbUser;
import tk.mybatis.mapper.Mymapper;

public interface TbUserMapper extends Mymapper<TbUser> {
    int insertUser(TbUser user) throws Exception;

}