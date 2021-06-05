package org.arch.ums.conf.mapper;

import org.arch.framework.crud.CrudMapper;
import org.apache.ibatis.annotations.Mapper;
import org.arch.ums.conf.entity.FileInfo;

/**
 * 对象存储文件信息(FileInfo) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-02-27 16:41:08
 * @since 1.0.0
 */
@Mapper
public interface FileInfoMapper extends CrudMapper<FileInfo> {

}
