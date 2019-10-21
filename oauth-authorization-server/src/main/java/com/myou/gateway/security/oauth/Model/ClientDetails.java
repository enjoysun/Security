package com.myou.gateway.security.oauth.Model;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "ClientDetails")
public class ClientDetails implements Serializable {
    @Id
    @Column(name = "appId")
    private String appid;

    @Column(name = "resourceIds")
    private String resourceids;

    @Column(name = "appSecret")
    private String appsecret;

    @Column(name = "`scope`")
    private String scope;

    @Column(name = "grantTypes")
    private String granttypes;

    @Column(name = "redirectUrl")
    private String redirecturl;

    @Column(name = "authorities")
    private String authorities;

    @Column(name = "access_token_validity")
    private Integer accessTokenValidity;

    @Column(name = "refresh_token_validity")
    private Integer refreshTokenValidity;

    @Column(name = "additionalInformation")
    private String additionalinformation;

    @Column(name = "autoApproveScopes")
    private String autoapprovescopes;

    private static final long serialVersionUID = 1L;
}