package org.arch.framework.automate.generater.service.xmind.module;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.arch.framework.automate.generater.service.xmind.api.Entity;
import org.arch.framework.automate.generater.service.xmind.api.Interfac;
import org.arch.framework.automate.generater.service.xmind.database.Database;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static org.springframework.util.StringUtils.hasText;

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
    public static final String DEFAULT_API_PKG = "api";
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
    private final List<Interfac> interfaces = new ArrayList<>();
    private final Set<Entity> entities = new HashSet<>();
    /**
     * 缓存需要包后置处理 {@link Entity}
     * map(entityName, Entity): key 为 {@link Entity} name, value 为需要导入 import 的 {@link Entity}
     */
    private transient final Multimap<String, Entity> entityImports = HashMultimap.create();
    /**
     * 缓存需要包后置处理 {@link Interfac}
     * map(entityName, Interfac): key 为 {@link Entity} name, value 为需要导入 import 的 {@link Interfac}
     */
    private transient final Multimap<String, Interfac> apiImports = HashMultimap.create();
    /**
     * 缓存 Entity/Api 中用到的其他对象的包路径
     * map(entityName, pkg): key 为 {@link Entity} name, value 为 {@link Entity} 的包路径(全路径)
     */
    private transient final Map<String, String> otherImports = new HashMap<>();

    public boolean addDatabase(Database database) {
        return this.databases.add(database);
    }

    public boolean addDatabase(List<Database> databases) {
        return this.databases.addAll(databases);
    }

    public boolean addInterface(Interfac interfac) {
        return this.interfaces.add(interfac);
    }

    public boolean addInterface(List<Interfac> interfaces) {
        return this.interfaces.addAll(interfaces);
    }

    public boolean addEntity(Entity entity) {
        return this.entities.add(entity);
    }

    public boolean addEntity(List<Entity> entities) {
        return this.entities.addAll(entities);
    }


    /**
     * module 包后置处理, 需要在解析 xmind 完成后调用.
     *
     * @param modulePkg    module 包
     * @param otherImports 缓存 Entity/Api 中用到的其他对象的包路径, Map(对象名称[不包含包路径], 包路径),
     *                     待改进: 如果对象重名会覆盖原有对象的包路径.
     * @param isForce      是否复写原有的包设置, 当为 true 时, 会清除缓存的包后置处理信息, 再次调用此方法时不会更新相关的 import 包.
     */
    public void modulePkgPostHandle(@NonNull String modulePkg, @Nullable Map<String, String> otherImports,
                                    @NonNull Boolean isForce) {

        if (nonNull(otherImports)) {
            this.otherImports.putAll(otherImports);
        }

        final String oldPkg = this.pkg;

        // 设置 modulePkg
        if (isForce || !hasText(this.pkg)) {
            this.pkg = modulePkg;
        }
        final Set<Entity> entityList = this.getEntities();
        entityList.forEach(entity -> {
            if (isForce || !hasText(entity.getPkg())) {
                entity.setPkg(this.pkg + PKG_SEPARATOR + ofNullable(entity.getApi()).orElse("")
                                      + PKG_SEPARATOR + DEFAULT_ENTITY_PKG);
            }
        });
        this.getInterfaces().forEach(api -> {
            if (isForce || !hasText(api.getPkg())) {
                api.setPkg(this.pkg + PKG_SEPARATOR + ofNullable(api.getApi()).orElse("") + PKG_SEPARATOR + DEFAULT_API_PKG);
            }
        });

        // 从 entity/api 的 import 中清除旧的包
        if (isForce) {
            this.entities.forEach(entity -> {
                entity.getImports().removeIf(
                        impt -> hasText(impt) && impt.startsWith(oldPkg + PKG_SEPARATOR
                                                                         + ofNullable(entity.getApi()).orElse("")
                                                                         + PKG_SEPARATOR
                                                                         + DEFAULT_ENTITY_PKG));
            });
            this.interfaces.forEach(api -> {
                api.getImports().removeIf(
                        impt -> hasText(impt) && impt.startsWith(oldPkg + PKG_SEPARATOR
                                                                         + ofNullable(api.getApi()).orElse("")
                                                                         + PKG_SEPARATOR
                                                                         + DEFAULT_API_PKG));
            });
        }

        // 添加 entity import
        entityImports.forEach((entityName, pEntity) -> {
            final Set<String> imports = pEntity.getImports();
            boolean isAdd = false;
            for (Entity entity : entityList) {
                String name = entity.getName();
                if (entityName.equals(name)) {
                    imports.add(entity.getPkg() + PKG_SEPARATOR + ofNullable(entity.getApi()).orElse("")
                                        + PKG_SEPARATOR + DEFAULT_ENTITY_PKG + PKG_SEPARATOR + name);
                    isAdd = true;
                }
            }
            if (!isAdd) {
                this.otherImports.forEach((objName, objPkg) -> {
                    if (entityName.equals(objName)) {
                        imports.add(objPkg);
                    }
                });
            }
        });

        // 添加 api/interface import
        apiImports.forEach((entityName, pInterface) -> {
            final Set<String> imports = pInterface.getImports();
            boolean isAdd = false;
            for (Entity entity : entityList) {
                String name = entity.getName();
                if (entityName.equals(name)) {
                    imports.add(entity.getPkg() + PKG_SEPARATOR + entity.getApi()
                                        + PKG_SEPARATOR + DEFAULT_API_PKG + PKG_SEPARATOR  + name);
                    isAdd = true;
                }
            }
            if (!isAdd) {
                this.otherImports.forEach((objName, objPkg) -> {
                    if (entityName.equals(objName)) {
                        imports.add(objPkg);
                    }
                });
            }
        });

        // 清楚 imports 缓存
        if (isForce) {
            this.entityImports.clear();
            this.apiImports.clear();
        }
    }

    @SuppressWarnings("EqualsReplaceableByObjectsCall")
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Module module = (Module) o;

        if (!name.equals(module.name))
            return false;
        if (!typ.equals(module.typ))
            return false;
        if (comment != null ? !comment.equals(module.comment) : module.comment != null)
            return false;
        if (pkg != null ? !pkg.equals(module.pkg) : module.pkg != null)
            return false;
        if (!databases.equals(module.databases))
            return false;
        if (!interfaces.equals(module.interfaces))
            return false;
        return entities.equals(module.entities);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + typ.hashCode();
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (pkg != null ? pkg.hashCode() : 0);
        result = 31 * result + databases.hashCode();
        result = 31 * result + interfaces.hashCode();
        result = 31 * result + entities.hashCode();
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
                ", interfaces=" + interfaces +
                ", entities=" + entities +
                '}';
    }
}
