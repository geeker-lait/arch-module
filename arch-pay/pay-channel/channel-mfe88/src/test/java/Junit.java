//import java.sql.CallableStatement;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import oracle.jdbc.internal.OracleTypes;
//import start.StartApplication;
//@SpringBootTest(classes=StartApplication.class)
//@RunWith(SpringRunner.class)
//@WebAppConfiguration
////@ContextConfiguration(classes = {WebApp.class, WebMVCConfig.class, WebMVCSecurity.class, InitSecurity.class })
//public class Junit {
//	@Autowired
//	NamedParameterJdbcTemplate nameJdbcTemplate;
//	@Autowired
//	private JdbcTemplate jdbcTemplate;
//	@Value("${aesKey}")
//	private String aesKey;
////	@Test
//	public void test(){
//	Map<String, String> checkBinMap = new HashMap<>();
//	try {
//		checkBinMap = jdbcTemplate.execute("{call check_bank_bin(?,?,?,?,?,?,?,?)}", (CallableStatement cs) -> {
//			cs.setString(1, "");
//			cs.registerOutParameter(2, OracleTypes.VARCHAR); // 输出参数code
//			cs.registerOutParameter(3, OracleTypes.NUMBER); // 当有重复订单号时，这里返回重复订单号
//			cs.registerOutParameter(4, OracleTypes.NUMBER); // 输出参数msg
//			cs.registerOutParameter(5, OracleTypes.VARCHAR); // 输出参数msg
//			cs.registerOutParameter(6, OracleTypes.VARCHAR); // 输出参数msg
//			cs.registerOutParameter(7, OracleTypes.VARCHAR); // 输出参数msg
//			cs.registerOutParameter(8, OracleTypes.VARCHAR); // 输出参数msg
//			cs.execute();
//			Map<String, String> m = new HashMap<String, String>();
//			m.put("msg", cs.getString(2));
//			m.put("code", cs.getString(3));
//			m.put("card_length", cs.getString(4));
//			m.put("card_type", cs.getString(5));
//			m.put("bank_type", cs.getString(6));
//			System.out.println("---------------------"+m+cs.getString(2));
//			return m;
//		});
//	} catch (Exception e) {
//	}
//	System.out.println(checkBinMap);
//	}
//	
//	@Test
//	public   void testAddUser(){
//		String  a="";
//		System.out.println(aesKey);
////		a=Demo.AESEncode(aesKey,"");
//		System.out.println(a);
//		
//	}
//		
//}
