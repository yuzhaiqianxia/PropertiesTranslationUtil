import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Demo1 {

    /**
     * 5. 方式五
     * 从文件中获取,使用InputStream字节,主要是需要加上当前配置文件所在的项目src目录地址。路径配置需要精确到绝对地址级别
     * BufferedInputStream继承自InputStream
     * InputStream inputStream = new BufferedInputStream(new FileInputStream(name)
     * 这种方法读取需要完整的路径，优点是可以读取任意路径下的文件，缺点是不太灵活
     * @throws IOException
     */
    public static void test5() throws IOException {
        InputStream inputStream = new BufferedInputStream(new FileInputStream("C:\\develop\\git-ekp-v16\\ekp_v16\\src\\com\\landray\\kmss\\km\\imeeting\\ApplicationResources.properties"));
        Properties properties = new Properties();
        properties.load(inputStream);
        properties.list(System.out);
        System.out.println("==============================================");
        String property = properties.getProperty("minio.endpoint");
        System.out.println("property = " + property);
    }

    /**
     * 7. 方式七
     * 使用InputStream流来进行操作ResourceBundle，获取流的方式由以上几种。
     * ResourceBundle resourceBundle = new PropertyResourceBundle(inputStream);
     * @throws IOException
     */
//    public static void test7() throws IOException {
//        InputStream inputStream = ClassLoader.getSystemResourceAsStream("C:\\develop\\git-ekp-v16\\ekp_v16\\src\\com\\landray\\kmss\\km\\imeeting\\ApplicationResources.properties");
//        ResourceBundle resourceBundle = new PropertyResourceBundle(inputStream);
//        Enumeration<String> keys = resourceBundle.getKeys();
//        while (keys.hasMoreElements()) {
//            String s = keys.nextElement();
//            System.out.println(s + " = " + resourceBundle.getString(s));
//        }
//    }


    public static void test6() throws IOException {
        InputStream inputStream = new BufferedInputStream(new FileInputStream("C:\\develop\\git-ekp-v16\\ekp_v16\\src\\com\\landray\\kmss\\km\\imeeting\\ApplicationResources.properties"));
        Properties properties = new Properties();
        properties.load(inputStream);
        Enumeration<String> enumeration = (Enumeration<String>) properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String key = (String) enumeration.nextElement();
            System.out.println("key=" + key);
            System.out.println("value=" + properties.getProperty(key));
        }

    }

//    获取配置文件所有的key
    public static void test8() throws IOException {
        InputStream inputStream = new BufferedInputStream(new FileInputStream("C:\\develop\\git-ekp-v16\\ekp_v16\\src\\com\\landray\\kmss\\km\\imeeting\\ApplicationResources.properties"));
        Properties properties = new Properties();
        properties.load(inputStream);
        Set<String> keySet = properties.stringPropertyNames();
        for (String key : keySet) {
            System.out.println("key=" + key);
            System.out.println("value=" + properties.getProperty(key));
        }

    }


    public static void test9() throws IOException {
        InputStream inputStream1 = new BufferedInputStream(new FileInputStream("C:\\develop\\git-ekp-v16\\ekp_v16\\src\\com\\landray\\kmss\\km\\imeeting\\ApplicationResources.properties"));
        InputStream inputStream2 = new BufferedInputStream(new FileInputStream("C:\\develop\\git-ekp-v16\\ekp_v16\\src\\com\\landray\\kmss\\km\\imeeting\\ApplicationResources_ja_JP.properties"));

        Properties properties1 = new Properties();
        Properties properties2 = new Properties();

        properties1.load(inputStream1);
        properties2.load(inputStream2);

        Set<String> keySet1 = properties1.stringPropertyNames();
        Set<String> keySet2 = properties2.stringPropertyNames();

        Set<String> result = new HashSet<>();

        for (String key1 : keySet1) {
            result.add(key1);
        }

        for (String key2 : keySet2) {
            result.remove(key2);
        }

//        for (String key1 : keySet1){
//            for (String key2 : keySet2){
//                if (key1 != key2){
//                    System.out.println(key1);
//                }
//            }
//        }

        for (String key : result) {
//            System.out.println("key=   " + key);
                        System.out.println("key=   " + key+"    value=    "+properties1.getProperty(key));

        }


    }

    //两个Set比较找出交集、差集、并集
    public static void  setCompare() {
        Set<Integer> result = new HashSet<Integer>();
        Set<Integer> set1 = new HashSet<Integer>() {{
            add(1);
            add(3);
            add(4);
        }};
        System.out.println("set1 = " + set1.toString());

        Set<Integer> set2 = new HashSet<Integer>() {{
            add(1);
            add(2);
            add(3);
        }};
        System.out.println("set2 = " + set2.toString());
        result.clear();
        result.addAll(set1);
        result.retainAll(set2);
        System.out.println("交集：" + result);

        result.clear();
        result.addAll(set1);
        result.removeAll(set2);
        System.out.println("差集：" + result);

        result.clear();
        result.addAll(set1);
        result.addAll(set2);
        System.out.println("并集：" + result);
    }

    public static void main(String[] args) {
        try {
            test9();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
