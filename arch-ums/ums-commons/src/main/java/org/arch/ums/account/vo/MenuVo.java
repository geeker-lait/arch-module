package org.arch.ums.account.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.arch.framework.beans.exception.BusinessException;
import org.arch.framework.beans.exception.constant.CommonStatusCode;
import org.springframework.lang.NonNull;
import top.dcenter.ums.security.common.utils.JsonUtil;

import java.io.Serializable;

import static java.util.Objects.isNull;

/**
 * 账号-菜单(Menu) 实体 vo
 *
 * @author YongWu zheng
 * @date 2021-03-17 21:25:27
 * @since 1.0.0
 */
@Getter
@Setter
@EqualsAndHashCode
@Accessors(chain = true)
public class MenuVo implements Comparable<MenuVo>, Serializable {
    private static final long serialVersionUID = 1L;

    public MenuVo() {
    }

    public MenuVo(@NonNull String json) {
        MenuVo menuVo = JsonUtil.json2Object(json, MenuVo.class);
        if (isNull(menuVo)) {
        	throw new BusinessException(CommonStatusCode.JSON_RESOLVE_FAILED);
        }
        this.id = menuVo.id;
        this.pid = menuVo.pid;
        this.menuCode = menuVo.menuCode;
        this.menuName = menuVo.menuName;
        this.menuVal = menuVo.menuVal;
        this.level = menuVo.level;
        this.sorted = menuVo.sorted;
        this.frame = menuVo.frame;
        this.icon = menuVo.icon;
        this.tenantId = menuVo.tenantId;
        this.appId = menuVo.appId;
    }

    /**
     * 账号-菜单ID
     */
    private Long id;

    /**
     * 父节点ID, 如果没有父节点则为: -1
     */
    private Long pid;

    /**
     * 英文码
     */
    private String menuCode;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单值
     */
    private String menuVal;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 排序
     */
    private Integer sorted;

    /**
     * 是否iframe: 1是, 0不是, 默认: 1
     */
    private Integer frame;

    /**
     * 图标
     */
    private String icon;

    /**
     * 租户 id
     */
    private Integer tenantId;

    /**
     * 应用 id
     */
    private Integer appId;

    /**
     * 店铺 id
     */
    private Integer storeId;

    @Override
    @JsonValue
    public String toString() {
        return JsonUtil.toJsonString(this);
    }

    @Override
    public int compareTo(MenuVo o) {
        int levelCp = this.level.compareTo(o.getLevel());
        if (levelCp != 0) {
            return levelCp;
        }
        return this.sorted.compareTo(o.getSorted());
    }
}
