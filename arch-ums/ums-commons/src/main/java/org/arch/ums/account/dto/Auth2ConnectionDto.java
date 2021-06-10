package org.arch.ums.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.arch.ums.account.entity.Identifier;

import java.io.Serializable;

/**
 * 查询账号-标识(Identifier) 下的第三方绑定账号 dto
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.3 20:03
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Auth2ConnectionDto implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * {@link IdentifierRequest#getId()}
	 */
	private Long id;
	/**
	 * 账号ID/用户ID/会员ID/商户ID
	 */
	private Long aid;
	/**
	 * 账号标识
	 */
	private String identifier;

}