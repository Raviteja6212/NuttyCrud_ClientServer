import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.io.*;
import java.net.*;

public class NC_Server {
	public static void main(String args[]) {
		
			System.out.println("Server side started running. Waiting for the client request");
			ServerSocket ss=null;
			Socket socketForServer=null;
			PrintStream responseToClient = null;
			BufferedReader br = null;
			Scanner sc = null;
			String text = null;
			
			try {
				ss = new ServerSocket(5510);
				socketForServer = ss.accept(); 
				System.out.println("\nCLIENT REQUEST RECEIVED AND ACCEPTED");
				sc = new Scanner(socketForServer.getInputStream());
			}catch(IOException k1) {
				System.out.println("Error");
			}
				
			text = sc.nextLine();
			String[] details = text.split(",");
			String line="";boolean newrecord = true;
			
			while(details[0].equals("0") || details[0].equals("1") || details[0].equals("2") || details[0].equals("3") || details[0].equals("4") || details[0].equals("id_check")) {	
				if(details[0].equals("0")) {
					newrecord = true;
					try {
						br = new BufferedReader(new FileReader("users.csv"));
						while ((line = br.readLine()) != null)    
						  {  
							 if(line.split(",")[0].equals(details[1]) && line.split(",")[1].equals(details[2]) && line.split(",")[2].equals(details[3])) {
								newrecord=false; 
								responseToClient = new PrintStream(socketForServer.getOutputStream());
								responseToClient.println("True");
								System.out.println("SERVER SENT BACK THE USER CHECK TO CLIENT");
							 }
						  }
						
						if(newrecord) {
							System.out.println("user doesn't exist.");
							responseToClient = new PrintStream(socketForServer.getOutputStream());
					    	responseToClient.println("False");
					    }
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  	
				}
				else if(details[0].equals("1")) {
					create(text);
					System.out.println("Client Created a record.");
				}
				else if(details[0].equals("2")) {
					String toread = read(text);
					System.out.println("Client read a record.");
					try {
						responseToClient = new PrintStream(socketForServer.getOutputStream());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    	responseToClient.println(toread);
				}
				else if(details[0].equals("3")) {
					update(text);
					System.out.println("Client updated a record.");
				}
				else if(details[0].equals("4")) {
					delete(text);
					System.out.println("Client deleted a record.");
				}
				else if(details[0].equals("id_check")) {
					 String idc = id_check(details[1]);
					 try {
							responseToClient = new PrintStream(socketForServer.getOutputStream());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    	responseToClient.println(idc);
				}
				
				if(sc.hasNextLine()) {
					text=sc.nextLine();
				}
				else {
					System.out.println("---- Client terminated. ----");
					break;
				}
				
				//System.out.println("Message to server from client - "+text);
				details=text.split(",");
			}

				
	}
	private static void create(String record) {
		try {
			FileWriter writer = new FileWriter("customers.csv",true);
			writer.append(record.substring(2)+"\n");
			writer.flush();
			writer.close();			
		}
		catch(Exception e) {
			System.out.println("Can't write the record.");
		}
	}
	private static String read(String record) {
		try {
	         File file = new File("customers.csv");
	         FileReader fr = new FileReader(file);
	         BufferedReader br = new BufferedReader(fr);
	         String line = "";
	         while((line = br.readLine()) != null) {
	            if(line.split(",")[0].equals(record.split(",")[1]))
	            {
	            	return line;
	            }
	         }
	     }
		 catch(IOException ioe) {
	         ioe.printStackTrace();
	     }
		return "Id not found";
	}
	private static void update(String record) {
		try {
	         File file = new File("customers.csv");
	         FileReader fr = new FileReader(file);
	         BufferedReader br = new BufferedReader(fr);
	         String line = "",updated_line="";
	         String[] tempArr;
	         List<String> storedata = new ArrayList<String>();
	        		 
	         while((line = br.readLine()) != null) {
	            if(line.split(",")[0].equals(record.split(",")[1]))
	            {
	            	if(record.split(",")[2].equals("name")) {
	            		updated_line =  line.split(",")[0] + "," + record.split(",")[3] + "," + line.split(",")[2] + "," + line.split(",")[3] + ",active,0" ;
	            		storedata.add(updated_line);
	            	}
	            	else if(record.split(",")[2].equals("email")) {
	            		updated_line =  line.split(",")[0] + "," +  line.split(",")[1] + "," + record.split(",")[3] + "," + line.split(",")[3]  + ",active,0";
	            		storedata.add(updated_line);
	            	}
	            	else if(record.split(",")[2].equals("cp")) {
	            		storedata.add(updated_line);
	            	}
	            	else {
	            		System.out.println("update id doesn't match with any. please recheck");
	            	}
	            }
	            else {
	            	storedata.add(line);
	            }
	         }
	         br.close();fr.close();
	         
	         try {
	 			FileWriter writer = new FileWriter("customers.csv");
	 			for(String i:storedata) {
		        	 writer.append(i+"\n");
		        	 writer.flush();
		         }	 			
	 			 writer.close();			
	 		}
	 		catch(Exception e) {
	 			System.out.println("Can't write the record.");
	 		}
	     }
		 catch(IOException ioe) {
	         ioe.printStackTrace();
	     }
	}
	private static void delete(String record) {
		try {
	         File file = new File("customers.csv");
	         FileReader fr = new FileReader(file);
	         BufferedReader br = new BufferedReader(fr);
	         String line = "",updated_line="";
	         String[] tempArr;
	         List<String> storedata = new ArrayList<String>();
	        		 
	         while((line = br.readLine()) != null) {
	            if(line.split(",")[0].equals(record.split(",")[1]))
	            {
	            	continue;
	            }
	            else {
	            	storedata.add(line);
	            }
	         }
	         br.close();fr.close();
	         
	         try {
	 			FileWriter writer = new FileWriter("customers.csv");
	 			for(String i:storedata) {
		        	 writer.append(i+"\n");
		        	 writer.flush();
		         }	 			
	 			 writer.close();			
	 		}
	 		catch(Exception e) {
	 			System.out.println("Can't write the record.");
	 		}
	     }
		 catch(IOException ioe) {
	         ioe.printStackTrace();
	     }
	}
	private static String id_check(String id) {
		String line="";
		BufferedReader br=null;
		try {
			br = new BufferedReader(new FileReader("customers.csv"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while ((line = br.readLine()) != null)    
			  {  
				if(line.split(",")[0].equals(id)) {
					return "True";
				}
			  }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "False";
	}
}
