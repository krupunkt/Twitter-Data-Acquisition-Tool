/*
 * Copyright 2018 Umut Canbolat.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
