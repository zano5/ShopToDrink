package za.co.codetribe.shoptodrink.classes;

import java.io.Serializable;

/**
 * Created by ProJava on 2/3/2017.
 */

public class Feedback implements Serializable {

    private String userID;
    private String subject;
    private String comment;

    public Feedback() {
        super();
    }

    public Feedback(String comment, String subject, String userID) {
        this.comment = comment;
        this.subject = subject;
        this.userID = userID;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
