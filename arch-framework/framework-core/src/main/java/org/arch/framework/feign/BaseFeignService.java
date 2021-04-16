package org.arch.framework.feign;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.arch.framework.beans.Response;
import org.arch.framework.crud.CrudController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * {@link CrudController} API 对应的通用 feign 客户端.
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.2.26 15:15
 */
public interface BaseFeignService<T extends Model<T>, ID extends Serializable> {

    /**
     * 保存
     * @param t     实体类
     * @return  {@link Response}
     */
    @PostMapping
    Response<T> save(@Valid @RequestBody T t);

    /**
     * 批量保存
     * @param entityList 实体类列表
     * @return  {@link Response}
     */
    @PostMapping("/saves")
    Response<List<T>> saveAll(@Valid @RequestBody List<T> entityList);

    /**
     * 根据 id 查询对象
     * @param id    id
     * @return  {@link Response}
     */
    @GetMapping(path = "/{id}")
    Response<T> findById(@PathVariable("id") ID id);

    /**
     * 根据 entity 条件查询对象
     * @param entity    实体类
     * @return  {@link Response}
     */
    @GetMapping("/single")
    Response<T> findOne(@RequestBody T entity);

    /**
     * 根据 entity 条件查询对象列表
     * @param t         实体类
     * @return  {@link Response}
     */
    @GetMapping("/find")
    Response<List<T>> find(@RequestBody T t);

    /**
     * 查询所有列表
     * @return  {@link Response}
     */
    @GetMapping("/list")
    Response<List<T>> list();

    /**
     * 分页查询
     * @param entity        实体类
     * @param pageNumber    第几页
     * @param pageSize      页大小
     * @return  {@link Response}
     */
    @GetMapping(value = "/page/{pageNumber}/{pageSize}")
    Response<Page<T>> page(@RequestBody T entity,
                           @PathVariable(value = "pageNumber") Integer pageNumber,
                           @PathVariable(value = "pageSize") Integer pageSize);

    /**
     * 根据 id 删除
     * @param id    id
     * @return  {@link Response}
     */
    @DeleteMapping(path = "/{id}")
    Response<Boolean> deleteById(@PathVariable("id") ID id);

    /**
     * 根据 id 更新实体
     * @param entity    实体
     * @return  true 表示更新成功
     */
    @PutMapping
    Response<Boolean> updateById(@RequestBody T entity);

    /**
     * 根据 entity 条件模糊查询对象; 模糊查询的条件拼接 {@code CONCAT("%", condition ,"%")},
     * 此方法会对不为 null 的 {@link String} 类型的字段都进行模糊查询.
     * @param entity    实体类
     * @return  {@link Response}
     */
    @GetMapping("/like")
    Response<List<T>> like(@RequestBody T entity);
}
