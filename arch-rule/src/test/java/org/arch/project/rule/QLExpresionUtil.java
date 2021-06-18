package org.arch.project.rule;

import com.ql.util.express.ExpressRunner;
import com.ql.util.express.IExpressContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author lait.zhang@gmail.com
 * @description:
 * @weixin PN15855012581
 * @date 6/18/2021 2:03 PM
 */
@Component
public class QLExpresionUtil implements ApplicationContextAware {
    private static ExpressRunner runner;
    private static boolean isInitialRunner = false;
    //spring上下文
    private ApplicationContext applicationContext;

    static {
        runner = new ExpressRunner();
    }

    /**
     * @param statement 执行语句
     * @param context   上下文
     * @throws Exception
     */
    public Object execute(String statement, Map<String, Object> context) throws Exception {
        initRunner(runner);
        IExpressContext expressContext = new QLExpresionContext(context, applicationContext);
        statement = initStatement(statement);
        return runner.execute(statement, expressContext, null, true, false);
    }

    /**
     * 在此处把一些中文符号替换成英文符号，使用更加友好
     *
     * @param statement
     * @return
     */
    private String initStatement(String statement) {
        return statement.replace("（", "(").replace("）", ")")
                .replace("；", ";").replace("，", ",")
                .replace("“", "\"").replace("”", "\"");
    }

    private void initRunner(ExpressRunner runner) {
        if (isInitialRunner == true) {
            return;
        }
        synchronized (runner) {
            if (isInitialRunner == true) {
                return;
            }
            try {
                //在此可以加入预定义函数
            } catch (Exception e) {
                throw new RuntimeException("初始化表达式失败", e);
            }
        }
        isInitialRunner = true;
    }

    public void setApplicationContext(ApplicationContext aContext) {
        applicationContext = aContext;
    }

}
