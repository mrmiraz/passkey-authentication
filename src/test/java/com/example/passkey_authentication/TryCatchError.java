package com.example.passkey_authentication;

public class TryCatchError extends Exception{

    public static void main(String[] args) {
        int be = 4/1;
        System.out.println("It's okay");
        try{
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
