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
    //サイコロを振る
    public static List<Integer> diceThrow() {
        Random rand = new Random();

        return Stream.generate(() -> rand.nextInt(5) + 1)
                .limit(3)
                .sorted()
                .collect(Collectors.toList());
    }

    //出た目の役を調べる
    public static String rankCheck(List<Integer> dice) {
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
    public static Map<String, List<Integer>> userDice(List<String> userlist) {
        int num = userlist.size();

        Map<String, List<Integer>> userDice = new HashMap();
        for (int i = 0; i < num; i++) {
            List<Integer> dice = diceThrow();
            userDice.put(userlist.get(i), dice);
        }

        return userDice;
    }

    //ユーザと役のマップ
    public static Map<String, String> userRankCalc(Map<String, List<Integer>> userDice) {
        Map<String, String> userRank = new HashMap<>();

        for (String key : userDice.keySet()) {
            userRank.put(key, rankCheck(userDice.get(key)));
        }

        //Map<String, String> userRank = userlist.stream().collect(Collectors.toMap(userName -> userName, userName -> rankcheck(dicethrow())));
        return userRank;

    }

    //役の強さを受け取って数字に変換
    public static int strToRank(String rank) {
        int toRank = 0;

        if (null != rank) {
            switch (rank) {
                case "5倍づけ":
                    toRank = 11;
                    break;
                case "3倍づけ":
                    toRank = 10;
                    break;
                case "1倍づけ(6の目)":
                    toRank = 9;
                    break;
                case "1倍づけ(5の目)":
                    toRank = 8;
                    break;
                case "1倍づけ(4の目)":
                    toRank = 7;
                    break;
                case "1倍づけ(3の目)":
                    toRank = 6;
                    break;
                case "1倍づけ(2の目)":
                    toRank = 5;
                    break;
                case "1倍づけ(1の目)":
                    toRank = 4;
                    break;
                case "1倍払い":
                    toRank = 3;
                    break;
                case "2倍払い":
                    toRank = 2;
                    break;
                default:
                    break;
            }
        }

        return toRank;
    }

    //参加者と出た目のマップを引数として、勝者のリストを返す
    public static List<String> getWinners(Map<String, List<Integer>> rankmap) {
        int num = rankmap.size();
        int max = 0;

        List<Integer> resultList = new ArrayList<>();
        List<String> userlist = new ArrayList<>();
        List<String> winners = new ArrayList<>();

        //ユーザリスト作成
        for (String key : rankmap.keySet()) {
            userlist.add(key);
        }

        for (String key : rankmap.keySet()) {
            resultList.add(strToRank(rankCheck(rankmap.get(key))));
        }

        for (int i = 0; i < resultList.size(); i++) {
            int tmp = resultList.get(i);
            if (max < tmp) {
                max = tmp;
            }
        }

        for (int i = 0; i < num; i++) {
            if (resultList.get(i) == max) {
                winners.add(userlist.get(i));
            }
        }

        return winners;
    }

    public static List<String> chinchiroRun(List<String> users) {
        List<String> resultMessage = new ArrayList();

        // ダイスを振った結果を得る
        Map<String, List<Integer>> chinchiroValue = userDice(users);
        {
            String result = "";
            Integer index = 0;
            for (String key : chinchiroValue.keySet()) {
                result = result + (key + "さんの目：" + chinchiroValue.get(key));
                if (!index.equals(chinchiroValue.size() - 1)) {
                    result = result + ", ";
                }
                index++;
            }
            resultMessage.add(result);
        }

        // ダイスの結果から、役の名前を得る
        {
            Map<String, String> userRanks = userRankCalc(chinchiroValue);
            String result = "";
            Integer index = 0;
            for (String key : userRanks.keySet()) {
                result = result + (key + "さんの役：" + userRanks.get(key));
                if (!index.equals(userRanks.size() - 1)) {
                    result = result + ", ";
                }
                index++;
            }
            resultMessage.add(result);
        }

        // 勝者の名前を得る
        {
            List<String> winners = getWinners(chinchiroValue);
            String result = "勝者は、";
            Integer index = 0;
            for(String winner : winners){
                result = result + winner;
                if(!index.equals(winners.size() - 1)){
                    result = result + "さんと";
                }
                index++;
            }
            result = result + "さんです！";
            resultMessage.add(result);
        }

        return resultMessage;
    }
}
