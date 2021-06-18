package org.arch.framework.beans.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;

/**
 * @author lait.zhang@gmail.com
 * @description: 将一个层级类对象(领域驱动model对象)转换为平铺类对象(view)
 * @weixin PN15855012581
 * @date 12/31/2020 11:00 AM
 */
@UtilityClass
public class ReflectUtils {

    public static <T> T convert(Object src, Class<T> tClass) {
        Constructor<T> cons = null;
        try {
            cons = tClass.getDeclaredConstructor(null);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        cons.setAccessible(true);
        T result = null;
        try {
            result = cons.newInstance(null);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        convert(src, result);
        return result;
    }

    private static void getSrcALLFieldMap(Object obj, Map<String, Object> collectMap) {
        Class srcClazz = obj.getClass();
        Field[] srcFields = srcClazz.getDeclaredFields();
        Stream.of(srcFields).forEach(field -> {
            field.setAccessible(true);
            try {
                if (field.getType().toString().contains("aacoin.account") && !field.getType().isEnum()) {
                    Object subObj = field.get(obj);
                    if (subObj != null)
                        getSrcALLFieldMap(subObj, collectMap);
                } else {
                    collectMap.put(field.getName(), field.get(obj));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    private static void setTargetAllField(Object obj, Map<String, Object> srcMap) {
        Class srcClazz = obj.getClass();
        Field[] srcFields = srcClazz.getDeclaredFields();
        Stream.of(srcFields).forEach(field -> {
            field.setAccessible(true);
            try {
                if (field.getType().toString().contains("aacoin.trade.otc") && !field.getType().isEnum()) {
                    Object subObj = field.get(obj);
                    if (subObj == null) {
                        Constructor cons = field.getType().getDeclaredConstructor(null);
                        cons.setAccessible(true);
                        subObj = cons.newInstance(null);
                        field.set(obj, subObj);
                    }
                    setTargetAllField(subObj, srcMap);
                } else {
                    //collectMap.put(field,obj);
                    Object currentField = srcMap.get(field.getName());
                    if (currentField != null && field.getType() == currentField.getClass()) {
                        field.set(obj, currentField);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }


    private static <T> T convert(Object src, T result) {
        Map<String, Object> srcMap = new HashMap();
        getSrcALLFieldMap(src, srcMap);

        setTargetAllField(result, srcMap);
        return result;
    }

    public static Optional<Field> getFieldByAnnotation(Class<?> clazz, Class<? extends Annotation> annotationType) {
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            Annotation[] annotations = field.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().equals(annotationType)) {
                    return Optional.of(field);
                }
            }
        }
        return Optional.empty();
    }

    /**
     * 获取指定包名下的所有类（可根据注解进行过滤）
     *
     * @param packageName     包名
     * @param annotationClass 注解类
     * @return 类
     */
    public static List<Class<?>> getClassListByAnnotation(String packageName,
                                                          Class<? extends Annotation> annotationClass) {
        List<Class<?>> classList = new ArrayList<>();
        try {
            Enumeration<URL> urls = Thread.currentThread().getContextClassLoader()
                    .getResources(packageName.replaceAll("\\.", "/"));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    String protocol = url.getProtocol();
                    if ("file".equals(protocol)) {
                        String packagePath = url.getPath();
                        addClassByAnnotation(classList, packagePath, packageName, annotationClass);
                    } else if ("jar".equals(protocol)) {
                        JarURLConnection urlConnection = (JarURLConnection) url.openConnection();
                        JarFile jarFile = urlConnection.getJarFile();
                        Enumeration<JarEntry> jarEntries = jarFile.entries();
                        while (jarEntries.hasMoreElements()) {
                            JarEntry jarEntry = jarEntries.nextElement();
                            String jarEntryName = jarEntry.getName();
                            if (jarEntryName.endsWith(".class")) {
                                String className = jarEntryName.substring(0, jarEntryName.lastIndexOf("."))
                                        .replaceAll("/", ".");
                                Class<?> cls = Class.forName(className);
                                if (cls.isAnnotationPresent(annotationClass)) {
                                    classList.add(cls);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format("get class from package %s by annotation %s error", packageName,
                    annotationClass.getSimpleName()), e);
        }
        return classList;
    }

    private static String getClassName(String packageName, String fileName) {
        String className = fileName.substring(0, fileName.lastIndexOf("."));
        if (!org.apache.commons.lang3.StringUtils.isEmpty(packageName)) {
            className = packageName + "." + className;
        }
        return className;
    }

    private static String getSubPackagePath(String packagePath, String filePath) {
        String subPackagePath = filePath;
        if (!org.apache.commons.lang3.StringUtils.isEmpty(packagePath)) {
            subPackagePath = packagePath + "/" + subPackagePath;
        }
        return subPackagePath;
    }

    private static String getSubPackageName(String packageName, String filePath) {
        String subPackageName = filePath;
        if (!StringUtils.isEmpty(packageName)) {
            subPackageName = packageName + "." + subPackageName;
        }
        return subPackageName;
    }

    private static void addClassByAnnotation(List<Class<?>> classList, String packagePath, String packageName,
                                             Class<? extends Annotation> annotationClass) {
        try {
            File[] files = getClassFiles(packagePath);
            if (files != null) {
                for (File file : files) {
                    String fileName = file.getName();
                    if (file.isFile()) {
                        String className = getClassName(packageName, fileName);
                        Class<?> cls = Class.forName(className);
                        if (cls.isAnnotationPresent(annotationClass)) {
                            classList.add(cls);
                        }
                    } else {
                        String subPackagePath = getSubPackagePath(packagePath, fileName);
                        String subPackageName = getSubPackageName(packageName, fileName);
                        addClassByAnnotation(classList, subPackagePath, subPackageName, annotationClass);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("add class by annotation error", e);
        }
    }

    private static File[] getClassFiles(String packagePath) {
        return new File(packagePath)
                .listFiles(file -> (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory());
    }

    public static boolean hasAnnotation(Field field, Class<? extends Annotation> annotationType) {
        Annotation maybeExist = field.getAnnotation(annotationType);
        return maybeExist != null;
    }
}
