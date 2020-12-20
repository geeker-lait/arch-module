package ${packageName};

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ${entity.packageName}.${entity.className};

/**
 * repository for ${entity.className}
 * ${comments}
 *
 * @author ${author}
 * @since ${date}.
 */
@RepositoryRestResource(path = "${entity.className?uncap_first}", itemResourceRel = "resource", collectionResourceRel = "resources")
public interface ${className} extends BaseRepository<${entity.className}, ${entity.id.className}> {


}