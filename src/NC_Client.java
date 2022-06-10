import java.util.*;
import java.util.regex.Pattern;
import java.io.*;
import java.net.*;

public class NC_Client {
	public static void main(String[] args) {		
		
		System.out.println("---- Welcome to Nutty Crud App ----");

		int op_value=0,i=4,chanceforuser=4;
		while(i!=0) {
			Scanner op_input = new Scanner(System.in);
			try {
				System.out.print("\nPress 1-Admin/2-Operator : ");
				op_value = op_input.nextInt();
				if(op_value>2||op_value<1) {
					i--;
					System.out.println("Only "+String.valueOf(i)+" left!");
					System.out.println("Input should be 1 or 2. Please recheck and enter again.");
				}
				else {
					break;
				}
			}
			catch(Exception e) { 
				i--;
				System.out.print("Only "+String.valueOf(i)+" left!");
				System.out.println("Please only enter integers!");		
			}
		}
		
		if(op_value==1) {
			
			String username,password,line="";
			Scanner cscan = new Scanner(System.in);
			Scanner option_ = new Scanner(System.in);
			Scanner user = new Scanner(System.in);
			int youroption =  0;
			
			PrintStream sendtoserver = null; // this printstream is used to send information to the server.
			Scanner serverResponse = null; // this scanner is used to accept the response from the server.
			Socket nc_socket=null; // socket is to make the connection between client and server.
			try {
				// socket to make connection with the server.
				nc_socket = new Socket("127.0.0.1",5510);
				System.out.println("\nClient: Made a connection with the server.");
				sendtoserver = new PrintStream(nc_socket.getOutputStream()); 
			} catch (IOException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			while(chanceforuser!=0) {
				System.out.println("\n-------- LOGIN PAGE --------");
				System.out.print("Enter username: ");
				username = user.next();
				System.out.print("Enter password: ");
				password = user.next();
				
				
				String temp=null;
				Scanner input = new Scanner(System.in); // scanner to take input from console.
				try {				
			
					String name = "0"+","+op_value+","+username+","+password;
					
					// we are using the printstream object to pass the value to the server
					sendtoserver.println(name);
					System.out.println("CLIENT SENT THE INPUT TO SERVER.");
					
					//Accepting response from the server.
					System.out.println("CLIENT RECEIVED THE RESPONSE FROM SERVER.\n");
					serverResponse = new Scanner(nc_socket.getInputStream()); //sending socket object method to a scanner object
					temp = serverResponse.nextLine();  // which can be used to accept response from the server.
				}
				catch(UnknownHostException  e1) {
					  System.out.println("Check the host.unknown host error.");
				}catch(IOException e2) {
				  System.out.println("Input output error.");
		    	}	
				 
				
		    	if("True".equals(temp)) {	
		    	  String cid="",cname="",cemail=""; int cperiod=0; 
	  			  Scanner cinputs = new Scanner(System.in);
	  			  
	  			  System.out.println("\nLogin Succesful as Admin user "+username+".");
	  			  System.out.println("\n-------- HOME PAGE --------\n\n1.Create Record\n2.Read Record\n3.Update Record\n4.Delete Record\n5.Exit\n");
	  			  System.out.print("Choose your option - ");
	  			  option_ = new Scanner(System.in);
	  			  
	  			  
	  			  try {
					   youroption = option_.nextInt();
				  }
				  catch(Exception e){
					  System.out.println("Please only enter integers");
				  }
	  			
	  			  String text = ""; 
	  			  
	  			  while(youroption!=5 ) {
					  String continue_ = "y";
					  
					  if(youroption==1) {
						  while( continue_.equals("y") ) {
							  try {
								  boolean goodinput = false,badinput=false; int c1;
								  
								  c1=4;
								  while(c1!=0) {
									  badinput=false;
									  cinputs = new Scanner(System.in);
									  System.out.print("\nEnter Customer ID  - ");
									  cid = cinputs.next();
									  sendtoserver.println("id_check,"+cid);
									  temp = serverResponse.nextLine();
									  if("True".equals(temp)) {
										  badinput=true;
										  c1--;
										  System.out.println("Entered customer id already exists. \nTry Again!! Only "+String.valueOf(c1)+" chances left!");
									  }
									  else {
										  if(Pattern.matches("^(?=.*?\\d)(?=.*?[a-zA-Z])[a-zA-Z\\d]+$",cid )) {
											  break;
										  }
										  else {
											  badinput=true;
											  c1--;
											  System.out.println("\nCustomer id must be alphanumeric.\nTry again! Only "+String.valueOf(c1)+" chances left");
										  }
									  }
								  }
								  
								  c1=4;
								  if(badinput==false) {
									  while(c1!=0) {
										  badinput=false;
										  System.out.print("Enter Customer Name   - ");
										  cname=cinputs.next();
										  if(Pattern.matches("[a-zA-Z]+",cname )) {
											  break;
										  }
										  else {
											  badinput=true;
											  c1--;
											  System.out.println("\nCustomer Name consists only alphabets.\nTry again! Only "+String.valueOf(c1)+" chances left");
										  }								  
									  } 
								  }
								 
								  c1=4;
								  if(badinput==false) {
									  while(c1!=0) {
										  badinput=false;
										  System.out.print("Enter Customer Email - ");
										  cemail=cinputs.next(); cperiod=0;
										  String emailRegex = "[a-zA-Z0-9_.]+@[a-zA-Z0-9]+.[a-zA-Z]{2,3}[.] {0,1}[a-zA-Z]+";
										  if(Pattern.matches(emailRegex,cemail)) {
											  break;
										  }
										  else {
											  badinput=true;
											  c1--;
											  System.out.println("\nCustomer email is not valid.\nTry again! Only "+String.valueOf(c1)+" chances left");
										  }	
									  }
								  }
								  
								  c1=4;
								  if(badinput==false) {
									  while(c1!=0) {	
										  badinput=false;
										  System.out.print("Enter Credit period  - ");
										  try{
											  Scanner cpint = new Scanner(System.in);
											  cperiod=cpint.nextInt();
											  if(cperiod>=5&&cperiod<=30) {
												  goodinput=true;
												  break;
											  }else {
												  c1--;
												  System.out.println("\nPlease give the credit period between 5 and 30.only "+String.valueOf(c1)+" chances left.");
												  continue;
											  }
										  }catch(Exception e) {
											  c1--;
											  System.out.println("\nPlease give number for credit period. only "+String.valueOf(c1)+" chances left.");
										  }
									  }	
									  
								  }
								  
								  if(goodinput==true&&badinput==false) {
									  text = "1"+","+cid+","+cname+","+cemail+","+cperiod+",active"+",0";
									  sendtoserver.println(text);
									  System.out.println("\nRecord succesfully written to the file.\n");
								  }else {
									  System.out.println("\nRecord not written to the file.\n");
								  }
								  
								  System.out.print("\nAdd more records? y/n - ");
								  continue_ = option_.next();
								  if(continue_.equals("n")) {
									 break;		  			    						  
								  }
							  }
							  catch(NumberFormatException k) {
								  System.out.println("Check the dataype of the inputs.");
							  }			    						  
						  }
					  }
					  else if(youroption==2) {
						  System.out.print("\nEnter the customer id to search - ");
						  String cid_find = cscan.next();
						  cid_find="2,"+cid_find;
						  sendtoserver.println(cid_find);
						  
						  try {
							  serverResponse = new Scanner(nc_socket.getInputStream());
							  temp = serverResponse.nextLine();
							  System.out.println("Record - "+temp);
						  }
						  catch(UnknownHostException  e1) {
							  System.out.println("Check the host.unknown host error.");
						  }catch(IOException e2) {
							  System.out.println("Input output error.");
						  }			  
					  }
					  else if(youroption==3) {
						  System.out.print("\nEnter the customer id to update - ");
						  String cid_find = cscan.next();
						  sendtoserver.println("id_check,"+cid_find);
						  temp = serverResponse.nextLine();
						  if("True".equals(temp)) {
							  String find="2,"+cid_find;
							  sendtoserver.println(find);
							  temp = serverResponse.nextLine();
							  
							  System.out.println("\nRecord - "+temp);
							  System.out.print("\nAre you sure you want to update? y/n - ");
							  String choice_ = cinputs.next();
							  if(choice_.equals("y")) {
								  System.out.print("What do you want to update? name/email/cp - ");
								  Scanner option_update = new Scanner(System.in);
								  Scanner val = new Scanner(System.in);
								  String update_ = option_update.nextLine();
								  boolean correctinput = true;
								  String new_val = null;
								  if(update_.equals("name")) {
									  System.out.print("\nEnter new name - ");
									  new_val = val.nextLine();
								  }
								  else if(update_.equals("email")) {
									  System.out.print("\nEnter new email - ");
									  new_val = val.nextLine();
								  }
								  else if(update_.equals("cp")) {
									  System.out.print("\nEnter new credit period - ");
									  new_val = val .nextLine();
								  }
								  else {
									  correctinput=false;
									  System.out.println("Please recheck the update_id entered.");
								  }
								  if(correctinput) {
									  update_ = "3,"+cid_find+","+update_+","+new_val;
									  sendtoserver.println(update_);
									  System.out.println("Updated Succesfully:)");						  
								  }		
							 }
							 else {
								  System.out.println("Not updated:)");
							 }
						  }
						  else {
							  System.out.println("\nEntered Customer Id is  not found!");
						  }
						  
					  }
					  else if(youroption==4) {
						  System.out.print("\nEnter the customer id to delete - ");
						  String cid_find = cscan.next();
						  sendtoserver.println("id_check,"+cid_find);
						  temp = serverResponse.nextLine();
						  if("True".equals(temp)) {
							  String find="2,"+cid_find;
							  sendtoserver.println(find);
							  temp = serverResponse.nextLine();
							  System.out.println("\nRecord - "+temp);
							  
							  System.out.print("\nAre you sure you want to delete? y/n - ");
							  String choice_ = option_.next();
							  if(choice_.equals("y")) {
								  String delete_ = "4,"+cid_find;
								  sendtoserver.println(delete_);
								  System.out.println("Succesfully Deleted:)");
							  }
							  else {
								  System.out.println("Not Deleted:)");
							  }
						  }
						  else {
							  System.out.println("\nEntered Customer Id is  not found!");
						  }
						  
					  }
					  else {
						  System.out.println("Please only enter the options available");
					  }
					  System.out.println("\n-------- HOME PAGE --------\n\n1.Create Record\n2.Read Record\n3.Update Record\n4.Delete Record\n5.Exit\n");
					  System.out.print("Choose your option - ");
					  try {
						   youroption = option_.nextInt();
					  }
					  catch(Exception e){
						  System.out.println("Please only enter integers");
					  }
				  }
		    	}	
		    	else {
		    		chanceforuser--;
		    		if(chanceforuser==0) {
		    			System.out.println("Out of chances. Try again!");
		    		}
		    		else {
		    			System.out.print("Only "+chanceforuser+" left! ");
		    			System.out.println("please recheck the input details.");
		    		}
		    	}
		    	
		    	if(youroption==5) {
					  chanceforuser=0;
				}
			}
			
			user.close();
		}
		else if(op_value==2) {
			Scanner user = new Scanner(System.in);
			Scanner pass = new Scanner(System.in);
			String username,password;
			
			System.out.print("Enter username: ");
			username = user.next();
			System.out.print("Enter password: ");
			password = pass.next();
			
			//check the user data entered with the users.csv
			System.out.println("sorry, operator interface is not developed yet!");
		}
		else {
			chanceforuser--;
			System.out.print("Sorry invalid input.");
		}
	}
	private static void validations() {
		
	}
}
