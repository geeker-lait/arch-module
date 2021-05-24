package org.arch.workflow.ums.resource;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.arch.workflow.common.annotation.NotAuth;
import org.arch.workflow.common.constant.CoreConstant;
import org.arch.workflow.common.model.ObjectMap;
import org.arch.workflow.common.resource.BaseResource;
import org.arch.workflow.common.utils.DateUtils;
import org.arch.workflow.ums.constant.ErrorConstant;
import org.arch.workflow.ums.constant.TableConstant;
import org.arch.workflow.ums.domain.Menu;
import org.arch.workflow.ums.domain.User;
import org.arch.workflow.ums.repository.MenuRepository;
import org.arch.workflow.ums.repository.UserRepository;
import org.arch.workflow.ums.response.ConvertFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 授权控制类
 *
 * @author lait.zhang@gmail.com
 * @date 2018年4月8日
 */
@RestController
public class AuthResource extends BaseResource {
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

    @Autowired
    public AuthResource(UserRepository userRepository, MenuRepository menuRepository) {
        this.userRepository = userRepository;
        this.menuRepository = menuRepository;
    }

    @PostMapping("/auths/login")
    @ResponseStatus(HttpStatus.OK)
    @NotAuth
    public ObjectMap login(@RequestBody ObjectMap loginRequest) {
        String account = loginRequest.getAsString("account");
        String password = loginRequest.getAsString("password");
        User user = userRepository.findByAccount(account);
        if (user == null) {
            exceptionFactory.throwObjectNotFound(ErrorConstant.USER_NOT_FOUND);
        }
        if (!user.getPwd().equals(password)) {
            exceptionFactory.throwConflict(ErrorConstant.USER_PWD_NOT_MATCH);
        }
        if (user.getStatus() == TableConstant.USER_STATUS_STOP) {
            exceptionFactory.throwForbidden(ErrorConstant.USER_ALREADY_STOP);
        }
        String token = Jwts.builder().setSubject(account).setId(user.getId().toString()).setIssuedAt(DateUtils.currentTimestamp())
                .setExpiration(new Date(DateUtils.currentTimeMillis() + CoreConstant.LOGIN_USER_EXPIRE_IN)).signWith(SignatureAlgorithm.HS256, CoreConstant.JWT_SECRET).compact();

        return ConvertFactory.convertUseAuth(user, token);
    }

    @GetMapping("/auths/menus")
    @ResponseStatus(HttpStatus.OK)
    public List<ObjectMap> getUserMenus(@RequestParam Integer userId) {
        List<Menu> childMenus = menuRepository.findByUserId(userId);
        List<Menu> parentMenus = menuRepository.findByTypeAndStatusOrderByOrderAsc(TableConstant.MENU_TYPE_PARENT, TableConstant.MENU_STATUS_NORMAL);
        return ConvertFactory.convertUserMenus(parentMenus, childMenus);
    }

    @PutMapping("/auths/password/change")
    @ResponseStatus(HttpStatus.OK)
    public User changePwd(@RequestBody ObjectMap changeRequest) {
        String newPassword = changeRequest.getAsString("newPassword");
        String confirmPassword = changeRequest.getAsString("confirmPassword");
        String oldPassword = changeRequest.getAsString("oldPassword");
        Integer userId = changeRequest.getAsInteger("userId");
        if (!newPassword.equals(confirmPassword)) {
            exceptionFactory.throwConflict(ErrorConstant.USER_PASSWORD_CONFIRM_ERROR);
        }

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            exceptionFactory.throwObjectNotFound(ErrorConstant.USER_NOT_FOUND);
        }

        if (!user.getPwd().equals(oldPassword)) {
            exceptionFactory.throwConflict(ErrorConstant.USER_OLD_PASSWORD_WRONG);
        }

        user.setPwd(newPassword);

        return userRepository.save(user);
    }

}
