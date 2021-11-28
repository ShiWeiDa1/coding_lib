package leetcode;

import org.junit.Test;

import java.util.Arrays;

/**
 *@className: Solution1
 *@description:
 *@author: Regan_alex
 *@date: 2021/11/11 23:18
 *@version: V1.0
 **/
public class Solution1 {
   @Test
    public void test01(){
       solution1(new int[]{1,2,3,4,5,6,7},3);
   }
   private void solution1(int[] nums,int k){
       if (nums==null||k<0) {
           return;
       }
       int len=nums.length;
       int[] res = new int[len];
       for (int i = 0; i < len;++i ) {
           int index=(i+len+k)%len;
           res[index]=nums[i];
       }
       System.out.println(Arrays.toString(res));
       
   }
}