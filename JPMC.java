package org.example;

import java.util.*;

public class JPMC {
    public static void main(String[] args) {
        List<Object> objectList = Arrays.asList(1, 2, 3, 4, '@', 5, 9, 'a', 6, 7, 8, 4, -8, -7, -3, -2, -1);
        boolean continueInd = false;
        Set<Integer> seqSet = new LinkedHashSet<>();
        int cur,prev = 0;
        for(Object i : objectList){
            if(i instanceof Integer){
                if(continueInd) {               //Clear Set, previousInteger, boolean flag
                    seqSet.clear();
                    prev = 0;
                    continueInd = false;
                }
                cur = (int)i;
                if(prev != 0){
                    if(cur - prev == 1){
                        seqSet.add(prev);
                        seqSet.add(cur);
                        if(seqSet.size() == 3){
                            System.out.println(seqSet);
                        } else if (seqSet.size() > 3) {
                            seqSet.clear();         //clear the set when size reached
                        }
                    }
                }
                prev = cur;
            }
            else {
                continueInd = true;
            }
        }
    }
}
