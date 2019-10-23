package com.myou.gateway.security.oauth.Model;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Builder;
import lombok.Data;

@Data
@Table(name = "oauth_client_details")
@Builder
public class OauthClientDetails implements Serializable {
    /**
     * 第三方ID
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 注册名称
     */
    @Column(name = "`name`")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "resource_ids")
    private String resourceIds;

    @Column(name = "client_secret")
    private String clientSecret;

    @Column(name = "`scope`")
    private String scope;

    @Column(name = "authorized_grant_types")
    private String authorizedGrantTypes;

    @Column(name = "web_server_redirect_uri")
    private String webServerRedirectUri;

    @Column(name = "authorities")
    private String authorities;

    @Column(name = "access_token_validity")
    private Integer accessTokenValidity;

    @Column(name = "refresh_token_validity")
    private Integer refreshTokenValidity;

    @Column(name = "additional_information")
    private String additionalInformation;

    @Column(name = "autoapprove")
    private String autoapprove;

    @Column(name = "platform_id")
    private String platformId;

    private static final long serialVersionUID = 1L;
}