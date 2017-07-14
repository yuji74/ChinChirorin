/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author kyo1
 */
public class ChinchirorinRun {

    /**
     * @param args the command line arguments
     */
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        List<String> user = new ArrayList<>();
        user.add("A");
        user.add("B");
        user.add("C");
        user.add("D");
        user.add("E");
        user.add("F");
        
        
        Map<String,List<Integer>> result = userDice(user);
        System.out.println(result);
        System.out.println(userRank(result));
        System.out.println(Victory(result));
    }
    
    
    //サイコロを振る
    public static List<Integer> dicethrow() {
        Random rand = new Random();

        return Stream.generate(() -> rand.nextInt(5) + 1)
                .limit(3)
                .sorted()
                .collect(Collectors.toList());
    }
    
    //出た目の役を調べる
    public static String rankcheck(List<Integer> dice) {
    String rank;

    if (dice.get(0) == 1 && dice.get(1) == 1 && dice.get(2) == 1) {
        rank = "5倍づけ";
    } else if (dice.get(0).equals(dice.get(1)) && dice.get(1).equals(dice.get(2))) {
        rank = "3倍づけ";
    } else if (dice.get(0) == 4 && dice.get(1) == 5 && dice.get(2) == 6) {
        rank = "2倍づけ";
    } else if (dice.get(0).equals(dice.get(1))) {
        int me = dice.get(2);
        //System.out.println(me);
        rank = "1倍づけ(" + me + "の目)";
    } else if (dice.get(1).equals(dice.get(2))) {
        int me = dice.get(0);
        //System.out.println(me);
        rank = "1倍づけ(" + me + "の目)";
    } else if (dice.get(0) == 1 & dice.get(1) == 2 && dice.get(2) == 3) {
        rank = "2倍払い";
    } else {
        rank = "1倍払い";
    }

    return rank;
    }
    
    //ユーザと出目のマップ
    public static Map<String, List<Integer>> userDice(List<String> userlist){
        int num = userlist.size();
      
        Map<String, List<Integer>> userDice = new HashMap();
        for(int i =0; i < num; i++){
            List<Integer> dice = dicethrow();
            userDice.put(userlist.get(i), dice);
        }
        
        return userDice;
    }
    
    //ユーザと役のマップ
     public static Map<String, String> userRank(Map<String, List<Integer>> userDice) {
        Map<String, String> userRank = new HashMap<>();
    
        for (String key : userDice.keySet()){
            userRank.put(key,rankcheck(userDice.get(key)));
        }
        
        //Map<String, String> userRank = userlist.stream().collect(Collectors.toMap(userName -> userName, userName -> rankcheck(dicethrow())));
        return userRank;

    }
    
    //役の強さを受け取って数字に変換
    public static int str_torank(String rank) {
        int torank = 0;

        if (null != rank) {
            switch (rank) {
                case "5倍づけ":
                    torank = 11;
                    break;
                case "3倍づけ":
                    torank = 10;
                    break;
                case "1倍づけ(6の目)":
                    torank = 9;
                    break;
                case "1倍づけ(5の目)":
                    torank = 8;
                    break;
                case "1倍づけ(4の目)":
                    torank = 7;
                    break;
                case "1倍づけ(3の目)":
                    torank = 6;
                    break;
                case "1倍づけ(2の目)":
                    torank = 5;
                    break;
                case "1倍づけ(1の目)":
                    torank = 4;
                    break;
                case "1倍払い":
                    torank = 3;
                    break;
                case "2倍払い":
                    torank = 2;
                    break;
                default:
                    break;
            }
        }

        return torank;
    }
    
    //参加者と出た目のマップを引数として、勝者のリストを返す
    public static List<String> Victory(Map<String,List<Integer>> rankmap){
        int num = rankmap.size();
        int max = 0;
        
        List<Integer> resultList = new ArrayList<>();
        List<String> userlist = new ArrayList<>();
        List<String> victory = new ArrayList<>();
        
        //ユーザリスト作成
        for (String key : rankmap.keySet()){
            userlist.add(key);
        }
        
        for (String key : rankmap.keySet()){
             resultList.add(str_torank(rankcheck(rankmap.get(key))));
        }
        
        for (int i = 0; i < resultList.size(); i++) {
            int tmp = resultList.get(i);
            if (max < tmp) {
                max = tmp;
            }
        }
        
        for (int i = 0; i < num; i ++) {
            if(resultList.get(i) == max) {
                victory.add(userlist.get(i));
            }
        }
   
        return victory;     
    }
}
