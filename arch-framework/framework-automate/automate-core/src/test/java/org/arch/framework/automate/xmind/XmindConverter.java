package org.arch.framework.automate.xmind;

import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.arch.framework.automate.common.utils.JdbcTypeUtils;
import org.arch.framework.automate.generater.service.xmind.meta.Attached;
import org.arch.framework.automate.generater.service.xmind.meta.Children;
import org.arch.framework.automate.generater.service.xmind.meta.JsonRootBean;
import org.arch.framework.automate.generater.service.xmind.meta.RootTopic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 4/26/2021 10:36 AM
 */
public class XmindConverter {

    ThreadLocal<Database> DATABASE = new ThreadLocal();


    public void doResolve(Children children){
        List<Attached> attached = children.getAttached();
        if(attached != null && attached.size()>0){
            attached.forEach(a->{
                if(attached != null && attached.size()>0){
                    doResolve(a.getChildren());
                } else {
                    String title = a.getTitle();
                    if(StringUtils.isNoneEmpty(title)){
                        int firstSplit = title.indexOf(File.separator);
                        int secondSplit = title.indexOf(File.separator,firstSplit);
                        String namespace = title.substring(0,firstSplit);
                        String name = title.substring(firstSplit,secondSplit);
                        String undlint_name = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
                        String descr = title.substring(secondSplit);
                    }
                }
            });
        }


    }

    public void resolve(JsonRootBean jsonRootBean) {
        Database database = DATABASE.get();
        if (null == database) {
            DATABASE.set(new Database());
        }
        String title = jsonRootBean.getTitle();

        Table tab = new Table();

        List<Column> cols = new ArrayList<>();

        RootTopic rootTopic = jsonRootBean.getRootTopic();
        if(null != rootTopic.getChildren()){

        }

        if(StringUtils.isNoneEmpty(title)){
            int firstSplit = title.indexOf(File.separator);
            int secondSplit = title.indexOf(File.separator,firstSplit);
            String namespace = title.substring(0,firstSplit);
            String name = title.substring(firstSplit,secondSplit);
            String undlint_name = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
            String descr = title.substring(secondSplit);

            if(NodeNamespace.LONG.getName().equalsIgnoreCase(namespace) ||
                    NodeNamespace.BOOLEAN.getName().equalsIgnoreCase(namespace) ||
                    NodeNamespace.INTEGER.getName().equalsIgnoreCase(namespace)||
                    NodeNamespace.STRING.getName().equalsIgnoreCase(namespace) ||
                    NodeNamespace.DATE.getName().equalsIgnoreCase(namespace)) {
                Column column = new Column();
                column.setComment(descr);
                column.setName(name);
                //JdbcTypeUtils.getFieldType(name.toUpperCase());
                column.setTyp(name.toUpperCase());
                cols.add(column);
                return;
            }



            if(NodeNamespace.MODULE.getName().equalsIgnoreCase(namespace)){
                database.setName(undlint_name);
                database.setTables(new ArrayList<>());
            } else if(NodeNamespace.ENTITY.getName().equalsIgnoreCase(namespace)){
                Table table = new Table();
                table.setTable(undlint_name);
                table.setComment(descr);
                database.getTables().add(table);
            }  else if(NodeNamespace.LONG.getName().equalsIgnoreCase(namespace) ||
                    NodeNamespace.BOOLEAN.getName().equalsIgnoreCase(namespace) ||
                    NodeNamespace.INTEGER.getName().equalsIgnoreCase(namespace)||
                    NodeNamespace.STRING.getName().equalsIgnoreCase(namespace) ||
                    NodeNamespace.DATE.getName().equalsIgnoreCase(namespace)) {
                Column column = new Column();
                column.setComment(descr);
                column.setName(name);
                //JdbcTypeUtils.getFieldType(name.toUpperCase());
                column.setTyp(name.toUpperCase());
                cols.add(column);
            }






        }
//        if (nodeModel.getNamespace().getName().equalsIgnoreCase(NodeNamespace.ENTITY.getName())) {
//
//        } else if (nodeModel.getNamespace().getName().equalsIgnoreCase(NodeNamespace.MODULE.getName())) {
//
//        }

    }
}
