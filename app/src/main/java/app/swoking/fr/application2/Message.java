package app.swoking.fr.application2;

import java.util.Date;

public class Message {

    private String mtxt;
    private String mSender;
    private Date   mDate;

    public String getMtxt() {
        return mtxt;
    }

    public void setMtxt(String mtxt) {
        this.mtxt = mtxt;
    }

    public String getmSender() {
        return mSender;
    }

    public void setmSender(String mSender) {
        this.mSender = mSender;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }
}
