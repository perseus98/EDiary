
package e.dao;

import ECUtils.BaseDAO;
import static ECUtils.BaseDAO.closeCon;
import static ECUtils.BaseDAO.getCon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import e.bean.User;
import e.diary.MainFXMLController;

/**
 *
 * @author perseus85
 */
public class UserDAO extends BaseDAO{ 
        @SuppressWarnings("CallToPrintStackTrace")
        public static User validate(String name, String passwd) {
        User res = new User();
        res.setUid("0");
        res.setUname("NOTFOUND");
        res.setPass("NOTFOUND");
        Connection con = null;
        try {
            con = getCon();
            String sql = " select * from users where uname = ? and pass = ? ";
            PreparedStatement st = con.prepareStatement(sql);
            int i = 1;
            st.setString(i++, name);
            st.setString(i++, passwd);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                res.setUid(rs.getString("uid"));
                res.setUname(rs.getString("uname"));
                res.setPass(rs.getString("pass"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeCon(con);
        }
        return res;
    }
        
        
        @SuppressWarnings("CallToPrintStackTrace")
        public static LinkedList<String> getUnames() {
        LinkedList<String> res = new LinkedList();
        res.addFirst("New/Existing User");
        Connection con = null;
        try {
            con = getCon();
            String sql = " select uname from users ";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
               res.add(rs.getString("uname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeCon(con);
        }
        return res;
    } 
        
        
       @SuppressWarnings("CallToPrintStackTrace")
        public static void registerUser(String uname, String passwd) {
        Connection con = null;
        try {
            con = getCon();
            String sql = " insert into users ( uname, pass ) "
                                  + " values (  ?   ,  ?   ) ";
            PreparedStatement st = con.prepareStatement(sql);
            int i = 1;
            st.setString(i++, uname);
            st.setString(i++, passwd);
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeCon(con);
        }
    }
        
        
        public static User getUserDetails( int id){
            User res = new User();
            Connection con = null;
        try {
            con = getCon();
            String sql = " select * from users ";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
               res.setUid(rs.getString("uid"));
               res.setUname(rs.getString("uname"));
               res.setPass(rs.getString("pass"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeCon(con);
        }
            return res; 
        }
        
       
}
