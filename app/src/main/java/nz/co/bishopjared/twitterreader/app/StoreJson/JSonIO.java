package nz.co.bishopjared.twitterreader.app.StoreJson;

import android.content.Context;
import android.util.JsonWriter;
import org.json.JSONException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import twitter4j.Status;

/**
 * Created by jxbishop on 11/07/2014.
 * Helper class to read and write Tweet information to a JSON file
 */
public class JSonIO {

    public static boolean WriteToFile(List<Status> StatusList, String queryString,
                               Context context) throws JSONException, IOException {

        String dir = context.getFilesDir().toString() + "/" + queryString + ".txt";

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
        writer.name("user").value(message.getUser().toString());
        writer.name("isRetweet").value(message.isRetweet());
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
