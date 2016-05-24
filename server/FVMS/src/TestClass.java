import connection.Connector;
import db.UsersDB;


public class TestClass {
	public static void main(String args[]){
		Connector connector = Connector.getInstance();
		UsersDB.getInstance().insertUser("co", "password", "email3@email.com");
	}
}
