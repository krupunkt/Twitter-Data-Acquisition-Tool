# Twitter-Data-Acquisition-Software

A tool which helps users gather a kind of big data from Twitter for text analysis using Twitter API.

## Description
To obtain a huge tweet data, the program uses social network of Twitter users to reach bigger populations. It starts with 1 user, and jumps to other people on the social network of this user.

At the beginning, the program asks for a Twitter username as input. After that it starts downloading up to the latest 
3200 tweets ([API Limitation](#twitter-api-limits)) of the user and writes them down in a JSON file.

After it finishes downloading, it gathers list of the people followed by the given user.
And starts downloading up to the latest 3200 tweets of these bunch of users too. 
These steps follow each other in a recursive way until user-specified stepcount limit is exceeded.

## How To Use?
* First of all you need to have a Twitter account and create an application on the Twitter developer site. 

  * Go to https://apps.twitter.com/ and log in with your Twitter Account.
  * Click “Create New App”.
  * Fill out the form, agree to the terms, and click “Create your Twitter application”
  * In the next page, click on “Keys and Access Tokens” tab, and copy your “Consumer Key” and “Consumer Secret”. Scroll down and click “Create my access token”, and copy your “Access Token” and “Access Token Secret”.
  * You can create more than 1 application and get more keys in order not to wait for [Twitter API limitation](#twitter-api-limits)s.

* After obtaining API Keys, put them in the [ApiKey.java](src/main/java/com/umutcanbolat/twproject/ApiKey.java)
  * <img src="https://i.hizliresim.com/Mdlon2.png">

## Twitter API Limits
* Twitter API allows us to retrieve up to last 3200 tweets of each user only.
* It is not allowed to send more than 900 requests to "/statuses/user_timeline" endpoint in 900 seconds. (For each key). 
Because of that, program uses more than 1 apiKey to continue downloading data without stop. 

## Libraries

* [Twitter4j](http://twitter4j.org/en/) - Twitter API for Java
* [Jackson](https://github.com/codehaus/jackson) - For JSON Serialization

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Authors

* **Umut Canbolat** - *Initial work* - [umutcanbolat](https://github.com/umutcanbolat)

## License

This project is licensed under the Apache License 2.0 (AL 2.0) - see the [LICENSE.md](LICENSE.md) file for details
