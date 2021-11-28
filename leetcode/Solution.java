package leetcode;


import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Solution {
    /**
     * 题目:
     * Given a string, find the length of the longest substring without repeating characters.
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/longest-substring-without-repeating-characters
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */


    /**
     *测试用例
     *
     *Example 1:
     *
     * Input: "abcabcbb"
     * Output: 3
     * Explanation: The answer is "abc", with the length of 3.
     *
     * Example 2:
     *
     * Input: "bbbbb"
     * Output: 1
     * Explanation: The answer is "b", with the length of 1.
     *
     * Example 3:
     *
     * Input: "pwwkew"
     * Output: 3
     * Explanation: The answer is "wke", with the length of 3.
     *              Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
     *
     */

    public static int lengthOfLongestSubstring01(String s) {
        //暴力解法
        /**
         *  abcabcbb
         *  1:(abc)abcbb
         *  2:a(bca)bcbb
         *  3:ab(cab)cbb
         *  4:abc(abc)bb
         *  5:abca(bc)bb
         *  6:abcab(cb)b
         *  7:abcabc(b)b
         *  8:abcabcb(b)
         */
        //执行用时：275 ms
        //内存消耗：40.5 MB
        if (Objects.isNull(s) ||s.isEmpty()) {
            return 0;
        }
        StringBuilder sb;
        int result = 0;
        for (int i = 0, j = s.length(); i < j; i++) {
            sb = new StringBuilder();
            sb.append(s.charAt(i));
            String sub;
            if (i + 1 < j) {
                sub = s.substring(i + 1);
            } else {
                sub = s.substring(i);
            }
            for (int x = 0, y = sub.length(); x < y; x++) {
                //重复子串
                if (sb.indexOf(String.valueOf(sub.charAt(x))) > -1) {
                    break;
                } else {
                    sb.append(sub.charAt(x));
                }
            }
            result = Math.max(result, sb.length());
        }
        return result;
    }


    public static int lengthOfLongestSubstring02(String s) {
        //官方 解法
        //执行用时：8 ms
        //内存消耗：39.8 MB
        if (Objects.isNull(s) ||s.isEmpty()) {
            return 0;
        }
        Set<Character> children = new HashSet<>();
        int rk = -1, result = 0;
        for (int i = 0, j = s.length(); i < j; ++i) {
            if (i != 0) {
                children.remove(s.charAt(i - 1));
            }
            while (rk + 1 < j && !children.contains(s.charAt(rk + 1))) {
                children.add(s.charAt(rk + 1));
                rk++;
            }
            result = Math.max(result, rk - i + 1);
        }
        return result;
    }

    public static int lengthOfLongestSubstring03(String s) {

        if (Objects.isNull(s) ||s.isEmpty()) {
            return 0;
        }
        //窗口
        Set<Character> children = new HashSet<>();
        int result = 0;
        for (int i = 0, j = s.length(); i < j; ++i) {
            
        }
        System.out.println(result);
        return result;
    }

    public static void main(String[] args) {

        //lengthOfLongestSubstring03("nfpdmpi");  //5
        //lengthOfLongestSubstring03("au");  //2
        //lengthOfLongestSubstring03("pwwkew");//3
        //lengthOfLongestSubstring03("bbbbb"); //1
        //lengthOfLongestSubstring03("abcabcbb");//3
        //lengthOfLongestSubstring03("a"); //1
        //lengthOfLongestSubstring03(""); //1
        //lengthOfLongestSubstring03("");//0
        //String s = " ";
        //int i = s.indexOf(" ");
        //System.out.println(i);

    }
}