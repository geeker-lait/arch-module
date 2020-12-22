package org.arch.project.event.controller;

import org.arch.project.event.api.FulfilPhase;
import org.arch.project.event.api.FulfilRequest;
import org.arch.project.event.service.FulfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lait.zhang@gmail.com
 * @Tel 15801818092
 * @date 11/23/2020 10:14 AM
 * @description: 仓位作业相关操作
 */
@RestController
@RequestMapping("fulfil-outer")
public class StoreOuterWorkController extends CommonWorkController{
    @Autowired
    private FulfilService fulfilService;

    /**
     * 接单，预操作占位，如2分钟内没有完成揽件，再次释放该单
     * @param request
     * @return
     */
    @PostMapping("accept")
    public ResponseEntity accept(HttpServletRequest request) {

        FulfilRequest fulfilRequest = FulfilRequest.of(FulfilPhase.ACCEPT).init(request);
        fulfilService.fulfil(fulfilRequest);
        return ResponseEntity.ok(null);
    }

    /**
     * 揽件，确认接单的操作
     * @param request
     * @return
     */
    @PostMapping("collection")
    public ResponseEntity collection(HttpServletRequest request) {

        FulfilRequest fulfilRequest = FulfilRequest.of(FulfilPhase.COLLECTION).init(request);
        fulfilService.fulfil(fulfilRequest);
        return ResponseEntity.ok(null);
    }


    /**
     * 配送，对揽件之后进行下一步的操作
     * （如有中转操作则在此逻辑中记录，对于骑手直达，则根据业务情况）
     * @param request
     * @return
     */
    @PostMapping("distribute")
    public ResponseEntity distribute(HttpServletRequest request) {

        FulfilRequest fulfilRequest = FulfilRequest.of(FulfilPhase.DISTRIBUTE).init(request);
        fulfilService.fulfil(fulfilRequest);
        return ResponseEntity.ok(null);
    }


    /**
     * 派件/派发，对经过一系列配送段之后的派件通知和动作相关的逻辑
     * @param request
     * @return
     */
    @PostMapping("delivery")
    public ResponseEntity delivery(HttpServletRequest request) {

        FulfilRequest fulfilRequest = FulfilRequest.of(FulfilPhase.DELIVERY).init(request);
        fulfilService.fulfil(fulfilRequest);
        return ResponseEntity.ok(null);
    }

    /**
     *
     * @param request
     * @return
     */
    @PostMapping("...")
    public ResponseEntity other(HttpServletRequest request) {

        FulfilRequest fulfilRequest = FulfilRequest.of(FulfilPhase.PACK).init(request);
        fulfilService.fulfil(fulfilRequest);
        return ResponseEntity.ok(null);
    }
}
