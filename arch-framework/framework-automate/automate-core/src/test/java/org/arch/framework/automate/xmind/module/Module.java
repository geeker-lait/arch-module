package org.arch.framework.automate.xmind.module;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.arch.framework.automate.xmind.api.Interfac;
import org.arch.framework.automate.xmind.table.Database;

import java.util.ArrayList;
import java.util.List;

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
    @Setter
    private String name;
    @Setter
    private String typ;
    @Setter
    private String comment;
    private final List<Database> databases = new ArrayList<>();
    private final List<Interfac> interfaces = new ArrayList<>();

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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Module module = (Module) o;

        if (!getName().equals(module.getName()))
            return false;
        if (!getTyp().equals(module.getTyp()))
            return false;
        if (!getComment().equals(module.getComment()))
            return false;
        if (!getDatabases().equals(module.getDatabases()))
            return false;
        return getInterfaces().equals(module.getInterfaces());
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getTyp().hashCode();
        result = 31 * result + getComment().hashCode();
        result = 31 * result + getDatabases().hashCode();
        result = 31 * result + getInterfaces().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Module{" +
                "name='" + name + '\'' +
                ", typ='" + typ + '\'' +
                ", comment='" + comment + '\'' +
                ", databases=" + databases +
                ", interfaces=" + interfaces +
                '}';
    }
}
