package org.arch.pms.sku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * SKU排列组合
 */
public class SkuTest {

    static class Item {
        List<String> options;

        public List<String> getOptions() {
            return options;
        }

        public void setOptions(List<String> item) {
            this.options = item;
        }
    }

    /**
     * <pre>
     * 手机内存：16G，32G
     * 手机颜色：红，黑
     * 手机渠道：国行，港行
     * 
     * 列出所有组合，16G/红/国行,16G/黑/国行...
     * </pre>
     */
    public static void main(String[] args) {
        Item mem = new Item();
        mem.setOptions(Arrays.asList("16G", "32G"));

        Item color = new Item();
        color.setOptions(Arrays.asList("红", "黑"));

        Item channel = new Item();
        channel.setOptions(Arrays.asList("国行", "港行"));

        List<Item> itemList = Arrays.asList(mem, color, channel);

        int startIndex = 0;
        List<String> result = combine(itemList.get(startIndex), itemList, startIndex);
        System.out.println(result); 
        /*
         [16G/红/国行, 16G/红/港行, 16G/黑/国行, 16G/黑/港行, 32G/红/国行, 32G/红/港行, 32G/黑/国行, 32G/黑/港行]
         */

    }

    /**
     * 递归算法
     * @param first 第一个item
     * @param itemList 所有item
     * @param index 索引
     * @return 返回当前Item具备的组合
     */
    public static List<String> combine(Item first, List<Item> itemList, int index) {
        List<String> firstItem = first.getOptions();
        List<String> ret = new ArrayList<>();
        for (String item : firstItem) {
            int nextIndex = index + 1;
            if (nextIndex < itemList.size()) {
                List<String> nextRet = combine(itemList.get(nextIndex), itemList, nextIndex);
                for (String string : nextRet) {
                    StringBuilder chain = new StringBuilder();
                    chain.append(item).append("/").append(string);
                    ret.add(chain.toString());
                }
            } else {
                ret.add(item);
            }
        }
        return ret;
    }

}