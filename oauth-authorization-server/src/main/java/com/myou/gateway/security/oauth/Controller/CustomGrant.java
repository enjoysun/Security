package com.myou.gateway.security.oauth.Controller;

import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/*
 * @Time    : 2019/10/16 4:30 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : CustomGrant.java
 * @Software: IntelliJ IDEA
 */
@Controller
@SessionAttributes("authorizationRequest")
public class CustomGrant {
    @RequestMapping("/rbac/confirm_access")
    public ModelAndView getAccessConfirmation(Map<String, Object> model, HttpServletRequest request) throws Exception {
        AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.get("authorizationRequest");
        ModelAndView view = new ModelAndView();
        view.setViewName("oauth_approval");
        view.addObject("clientId", authorizationRequest.getClientId());
        view.addObject("scopes", authorizationRequest.getScope());
        return view;
    }
}
