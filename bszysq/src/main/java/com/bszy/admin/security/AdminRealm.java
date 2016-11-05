package com.bszy.admin.security;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bszy.admin.pojo.Admin;
import com.bszy.admin.service.AdminService;
import com.mao.ssm.AjaxResult;


/**
 * 后台用户登录验证
 * @author Mao 2016年10月28日 上午1:52:39
 */
@Component
public class AdminRealm extends AuthorizingRealm {
	private static final Logger log = LoggerFactory.getLogger(AdminRealm.class);

	@Inject
	private AdminService adminService;

	public AdminRealm() {
		setName("AdminRealm");
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		AjaxResult ar = new AjaxResult();
		try {
			UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
			Map<String, String> params = new HashMap<String, String>();
			params.put("name", token.getUsername());
			params.put("pwd", DigestUtils.md5Hex(String.valueOf(token.getPassword())));
			Admin mo = adminService.login(params);
			if(mo != null){
				if(mo.getIsdel() != 0){
					ar.t_fail("1028");	// 被冻结
				}else{
					ar.t_succ();	// 成功
					return new SimpleAuthenticationInfo(mo.getId(), String.valueOf(token.getPassword()), getName());
				}
			}else{
				ar.t_fail("1004");	// 帐号或密码错误
			}
		} catch (Exception e) {
			log.error("后台认证异常", e);
			ar.t_fail("1004");	// 帐号或密码错误
		}
		return null;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return AdminCurUtil.cur_shiro_auth();
	}

}
