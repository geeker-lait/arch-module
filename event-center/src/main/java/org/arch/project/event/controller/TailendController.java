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
 * @description: TODO
 */
@RestController
@RequestMapping("fulfil-tailer")
public class TailendController extends CommonWorkController{
    @Autowired
    private FulfilService fulfilService;

    /**
     * 收货
     * @param request
     * @return
     */
    @PostMapping("receive")
    public ResponseEntity receive(HttpServletRequest request) {

        FulfilRequest fulfilRequest = FulfilRequest.of(FulfilPhase.RECEIVE).init(request);
        fulfilService.fulfil(fulfilRequest);
        return ResponseEntity.ok(null);
    }

    /**
     * 退货
     * @param request
     * @return
     */
    @PostMapping("refund")
    public ResponseEntity refund(HttpServletRequest request) {

        FulfilRequest fulfilRequest = FulfilRequest.of(FulfilPhase.REFUND).init(request);
        fulfilService.fulfil(fulfilRequest);
        return ResponseEntity.ok(null);
    }


    /**
     * 其他
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
