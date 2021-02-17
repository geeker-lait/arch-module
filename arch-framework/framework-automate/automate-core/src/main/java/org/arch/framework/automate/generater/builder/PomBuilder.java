package org.arch.framework.automate.generater.builder;

import com.unichain.framework.code.api.Buildable;
import com.unichain.framework.code.database.model.Table;
import com.unichain.framework.code.model.properties.PomModel;
import com.unichain.framework.code.model.properties.PropertiesModel;
import org.springframework.stereotype.Component;

@Component("pomBuilder")
public class PomBuilder implements Buildable<PomModel> {

    @Override
    public PomModel buildData(PropertiesModel propertiesModel, Table table) {
        PomModel pomModel = new PomModel();
        return pomModel;
    }


    @Override
    public void buildFile(PomModel pomModel, PomModel pomModel1) {
        buildPom(pomModel,pomModel.getPath());
    }

    @Override
    public String getTemplate() {
        return "templates/pom/pom.ftl";
    }
}
