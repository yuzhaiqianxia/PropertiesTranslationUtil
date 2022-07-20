
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 小程序：比对中英文properties文件，查找不匹配的key
 */
public class FilePropertiesCompare {
    private static Logger logger = LoggerFactory.getLogger(FilePropertiesCompare.class);

    private static String searchFileName1="messages.properties";
    private static File file;

    private static Properties properties;
    private static Properties properties_en_us;
    private FileInputStream fileInputStream;
    private FileInputStream fileInputStream_en_us;

//    private static String path = "E:\\his_leopard\\leopard\\branches\\dev\\" ;
    private static String path = "C:\\temptemp\\" ;


//    private static String filePath_zw = "\\i18n\\"+"messages.properties";
//    private static String filePath_en_us = "\\i18n\\"+"messages_en_US.properties";

    private static String filePath_zw = "\\i18n\\"+"ApplicationResources.properties";
    private static String filePath_en_us = "\\i18n\\"+"ApplicationResources_en_US.properties";

    public  void findFileName(File f,String name) {
        file=new File(path);
        if(!file.isDirectory()){
            logger.info("must be directory");
        }
        File[] files=file.listFiles();
        logger.info("files size:"+files.length);
        logger.info("\n");

        for(int i=0;i<files.length;i++){
            String filePath = files[i].getPath();
            String fileName = files[i].getName();
            String filePath1 = filePath+"\\src\\" +fileName.replace(".", "\\") + filePath_zw;
            String filePath2 = filePath+ "\\src\\" +fileName.replace(".", "\\") + filePath_en_us;

            comparePropertiesFileKey(filePath1,filePath2);
        }
    }

    private void comparePropertiesFileKey(String filePath,String filePath_en_us) {
        properties = new Properties();
        properties_en_us = new Properties();
        try {
            fileInputStream = new FileInputStream(filePath);
            fileInputStream_en_us = new FileInputStream(filePath_en_us);
            properties.load(fileInputStream);
            properties_en_us.load(fileInputStream_en_us);

            Enumeration<?> key = properties.propertyNames();
            Enumeration<?> key_en_us = properties_en_us.propertyNames();

            List<String> keyList = new ArrayList<String>();
            List<String> keyList_en_us = new ArrayList<String>();

            while (key.hasMoreElements()) {
                String keyName = (String) key.nextElement();
                keyList.add(keyName);
            }
            while (key_en_us.hasMoreElements()) {
                String keyName = (String) key_en_us.nextElement();
                keyList_en_us.add(keyName);
            }

            /**比较两个属性文件中的key是否一样多*/
            if(keyList.size() != keyList_en_us.size()){
                logger.info("====================================================================================================");
                logger.info("\n");
                logger.info("size::"+keyList.size()+"《=====》size_en_us::"+keyList_en_us.size());
                logger.info("\n");
                logger.info("filePath_messages:"+filePath);
                logger.info("\n");
                logger.info("filePath_messages_en_US:"+filePath_en_us);
                logger.info("\n");
                logger.info("\n");
                logger.info("messages_en_US.properties缺少的文件：");
                logger.info("\n");
            }
            for (String name : keyList) {
                if(!keyList_en_us.contains(name)){
                    logger.info(name+" = "+ properties.getProperty(name));
                    logger.info("\n");
                }
            }
            fileInputStream.close();
            fileInputStream_en_us.close();
            logger.info("==================================================================================================");
            logger.info("\n");

        } catch (FileNotFoundException e) {
            logger.info("读取文件====》失败！原因：文件路径错误或文件不存在.");
            e.printStackTrace();
        } catch (IOException e) {
            logger.info("装载文件====》失败.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FilePropertiesCompare rf = new FilePropertiesCompare();
        rf.findFileName(file,searchFileName1);
    }

}
