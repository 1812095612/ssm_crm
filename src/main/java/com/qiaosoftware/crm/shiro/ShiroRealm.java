package com.qiaosoftware.crm.shiro;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qiaosoftware.crm.entity.Authority;
import com.qiaosoftware.crm.entity.User;
import com.qiaosoftware.crm.mapper.UserMapper;

@Component
public class ShiroRealm extends AuthorizingRealm{

	@Autowired
	private UserMapper userMapper;
	
	@PostConstruct     //相当于xml文件中配置bean的init-method属性
	public void initCredentialsMatcher(){
		
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
		credentialsMatcher.setHashAlgorithmName("MD5");
		credentialsMatcher.setHashIterations(1024);
		
		setCredentialsMatcher(credentialsMatcher);
	}
	
	/**
	 * 用于授权的realm方法
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		
		User user = (User) principals.getPrimaryPrincipal();
		
		Set<String> roles = new HashSet<>();
		
		for (Authority authority : user.getRole().getAuthorities()) {
			roles.add(authority.getName());
		}
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRoles(roles);
		
		return info;
	}

	/**
	 * 用于认证的realm的回调方法
	 * 参数 AuthenticationToken 即为登录时调用 Subject 的 login(AuthenticationToken) 方法时传入的参数
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		//1. 把 AuthenticationToken 参数强转为 UsernamePasswordToken
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		//2. 从 UsernamePasswordToken 中获取 username
		String username = upToken.getUsername();
		//3. 利用 username 查询数据表, 获取用户的信息
		User user = userMapper.getByName(username);
		
		if (user == null) {
			throw new UnknownAccountException("用户【" + username + "】不存在！");
		}
		if (user.getEnabled() != 1) {
			throw new LockedAccountException("用户【" + username + "】被锁定！");
		}
		
		Object principal = user;
		Object hashedCredentials = user.getPassword();
		ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
		String realmName = getName();
		//4. 利用从数据库中获取的用户信息来创建 SimpleAuthenticationInfo 对象并返回
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, hashedCredentials,
							credentialsSalt, realmName);
		
		return info;
	}

	public static void main(String[] args) {
	    
	    String hashAlgorithmName = "MD5";
	    String credentials = "123456";
		ByteSource salt = ByteSource.Util.bytes("e2b87e6eced06509");
		int hashIterations = 1024;
		
		Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
		
		System.out.println(result);//52bcb02821b4d9d71f955fe71f3d4857
	}
	
}
