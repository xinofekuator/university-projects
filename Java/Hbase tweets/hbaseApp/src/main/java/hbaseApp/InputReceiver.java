package hbaseApp;

import java.io.IOException;
import java.util.ArrayList;

public class InputReceiver {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		if(args.length!=2 && args.length!=6 && args.length!=5){
			System.out.println("Wrong argument list.");
			return;
		}else{
			int command= Integer.parseInt(args[0]);
			
			if(command>4 || command<0 ){
				System.out.println("Wrong command submitted.");
				return;
			}
			
			if(args.length == 2 && command==4){
				String inputFolder=args[1];//"/Users/Hugo/Documents/workspace/inputFolderPath";

				final TaskManager taskManager = new TaskManager(command, inputFolder);
				
				
			}else if(args.length == 6 && command==1){
				long startTS= Long.parseLong(args[1]);
				long endTS=Long.parseLong(args[2]);
				int nRanking=Integer.parseInt(args[3]);
				String langsArgument = args[4];
				ArrayList<String> splittedLan=getLanguageList(langsArgument);
				String outputFolder=args[5];//"/Users/Hugo/Documents/workspace/outputFolderPath";
				/*Code to test arguments
				printLanguageList(splittedLan);
				printLanguageNumber(splittedLan);
				*/
				if(splittedLan.size()>1){
					System.out.println("Only one language should be submitted for command:1");
					return;
				}
				
				final TaskManager taskManager = new TaskManager(command, startTS, endTS, nRanking, splittedLan, outputFolder);
				
			}else if(args.length == 6 && command==2){
				long startTS= Long.parseLong(args[1]);
				long endTS=Long.parseLong(args[2]);
				int nRanking=Integer.parseInt(args[3]);
				String langsArgument = args[4];
				ArrayList<String> splittedLan=getLanguageList(langsArgument);
				String outputFolder=args[5];//"/Users/Hugo/Documents/workspace/outputFolderPath";
				/*Code to test arguments
				printLanguageList(splittedLan);
				printLanguageNumber(splittedLan);
				*/
				
				final TaskManager taskManager = new TaskManager(command, startTS, endTS, nRanking, splittedLan, outputFolder);
			}else if(args.length == 5 && command==3){
				long startTS= Long.parseLong(args[1]);
				long endTS=Long.parseLong(args[2]);
				int nRanking=Integer.parseInt(args[3]);
				String outputFolder=args[4];//"/Users/Hugo/Documents/workspace/outputFolderPath";
				
				final TaskManager taskManager = new TaskManager(command, startTS, endTS, nRanking, outputFolder);
			}else {
				//Production application, receiving arguments.
				System.out.println("Wrong argument list.");
				return;
				
			}
		}
		
	}
	
	static int getNumberOfLanguages(String listOfLanguages){
		String[] splittedLan=listOfLanguages.split(",");
		
		return splittedLan.length;
	}
	
	static ArrayList<String> getLanguageList(String listOfLanguages){
		ArrayList<String> splittedLanAL= new ArrayList<String>();
		
		String[] splittedLanguges=listOfLanguages.split(",");
		
		for(int i = 0; i< splittedLanguges.length; i++){
			splittedLanAL.add(splittedLanguges[i]);
		}
		return splittedLanAL;
	}
	
	static void printLanguageList(String[] languageList){
		for(int i = 0; i<languageList.length; i++){
			System.out.println("LanguageList = " + languageList[i]);
		}
	}
	
	static void printLanguageNumber(String[] languageList){
			System.out.println("LanguageNumber = " + languageList.length);
	}

}
