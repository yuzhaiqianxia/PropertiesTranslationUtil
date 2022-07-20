import java.io.IOException;

public class UtilTest {
    public static void main(String[] args) {
        String path1 = "C:\\develop\\git-ekp-v16\\ekp_v16\\src\\com\\landray\\kmss\\km\\imeeting\\ApplicationResources.properties";
//        String path2 = "C:\\develop\\git-ekp-v16\\ekp_v16\\src\\com\\landray\\kmss\\km\\imeeting\\ApplicationResources_ja_JP.properties";
        String path2 = "C:\\develop\\git-ekp-v16\\ekp_v16\\src\\com\\landray\\kmss\\km\\imeeting\\ApplicationResources_zh_HK.properties";
        try {
            PropertiesCompareUtil.showDifference(path1,path2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
