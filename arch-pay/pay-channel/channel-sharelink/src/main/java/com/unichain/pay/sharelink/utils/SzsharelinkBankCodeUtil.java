//package com.unichain.pay.sharelink.utils;
//
//import cn.hutool.json.JSONUtil;
//import com.unichain.pay.core.util.BankCardNoUtil;
//import com.unichain.pay.core.util.BankInfoUtil;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class SzsharelinkBankCodeUtil {
//
//    private static Map<String, String> code = new HashMap<>();
//    private static Map<String, String> bankCode = new HashMap<>();
//    static {
//        code.put("中国工商银行", "4000100005");
//        code.put("中国农业银行", "4000200006");
//        code.put("中国银行", "4000300007");
//        code.put("中国建设银行", "4000400008");
//        code.put("中国邮政储蓄银行", "4000500009");
//        code.put("交通银行", "4000600000");
//        code.put("中信银行", "4000700001");
//        code.put("中国光大银行", "4000800002");
//        code.put("华夏银行", "4000900003");
//        code.put("中国民生银行", "4001000005");
//        code.put("广东发展银行", "4001100006");
//        code.put("招商银行", "4001200007");
//        code.put("兴业银行", "4001300008");
//        code.put("上海浦东发展银行", "4001400009");
//        code.put("平安银行", "4001500000");
//        code.put("上海银行", "4001600001");
//        code.put("恒丰银行", "4001800003");
//        code.put("浙商银行", "4001900004");
//        code.put("北京银行","4001700002");
//
//
//        bankCode.put("SRCB","深圳农村商业银行");
//        bankCode.put("BGB","广西北部湾银行");
//        bankCode.put("SHRCB","上海农村商业银行");
//        bankCode.put("BJBANK","北京银行");
//        bankCode.put("WHCCB","威海市商业银行");
//        bankCode.put("BOZK","周口银行");
//        bankCode.put("KORLABANK","库尔勒市商业银行");
//        bankCode.put("SPABANK","平安银行");
//        bankCode.put("SDEB","顺德农商银行");
//        bankCode.put("HURCB","湖北省农村信用社");
//        bankCode.put("WRCB","无锡农村商业银行");
//        bankCode.put("BOCY","朝阳银行");
//        bankCode.put("CZBANK","浙商银行");
//        bankCode.put("HDBANK","邯郸银行");
//        bankCode.put("BOC","中国银行");
//        bankCode.put("BOD","东莞银行");
//        bankCode.put("CCB","中国建设银行");
//        bankCode.put("ZYCBANK","遵义市商业银行");
//        bankCode.put("SXCB","绍兴银行");
//        bankCode.put("GZRCU","贵州省农村信用社");
//        bankCode.put("ZJKCCB","张家口市商业银行");
//        bankCode.put("BOJZ","锦州银行");
//        bankCode.put("BOP","平顶山银行");
//        bankCode.put("HKB","汉口银行");
//        bankCode.put("SPDB","上海浦东发展银行");
//        bankCode.put("NXRCU","宁夏黄河农村商业银行");
//        bankCode.put("NYNB","广东南粤银行");
//        bankCode.put("GRCB","广州农商银行");
//        bankCode.put("BOSZ","苏州银行");
//        bankCode.put("HZCB","杭州银行");
//        bankCode.put("HSBK","衡水银行");
//        bankCode.put("HBC","湖北银行");
//        bankCode.put("JXBANK","嘉兴银行");
//        bankCode.put("HRXJB","华融湘江银行");
//        bankCode.put("BODD","丹东银行");
//        bankCode.put("AYCB","安阳银行");
//        bankCode.put("EGBANK","恒丰银行");
//        bankCode.put("CDB","国家开发银行");
//        bankCode.put("TCRCB","江苏太仓农村商业银行");
//        bankCode.put("NJCB","南京银行");
//        bankCode.put("ZZBANK","郑州银行");
//        bankCode.put("DYCB","德阳商业银行");
//        bankCode.put("YBCCB","宜宾市商业银行");
//        bankCode.put("SCRCU","四川省农村信用");
//        bankCode.put("KLB","昆仑银行");
//        bankCode.put("LSBANK","莱商银行");
//        bankCode.put("YDRCB","尧都农商行");
//        bankCode.put("CCQTGB","重庆三峡银行");
//        bankCode.put("FDB","富滇银行");
//        bankCode.put("JSRCU","江苏省农村信用联合社");
//        bankCode.put("JNBANK","济宁银行");
//        bankCode.put("CMB","招商银行");
//        bankCode.put("JINCHB","晋城银行JCBANK");
//        bankCode.put("FXCB","阜新银行");
//        bankCode.put("WHRCB","武汉农村商业银行");
//        bankCode.put("HBYCBANK","湖北银行宜昌分行");
//        bankCode.put("TZCB","台州银行");
//        bankCode.put("TACCB","泰安市商业银行");
//        bankCode.put("XCYH","许昌银行");
//        bankCode.put("CEB","中国光大银行");
//        bankCode.put("NXBANK","宁夏银行");
//        bankCode.put("HSBANK","徽商银行");
//        bankCode.put("JJBANK","九江银行");
//        bankCode.put("NHQS","农信银清算中心");
//        bankCode.put("MTBANK","浙江民泰商业银行");
//        bankCode.put("LANGFB","廊坊银行");
//        bankCode.put("ASCB","鞍山银行");
//        bankCode.put("KSRB","昆山农村商业银行");
//        bankCode.put("YXCCB","玉溪市商业银行");
//        bankCode.put("DLB","大连银行");
//        bankCode.put("DRCBCL","东莞农村商业银行");
//        bankCode.put("GCB","广州银行");
//        bankCode.put("NBBANK","宁波银行");
//        bankCode.put("BOYK","营口银行");
//        bankCode.put("SXRCCU","陕西信合");
//        bankCode.put("GLBANK","桂林银行");
//        bankCode.put("BOQH","青海银行");
//        bankCode.put("CDRCB","成都农商银行");
//        bankCode.put("QDCCB","青岛银行");
//        bankCode.put("HKBEA","东亚银行");
//        bankCode.put("HBHSBANK","湖北银行黄石分行");
//        bankCode.put("WZCB","温州银行");
//        bankCode.put("TRCB","天津农商银行");
//        bankCode.put("QLBANK","齐鲁银行");
//        bankCode.put("GDRCC","广东省农村信用社联合社");
//        bankCode.put("ZJTLCB","浙江泰隆商业银行");
//        bankCode.put("GZB","赣州银行");
//        bankCode.put("GYCB","贵阳市商业银行");
//        bankCode.put("CQBANK","重庆银行");
//        bankCode.put("DAQINGB","龙江银行");
//        bankCode.put("CGNB","南充市商业银行");
//        bankCode.put("SCCB","三门峡银行");
//        bankCode.put("CSRCB","常熟农村商业银行");
//        bankCode.put("SHBANK","上海银行");
//        bankCode.put("JLBANK","吉林银行");
//        bankCode.put("CZRCB","常州农村信用联社");
//        bankCode.put("BANKWF","潍坊银行");
//        bankCode.put("ZRCBANK","张家港农村商业银行");
//        bankCode.put("FJHXBC","福建海峡银行");
//        bankCode.put("ZJNX","浙江省农村信用社联合社");
//        bankCode.put("LZYH","兰州银行");
//        bankCode.put("JSB","晋商银行");
//        bankCode.put("BOHAIB","渤海银行");
//        bankCode.put("CZCB","浙江稠州商业银行");
//        bankCode.put("YQCCB","阳泉银行");
//        bankCode.put("SJBANK","盛京银行");
//        bankCode.put("XABANK","西安银行");
//        bankCode.put("BSB","包商银行");
//        bankCode.put("JSBANK","江苏银行");
//        bankCode.put("FSCB","抚顺银行");
//        bankCode.put("HNRCU","河南省农村信用");
//        bankCode.put("COMM","交通银行");
//        bankCode.put("XTB","邢台银行");
//        bankCode.put("CITIC","中信银行");
//        bankCode.put("HXBANK","华夏银行");
//        bankCode.put("HNRCC","湖南省农村信用社");
//        bankCode.put("DYCCB","东营市商业银行");
//        bankCode.put("ORBANK","鄂尔多斯银行");
//        bankCode.put("BJRCB","北京农村商业银行");
//        bankCode.put("XYBANK","信阳银行");
//        bankCode.put("ZGCCB","自贡市商业银行");
//        bankCode.put("CDCB","成都银行");
//        bankCode.put("HANABANK","韩亚银行");
//        bankCode.put("CMBC","中国民生银行");
//        bankCode.put("LYBANK","洛阳银行");
//        bankCode.put("GDB","广东发展银行");
//        bankCode.put("ZBCB","齐商银行");
//        bankCode.put("CBKF","开封市商业银行");
//        bankCode.put("H3CB","内蒙古银行");
//        bankCode.put("CIB","兴业银行");
//        bankCode.put("CRCBANK","重庆农村商业银行");
//        bankCode.put("SZSBK","石嘴山银行");
//        bankCode.put("DZBANK","德州银行");
//        bankCode.put("SRBANK","上饶银行");
//        bankCode.put("LSCCB","乐山市商业银行");
//        bankCode.put("JXRCU","江西省农村信用");
//        bankCode.put("ICBC","中国工商银行");
//        bankCode.put("JZBANK","晋中市商业银行");
//        bankCode.put("HZCCB","湖州市商业银行");
//        bankCode.put("NHB","南海农村信用联社");
//        bankCode.put("XXBANK","新乡银行");
//        bankCode.put("JRCB","江苏江阴农村商业银行");
//        bankCode.put("YNRCC","云南省农村信用社");
//        bankCode.put("ABC","中国农业银行");
//        bankCode.put("GXRCU","广西省农村信用");
//        bankCode.put("PSBC","中国邮政储蓄银行");
//        bankCode.put("BZMD","驻马店银行");
//        bankCode.put("ARCU","安徽省农村信用社");
//        bankCode.put("GSRCU","甘肃省农村信用");
//        bankCode.put("LYCB","辽阳市商业银行");
//        bankCode.put("JLRCU","吉林农信");
//        bankCode.put("URMQCCB","乌鲁木齐市商业银行");
//        bankCode.put("XLBANK","中山小榄村镇银行");
//        bankCode.put("CSCB","长沙银行");
//        bankCode.put("JHBANK","金华银行");
//        bankCode.put("BHB","河北银行");
//        bankCode.put("NBYZ","鄞州银行");
//        bankCode.put("LSBC","临商银行");
//        bankCode.put("BOCD","承德银行");
//        bankCode.put("SDRCU","山东农信");
//        bankCode.put("NCB","南昌银行");
//        bankCode.put("TCCB","天津银行");
//        bankCode.put("WJRCB","吴江农商银行");
//        bankCode.put("CBBQS","城市商业银行资金清算中心");
//        bankCode.put("HBRCU","河北省农村信用社");
//
//
//    }
//
//
//
//
//    public static String getByBankName(String bankName) {
//        return code.get(bankName);
//    }
//
//    public static String getByCard(String bankCard) {
//        String sbankCode = BankCardNoUtil.getBankCardNo(bankCard);
//        String bankName = bankCode.get(sbankCode);
//        return code.get(bankName);
//    }
//
//
//    public static void main(String[] args) {
//        //JSONUtil.parseObj()
//    }
//}
