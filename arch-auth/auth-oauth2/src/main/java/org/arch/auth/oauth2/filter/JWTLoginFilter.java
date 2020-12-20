//package com.yonghui.arch.auth.api.filter;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.*;
//
///**
// * 验证用户名密码正确后，生成一个token，并将token返回给客户端
// * 该类继承自UsernamePasswordAuthenticationFilter，重写了其中的2个方法
// * attemptAuthentication ：接收并解析用户凭证。
// * successfulAuthentication ：用户成功登录后，这个方法会被调用，我们在这个方法里生成token。
// */
//public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {
//
//    private AuthenticationManager authenticationManager;
//
//    public JWTLoginFilter(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//    }
//
//    // 接收并解析用户凭证
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
//        try {
//            User user = new ObjectMapper().readValue(req.getInputStream(), User.class);
//            return authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            user.getUsername(),
//                            user.getPassword(),
//                            new ArrayList<>())
//            );
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    // 用户成功登录后，这个方法会被调用，我们在这个方法里生成token
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request,
//                                            HttpServletResponse response,
//                                            FilterChain chain,
//                                            Authentication auth) throws IOException, ServletException {
//        // builder the token
//        String token = null;
//        try {
//            Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
//            // 定义存放角色集合的对象
//            List roleList = new ArrayList<>();
//            for (GrantedAuthority grantedAuthority : authorities) {
//                roleList.add(grantedAuthority.getAuthority());
//            }
//            // 设置过期时间
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(new Date());
//            calendar.add(Calendar.DAY_OF_MONTH, 30);
//            Date time = calendar.getTime();
//            token = Jwts.builder()
//                    .setSubject(auth.getName() + "-" + roleList)
//                    .setExpiration(time) // 设置过期时间30天
//                    .signWith(SignatureAlgorithm.HS512, ConstantKey.SIGNING_KEY) //采用什么算法是可以自己选择的，不一定非要采用HS512
//                    .compact();
//            // 登录成功后，返回token到header里面
//            response.addHeader("Authorization", "Bearer " + token);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//}
