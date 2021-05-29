//package org.arch.automate.common.utils;
//
//import java.lang.reflect.Field;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//public class TableColumnUtils {
//    private static TableColumnUtils tableColumnUtil = null;
//    private List<LinkedHashMap> list;
//    private List<Map> tableStruct;
//
//    private TableColumnUtils(List<Map> tableStruct, List<LinkedHashMap> data) {
//        this.tableStruct = tableStruct;
//        this.list = data;
//    }
//
//    public static TableColumnUtils getInstance(List<Map> tableStruct, List<LinkedHashMap> data) {
//        if (tableColumnUtil == null) {
//            synchronized (TableColumnUtils.class) {
//                if (tableColumnUtil == null) {
//                    tableColumnUtil = new TableColumnUtils(tableStruct, data);
//                }
//            }
//        }
//        return tableColumnUtil;
//    }
//
//    //将原表中的 index下标的 字段删除
//    public void delColumn(int index) {
//        Map remove = tableStruct.remove(index);
//        for (LinkedHashMap map : list) {
//            map.remove(remove.get("COLUMN_NAME"));
//        }
//    }
//
//    //添加varchar2字段
//    public void addVarchar2(int index, String columnName, int length, boolean nullable, String dataDefault, String comments) throws Exception {
//        if (index < 0 || index > tableStruct.size() - 1) {
//            throw new Exception("插入位置不能小于0,或大于所有字段数量");
//        }
//        if (columnName == null || columnName.equals("")) {
//            throw new Exception("列名不能为空");
//        }
//        if (length < 1) {
//            throw new Exception("长度必须大于 0");
//        }
//        Varchar2Field field = new Varchar2Field(columnName, length + "", nullable ? "Y" : "N", dataDefault, comments);
//        tableStruct.add(index, this.getMapFromBean(field));
//    }
//
//    public void addNumber(int index, String columnName, int zLength, int xLength, boolean nullable, String dataDefault, String comments) throws Exception {
//        if (index < 0 || index > tableStruct.size() - 1) {
//            throw new Exception("插入位置不能小于0,或大于所有字段数量");
//        }
//        if (columnName == null || columnName.equals("")) {
//            throw new Exception("列名不能为空");
//        }
//        if (zLength < 1 || xLength < 0) {
//            throw new Exception("长度不对");
//        }
//        NumberField field = new NumberField(columnName, zLength + "", xLength + "", nullable ? "Y" : "N", dataDefault, comments);
//        tableStruct.add(index, this.getMapFromBean(field));
//    }
//
//    public void addTimeStamp(int index, String columnName, boolean nullable, String dataDefault, String comments) throws Exception {
//        if (index < 0 || index > tableStruct.size() - 1) {
//            throw new Exception("插入位置不能小于0,或大于所有字段数量");
//        }
//        if (columnName == null || columnName.equals("")) {
//            throw new Exception("列名不能为空");
//        }
//        TimeStampField field = new TimeStampField(columnName, dataDefault, nullable ? "Y" : "N", comments);
//        tableStruct.add(index, this.getMapFromBean(field));
//    }
//
//
//    /**
//     * 实体转map
//     *
//     * @param obj
//     * @return
//     */
//    private Map<String, Object> getMapFromBean(Object obj) {
//        Map<String, Object> propertiesMap = new HashMap<>();
//        Field[] fields = obj.getClass().getDeclaredFields();
//        for (Field field : fields) {
//            // 对于每个属性，获取属性名
//            String varName = field.getName();
//            try {
//                // 获取原来的访问控制权限
//                boolean accessFlag = field.isAccessible();
//                // 修改访问控制权限
//                field.setAccessible(true);
//                // 获取在对象f中属性fields[i]对应的对象中的变量
//                Object o;
//                try {
//                    o = field.get(obj);
//                    propertiesMap.put(varName, o);
//                } catch (IllegalAccessException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                // 恢复访问控制权限
//                field.setAccessible(accessFlag);
//            } catch (IllegalArgumentException ex) {
//                ex.printStackTrace();
//            }
//        }
//        return propertiesMap;
//    }
//
//
//}
