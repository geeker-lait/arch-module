package org.arch.framework.crud;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.beanmapper.BeanMapper;
import io.beanmapper.config.BeanMapperBuilder;
import org.arch.framework.beans.Convertable;
import org.arch.framework.crud.utils.EnumConverter;
import org.arch.framework.crud.utils.RequestSearchUtils;
import org.arch.framework.crud.utils.SearchFilter;
import org.arch.framework.crud.utils.SearchFilter.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 基础业务模型，用于实现基础的业务功能<br>
 * 本身自带强大的查询参数构造
 *
 * @param <T>  数据库实体
 * @param <ID> 数据库实体的主键实体
 */
public abstract class CrudService<T, ID extends Serializable> implements Convertable {

    @Autowired
    protected CrudDao<T> crudDao;

    /**
     * 如果是采用spring boot的话，会自动注入，其他情况需要手动创建
     */
    @Autowired
    protected ObjectMapper objectMapper;

    /**
     * 属性复制工具
     */
    protected BeanMapper beanMapper = new BeanMapperBuilder().addConverter(new EnumConverter()).build();

    /**
     * 保存单个实体
     *
     * @param t 实体
     * @return 返回保存的实体
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean save(T t) {
        return crudDao.save(t);
    }

    /**
     * 保存多个实体
     *
     * @param tlist 实体
     * @return 返回保存的实体
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean saveList(List<T> tlist) {
        return crudDao.saveBatch(tlist);
    }

    /***
     * 删除实体
     *
     * @param id 主键id
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(ID id) {
        return crudDao.removeById(id);
    }

    /***
     * 删除实体
     *
     * @param t 需要删除的实体
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(T t) {
        return crudDao.remove(new QueryWrapper<>(t));
    }

    /***
     * 根据主键集合删除实体
     *
     * @param ids 主键集合
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteAllById(List<ID> ids) {
        return crudDao.removeByIds(ids);
    }


    /**
     * 删除所有实体
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteAll() {

    }

    /**
     * 根据 id 更新实体
     *
     * @param entity 实体
     * @return 是否更新成功
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean updateById(T entity) {
        return crudDao.updateById(entity);
    }

    /**
     * 根据 {@link UpdateWrapper} 更新实体
     *
     * @param updateWrapper {@link UpdateWrapper}
     * @return 是否更新成功
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean updateBySpec(Wrapper<T> updateWrapper) {
        return crudDao.update(updateWrapper);
    }

    /**
     * 统计实体总数
     *
     * @return 实体总数
     */
    @Transactional(readOnly = true)
    public long count() {
        List<SearchFilter> sfList = Arrays.asList(new SearchFilter("deleted", Operator.EQ, Boolean.FALSE));
        return this.countBySpec(RequestSearchUtils.buildWrapper(sfList));
    }

    /**
     * 根据某个字段查询数量<br>
     *
     * @param param    实体的搜索字段，字段必须在实体中存在
     * @param operator 搜索查询的方式
     * @param object   搜索查询的值
     * @return 根据条件查询到数量
     */
    @Transactional(readOnly = true)
    public long countByParam(String param, Operator operator, Object object) {
        List<SearchFilter> sfList = this.doDefaultSearchFilter(param, operator, object);
        return this.countBySpec(RequestSearchUtils.buildWrapper(sfList));
    }

    /**
     * 根据map条件获取查询符合条件的数量
     *
     * @param searchParams
     * @return
     */
    @Transactional(readOnly = true)
    public long countByMapParams(Map<String, Object> searchParams) {
        return this.countBySpec(RequestSearchUtils.buildWrapper(searchParams));
    }

    /**
     * 根据查询条件获取数量
     *
     * @param spec 构造的JPA/MP搜索条件
     * @return 查询的数量
     */
    @Transactional(readOnly = true)
    public long countBySpec(Wrapper<T> spec) {
        return crudDao.count(spec);
    }

    /**
     * 按照主键查询
     *
     * @param id 主键
     * @return 返回id对应的实体
     */
    @Transactional(readOnly = true)
    public T findById(ID id) {
        return crudDao.getById(id);
    }

    /**
     * 根据某个字段查询单个实体<br>
     *
     * @param param    实体的搜索字段，字段必须在实体中存在
     * @param operator 搜索查询的方式
     * @param object   搜索查询的值
     * @return 查询不到返回null
     */
    @Transactional(readOnly = true)
    public T findOneByParam(String param, Operator operator, Object object) {
        List<SearchFilter> sfList = this.doDefaultSearchFilter(param, operator, object);
        return this.findOneBySpec(RequestSearchUtils.buildWrapper(sfList));
    }

    /**
     * 查询所有实体，根据排序方式和字段排序<br>
     * searchParams的参数key必须包含如：EQ_name=xxx<br>
     * 否则无法正确解析构造动态的jpa/mp搜索条件
     *
     * @param searchParams 搜索参数
     * @return
     */
    @Transactional(readOnly = true)
    public T findOneByMapParams(Map<String, Object> searchParams) {
        return this.findOneBySpec(RequestSearchUtils.buildWrapper(searchParams));
    }

    /**
     * 根据查询条件获取，返回Optional
     *
     * @param spec 构造的JPA搜索条件
     * @return 返回实体的Optional信息
     */
    @Transactional(readOnly = true)
    public T findOneBySpec(Wrapper<T> spec) {
        return crudDao.getOne(spec);
    }

    /**
     * 查询所有实体
     *
     * @return 实体集合
     */
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return crudDao.list();
    }

    /**
     * 根据某个字段查询实体集合<br>
     * 该查询会过滤掉实体字段isActive=false的数据
     *
     * @param param    实体的搜索字段，字段必须在实体中存在
     * @param operator 搜索的方式
     * @param object   搜索的值
     * @return 实体集合
     */
    @Transactional(readOnly = true)
    public List<T> findAllByParam(String param, SearchFilter.Operator operator, Object object) {
        List<SearchFilter> sfList = this.doDefaultSearchFilter(param, operator, object);
        return this.findAllBySpec(RequestSearchUtils.buildWrapper(sfList));
    }

    /**
     * 查询所有实体，根据排序方式和字段排序<br>
     * searchParams的参数key必须包含如：EQ_name=xxx<br>
     * 否则无法正确解析构造动态的jpa搜索条件
     *
     * @param searchParams 搜索参数
     * @return 实体集合
     */
    @Transactional(readOnly = true)
    public List<T> findAllByMapParams(Map<String, Object> searchParams) {
        return this.findAllBySpec(RequestSearchUtils.buildWrapper(searchParams));
    }

    /**
     * 查询所有实体，根据排序方式和字段排序<br>
     * searchParams的参数key必须包含如：EQ_name=xxx<br>
     * 否则无法正确解析构造动态的jpa/mp搜索条件
     *
     * @param searchParams 搜索参数
     * @param direction    排序方式
     * @param sortField    排序字段
     * @return 实体集合
     */
    @Transactional(readOnly = true)
    public List<T> findAllBySort(Map<String, Object> searchParams, Direction direction, String... sortField) {
        QueryWrapper<T> queryWrapper = RequestSearchUtils.buildWrapper(searchParams);
        if (direction.isAscending()) {
            queryWrapper.orderByAsc(sortField);
        }
        if (direction.isDescending()) {
            queryWrapper.orderByDesc(sortField);
        }
        return crudDao.list(RequestSearchUtils.buildWrapper(searchParams));
    }

    /**
     * 根据查询条件获取所有
     *
     * @param spec 构造的JPA/MP搜索条件
     * @return 实体集合
     */
    @Transactional(readOnly = true)
    public List<T> findAllBySpec(Wrapper<T> spec) {
        return crudDao.list(spec);
    }

    /**
     * 获取分页，不排序
     *
     * @param searchParams 搜索参数
     * @param pageNumber   页码
     * @param pageSize     分页大小
     * @return 分页实体信息
     */
    //@Cacheable(cacheNames = "", key = "#root.targetClass.simpleName+':'+#root.methodName+':'+#key")
    @Transactional(readOnly = true)
    public IPage<T> findPage(Map<String, Object> searchParams, int pageNumber, int pageSize/*,String key*/) {
        pageNumber = pageNumber > 0 ? pageNumber - 1 : pageNumber;
        IPage<T> page = new Page<>(pageNumber, pageSize);
        return crudDao.page(page, RequestSearchUtils.buildWrapper(searchParams));
    }

    /**
     * 获取分页
     *
     * @param searchParams 搜索参数
     * @param pageNumber   页码
     * @param pageSize     分页大小
     * @param direction    排序方式
     * @param sortField    排序字段
     * @return 分页实体信息
     */
    @Transactional(readOnly = true)
    public IPage<T> findPageBySort(Map<String, Object> searchParams, int pageNumber, int pageSize, Direction direction, String... sortField) {

        QueryWrapper<T> queryWrapper = RequestSearchUtils.buildWrapper(searchParams);
        if (direction.isAscending()) {
            queryWrapper.orderByAsc(sortField);
        }
        if (direction.isDescending()) {
            queryWrapper.orderByDesc(sortField);
        }

        pageNumber = pageNumber > 0 ? pageNumber - 1 : pageNumber;
        IPage<T> page = new Page<>(pageNumber, pageSize);
        return crudDao.page(page, RequestSearchUtils.buildWrapper(searchParams));
    }

    /**
     * 获取集合的子列表，第二个参数是获取的大小，从0开始
     *
     * @param list  数据集合
     * @param limit 子列表大小
     * @return 实体集合
     */
    @Transactional(readOnly = true)
    public List<T> getLimit(List<T> list, int limit) {
        return list.size() > limit ? list.subList(0, limit) : list;
    }

    /**
     * 统一构造默认查询条件的<code>SearchFilter</code>集合
     *
     * @param param    实体的搜索字段，字段必须在实体中存在
     * @param operator 搜索查询的方式
     * @param object   搜索查询的值
     * @return 搜索参数集合
     */
    protected List<SearchFilter> doDefaultSearchFilter(String param, Operator operator, Object object) {
        return Arrays.asList(new SearchFilter(param, operator, object));
    }

    /**
     * 克隆对象属性值<br>
     * 来源和接收实体都不能为空
     *
     * @param source      属性来源实体
     * @param destination 属性接收实体
     */
    @Transactional(readOnly = true)
    public void mapper(Object source, Object destination) {
        beanMapper.map(source, destination);
    }

    /**
     * 克隆对象属性值<br>
     *
     * @param <E>
     * @param source 属性来源实体，不能为null
     * @param clz    属性实体的class类型
     */
    @Transactional(readOnly = true)
    public <E> E mapperByClass(Object source, Class<E> clz) {
        return beanMapper.map(source, clz);
    }

    /**
     * 克隆集合对象属性
     *
     * @param <D>
     * @param <E>
     * @param sourceList 属性来源实体集合
     * @param clz        属性接收实体的class
     */
    @Transactional(readOnly = true)
    public <D, E> List<E> mapperList(List<D> sourceList, Class<E> clz) {
        return beanMapper.map(sourceList, clz);
    }

    /**
     * 将一个实体转换成另一个实体<br>
     * 通常情况下是将map转成Java Bean
     *
     * @param <S>
     * @param source 来源实体
     * @param cls    目标转换的class
     * @return
     */
    public <S> S conver(Object source, Class<S> cls) {
        return objectMapper.convertValue(source, cls);
    }

}
