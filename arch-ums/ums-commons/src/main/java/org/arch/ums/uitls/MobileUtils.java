package org.arch.ums.uitls;

import org.arch.framework.beans.Response;
import org.arch.framework.beans.exception.constant.CommonStatusCode;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.conf.dto.MobileInfoRequest;
import org.arch.ums.conf.dto.MobileSegmentRequest;
import org.arch.ums.conf.dto.MobileSegmentSearchDto;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.util.StringUtils.hasText;

/**
 * 上传手机归属地与号段的工具集
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.17 15:34
 */
public class MobileUtils {

    /**
     * 根据 {@link Response} 响应相应的 结果
     * @param errorList             保存部分失败的错误列表
     * @param savesResponse         {@link Response}
     * @param savesFailed           保存失败相应的 {@link CommonStatusCode}
     * @param savesPartialFailed    保存部分失败相应的 {@link CommonStatusCode}
     * @return  {@link Response}
     */
    @NonNull
    public static Response<Boolean> getBooleanResponse(@NonNull List<String> errorList,
                                                 @NonNull Response<Boolean> savesResponse,
                                                 @NonNull CommonStatusCode savesFailed,
                                                 @NonNull CommonStatusCode savesPartialFailed) {
        Boolean successData = savesResponse.getSuccessData();
        if (isNull(successData) || !successData) {
            return Response.failed(savesFailed);
        }

        if (errorList.size() > 0) {
            return Response.failed(savesPartialFailed,
                                   "保存失败的信息: " + errorList.toString());
        }
        return Response.success(Boolean.TRUE);
    }

    /**
     * 分隔符参数校验, 与 token 是否为 null 的校验
     * @param delimiter 分隔符
     * @param token     {@link TokenInfo}
     * @return  当返回 null 时表示校验通过, 校验不通过时返回 {@link Response}.
     */
    @NonNull
    public static Boolean check(@NonNull String delimiter, @Nullable TokenInfo token) {
        if (isNull(token)) {
            return false;
        }
        return hasText(delimiter);
    }

    /**
     * 获取手机归属地信息列表
     *
     * @param delimiter      行格式分隔符
     * @param bufferedReader {@link BufferedReader} 手机归属地信息
     * @param segmentMap     手机号段信息
     * @param errorList      放置解析行格式时错误的列表
     * @return 返回手机归属地的信息列表
     */
    @NonNull
    public static List<MobileInfoRequest> getMobileInfo(@NonNull String delimiter, @NonNull BufferedReader bufferedReader,
                                                        @NonNull Map<Integer, MobileSegmentSearchDto> segmentMap,
                                                        @NonNull List<String> errorList) {

        // 解析手机归属地信息. 格式: 1999562	甘肃-兰州
        return bufferedReader.lines()
                             .map(line -> line.split(delimiter))
                             .map(arr -> {
                                 //noinspection AlibabaUndefineMagicConstant
                                 if (2 != arr.length) {
                                     errorList.add(Arrays.toString(arr));
                                     return null;
                                 }
                                 MobileInfoRequest mobileInfo = new MobileInfoRequest();
                                 String prefix = arr[0].trim();
                                 mobileInfo.setPrefix(Integer.valueOf(prefix));
                                 if (hasText(arr[1])) {
                                     String[] splits = arr[1].split("-");
                                     //noinspection AlibabaUndefineMagicConstant
                                     if (2 != splits.length) {
                                         errorList.add(Arrays.toString(arr));
                                         return null;
                                     }
                                     mobileInfo.setProvince(splits[0]);
                                     mobileInfo.setCity(splits[1]);
                                 }
                                 else {
                                     errorList.add(Arrays.toString(arr));
                                     return null;
                                 }

                                 Integer segmentPrefix3 = Integer.valueOf(prefix.substring(0, 3));
                                 Integer segmentPrefix4 = Integer.valueOf(prefix.substring(0, 4));
                                 MobileSegmentSearchDto segmentInfo = segmentMap.getOrDefault(segmentPrefix4,
                                                                                              null);
                                 if (isNull(segmentInfo)) {
                                     segmentInfo = segmentMap.getOrDefault(segmentPrefix3, null);
                                 }
                                 if (nonNull(segmentInfo)) {
                                     mobileInfo.setMno(segmentInfo.getMno());
                                     mobileInfo.setVirtual(segmentInfo.getVirtual());
                                 }
                                 else {
                                     errorList.add(Arrays.toString(arr));
                                     return null;
                                 }
                                 mobileInfo.setDeleted(Boolean.FALSE);
                                 return mobileInfo;
                             })
                             .filter(Objects::nonNull)
                             .collect(Collectors.toList());
    }

    /**
     * 获取手机号段信息列表
     *
     * @param delimiter      行格式分隔符
     * @param bufferedReader {@link BufferedReader} 手机号段信息
     * @param errorList      放置解析行格式时错误的列表
     * @return 返回手机号段的信息列表
     */
    @NonNull
    public static List<MobileSegmentRequest> getMobileSegment(@NonNull String delimiter,
                                                              @NonNull BufferedReader bufferedReader,
                                                              @NonNull List<String> errorList) {

        // 解析手机号段信息. 格式: 1345   CMCC	0
        return bufferedReader.lines()
                             .map(line -> line.split(delimiter))
                             .map(arr -> {
                                 //noinspection AlibabaUndefineMagicConstant
                                 if (3 != arr.length) {
                                     errorList.add(Arrays.toString(arr));
                                     return null;
                                 }
                                 MobileSegmentRequest mobileSegment = new MobileSegmentRequest();
                                 mobileSegment.setPrefix(Integer.valueOf(arr[0]));
                                 mobileSegment.setMno(arr[1]);
                                 mobileSegment.setVirtual(Integer.parseInt(arr[2]) == 0 ? Boolean.FALSE : Boolean.TRUE);
                                 mobileSegment.setDeleted(Boolean.FALSE);
                                 return mobileSegment;
                             })
                             .filter(Objects::nonNull)
                             .collect(Collectors.toList());

    }
}
