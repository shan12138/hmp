package hfut.software.hmp.edu.cn.backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import hfut.software.hmp.edu.cn.backend.bean.User;
import hfut.software.hmp.edu.cn.backend.service.UserService;
import hfut.software.hmp.edu.cn.backend.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().formLogin()
                //没有登录提示
                .loginPage("/authentication/noLogin")
                //指定登录接口，需要controller，不需要实现
                .loginProcessingUrl("/authentication/login")
                .usernameParameter("username")
                .passwordParameter("password")
                //登录成功的处理
                .successHandler((request, response, authentication) -> {
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    Map<String, Object> map = new HashMap<>(2);
                    User user = ((User) authentication.getPrincipal());
                    map.put("realName", user.getRealName());
                    map.put("id", user.getUserId());
                    map.put("roles", user.getRoles());
                    out.write(new ObjectMapper().writeValueAsString(ResultUtil.success(map)));
                    out.flush();
                    out.close();
                })
                //登录失败处理
                .failureHandler((request, response, exception) -> {
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.write(new ObjectMapper().writeValueAsString(ResultUtil.error("AH_F", exception.getMessage())));
                    out.flush();
                    out.close();
                })
                .and()
                .logout()
                //登出接口地址，需要controller，无需实现具体逻辑
                .logoutUrl("/authentication/logout")
                //登出成功处理
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.write(new ObjectMapper().writeValueAsString(ResultUtil.success("登出成功")));
                    out.flush();
                    out.close();
                })
                //使session无效
                .invalidateHttpSession(true)
                .and()
                //权限控制
                .authorizeRequests()
                .antMatchers("/authentication/**","/ui/**","/webjars/**","/","/index").permitAll()
                .antMatchers("/actuator/**").hasRole("SUPERADMIN")
                .anyRequest().authenticated();
        http.csrf().disable();
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter, CsrfFilter.class);
    }

    //userService是extends UserDetailsService 需要override一个名为loadUserByUsername的方法
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(this.passwordEncoder());
    }


    /**
     * 加密算法 BCrypt
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 跨域访问配置
     *
     * @return
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("application/json", "text/plain", "content-type", "dnt"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
