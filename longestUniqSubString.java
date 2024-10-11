package org.example;

import java.util.*;

public class longestUniqSubString {
    public static String findLongestTwoUniqueSubstring(String[] strings) {
        String longestSubstring = "";

        for (String str : strings) {
            String twoUniqueSubstring = findTwoUniqueSubstring(str);
            if (twoUniqueSubstring.length() > longestSubstring.length()) {
                longestSubstring = twoUniqueSubstring;
            }
        }
        return longestSubstring;
    }

    private static String findTwoUniqueSubstring(String str) {
        HashMap<Character, Integer> charCount = new HashMap<>();
        StringBuilder current = new StringBuilder();
        String longest = "";
        int start = 0;

        for (int end = 0; end < str.length(); end++) {
            char c = str.charAt(end);
            charCount.put(c, charCount.getOrDefault(c, 0) + 1);
            current.append(c);

            //remove element from head when unique element size > 2
            while (charCount.size() > 2) {
                char startChar = str.charAt(start);
                charCount.put(startChar, charCount.get(startChar) - 1);
                //delete from Hashmap & StringBuffer
                if (charCount.get(startChar) == 0) {
                    charCount.remove(startChar);
                }
                current.deleteCharAt(0);
                start++;
            }
            //longest string of 2 unique element
            if (charCount.size() == 2 && current.length() > longest.length()) {
                longest = current.toString();
            }
        }

        return longest;
    }

    private static void LongestUniqueSubstring(String s){
        int maxSubStringSize = 0;
        String maxSubString = null;
        Map<Character,Integer> map = new LinkedHashMap<>();
        for(int i=0; i< s.length(); i++){
            char c = s.charAt(i);
           if (!map.containsKey(c)){
               map.put(c, i);
           }
           else {
               i = map.get(c);
               map.clear();                                                                  // "Else" map get cleared & mapsize = 0
           }
           if(map.size() > maxSubStringSize) {
               maxSubStringSize = map.size();
               maxSubString = map.keySet().toString();
           }
        }
        System.out.println("maxSubString = " + maxSubString);
        System.out.println("maxSubStringSize = " + maxSubStringSize);
    }

    public static void main(String[] args) {
        String[] strings = {"abcabcbb", "aaaccaa", "pwwkew", "2aabbaa", "xyxxyy"};          // Longest  2 unique substring
        System.out.println("Longest substring with exactly two unique characters: " + findLongestTwoUniqueSubstring(strings));

        LongestUniqueSubstring("asdweasdferxcsdfg");                                        // Longest unique substring

        List<String> stringList = Arrays.asList("Jaava", "Animesh", "Catastrophic","Roublest");
        Optional<String> longestString = stringList.stream().max((s1,s2) -> (int)(s1.length()-s2.length()));
        if(longestString.isPresent())
            System.out.println("Largest String = " + longestString.get());                  // Longest string result give optional, need to use .get()
        longestString.ifPresent(System.out :: printf);                                      // Provide result without optional
    }
}


