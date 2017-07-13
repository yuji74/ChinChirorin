/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author yuji
 */
public class User {
    private enum State{
        WAIT,
        READY
    }
    private String name;
    private int money;
    private State state;
    
    public User(String name, int money){
        this.name = name;
        this.money = money;
        this.state = State.WAIT;
    }
    
    public String getName(){
        return this.name;
    }
    public void setMoney(int money){
        this.money = money;
    }
    public int getMoney(){
        return this.money;
    }
    public void setWaitState(){
        this.state = State.WAIT;
    }
    public void setReadyState(){
        this.state = State.READY;
    }
    public boolean isStateReady(){
        if(this.state == State.READY){
            return true;
        }
        return false;
    }
}
