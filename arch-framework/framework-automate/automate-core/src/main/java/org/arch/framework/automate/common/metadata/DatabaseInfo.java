package org.arch.framework.automate.common.metadata;

import lombok.Data;

import java.util.List;

/**
 * @Author lait.zhang@gmail.com
 * @Tel 15801818092
 * @Date 6/27/2020
 * @Description
 * {
 *   "name": "mydatabase",
 *   "table": {
 *     "name": "user",
 *     "field_list": [
 *       {
 *         "name": "username",
 *         "type": "VARCHAR(30)",
 *         "not_null": true,
 *         "comment": "Login username"
 *       },
 *       {
 *         "name": "email",
 *         "type": "VARCHAR(64)",
 *         "not_null": true,
 *         "comment": "User email"
 *       },
 *       {
 *         "name": "password",
 *         "type": "VARCHAR(128)",
 *         "not_null": true,
 *         "comment": "User password"
 *       },
 *       {
 *         "name": "confirmed_at",
 *         "type": "DATETIME",
 *         "not_null": false,
 *         "comment": "User confirmation date"
 *       }
 *     ],
 *     "fk_list": [
 *       {
 *         "name": "inviter_id",
 *         "references": "user",
 *         "comment": "If user was invited by someone, contains the ID of that user account, NULL otherwise"
 *       }
 *     ],
 *     "unique_list": [
 *       ["username"],
 *       ["email"]
 *     ]
 *   }
 * }
 */
@Data
public class DatabaseInfo {

    /**
     * 模块名称->对应database
     */
    private String moduleName;

    /**
     * 实体->对应table
     */
    private List<EntityInfo> entityInfos;
}
