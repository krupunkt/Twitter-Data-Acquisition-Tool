/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umutcanbolat.twproject;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author umut
 */
public class ApiKey {
    public String consumerKey;
    public String consumerSecret;
    public String accessToken;
    public String accessTokenSecret;
    
    public static List<ApiKey> keys;
    
    public ApiKey(){
        keys =  new ArrayList();
        ApiKey a = new ApiKey("consumerKey-1", 
                "consumerSecret-1", 
                "accessToken-1", 
                "accessTokenSecret-1");
        keys.add(a);
        
        a = new ApiKey("consumerKey-2", 
                "consumerSecret-2", 
                "accessToken-2", 
                "accessTokenSecret-2");
        keys.add(a);
        
        a = new ApiKey("consumerKey-3", 
                "consumerSecret-3", 
                "accessToken-3", 
                "accessTokenSecret-3");
        keys.add(a);
    }
    
    public ApiKey(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret){
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.accessToken = accessToken;
        this.accessTokenSecret = accessTokenSecret;
    }
    

    
}
