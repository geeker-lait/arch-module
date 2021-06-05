package org.arch.framework.crud;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.arch.framework.ums.bean.TokenInfo;
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
 * 面向内部的 rest 接口
 * @param <R>   实体的 request 封装类型
 * @param <ID>  ID 类型
 * @param <DTO> 返回的 DTO 类型
 */
public interface CrudRest<R, ID extends Serializable, DTO> {

    /**
     * 保存
     * @param r     实体的 request 封装类型
     * @param token token info
     * @return  DTO
     */
    @PostMapping
    DTO save(@Valid @RequestBody R r, TokenInfo token);

    /**
     * 批量保存
     * @param requestList 实体的 request 封装类型列表
     * @return  DTO 列表
     */
    @PostMapping("/saves")
    List<DTO> saveAll(@Valid @RequestBody List<R> requestList);

    /**
     * 根据 id 查询对象
     * @param id    id
     * @return  DTO
     */
    @GetMapping(path = "/{id}")
    DTO findById(@PathVariable("id") ID id);

    /**
     * 根据 entity 条件查询对象.
     * @param request   实体的 request 封装类型
     * @param token     token info
     * @return  DTO
     */
    @GetMapping("/single")
    DTO findOne(@Valid R request, TokenInfo token);

    /**
     * 根据 entity 条件查询对象列表.
     * @param request   实体的 request 封装类型
     * @param token     token info
     * @return  DTO
     */
    @GetMapping("/find")
    List<DTO> find(@Valid R request, TokenInfo token);

    /**
     * 查询所有列表
     * @param token     token info
     * @return  DTO
     */
    @GetMapping("/list")
    List<DTO> list(TokenInfo token);

    /**
     * 分页查询.
     *
     * @param request    实体的 request 封装类型
     * @param pageNumber 第几页
     * @param pageSize   页大小
     * @param token      token info
     * @return {@link IPage}
     */
    @GetMapping(value = "/page/{pageNumber}/{pageSize}")
    IPage<DTO> page(@Valid R request,
                    @PathVariable(value = "pageNumber") Integer pageNumber,
                    @PathVariable(value = "pageSize") Integer pageSize, TokenInfo token);

    /**
     * 根据 id 删除
     * @param id    id
     * @return  DTO
     */
    @DeleteMapping(path = "/{id}")
    Boolean deleteById(@PathVariable("id") ID id);

    /**
     * 根据 id 更新实体, 对实体未进行校验, 直接更新 不为 null 的值.
     * @param request   实体的 request 封装类型
     * @param token     token info
     * @return  true 表示更新成功
     */
    @PutMapping
    Boolean updateById(@RequestBody @Valid R request, TokenInfo token);

    /**
     * 根据 entity 条件模糊查询对象; 模糊查询的条件拼接 {@code CONCAT("%", condition ,"%")},
     * 此方法会对不为 null 的 {@link String} 类型的字段都进行模糊查询.
     * @param request   实体的 request 封装类型
     * @param token     token info
     * @return  DTO 列表
     */
    @GetMapping("/like")
    List<DTO> like(@Valid R request, TokenInfo token);
}
