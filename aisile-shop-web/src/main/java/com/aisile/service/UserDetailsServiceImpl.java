package com.aisile.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.aisile.pojo.TbSeller;
import com.aisile.sellergoods.service.SellerService;
/**
 * 自定义认证类
 * 认证:登录
 * 授权:授予权限
 * 角色:授予角色
 * 验证:验证权限是否可以通过
 * 逻辑:如果你有什么角色,你就能做什么事
 * @author admin
 *
 */
public class UserDetailsServiceImpl implements UserDetailsService {

	private SellerService sellerService;

	public void setSellerService(SellerService sellerService) {
		this.sellerService = sellerService;
	}



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//构建角色列表
		List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
		grantedAuths.add(new SimpleGrantedAuthority("ROLE_SELLER"));
		TbSeller seller = sellerService.findOne(username);
		if(seller != null) {
			if(seller.getStatus().equals("1")) {
				return new User(username, seller.getPassword(), grantedAuths);
			}else {
				return null;
			}
		}else {
			return null;
		}
	}

	

}
