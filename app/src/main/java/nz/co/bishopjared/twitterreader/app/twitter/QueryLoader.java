package nz.co.bishopjared.twitterreader.app.twitter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import nz.co.bishopjared.twitterreader.app.R;
import nz.co.bishopjared.twitterreader.app.StoreJson.JSonIO;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * Created by jxbishop on 29/06/2014.
 */
public class QueryLoader extends AsyncTask<String, Void, String> {

    String pQueryStr;
    int pNoOfItems;
    Twitter pTwitter;
    ListView pListView;
    Context pContext;
    int pMemClass;

    private List<twitter4j.Status> statusList;

    public QueryLoader(Context context, String queryStr, int noOfItems, Twitter twitter,
                       ListView listView, int memClass) {
        super();
        pQueryStr = queryStr;
        pNoOfItems = noOfItems;
        pTwitter = twitter;
        pListView = listView;
        pContext = context;
        pMemClass = memClass;
    }

    private void RunQuery(String queryStr, int noOfItems, Twitter twitter) throws TwitterException {

        Query query = new Query(queryStr);
        query.setCount(noOfItems);
        QueryResult result = null;
        try {
            result = twitter.search(query);
        } catch (TwitterException e) {
            e.printStackTrace();
            result = null;
        }
        statusList = result.getTweets();
    }

    @Override
    protected String doInBackground(String... strings) {

        String taskMessage = "";
        try {
            RunQuery(pQueryStr, pNoOfItems, pTwitter);
            Boolean bl = JSonIO.WriteToFile(statusList, pQueryStr, pContext);
        } catch (TwitterException e) {
            e.printStackTrace();
            taskMessage = "Twitter Exception";
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        CharSequence text = taskMessage;
//        int duration = Toast.LENGTH_SHORT;
//        Looper.prepare();
//        Toast toast = Toast.makeText(pContext, taskMessage, duration);
//        toast.show();
        return taskMessage;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        //Read from File
        super.onPostExecute(s);
        ArrayList<Tweet> messageList = null;
        try {
            messageList = JSonIO.ReadFromFile(pContext, pQueryStr);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TwitterException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        StatusListAdapter listAdapter = new StatusListAdapter(pContext,
                R.layout.tweet_list_item, messageList, pMemClass);
        pListView.setAdapter(listAdapter);
    }
}

class StatusListAdapter extends ArrayAdapter<Tweet> {

    private ArrayList<Tweet> items;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private Context pContext;
    public StatusListAdapter(Context context, int textViewResourceId,
                             ArrayList<Tweet> items, int memClass) {
        super(context, textViewResourceId, items);
        this.items = items;
        pContext = context;

        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(context)
                .memoryCache(new LruMemoryCache(1024 * 1024 * memClass / 8))
                .threadPriority(Thread.MAX_PRIORITY)
                .denyCacheImageMultipleSizesInMemory()
                .threadPoolSize(5)
                .tasksProcessingOrder(QueueProcessingType.LIFO);

        ImageLoaderConfiguration config = builder.build();

        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .imageScaleType(ImageScaleType.NONE)
                .build();

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)pContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.tweet_list_item, null);
        }
        Tweet o = items.get(position);
        if (o != null) {
            TextView tn = (TextView) v.findViewById(R.id.textUser);
            TextView tt = (TextView) v.findViewById(R.id.textStatus1);
            ImageView bt = (ImageView) v.findViewById(R.id.imageView1);
            if (tt != null && tn != null) {
                tn.setText("Name: "+ o.getUser() + " Date: " + o.getDateCreated() + " "
                + o.getUser());
                tt.setText("Status: "+ o.getText());
                if(bt != null){
                    // show The Image
                    String imgURL = o.getUserImageURI();

                        imageLoader.displayImage(imgURL, bt, options, new ImageLoadingListener() {

                        @Override
                        public void onLoadingStarted(String imageUri,
                                                     View view) {
                            view.setBackgroundColor(android.R.color.background_dark);
                            view.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onLoadingFailed(String imageUri,
                                                    View view, FailReason failReason) {
                            view.setBackgroundColor(android.R.color.background_dark);
                        }

                        @Override
                        public void onLoadingComplete(String imageUri,
                                                      View view, Bitmap loadedImage) {
                            // TODO Auto-generated method stub
                            view.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onLoadingCancelled(String imageUri,
                                                       View view) {
                            view.setBackgroundColor(android.R.color.background_dark);

                        }
                    });

                }
            }
        }
        return v;
    }
}