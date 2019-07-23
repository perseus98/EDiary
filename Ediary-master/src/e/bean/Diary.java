
package e.bean;

import java.sql.Date;

/**
 *
 * @author perseus85
 */
public class Diary {
    private String uid;
    private Date edate;
    private String descrip;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Date getEdate() {
        return edate;
    }

    public void setEdate(Date edate) {
        this.edate = edate;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }
}
