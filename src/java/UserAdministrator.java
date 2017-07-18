
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author yuji
 */
public class UserAdministrator {
    private static List<User> users ;
    
    public void addUser(String name, int money){
        if (users == null)
            users = new ArrayList<>();
        users.add(new User(name, money));
    }
    public void removeUser(String name){
        for(User user : users){
            if(user.getName().equals(name)){
                users.remove(user);
                break;
            }
        }
    }
    
    // 受け取った名前のユーザーの状態をREADYにする。
    // もし変更したらtrueを、すでに変更済みの場合はfalseを返す。
    public boolean setUserStateReady(String name){
        for(User user : users){
            if(user.getName().equals(name)){
                if(!user.isStateReady()){
                    user.setReadyState();
                    // 変更した
                    return true;
                }else{
                    // すでにユーザーの状態がREADYになっている
                    return false;
                }
            }
        }
        // そのような名前のユーザーは登録されていない
        return false;
    }
    public boolean isAllUsersReady(){
        for(User user : users){
            if(!user.isStateReady()){
                return false;
            }
        }
        return true;
    }
    public void userStatesReset(){
        for(User user : users){
            user.setWaitState();
        }
    }
    public List<User> getUsers(){
        return users;
    }
}
