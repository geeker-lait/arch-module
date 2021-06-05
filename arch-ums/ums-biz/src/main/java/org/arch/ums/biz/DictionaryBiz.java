//package org.arch.ums.biz;
//
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import lombok.extern.slf4j.Slf4j;
//import org.arch.framework.beans.Response;
//import org.arch.framework.crud.CrudBiz;
//import org.arch.framework.ums.bean.TokenInfo;
//import org.arch.ums.conf.client.ConfDictionaryApi;
//import org.arch.ums.conf.controller.DictionaryRest;
//import org.arch.ums.conf.dto.DictionaryRequest;
//import org.arch.ums.conf.dto.DictionarySearchDto;
//import org.arch.ums.conf.entity.Dictionary;
//import org.arch.ums.conf.service.DictionaryService;
//
//import java.util.List;
//
//@Slf4j
//public class DictionaryBiz implements DictionaryRest, ConfDictionaryApi,
//        CrudBiz<DictionaryRequest, Dictionary, Long, DictionarySearchDto, DictionaryService>{
//
//
//    @Override
//    public DictionaryService getCrudService() {
//        // TODO
//        return null;
//    }
//
//    @Override
//    public DictionarySearchDto getSearchDto() {
//        // TODO
//        return null;
//    }
//
//    @Override
//    public Dictionary resolver(TokenInfo token, DictionaryRequest dictionaryRequest) {
//        // TODO
//        return null;
//    }
//
//    @Override
//    public Response<DictionarySearchDto> save(DictionaryRequest request) {
//        // TODO
//        return null;
//    }
//
//    @Override
//    public Response<List<DictionarySearchDto>> saveAll(List<DictionaryRequest> requestList) {
//        // TODO
//        return null;
//    }
//
//    @Override
//    public Response<DictionarySearchDto> findById(Long aLong) {
//        // TODO
//        return null;
//    }
//
//    @Override
//    public Response<DictionarySearchDto> findOne(DictionaryRequest request) {
//        // TODO
//        return null;
//    }
//
//    @Override
//    public Response<List<DictionarySearchDto>> find(DictionaryRequest request) {
//        // TODO
//        return null;
//    }
//
//    @Override
//    public Response<List<DictionarySearchDto>> list() {
//        // TODO
//        return null;
//    }
//
//    @Override
//    public Response<Page<DictionarySearchDto>> page(DictionaryRequest request, Integer pageNumber, Integer pageSize) {
//        // TODO
//        return null;
//    }
//
//    @Override
//    public Response<Boolean> deleteById(Long aLong) {
//        // TODO
//        return null;
//    }
//
//    @Override
//    public Response<Boolean> updateById(DictionaryRequest request) {
//        // TODO
//        return null;
//    }
//
//    @Override
//    public Response<List<DictionarySearchDto>> like(DictionaryRequest request) {
//        // TODO
//        return null;
//    }
//
//    @Override
//    public Response<DictionarySearchDto> findOne(DictionaryRequest request, TokenInfo token) {
//        // TODO
//        return null;
//    }
//
//    @Override
//    public Response<List<DictionarySearchDto>> find(DictionaryRequest request, TokenInfo token) {
//        // TODO
//        return null;
//    }
//
//    @Override
//    public Response<IPage<DictionarySearchDto>> page(DictionaryRequest request, Integer pageNumber, Integer pageSize, TokenInfo token) {
//        // TODO
//        return null;
//    }
//}
