import java.io.IOException; 
import java.sql.*;
import java.util.ArrayList;

public class DAO {
	Connection con = null;

	public void connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/Q3wF9QbrBX", "Q3wF9QbrBX", "B9bRsouGAF");
		        
                } catch (Exception e) {
			System.out.println(e);
                        
		}
                
	}

	public void cred(Company comp) {
		String query = "insert into organisations values (?,?,?)";
		PreparedStatement pst;
		try {
			pst = con.prepareStatement(query);
			pst.setString(1, comp.Org);
			pst.setString(2, comp.username);
			pst.setString(3, comp.password);
			pst.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void createTable(Company comp) {
		String query = "CREATE TABLE " + "`O_" + comp.Org
				+ "` ( S_name VARCHAR(50) NOT NULL PRIMARY KEY, S_user VARCHAR(50) NOT NULL , S_pass VARCHAR(50) NOT NULL, Current_users INTEGER NOT NULL DEFAULT 0, Total_users INTEGER NOT NULL DEFAULT 0)";

		try {
			Statement st = con.createStatement();
			st.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void insertTable(Services S) {
		String query = "insert into " + "`O_" + S.comp + "` (S_name,S_user,S_pass) values (?,?,?); ";
		PreparedStatement pst;
		try {
			pst = con.prepareStatement(query);
			pst.setString(1, S.sName);
			pst.setString(2, S.sUser);
			pst.setString(3, S.sPass);
			pst.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}

		String q = "CREATE TABLE `S_" + S.sName
				+ "` ( C_id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT, C_name VARCHAR(50) NOT NULL)";

		try {
			Statement st = con.createStatement();
			st.executeUpdate(q);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String al = "ALTER TABLE `S_" + S.sName + "` AUTO_INCREMENT=1001";
		try {
			Statement st = con.createStatement();
			st.executeUpdate(al);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean isVerified(String user, String pass, char c, String tName) {
		String query = "SELECT * FROM " + tName + " WHERE " + c + "_user LIKE " + "'" + user + "'" + " AND " + c
				+ "_pass LIKE " + "'" + pass + "'" + "";

		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean checkSetup() {
		String query = "SELECT * FROM organisations";

		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public String getOName() {
		String query = "SELECT * FROM organisations";
		String name = "";
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			rs.next();
			name = rs.getString(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return name;
	}

	public ArrayList servicesList(String compName) {
		String query = "SELECT S_name FROM `O_" + compName+"`";
		ArrayList<String> list = new ArrayList<>();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				list.add(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	

	public void viewAll(String compName) {
		String q1 = "SELECT * FROM `O_" + compName+"`";

		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(q1);
			while (rs.next()) {
				System.out.printf(" | %-30s | %-30s | %-12d | %-11d|",rs.getString(1) , rs.getString(2),rs.getInt(4),rs.getInt(5) );
			        System.out.println();
                        }

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void removeservice(String compName, String remove) {
		String q1 = "DELETE FROM `O_" + compName + "` WHERE S_name LIKE '" + remove + "' ";
		String q2 = "DROP TABLE `S_" + remove + "` ";
		try {
			Statement st = con.createStatement();
			st.executeUpdate(q1);
			st.executeUpdate(q2);
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public ArrayList<String> operatorList(String compName) {
		String query = "SELECT * FROM `O_" + compName+"`";
		ArrayList<String> list = new ArrayList<>();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				list.add(rs.getString(1));
				list.add(rs.getString(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public void updateCredentials(Services s) {
		String q2 = "UPDATE `O_" + s.comp + "` SET S_user = '" + s.sUser + "', S_pass = '" + s.sPass
				+ "' WHERE S_name LIKE '" + s.sName + "' ";
		try {
			Statement st = con.createStatement();
			st.executeUpdate(q2);
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public boolean checkString(String S) {

		for (int i = 0; i < S.length(); i++) {
			if (Character.isDigit(S.charAt(i)) == false) {
				return false;
			}
		}
		    return true;
	}

	public void pause(int i) {
		  try {
              Thread.sleep(i);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
		
	}
	

     public void waitMsg(String msg,int t,int n)throws IOException, InterruptedException {
        System.out.print(msg);
        for (int i = 0; i < n; i++) {
        	this.pause(t);
            System.out.print(".");	
	}
        this.clear();
          
      }

      public static void clear() throws IOException, InterruptedException{  
                  new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();  
      }  

}