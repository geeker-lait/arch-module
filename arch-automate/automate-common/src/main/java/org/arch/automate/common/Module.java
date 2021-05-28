package org.arch.automate.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.arch.automate.common.api.Api;
import org.arch.automate.common.api.Model;
import org.arch.automate.common.database.Database;

import java.util.HashSet;
import java.util.Objects;
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
    private final Set<Database> databases = new HashSet<>();
    private final Set<Api> apis = new HashSet<>();
    private final Set<Model> models = new HashSet<>();

    public void addDatabase(Database database) {
        this.databases.add(database);
    }

    public void addApi(Api api) {
        this.apis.add(api);
    }

    public void addModel(Model model) {
        this.models.add(model);
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
        return name.equals(module.name) && Objects.equals(typ, module.typ) && Objects.equals(comment, module.comment) && Objects.equals(pkg, module.pkg) && databases.equals(module.databases) && apis.equals(module.apis) && models.equals(module.models);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, typ, comment, pkg, databases, apis, models);
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
                '}';
    }
}
