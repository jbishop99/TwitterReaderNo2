package nz.co.bishopjared.twitterreader.app.terms;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import nz.co.bishopjared.twitterreader.app.MainActivity;
import nz.co.bishopjared.twitterreader.app.R;

/**
 * Created by jxbishop on 29/06/2014.
 */
public class TermsActivity extends Activity {

    ListView listView1;
    EditText editText;
    Button button;
    Button addButton;

    TermListArrayAdapter termListArrayAdapter;
    ArrayList<Term> termArrayList = new ArrayList<Term>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.terms_list_main);
        super.onCreate(savedInstanceState);

        listView1 = (ListView) findViewById(R.id.listView1);
        editText = (EditText) findViewById(R.id.editText1);
        button = (Button) findViewById(R.id.buttonAddTerm);

        /*termArrayList.add(new Term(""));*/



        termListArrayAdapter = new TermListArrayAdapter(this, R.id.textViewTerm, termArrayList);
        listView1.setAdapter(termListArrayAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                termListArrayAdapter.add(new Term(editText.getText().toString()));

            }
        });

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Context context = getApplicationContext();

                Term term = (Term) listView1.getAdapter().getItem(i);

                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("query", term.getQuery_String());
                startActivity(intent);

            }
        });

    }

}
