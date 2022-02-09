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

	public int addcustomer(String cname, String sname, String compName) {
        
		String query = "insert into `" + sname + "` (C_name) values ('" + cname + "') ";
		String q1 = "UPDATE `O_" + compName + "` SET current_users = current_users+1 WHERE S_name like '"
				+ sname.substring(2) + "' ";
		int tocken = 0;
		try {
			Statement st = con.createStatement();
			st.executeUpdate(query);
			st.executeUpdate(q1);
		} catch (Exception e) {
			System.out.println(e);
		}

		String q2 = "SELECT * FROM `" + sname + "` ORDER BY c_id DESC";
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(q2);
			rs.next();
			tocken = rs.getInt(1);

		} catch (Exception e) {
			System.out.println(e);
		}
		return tocken;

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