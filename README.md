# Twitter-Data-Acquisition-Software

A tool which helps users to gather a kind of big data from Twitter using Twitter API.

## Description
At the beginning, the program asks a Twitter username as input. After that it starts downloading the last 
3200 tweets of the user and writes down them in a JSON file.

After it finishes downloading, it gathers list of the people followed by the given user.
And starts downloading the last 3200 tweets of these bunch of users too. 
This steps follow each other in a recursive way until user-specified stepcount limit exceeded.

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

* **Umut Canbolat** - *Initial work* - [Krupunkt](https://github.com/krupunkt)

## License

This project is licensed under the Apache License 2.0 (AL 2.0) - see the [LICENSE.md](LICENSE.md) file for details
