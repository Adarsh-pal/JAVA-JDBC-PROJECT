import java.io.IOException; 
import java.util.ArrayList;
import java.util.Scanner;

public class Client {

	static Scanner sc = new Scanner(System.in);
	static DAO obj = new DAO();
	static String counterName;
	static String CompName;
	public static void main(String[] args) throws IOException, InterruptedException{
		obj.connect();
                obj.clear();
		clientLogin(false);
		
	}

	private static void mainPanel(boolean invalid) throws IOException, InterruptedException{
		String details="";
		System.out.print("\n\nCurrent Customer:  ");
                if(obj.currCustDet(counterName).size()==0) {
			System.out.print("No customer in queue\n\n");
		}
		else {
			details= obj.currCustDet(counterName).get(0);
                        System.out.println("\n\n---------------------------------");
		        System.out.println("Id: \t\t"+details.substring(0,4));
		        System.out.println("Name: \t\t"+details.substring(4));
                        System.out.println("---------------------------------");
                }
		System.out.println("\n---Menu---\n");
		System.out.println("1.Veiw All Customers");
		System.out.println("2.Remove Current");
		System.out.println("3.Log Out");
                if(invalid){
                  System.out.println("\n !!! Invalid Input Please Try Again !!! ");
                }
                System.out.print("\nSelect An Option To Continue: ");
                String option = sc.next();
                sc.nextLine();
		boolean check = obj.checkString(option);
		int select = 0;
		if (check) {
			select = Integer.parseInt(option);
		}
		if (check && (select>0 && select<4)) {
		   if(select==1){
                       printMenu(CompName,"VEIW ALL CUSTOMERS");
                       printAll(obj);
                   }
                   else if(select==2){
                        printMenu(CompName,"REMOVE CUSTOMERS");
              
                        if(obj.removeCurr(counterName,CompName)) {
                                System.out.print("Removing Customer");
                                obj.pause(1000);
                                System.out.print(".");
                                obj.pause(1000);
                                System.out.print(".");
                                obj.pause(1000);
                                System.out.print(".");
				System.out.println("\nCustomer Dispatched Successfully");
			}
			else {
				System.out.println("\nNo More customer In The Queue");
			}
                   }
                   else{
                        obj.waitMsg("Saving Data",1000,3);
                        obj.waitMsg("Signing Out",1000,5);
			System.out.println("\n-----------Log Out Succesfully-------------");
			System.exit(0);         
                   }	
		} else {
			obj.clear();
                        printMenu(CompName,counterName.toUpperCase()+" Dashboard");
                        mainPanel(true);
		}
                System.out.println("\nPress Any Key To Go back To Previous Menu: ");
                String str= sc.next();
                sc.nextLine();
		printMenu(CompName,counterName.toUpperCase()+" Dashboard");
                mainPanel(false);
		
	}

	private static void printAll(DAO obj) {
		System.out.println("\n Id:\t|\tName\n");
		ArrayList<String> list= obj.currCustDet(counterName);
		for (String str : list) {
			System.out.println(str.substring(0,4)+"\t|\t"+str.substring(4));
		}
	}

	private static void clientLogin(boolean invalid) throws IOException, InterruptedException{ 
		CompName= obj.getOName();
		System.out.println("\n\n ----------------------------------------");
		System.out.println("\t      WELCOME TO " + CompName + "      \t");
		System.out.println(" ----------------------------------------\n");
		if (invalid) {
			System.out.println("\nIncorrect username or password, please try again !!!\n");
		}
                boolean verified= false;
		while(!verified) {
			System.out.print("\nEnter Username: ");
		        String user = sc.nextLine();
		        System.out.print("\nEnter Password: ");
		        String pass = sc.nextLine();
			verified=obj.isVerified(user,pass,'S',"`O_"+CompName.toUpperCase()+"`");
			if(!verified) {
				obj.clear();
                	        clientLogin(true);
			}
			else {
				obj.waitMsg("Signing in Securely",500,3);
                                System.out.println("\n---------------Login Successfully-----------------");
				counterName= obj.getCName(user,CompName);
                                printMenu(CompName,counterName.toUpperCase()+" Dashboard");
                                mainPanel(false);
			}
                      
		}
                
	}
 
        public static void printMenu(String CompName, String heading)throws IOException, InterruptedException{
                obj.clear();
                System.out.println("\n\n ----------------------------------------");
		System.out.println("\t      WELCOME TO " + CompName + "      \t");
		System.out.println(" ----------------------------------------\n");
		System.out.println("\n\t<---------"+heading.toUpperCase()+"----------->\n");
        }
    
}
