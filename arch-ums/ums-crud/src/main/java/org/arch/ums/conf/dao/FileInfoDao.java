package org.arch.ums.conf.dao;

import org.arch.framework.crud.CrudServiceImpl;
import org.arch.ums.conf.entity.FileInfo;
import org.arch.ums.conf.mapper.FileInfoMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.arch.framework.crud.CrudDao;
import org.springframework.stereotype.Repository;

/**
 * 对象存储文件信息(FileInfo) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-02-27 16:41:06
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class FileInfoDao extends CrudServiceImpl<FileInfoMapper, FileInfo> implements CrudDao<FileInfo> {
    private final FileInfoMapper fileInfoMapper;
}
