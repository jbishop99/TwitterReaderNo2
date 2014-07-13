package nz.co.bishopjared.twitterreader.app.twitter;

import java.util.Date;


public class Tweet {

    long id;
	Date dateCreated;
    String user;

    public String getUserImageURI() {
        return userImageURI;
    }

    public void setUserImageURI(String userImageURI) {
        this.userImageURI = userImageURI;
    }

    String userImageURI;
	String text;
	boolean isRetweeted;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isRetweeted() {
        return isRetweeted;
    }

    public void setRetweeted(boolean isRetweeted) {
        this.isRetweeted = isRetweeted;
    }
}
