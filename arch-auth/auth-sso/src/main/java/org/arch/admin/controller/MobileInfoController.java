package org.arch.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.ums.feign.conf.client.ConfMobileInfoFeignService;
import org.arch.ums.feign.conf.client.ConfMobileSegmentFeignService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 添加手机号段信息, 手机归属地信息
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.15 21:28
 */
@RestController
@RequestMapping("/sys")
@Slf4j
@RequiredArgsConstructor
public class MobileInfoController {

    private final ConfMobileInfoFeignService confMobileInfoFeignService;
    private final ConfMobileSegmentFeignService confMobileSegmentFeignService;

    /*@PostMapping("/add/mobileInfo")
    public Response<Boolean> addMobileInfo(@RequestParam("file") MultipartFile file, TokenInfo token){
        if (isNull(token)) {
            return Response.failed(AuthStatusCode.UNAUTHORIZED);
        }
        if (token.getAuthorities().contains(new SimpleGrantedAuthority(ROLE_PREFIX + Role.MEMBER.name()))) {
            try {
                BufferedReader bufferedReader =
                        new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
                String line;
                while (line = bufferedReader.readLine()) {

                }
            }
            catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        return Response.failed(AuthStatusCode.FORBIDDEN);
    }


    public void insertMobileInfoTest() throws SQLException, IOException {
        String insertSql = "insert into conf_mobile_info(`prefix`, `province`, `city`, `mno`, `virtual`) VALUES(?, ?, ?, ?, ?);";
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
        ) {
            List<MobileInfo> mobileInfoList = getMobileInfoList();

            connection.setAutoCommit(false);
            int count = 0;
            for (MobileInfo mobileInfo : mobileInfoList) {
                preparedStatement.setInt(1, Integer.parseInt(mobileInfo.getPrefix()));
                preparedStatement.setString(2, mobileInfo.getProvince());
                preparedStatement.setString(3, mobileInfo.getCity());
                preparedStatement.setString(4, mobileInfo.getMno());
                preparedStatement.setInt(5, mobileInfo.getVirtual());
                preparedStatement.addBatch();
                count++;
                if (count == 5000) {
                    count = 0;
                    preparedStatement.executeBatch();
                    connection.commit();
                    preparedStatement.clearParameters();
                }
            }
            if (count % 5000 != 0) {
                preparedStatement.executeBatch();
                connection.commit();
            }
            connection.setAutoCommit(true);
        }

    }

    public void insertMobileSegmentTest() throws SQLException, IOException {
        String insertSql = "insert into conf_mobile_segment(`prefix`, `mno`, `virtual`) VALUES(?, ?, ?);";
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
        ) {
            connection.setAutoCommit(false);
            List<SegmentInfo> segmentInfoList = getSegmentInfo();

            int count = 0;
            for (SegmentInfo segmentInfo : segmentInfoList) {
                preparedStatement.setInt(1, Integer.parseInt(segmentInfo.getPrefix()));
                preparedStatement.setString(2, segmentInfo.getMno());
                preparedStatement.setInt(3, segmentInfo.getVirtual());
                preparedStatement.addBatch();
                count++;
                if (count == 5000) {
                    count = 0;
                    preparedStatement.executeUpdate();
                    preparedStatement.clearParameters();
                }
            }
            if (count % 5000 != 0) {
                preparedStatement.executeBatch();
            }
            connection.commit();
            connection.setAutoCommit(true);
        }

    }

    private static List<SegmentInfo> getSegmentInfo() throws IOException {
        Path segmentPath = Paths.get("D:/资料/mobile-segment.txt");

        return Files.lines(segmentPath)
                    .map(line -> line.split("\t"))
                    .map(arr -> new SegmentInfo(arr[0], arr[1], Integer.valueOf(arr[2])))
                    .collect(Collectors.toList());
    }

    private static List<MobileInfo> getMobileInfoList() throws IOException {
        Path segmentPath = Paths.get("D:/资料/mobile-segment.txt");
        Path infoPath = Paths.get("D:/资料/mobile-info.txt");

        List<SegmentInfo> segmentInfoList = getSegmentInfo();
        Map<String, SegmentInfo> segmentMap =
                segmentInfoList.stream()
                               .collect(Collectors.toMap(SegmentInfo::getPrefix, segmentInfo -> segmentInfo));

        return Files.lines(infoPath, StandardCharsets.UTF_8)
                    .map(line -> line.split("\t"))
                    .map(arr -> {
                        MobileInfo mobileInfo = new MobileInfo();
                        String prefix = arr[0].trim();
                        mobileInfo.setPrefix(prefix);
                        if (hasText(arr[1])) {
                            String[] splits = arr[1].split("-");
                            mobileInfo.setProvince(splits[0]);
                            mobileInfo.setCity(splits[1]);
                        }
                        else {
                            System.out.println("arr = " + Arrays.toString(arr));
                        }

                        String segmentPrefix3 = mobileInfo.getPrefix().substring(0, 3);
                        String segmentPrefix4 = prefix.substring(0, 4);
                        SegmentInfo segmentInfo = segmentMap.getOrDefault(segmentPrefix4, null);
                        if (isNull(segmentInfo)) {
                            segmentInfo = segmentMap.getOrDefault(segmentPrefix3, null);
                        }
                        if (nonNull(segmentInfo)) {
                            mobileInfo.setMno(segmentInfo.getMno());
                            mobileInfo.setVirtual(segmentInfo.getVirtual());
                        }
                        else {
                            System.out.println("arr = " + Arrays.toString(arr));
                        }
                        return mobileInfo;
                    })
                    .collect(Collectors.toList());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class SegmentInfo {
        private String prefix;
        private String mno;
        private Integer virtual;

        @Override
        public String toString() {
            return super.toString();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class MobileInfo {
        private String prefix;
        private String province;
        private String city;
        private String mno;
        private Integer virtual;

        @Override
        public String toString() {
            return super.toString();
        }
    }*/
}
