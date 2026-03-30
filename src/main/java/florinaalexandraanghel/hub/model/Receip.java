package florinaalexandraanghel.hub.model;

import java.util.Date;

public class Receip {
    private int receipNumber;
    private Date dateReceip;
    private boolean receipStatus;

    Receip(){

    }

    public int getReceipNumber() {
        return receipNumber;
    }

    public void setReceipNumber(int receipNumber) {
        this.receipNumber = receipNumber;
    }

    public Date getDateReceip() {
        return dateReceip;
    }

    public void setDateReceip(Date dateReceip) {
        this.dateReceip = dateReceip;
    }

    public boolean isReceipStatus() {
        return receipStatus;
    }

    public void setReceipStatus(boolean receipStatus) {
        this.receipStatus = receipStatus;
    }
}
