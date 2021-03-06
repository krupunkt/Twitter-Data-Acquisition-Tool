# Twitter-Data-Acquisition-Software
[![Build Status](https://travis-ci.org/umutcanbolat/Twitter-Data-Acquisition-Software.svg?branch=master)](https://travis-ci.org/umutcanbolat/Twitter-Data-Acquisition-Software)

A tool which helps users gather a kind of big data from Twitter for text analysis using Twitter API.

## Description
To obtain a huge tweet data, the program uses the social network of Twitter users to reach bigger populations. It starts with 1 user and jumps to other people on the social network of this user.

In the beginning, the program asks for a Twitter username as input. After that, it starts downloading up to the latest 3200 tweets ([API Limitation](#twitter-api-limits)) of the user and writes them down in a JSON file.

After it finishes downloading, it gathers the list of the people followed by the given user. And starts downloading up to the latest 3200 tweets of these bunch of users too. These steps follow each other in a recursive way until the user-specified stepCount limit is exceeded.

## How To Use?
* First of all, you need to have a Twitter account and create an application on the Twitter developer site. 

  * Go to https://apps.twitter.com/ and log in with your Twitter Account.
  * Click “Create New App”.
  * Fill out the form, agree to the terms, and click “Create your Twitter application”
  * In the next page, click on “Keys and Access Tokens” tab, and copy your “Consumer Key” and “Consumer Secret”. Scroll down and click “Create my access token”, and copy your “Access Token” and “Access Token Secret”.
  * You can create more than 1 application and get more keys in order not to wait for [Twitter API limitation](#twitter-api-limits)s.

* After obtaining the API Keys, put them in the [ApiKey.java](src/main/java/com/umutcanbolat/twproject/ApiKey.java)
  * <img src="https://user-images.githubusercontent.com/10065235/38103796-04fd1676-3390-11e8-9ee2-3d9d3556ee59.png">

## Twitter API Limits
* Twitter API allows us to retrieve up to the latest 3200 tweets of each user only.
* It is not allowed to send more than 900 requests to `GET "/statuses/user_timeline"` endpoint in 900 seconds. (For each key). 
Because of that, the program uses more than 1 apiKey to continue downloading data without stopping. 
* Also, we cannot send more than 15 requests to `GET "/friends/ids"` endpoint in 15 minutes.
* For more info about all endpoints and limitations, visit the developer website: https://developer.twitter.com/en/docs/basics/rate-limits

## Libraries

* [Twitter4j](http://twitter4j.org/en/) - Twitter API for Java
* [Jackson](https://github.com/codehaus/jackson) - For JSON Serialization

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Authors

* **Umut Canbolat** - *Initial work* - [umutcanbolat](https://github.com/umutcanbolat)

## License

This project is licensed under the Apache License 2.0 (AL 2.0) - see the [LICENSE.md](LICENSE.md) file for details
