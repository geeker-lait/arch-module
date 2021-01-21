package org.arch.framework.crud;

import org.arch.framework.ums.bean.TokenInfo;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Author lait.zhang@gmail.com
 * @Tel 15801818092
 * @Date 11/21/2019
 * @Description ${Description}
 */
public interface CrudController<T extends CrudService> {


//    CrudService getCrudService();
//
//    default E resolver(TokenInfo token, E e) {
//        return e;
//    }
//
//    @PostMapping
//    default Response save(@Valid @RequestBody E e, TokenInfo token) {
//        resolver(token, e);
//        getCrudService().save(e);
//        return Response.ok(e);
//    }
//
//    @Transactional
//    @PostMapping("/saves")
//    default Response saveAll(@Valid @RequestBody List<E> es) {
//        getCrudService().saveAll(es);
//        return Response.ok(es);
//    }
//
//    @GetMapping(path = "/{id:.+}")
//    default Response findById(@PathVariable Long id) {
//        return Response.success(getCrudService().findById(id));
//    }
//
//    @GetMapping("single")
//    default Response findOne(E entity,TokenInfo token) {
//        try {
//            resolver(token, entity);
//            Object o = getCrudService().findOne(entity);
//            return Response.ok(o);
//        } catch (Exception e) {
//            if (e instanceof IncorrectResultSizeDataAccessException) {
//                return Response.error("查询到多个结果");
//            } else {
//                return Response.error(e.getMessage());
//            }
//        }
//    }
//
//    @GetMapping("find")
//    default Response find(E e,TokenInfo token) {
//        resolver(token, e);
//        return Response.ok(getCrudService().find(e));
//    }
//
//    @GetMapping("list")
//    default Response list(Token token) {
//        //resolver(token,null);
//        return Response.ok(getCrudService().findAll());
//    }
//
//    @GetMapping("page")
//    default Response page(QueryRequest<E> queryRequest,TokenInfo token) {
//        resolver(token, queryRequest.getParam());
//        return Response.ok(getCrudService().findBypage(queryRequest));
//    }
//
//
//    @DeleteMapping(path = "/{id:.+}")
//    default Response deleteById(@PathVariable Long id) {
//        getCrudService().deleteById(id);
//        return Response.ok(true);
//    }


}
