package nz.co.bishopjared.twitterreader.app.twitter;

import android.annotation.SuppressLint;
import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

import nz.co.bishopjared.twitterreader.app.StoreJson.JSonIO;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterConnection {

	public Twitter twitter;

	public TwitterConnection() {

		ConfigurationBuilder cb = new ConfigurationBuilder();
	    cb.setDebugEnabled(true)
	            .setOAuthConsumerKey("iCioTRuAKCGBNKs8hsmphcZvB")
	            .setOAuthConsumerSecret("v4Vkjd7KLfsU8LZhAJhnLAsSTuXcIvhCIaJLXnUrYRABv9iEj0")
	            .setOAuthAccessToken("50594572-YuQSiWwPs7gq4PUkG5ShaXMhdFEfbJKm8MNiuDjfq")
	            .setOAuthAccessTokenSecret("Cf4cfgZZtHQekWOGMvo7oPLbLhrApU21MiQGoEtciwhZJ")
	            .setJSONStoreEnabled(true);

	    TwitterFactory tf = new TwitterFactory(cb.build());
	    twitter = tf.getInstance();
    
	}

	@SuppressLint("NewApi")
	public List<Status> RunQuery(String queryStr, int noOfItems) throws TwitterException {

		Query query = new Query(queryStr);
		query.setCount(noOfItems);
	    QueryResult result = null;
		try {
			result = twitter.search(query);
		} catch (TwitterException e) {
			e.printStackTrace();
			result = null;
		}
		List<Status> messageList = result.getTweets();




		return messageList;
	}
}