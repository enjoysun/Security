package com.myou.service.security.Service;

import com.myou.service.security.Domain.TbUser;

public interface TbUserService {

    TbUser findByUserName(String name);

    TbUser insertTbUser(TbUser user) throws Exception;
}
