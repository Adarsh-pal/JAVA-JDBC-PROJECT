import java.util.*;
import java.io.IOException; 
public class Admin {

	static Scanner sc = new Scanner(System.in);
	static DAO obj = new DAO();

	static String compName;

	public static void main(String[] args) throws IOException, InterruptedException{
		obj.connect();
                obj.clear();
		if (obj.checkSetup()) {
                        compName = obj.getOName();
			adminPanel(false);
		} else {
			initialSet();
		}

	}

	public static void adminPanel(boolean invalid) throws IOException, InterruptedException{
		System.out.println("\n\n ----------------------------------------");
		System.out.println("\t      WELCOME TO " + compName + "      \t");
		System.out.println(" ----------------------------------------\n");
		boolean verified = false;
		if (invalid) {
			System.out.println("\nIncorrect username or password, please try again !!!\n");
		}

		System.out.print("\nEnter Username: ");
		String user = sc.nextLine();
		System.out.print("\nEnter Password: ");
		String pass = sc.nextLine();
		verified = obj.isVerified(user, pass, 'o', "organisations");
		if (!verified) {
                        obj.clear();
                	adminPanel(true);
		} else {
			System.out.println("\n---------------Login Successfully-----------------");
		}
                obj.waitMsg("Signing in Securely",500,3);
		compName = obj.getOName();
                printMenu(compName,"MENU");
		menu(compName,false);

	}

	private static void menu(String compName,boolean invalid) throws IOException, InterruptedException{
                 System.out.println("1.View All\t\t2.Add Counter\n3.Remove Counter\t4.Update Counter Credentials\n5.Log Out");    
                if(invalid){
                   System.out.println("\n!!! Invalid Input, Please Try Again!\n");
                }
                Company comp1 = new Company();
		comp1.Org = compName;
                String option = sc.next();
		boolean check = obj.checkString(option); 
		int select = 0;
		if (check) {
			select = Integer.parseInt(option);                
		switch (select) {
		case 1: 
                        printMenu(compName,"VEIW ALL");
			System.out.println(" -----------------------------------------------------------------------------------------------");
			System.out.println(" |          SERVICE               |            OPERATOR            | WAITING-LIST | TOTAL-DONE |");
			System.out.println(" -----------------------------------------------------------------------------------------------");
			obj.viewAll(compName);
			System.out.println(" -----------------------------------------------------------------------------------------------");
			break;

		case 2: sc.nextLine();
                        printMenu(compName,"ADD COUNTER");
			Services S = new Services(comp1);
                        System.out.print("\nEnter Name Of Service: ");
			S.sName = sc.nextLine();
			addCounter(S);
			System.out.println("\n------"+S.sName.toUpperCase()+" Added Successfully------");
			break;
		case 3: printMenu(compName,"REMOVE COUNTER");
			removeService(compName,false);
			break;
		case 4:
                        printMenu(compName,"UPDATE CREDENTIALS");
			updateCredentials(comp1,false);
			break;
		case 5: 
                        obj.waitMsg("Saving Data",1000,3);
                        obj.waitMsg("Signing Out",1000,5);
			System.out.println("\n-----------Log Out Succesfully-------------");
                        System.exit(0);
		default:
                        printMenu(compName,"MENU");
                        menu(compName,true);
			break;
		}
               }
               else{
                 printMenu(compName,"MENU");
                 menu(compName,true);
               }
                  System.out.println("\nPress Any Key To Go back To Previous Menu: ");
                  String str= sc.next();
                  sc.nextLine();
		  obj.clear();
                  printMenu(compName,"MENU");
                  menu(compName,false);
                 
	}
        
        public static void printMenu(String compName, String heading)throws IOException, InterruptedException{
                obj.clear();
                System.out.println("\n\n ----------------------------------------");
		System.out.println("\t      WELCOME TO " + compName + "      \t");
		System.out.println(" ----------------------------------------\n");
		System.out.println("\n\t<---------"+heading.toUpperCase()+"----------->\n");
        }

	private static void updateCredentials(Company comp,boolean invalid) throws IOException, InterruptedException{
		System.out.println("Available Operators: \n");
		System.out.println("Sno.   Service\t\t\tOperators\n");
		ArrayList<String> list = new ArrayList<String>();
		list = obj.operatorList(compName);
		int j = 1;
		for (int i = 0; i < list.size(); i = i + 2) {
			System.out.println((j) + ".   " + list.get(i) + "\t\t\t" + list.get(i + 1));
			j++;
		}
		System.out.println("\n---Select Operator You Want To Modify---\n");
		if(invalid){
                  System.out.println(" !!! Invalid Input Please Try Again !!! ");
                }
                String option = sc.next();
                sc.nextLine();
		boolean check = obj.checkString(option);
		int select = 0;
		if (check) {
			select = Integer.parseInt(option);
		}
		if (check && (select < j && select > 0)) {
			Services S = new Services(comp);
			S.sName = list.get(2 * select - 2).toUpperCase();
			System.out.println("\nService Selected : "+S.sName);
			System.out.print("\nEnter New Username For "+S.sName+" : ");
			S.sUser = sc.nextLine();
			System.out.print("\nEnter New Password For "+S.sName+" : ");
			S.sPass = sc.nextLine();
			obj.updateCredentials(S);
			System.out.println("\n"+S.sName+" Updated Succesfully");
		} else {
                        printMenu(compName,"UPDATE CREDENTIALS");
                        updateCredentials(comp,true);
		}

	}

	private static void removeService(String compName,boolean invalid) throws IOException, InterruptedException{
      		ArrayList<String> list = new ArrayList<String>();
		list = obj.servicesList(compName);
                if(list.size()==0){
                   System.out.println("No Counter to remove!\n");
                   return;
                }
                else{
                     System.out.println("Available Services : \n");
		     for (int i = 0; i < list.size(); i++) {
			System.out.println((i + 1) + ". " + list.get(i).toUpperCase());
		     }
                }
                
		if(invalid){
                  System.out.println("\n !!! Invalid Input Please Try Again !!! ");
                }
                System.out.print("\nSelect Name Of Service You Want To Remove: ");
                String option = sc.next();
                sc.nextLine();
		boolean check = obj.checkString(option);
		int select = 0;
                if (check) {
			select = Integer.parseInt(option);
		}
		if (check && (select >0 && select <= list.size())) {
		    obj.removeservice(compName, list.get(select - 1));
         	    System.out.println("\n"+list.get(select - 1).toUpperCase()+ " Removed Succesfully");	
		} 
                else {
	
                        printMenu(compName,"REMOVE COUNTER");
                        removeService(compName,true);
		}

	}

	public static void initialSet() throws IOException, InterruptedException{
		System.out.println("--------------WELCOME TO ORGANISATION CROWD MANAGEMENT SYSTEM----------");
		Company comp1 = new Company();
		System.out.println("\n---------------------------");
		System.out.println("INTIALISING FIRST TIME SETUP");
		System.out.println("---------------------------\n");
		System.out.print("\nEnter Organisation name: ");
		comp1.Org = sc.nextLine().toUpperCase();
		System.out.print("\nCreate Username for " + comp1.Org + ": ");
		comp1.username = sc.nextLine();
		System.out.print("\nCreate Password for " + comp1.Org + ": ");
		comp1.password = sc.nextLine();
		System.out.println("\n------Login Credentials Generated Successfully------");
		obj.cred(comp1);
		obj.createTable(comp1);
                obj.clear();
                System.out.println("--------------WELCOME TO ORGANISATION CROWD MANAGEMENT SYSTEM----------");
		System.out.println("--------------");
		System.out.println("INTIAL SETUP");
		System.out.println("--------------");
		System.out.println("\nEnter Number Of Service Windows Present At " + comp1.Org.toUpperCase());
		int count = sc.nextInt();
                sc.nextLine();
		for (int i = 0; i < count; i++) {
			Services S = new Services(comp1);
			System.out.print("\nEnter Name Of Service " + (i + 1) +" : ");
                        S.sName = sc.nextLine();
			addCounter(S);
		}
		System.out.println("\n-------------CONGRATS YOU HAVE SUCCESSFULLY COMPLETED INITIAL SETUP------------");
		obj.waitMsg("Redirecting To Login Page",1000,5);
                compName = obj.getOName();
                adminPanel(false);
	}

	private static void addCounter(Services S) {
                
                System.out.print("\nCreate Username For " + S.sName.toUpperCase() + " : ");
		S.sUser = sc.nextLine();
		System.out.print("\nCreate Password For " + S.sName.toUpperCase() + " : ");
		S.sPass = sc.nextLine();
		obj.insertTable(S);
	}

}

class Company {
	String Org;
	String username;
	String password;
}

class Services {
	String comp;
	String sName;
	String sUser;
	String sPass;

	public Services(Company C) {
		this.comp = C.Org;
	}
}