//package org.arch.auth.oauth2.properties;
//
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.properties.annotation.web.builders.HttpSecurity;
//import org.springframework.security.properties.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.properties.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * SpringSecurity配置
// */
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements InitializingBean {
//
//    // 启动时获取数据库的授权配置
//    private static Map<String,String> accredit = new HashMap<>();
//
////    @Autowired
////    private AccreditDao accreditDao;
//
//    /**
//     * 结合Thymeleaf修改Spring Security OAuth2默认的登录页面和授权页面
//     * 修改登录页面
//     * 在授权服务器中的web安全配置里，继承WebSecurityConfigurerAdapter的类重写configure(HttpSecurity http)的方法下修改
//     *
//     * 修改授权页面
//     * 在授权服务器中新建一个AuthController
//     * @Controller
//     * @SessionAttributes("authorizationRequest")//必须配置
//     * public class AuthController {
//     *
//     *     @RequestMapping("/oauth/confirm_access")//路径必须是这个
//     *     public String toAuthPage(){
//     *         return "oauth";
//     *     }
//     * }
//     * 新建一个oauth.html页面，作为默认的授权页面
//     * 结合Thymeleaf
//     * 1.引入依赖
//     *  <dependency>
//     *      <groupId>org.springframework.boot</groupId>
//     *      <artifactId>spring-boot-starter-thymeleaf</artifactId>
//     *  </dependency>
//     * spring:
//     *   application:
//     *     name: oauth2-auth
//     *   resources:
//     *     static-locations: classpath:/public
//     *   thymeleaf:
//     *     enabled: true
//     *     cache: false
//     *     mode: html
//     *     encoding: utf-8
//     *     prefix: classpath:/templates/
//     *     suffix: .html
//     * 2.配置完成之后，默认的授权页面返回视图时拼接成/templates/oauth.html
//     * @param http
//     * @throws Exception
//     */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
////        http.formLogin()//
////                .loginPage("/login.html")//自定义的登录页面
////                .loginProcessingUrl("/authentication/form")//登录页面表单的提交路径
////                .and()
////                .authorizeRequests()
////                .antMatchers("/login.html").permitAll()//放行
////                .anyRequest()
////                .authenticated()
////                .and()
////                .csrf().disable();
//
//        http.authorizeRequests()
//                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
//                .antMatchers("/rsa/publicKey").permitAll()
//                .anyRequest().authenticated();
//    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//
////        return new PasswordEncoder() {
////
////            @Override
////
////            public String encode(CharSequence charSequence) {
////
////                return charSequence.toString();
////
////            }
////
////            @Override
////
////            public boolean matches(CharSequence charSequence, String s) {
////
////                return Objects.equals(charSequence.toString(),s);
////
////            }
////
////        };
//    }
//
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        accredit.put("","");
//
//
//    }
//}
