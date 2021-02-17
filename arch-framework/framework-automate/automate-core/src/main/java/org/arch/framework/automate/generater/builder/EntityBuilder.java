package org.arch.framework.automate.generater.builder;

import com.unichain.framework.code.api.Buildable;
import com.unichain.framework.code.database.DataType;
import com.unichain.framework.code.database.model.Table;
import com.unichain.framework.code.model.EntityModel;
import com.unichain.framework.code.model.FieldModel;
import com.unichain.framework.code.model.properties.PomModel;
import com.unichain.framework.code.model.properties.PropertiesModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("entityBuilder")
public class EntityBuilder implements Buildable<EntityModel> {


    @Override
    public EntityModel buildData(PropertiesModel propertiesModel, Table table) {
        EntityModel entityModel = new EntityModel();
        entityModel.setClassName(UPPER_CAMEL_CONVERT.convert(table.getName()));
        wrapperDefaultValue(propertiesModel,entityModel);
        List<FieldModel> fieldModels = new ArrayList<>();
        table.getColumns().forEach(column -> {
            String type = DataType.convertType(column.getTyp()).getSimpleName();
            fieldModels.add(new FieldModel(column.getComment(),LOWER_CAMEL_CONVERT.convert(column.getName()),type));
        });
        entityModel.setPkg( propertiesModel.getPkg());
        entityModel.setTableName(table.getName());
        entityModel.setFields(fieldModels);

        return entityModel;
    }

    @Override
    public void buildFile(PomModel pomModel, EntityModel entityModel) {
        genClassFile(pomModel,entityModel);
    }

    @Override
    public String getTemplate() {
        return "templates/java/entity.ftl";
    }
}
