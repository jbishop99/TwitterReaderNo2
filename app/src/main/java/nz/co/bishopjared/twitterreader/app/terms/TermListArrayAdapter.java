package nz.co.bishopjared.twitterreader.app.terms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import nz.co.bishopjared.twitterreader.app.R;

/**
 * Created by jxbishop on 29/06/2014.
 */
public class TermListArrayAdapter extends ArrayAdapter<Term> {

    private ArrayList<Term> termList;

    public TermListArrayAdapter(Context context, int textViewResourceId, ArrayList<Term> objects) {
        super(context, textViewResourceId, objects);
        termList = objects;
    }

    @Override
    public Term getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int layoutRes;

        // assign the view we are converting to a local variable
        View v = convertView;


        layoutRes = R.layout.term_list_item;
/*        if (termList.size() == position) {
            layoutRes = R.layout.term_add_button_layout;
        } else {
            layoutRes = R.layout.term_list_item;
        }*/

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(layoutRes, null);
        }

		/*
		 * Recall that the variable position is sent in as an argument to this method.
		 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
		 * iterates through the list we sent it)
		 *
		 * Therefore, i refers to the current Item object.
		 */
        Term i = termList.get(position);

        if (i != null) {

            // This is how you obtain a reference to the TextViews.
            // These TextViews are created in the XML files we defined.

            TextView tt = (TextView) v.findViewById(R.id.textViewTerm);

            // check to see if each individual textview is null.
            // if not, assign some text!
            if (tt != null){
                tt.setText(i.getQuery_String().toString());
            }
        }

        // the view must be returned to our activity
        return v;
    }
}
