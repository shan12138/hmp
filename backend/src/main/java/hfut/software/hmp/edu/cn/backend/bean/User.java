package hfut.software.hmp.edu.cn.backend.bean;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class User implements UserDetails {
    /**
     * 数据库字段 account
     */
    private String username;
    /**
     * 数据库字段 user_id
     */
    private String userId;
    private String password;
    /**
     * 数据库字段 real_name
     */
    private String realName;
    private List<Role> roles;
    private Integer status;

    public User() {
    }

    public User(String username, String userId, String password, String realName, List<Role> roles, Integer status) {
        this.username = username;
        this.userId = userId;
        this.password = password;
        this.realName = realName;
        this.roles = roles;
        this.status = status;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<String> roles = this.getRoles();
        roles.forEach(role ->
                authorities.add(new SimpleGrantedAuthority(role))
        );
        return authorities;
    }

    public List<String> getRoles() {
        List<String> roleList = new ArrayList<>();
        this.roles.forEach(role -> roleList.add(role.getRoleCode()));
        return roleList;
    }

}
