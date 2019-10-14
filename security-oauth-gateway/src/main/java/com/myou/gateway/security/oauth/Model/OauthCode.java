package com.myou.gateway.security.oauth.Model;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "oauth_code")
public class OauthCode implements Serializable {
    @Column(name = "code")
    private String code;

    @Column(name = "authentication")
    private byte[] authentication;

    private static final long serialVersionUID = 1L;
}