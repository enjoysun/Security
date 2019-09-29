package com.myou.service.security.Service;

import com.myou.service.security.Domain.OauthClientDetails;

public interface OauthClientDetailsService {

    OauthClientDetails selectOauthByAppId(String aid);
}
