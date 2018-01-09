/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umutcanbolat.twproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.stream.Collectors;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author umut
 */
public class Application {

    public static Twitter twitter;
    public static List<Long> allUserIds = new ArrayList();
    public static int tokenId = -1;
    public static int stepCount;

    /**
     *
     * @param reason: 0: need configuration at the beginning of the proram 1:
     * lack of /statuses/user_timeline limit 2: lack of /friends/ids limit
     * @throws TwitterException
     */
    public static void configureCredentials(int reason) throws TwitterException, InterruptedException {
        RateLimitStatus st;

        ApiKey aKey = new ApiKey();
        if (tokenId == -1) {
            ConfigurationBuilder cb = new ConfigurationBuilder();
            tokenId = 0;
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey(ApiKey.keys.get(tokenId).consumerKey)
                    .setOAuthConsumerSecret(ApiKey.keys.get(tokenId).consumerSecret)
                    .setOAuthAccessToken(ApiKey.keys.get(tokenId).accessToken)
                    .setOAuthAccessTokenSecret(ApiKey.keys.get(tokenId).accessTokenSecret);
            TwitterFactory tf = new TwitterFactory(cb.build());
            twitter = tf.getInstance();
            System.out.println(tokenId + ". key has been taken initially");
        } else {
            ConfigurationBuilder cb = new ConfigurationBuilder();
            try {
                twitter.getOAuthAccessToken();
                st = twitter.getRateLimitStatus().get("/statuses/user_timeline");
                System.out.println(tokenId + ": " + st);
                st = twitter.getRateLimitStatus().get("/friends/ids");
                System.out.println(tokenId + ": " + st);
            } catch (Exception e) {

            }

            tokenId++;
            tokenId = tokenId % ApiKey.keys.size();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey(ApiKey.keys.get(tokenId).consumerKey)
                    .setOAuthConsumerSecret(ApiKey.keys.get(tokenId).consumerSecret)
                    .setOAuthAccessToken(ApiKey.keys.get(tokenId).accessToken)
                    .setOAuthAccessTokenSecret(ApiKey.keys.get(tokenId).accessTokenSecret);
            TwitterFactory tf = new TwitterFactory(cb.build());
            twitter = tf.getInstance();
            User verifyCredentials = twitter.verifyCredentials();
            System.out.println(verifyCredentials);
            System.out.println("Switched to " + tokenId + ". key" + " Reason: " + reason + "; 10 saniye uyuyacak");
            TimeUnit.SECONDS.sleep(10);
//            System.out.println("uyandı");
            try {
                twitter.getOAuthAccessToken();
                st = twitter.getRateLimitStatus().get("/statuses/user_timeline");
                System.out.println(tokenId + ": " + st);
                st = twitter.getRateLimitStatus().get("/friends/ids");
                System.out.println(tokenId + ": " + st);
            } catch (Exception e) {

            }
        }

    }

    /**
     *
     * @param Id: TheId of the user whose following list will be returned
     * @return Returns the list of Id's of the users whom are followed by the user whose Id is the parameter of the function
     * @throws TwitterException
     * @throws InterruptedException
     */
    public static List<Long> getFollowingIds(long Id) throws TwitterException, InterruptedException {
        try {
            IDs friendsIDs = twitter.getFriendsIDs(Id, -1);
            return Arrays.stream(friendsIDs.getIDs()).boxed().collect(Collectors.toList());
        } catch (TwitterException e) {
            configureCredentials(2);
            return getFollowingIds(Id);
        }
    }

    /**
     *
     * @param userId: The Id of the user whose tweets will be retrieved
     * @return Returns tweets of the user as ArrayList
     * @throws TwitterException
     * @throws InterruptedException
     */
    public static List<Status> getStatusesOfUser(long userId) throws TwitterException, InterruptedException {
        /*
        * Get last 3200 tweets of the user and store it in the list<Status>
         */
        if (allUserIds.contains(userId)) {
            return null;
        }
        int i = 1;
        List<Status> statuses = new ArrayList<>();
        List<Status> bufferList;
        while (true) {
            //System.out.println("while " + i);
            if (i > 25) {
                break;
            }
            //System.out.print(i + ". page");
            try {
                bufferList = twitter.getUserTimeline(userId, new Paging(i, 200));

                if (bufferList.size() > 0) {
                    statuses.addAll(bufferList);
                } else {
                    break;
                }

                //System.out.println(" has been retrieved - " + "tweet count: " + statuses.size());
            } catch (NullPointerException np) {
                np.printStackTrace();
                break;
            } catch (TwitterException twE) {

                if (twE.exceededRateLimitation()) {
                    System.out.println(tokenId + ". key's limit is exceeded, gonna take the next key...");
                    configureCredentials(1);
                    i--;
                } else if (twE.getStatusCode() == 503) {
//                    twE.printStackTrace();
                    System.out.println("Twitter serverları yoğunluktan yanıt vermiyor. 5 dakika uyuyacak");
                    TimeUnit.MINUTES.sleep(5);
                    i--;
                }

            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
            //System.out.println("2");
            i++;
        }

        //System.out.println(statuses.get(1));
        allUserIds.add(userId);
        return statuses;
    }

    /**
     *
     * @param userId: The Id of the user whose folder will be created
     * @param targetPath: the path of the folder which will be created
     * @param N: Number of steps. 0 step: retrieves only the 1st user's tweets. 
     * 1 step: Retrieves the tweets of the user, also retrieves the following peoples tweetes
     * 2 steps: Also retrieves the following of following peoples tweets.
     * @throws TwitterException
     * @throws FileNotFoundException
     * @throws InterruptedException
     * @throws UnsupportedEncodingException
     */
    public static void createUserFolder(long userId, String targetPath, int N) throws TwitterException, FileNotFoundException, InterruptedException, UnsupportedEncodingException {
        List<Status> statuses = getStatusesOfUser(userId);
        //System.out.println("got statuses");
        String target = targetPath + File.separator + twitter.showUser(userId).getScreenName();
        new File(target).mkdir();   //Create folder
        if (statuses != null) {
            PrintWriter writer = new PrintWriter(target + File.separator + String.format("tweets (%d).json", statuses.size()), "UTF-8");
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = "";
            try {
                writer.println(ow.writeValueAsString(statuses));
                //json = ow.writeValueAsString(statuses);
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }
            writer.close();
        }

        target = target + File.separator + "following";
        new File(target).mkdir();   //Create folder
        N--;    // Another step has been completed
        if (N > 0) {
            List<Long> Ids = getFollowingIds(userId);
            for (int i = 0; i < Ids.size(); i++) {
                for(int t=N ; t<stepCount-1 ; t++){
                    System.out.print("\t");
                    
                    
                }
                System.out.println(i + 1 + "/" + Ids.size() + " - N:" + N);
                for(Long l: allUserIds){
                        System.out.println(l);
                    }
                createUserFolder(Ids.get(i), target, N);
            }
        }

    }

    public static void main(String[] args) throws TwitterException, IOException, Exception {

        configureCredentials(0);

        
        /*
        Just fill the following 3 fields
        */
        String username = "nasa";
        String destinationPath = "./";
        stepCount = 2;
        
        
        long userId;

        try {
            User user = twitter.showUser(username);
            userId = user.getId();
            createUserFolder(userId, destinationPath, stepCount);
        } catch (TwitterException e) {
            System.out.println(e.getErrorMessage().toUpperCase() + " Exiting..");
           
//            System.out.println(username + " does not exist. Exiting..");
//            System.exit(0);
        }

    }

}
