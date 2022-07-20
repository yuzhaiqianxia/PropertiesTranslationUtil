import cn.hutool.core.io.FileUtil;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class PropertiesCompare {


    public static final String IGNORE_FLAG = "#";
    public static final String SPLITTER = "=";
//    public static final String SOURCE_FILE_PATH = "C:\\Users\\xxx\\Desktop\\source1.txt";
//    public static final String TARGET_FILE_PATH = "C:\\Users\\xxx\\Desktop\\target1.txt";

    public static final String SOURCE_FILE_PATH = "C:\\develop\\git-ekp-v16\\ekp_v16\\src\\com\\landray\\kmss\\km\\imeeting\\ApplicationResources.properties";
    public static final String TARGET_FILE_PATH = "C:\\develop\\git-ekp-v16\\ekp_v16\\src\\com\\landray\\kmss\\km\\imeeting\\ApplicationResources_en_US.properties";


    public static void main(String[] args) {
        List<String> source = FileUtil.readLines(SOURCE_FILE_PATH, StandardCharsets.UTF_8);
        Map<String, String> sourceMap = parseKeyValue(source);

        List<String> target = FileUtil.readLines(TARGET_FILE_PATH, StandardCharsets.UTF_8);
        Map<String, String> targetMap = parseKeyValue(target);

        CompareResult compare = compare(sourceMap, targetMap);
        output(compare);
    }


    /**
     * 解析每一行的文件的 key value
     */
    public static Map<String, String> parseKeyValue(List<String> properties) {
        if (CollectionUtils.isEmpty(properties)) {
            return Collections.EMPTY_MAP;
        }
        Map<String, String> result = new HashMap<>();
        for (String property : properties) {
            if (StringUtils.isBlank(property) || StringUtils.startsWith(property, IGNORE_FLAG)) {
                continue;
            }
            int splitterIndex = StringUtils.indexOf(property, SPLITTER);
            if (splitterIndex < 0) {
                continue;
            }

            String key = StringUtils.substring(property, 0, splitterIndex);
            String value = StringUtils.substring(property, splitterIndex + 1);

            result.put(StringUtils.trim(key), StringUtils.trim(value));
        }
        return result;
    }

    /**
     * 比较差异结果
     */
    public static CompareResult compare(Map<String, String> sourceMap, Map<String, String> targetMap) {
        CompareResult result = new CompareResult();

        Collection<String> diffKeys = CollectionUtils.removeAll(sourceMap.keySet(), targetMap.keySet());
        result.setTargetMissKeys(diffKeys);

        diffKeys = CollectionUtils.removeAll(targetMap.keySet(), sourceMap.keySet());
        result.setSourceMissKeys(diffKeys);

        Collection<String> intersectionKeys = CollectionUtils.intersection(sourceMap.keySet(), targetMap.keySet());
        List<DifferenceResult> differResults = new ArrayList<>();
        for (String intersectionKey : intersectionKeys) {
            String sourceValue = sourceMap.get(intersectionKey);
            String targetValue = targetMap.get(intersectionKey);
            if (StringUtils.equals(sourceValue, targetValue)) {
                continue;
            }
            differResults.add(DifferenceResult.builder().key(intersectionKey).sourceValue(sourceValue).targetValue(targetValue).build());
        }
        result.setDifferenceResults(differResults);
        return result;
    }

    /**
     * 输出比较结果
     */
    public static void output(CompareResult result) {
        if (!result.hasDifference()) {
            log.info("配置完全一致，没有差异");
            return;
        }
        if (CollectionUtils.isNotEmpty(result.getTargetMissKeys())) {
            log.info("存在 source 而不存在于 target 的 key : {}", result.getTargetMissKeys());
        }

        if (CollectionUtils.isNotEmpty(result.getSourceMissKeys())) {
            log.info("存在 target 而不存在于 source 的 key : {}", result.getSourceMissKeys());
        }

        if (CollectionUtils.isNotEmpty(result.getDifferenceResults())) {
            log.info("========================================================================");
            log.info("============================= 值有差异的配置 =============================");
            log.info("========================================================================");
            for (DifferenceResult difference : result.getDifferenceResults()) {
                log.info("[{}] : {} ---> {}", difference.getKey(), difference.getSourceValue(), difference.getTargetValue());
            }
        }
    }


    @Data
    public static class CompareResult {
        /**
         * 存在 source 而不存在于 target 的 key
         */
        private Collection<String> targetMissKeys;
        /**
         * 存在 target 而不存在于 source 的 key
         */
        private Collection<String> sourceMissKeys;
        /**
         * 值有差异的配置
         */
        private Collection<DifferenceResult> differenceResults;

        public boolean hasDifference() {
            return CollectionUtils.isNotEmpty(targetMissKeys) || CollectionUtils.isNotEmpty(sourceMissKeys) || CollectionUtils.isNotEmpty(differenceResults);
        }
    }

    @Data
    @Builder
    public static class DifferenceResult {
        /**
         * key
         */
        private String key;
        /**
         * source value
         */
        private String sourceValue;
        /**
         * target value
         */
        private String targetValue;
    }
}
