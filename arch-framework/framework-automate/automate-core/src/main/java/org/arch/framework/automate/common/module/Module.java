package org.arch.framework.automate.common.module;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.arch.framework.automate.common.api.Api;
import org.arch.framework.automate.common.api.Interfac;
import org.arch.framework.automate.common.api.Model;
import org.arch.framework.automate.common.database.Database;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 4/26/2021 10:55 AM
 */
@Getter
@NoArgsConstructor
@Accessors(chain = true)
public class Module {

    public static final String DEFAULT_ENTITY_PKG = "entity";
    public static final String PKG_SEPARATOR = ".";

    @Setter
    private String name;
    @Setter
    private String typ;
    @Setter
    private String comment;
    @Setter
    private String pkg;
    private final List<Database> databases = new ArrayList<>();
    private final List<Api> apis = new ArrayList<>();
    private final Set<Model> models = new HashSet<>();
    /**
     * 缓存需要包后置处理 {@link Model}
     * map(entityName, Model): key 为 {@link Model} name, value 为需要导入 import 的 {@link Model}
     */
    private transient final Multimap<String, Model> entityImports = HashMultimap.create();
    /**
     * 缓存需要包后置处理 {@link Interfac}
     * map(entityName, Interfac): key 为 {@link Model} name, value 为需要导入 import 的 {@link Interfac}
     */
    private transient final Multimap<String, Interfac> apiImports = HashMultimap.create();

    public void addDatabase(Database database) {
        this.databases.add(database);
    }

    public void addApi(Api api) {
        this.apis.add(api);
    }

    public void addEntity(Model model) {
        this.models.add(model);
    }

    public void addEntity(List<Model> entities) {
        this.models.addAll(entities);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Module module = (Module) o;
        if (!name.equals(module.name)) {
            return false;
        }
        if (!typ.equals(module.typ)) {
            return false;
        }
        if (!comment.equals(module.comment)) {
            return false;
        }
        if (!pkg.equals(module.pkg)) {
            return false;
        }
        if (!databases.equals(module.databases)) {
            return false;
        }
        if (!apis.equals(module.apis)) {
            return false;
        }
        if (!models.equals(module.models)) {
            return false;
        }
        if (!entityImports.equals(module.entityImports)) {
            return false;
        }
        return apiImports.equals(module.apiImports);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + typ.hashCode();
        result = 31 * result + comment.hashCode();
        result = 31 * result + pkg.hashCode();
        result = 31 * result + databases.hashCode();
        result = 31 * result + apis.hashCode();
        result = 31 * result + models.hashCode();
        result = 31 * result + entityImports.hashCode();
        result = 31 * result + apiImports.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Module{" +
                "name='" + name + '\'' +
                ", typ='" + typ + '\'' +
                ", comment='" + comment + '\'' +
                ", pkg='" + pkg + '\'' +
                ", databases=" + databases +
                ", apis=" + apis +
                ", models=" + models +
                ", entityImports=" + entityImports +
                ", apiImports=" + apiImports +
                '}';
    }
}
