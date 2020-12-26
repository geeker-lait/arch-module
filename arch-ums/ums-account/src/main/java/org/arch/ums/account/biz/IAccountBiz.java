package org.arch.ums.account.biz;

import org.arch.ums.account.dto.AuthAccountDto;
import org.arch.ums.account.dto.AuthRegDto;
import org.arch.ums.account.vo.JwtUserVo;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: 注册登陆业务
 *
 * @author kenzhao
 * @date 2020/3/28 16:05
 */
public interface IAccountBiz {

    /**
     * @Description: 注册
     * @author kenzhao
     * @date 2020/3/28 16:34
     * @version V1.0
     */
    JwtUserVo reg(AuthRegDto authRegDto) throws Exception;

    /**
     * @Description: 验证码登陆
     * @author kenzhao
     * @date 2020/3/28 16:34
     * @version V1.0
     */
    JwtUserVo verifiLogin(AuthRegDto authRegDto) throws Exception;

    /**
     * @Description: 登陆
     * @author kenzhao
     * @date 2020/3/28 16:34
     * @version V1.0
     */
    JwtUserVo login(AuthAccountDto authAccountDto) throws Exception;

    /**
     * @Description: 获取验证码
     * @author kenzhao
     * @date 2020/3/28 16:34
     * @version V1.0
     */
    JwtUserVo getAccountCode(AuthRegDto authRegDto, HttpServletRequest request);

    /**
     * @Description: 登出
     * @author kenzhao
     * @date 2020/3/28 16:34
     * @version V1.0
     */
    JwtUserVo logout(JwtUserVo jwtUserVo);
}