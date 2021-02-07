package org.arch.framework.automate.from.directive.request;

import lombok.Data;
import org.arch.framework.automate.from.directive.DirectiveRequest;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/5/2021 6:29 PM
 */
@Data
public class CreateDatabaseRequest implements DirectiveRequest {
    private String dbName;

}
