package nz.co.bishopjared.twitterreader.app;

import nz.co.bishopjared.twitterreader.app.twitter.QueryLoader;
import nz.co.bishopjared.twitterreader.app.twitter.TwitterConnection;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

@SuppressLint("NewApi")
public class MainActivity extends ListActivity  {

    public TwitterConnection twitterconnect;
    ListView listView1;
    int memClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweet_list_main);

        if (android.os.Build.VERSION.SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            //String query = intent.getStringExtra(SearchManager.QUERY);
        }

        memClass = ( ( ActivityManager )this.getSystemService( Context.ACTIVITY_SERVICE ) )
                .getMemoryClass();


        listView1 = (ListView) findViewById(android.R.id.list);

        twitterconnect = new TwitterConnection();

        //List<String> stringList = new ArrayList<String>();

        final String strQuery = intent.getExtras().getString("query");

        QueryLoader queryLoader = new QueryLoader(getApplicationContext(), strQuery, 15,
                twitterconnect.twitter, listView1, memClass);
        queryLoader.execute();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
	

