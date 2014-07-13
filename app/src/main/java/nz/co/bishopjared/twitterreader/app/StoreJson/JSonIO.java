package nz.co.bishopjared.twitterreader.app.StoreJson;

import android.content.Context;
import android.util.JsonReader;
import android.util.JsonWriter;

import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import nz.co.bishopjared.twitterreader.app.twitter.Tweet;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.User;

/**
 * Created by jxbishop on 11/07/2014.
 * Helper class to read and write Tweet information to a JSON file
 */
public class JSonIO {

    public static ArrayList<Tweet> ReadFromFile(Context context, String queryString) throws IOException, ParseException, TwitterException {

        InputStream inputStream = new FileInputStream(context.getExternalFilesDir(null).toString() + "/" + queryString + ".tr" );
        JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream));

        return readMessagesArray(jsonReader);
    }


    private static ArrayList<Tweet> readMessagesArray(JsonReader reader) throws IOException, TwitterException, ParseException {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();

        reader.beginArray();
        while (reader.hasNext()) {
            tweets.add(readMessage(reader));
        }
        reader.endArray();
        return tweets;
    }

    private static Tweet readMessage(JsonReader reader) throws IOException, ParseException, TwitterException {
        long id = -1;
        String text = null;
        Date dateCreated = null;
        User user = null;
        boolean isRetweeted = false;
        Tweet tweet = new Tweet();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id")) {
                tweet.setId(reader.nextLong());
            } else if (name.equals("text")) {
                tweet.setText(reader.nextString());
            } else if (name.equals("dateCreated")){
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                tweet.setDateCreated(dateFormat.parse(reader.nextString()));
            } else if (name.equals("user")) {
                tweet.setUser(reader.nextString());
            } else if (name.equals("userImageURI")) {
                tweet.setUserImageURI(reader.nextString());
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return tweet;
    }


    public static boolean WriteToFile(List<Status> StatusList, String queryString,
                               Context context) throws JSONException, IOException {

        String dir = context.getExternalFilesDir(null).toString() + "/" + queryString + ".tr";

        JsonWriter writer = new JsonWriter(getFileWriter(dir));
        writer.setIndent("  ");
        writeMessagesArray(writer, StatusList);
        writer.close();

        return true;
    }


    private static void writeMessagesArray(JsonWriter writer, List<Status> messages) throws IOException {
        writer.beginArray();
        for (Status message : messages) {
            writeMessage(writer, message);
        }
        writer.endArray();
    }

    private static void writeMessage(JsonWriter writer, Status message) throws IOException {
        writer.beginObject();
        writer.name("id").value(message.getId());
        writer.name("text").value(message.getText());
        writer.name("dateCreated").value(message.getCreatedAt().toString());
        writer.name("user").value(message.getUser().getName());
        writer.name("isRetweet").value(message.isRetweet());
        writer.name("userImageURI").value(message.getUser().getProfileImageURL());
        writer.endObject();


    }

    private static FileWriter getFileWriter(String dir) throws IOException {

        File file = new File(dir);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        return new FileWriter(dir);
    }

}
