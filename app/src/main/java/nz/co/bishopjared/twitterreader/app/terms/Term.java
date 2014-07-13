package nz.co.bishopjared.twitterreader.app.terms;

import java.util.Date;

/**
 * Created by jxbishop on 29/06/2014.
 */
public class Term {

    public String Query_String = "";
    public boolean AlertEnabled = false;
    public Date RemindDateTime;

    public Term(String query_String) {
        Query_String = query_String;
    }

    public String getQuery_String() {
        return Query_String;
    }

    public void setQuery_String(String query_String) {
        Query_String = query_String;
    }
}
