package com.boot.job.db.common.authentication;


import com.boot.job.db.common.annotation.Helper;
import org.apache.shiro.authz.AuthorizationInfo;

/**
 * @author MrBird
 */
@Helper
public class ShiroHelper extends ShiroRealm {

    /**
     * 获取当前用户的角色和权限集合
     *
     * @return AuthorizationInfo
     */
    public AuthorizationInfo getCurrentuserAuthorizationInfo() {
        return super.doGetAuthorizationInfo(null);
    }
}
