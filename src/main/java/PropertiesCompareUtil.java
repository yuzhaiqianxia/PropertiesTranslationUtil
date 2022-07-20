import java.io.*;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class PropertiesCompareUtil {

    public static void showDifference(String path1,String path2) throws IOException {
        InputStream inputStream1 = new BufferedInputStream(new FileInputStream(path1));
        InputStream inputStream2 = new BufferedInputStream(new FileInputStream(path2));

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

        for (String key : result) {
            System.out.println(key+"="+properties1.getProperty(key));
        }
    }

    public static void getPropertiesResult(String path)  {
        InputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Set<String> set = properties.stringPropertyNames();
        Set<String> result = new HashSet<>();
        // TODO: 2022/6/29
        //  for ()
    }

}
