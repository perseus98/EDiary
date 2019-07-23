package ECUtils;
public class ECConst {
	public static String DB_NAME ="fx_ediary";
	public static String DB_HOST="localhost";
	public static String DB_USER="root";
	public static String DB_PASS ="";
	public static String SQLS[] = 
	{
		"create table diary ( eid INT NOT NULL AUTO_INCREMENT, uid INT NOT NULL, edate date NOT NULL, descrip varchar(50) NOT NULL, PRIMARY KEY (eid))",	
		"create table users ( uid INT NOT NULL AUTO_INCREMENT, uname varchar(40), pass varchar(50), PRIMARY KEY (uid))"
	};
}
