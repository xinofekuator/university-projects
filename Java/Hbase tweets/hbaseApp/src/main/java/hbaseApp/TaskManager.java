package hbaseApp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HTable;

public class TaskManager {
	//This application contains the logic that handles the submitted command.
	
	FileManager fileManager;
	HBaseUtils hBaseUtils;
	Query query;
	
	public TaskManager(int command, String inputFolder) throws MasterNotRunningException, ZooKeeperConnectionException, IOException{
		fileManager= new FileManager();
		hBaseUtils= new HBaseUtils();
		//Inactivate and drop Table
		hBaseUtils.deleteHBaseTable();
		//Create table
		hBaseUtils.createHbaseTable();
		//Fill table:
		fileManager.getFileData(fileManager.getFiles(inputFolder), hBaseUtils);
		System.out.println("Command 4 Done.");
	}
	
	public TaskManager(int command, long startTS, 
			long endTS, int nRanking, ArrayList<String> splittedLanAL, String outputFolder) throws MasterNotRunningException, ZooKeeperConnectionException, IOException{
		fileManager= new FileManager();
		hBaseUtils= new HBaseUtils();
		if(command==1){
			query = new Query(startTS, endTS, nRanking, splittedLanAL,hBaseUtils);
			String resultQuery1 = query.runQuery1();
			fileManager.writeResult(outputFolder, command, resultQuery1);
		}else{
			query = new Query(startTS, endTS, nRanking, splittedLanAL,hBaseUtils);
			String resultQuery2 = query.runQuery2();
			fileManager.writeResult(outputFolder, command, resultQuery2);
		}
	}
	
	public TaskManager(int command, long startTS, 
			long endTS, int nRanking, String outputFolder) throws MasterNotRunningException, ZooKeeperConnectionException, IOException{
		fileManager= new FileManager();
		hBaseUtils= new HBaseUtils();
		ArrayList<String> hBaseInsertedLangs= hBaseUtils.getLanguages();
		query = new Query(startTS, endTS, nRanking, hBaseInsertedLangs, hBaseUtils);
		String resultQuery3 = query.runQuery3();
		fileManager.writeResult(outputFolder, command, resultQuery3);
	}	
	
	/*
	//Receives all parameters and executes the correct one.
	public void handleCommands(int command, long startTS, 
			long endTS, int nRanking, ArrayList<String> splittedLanAL, String inputFolder, String outputFolder) throws IOException{

		
		switch(command){
			case 1:  //Query1
				query = new Query(startTS, endTS, nRanking, splittedLanAL,hBaseUtils);
				String resultQuery1 = query.runQuery1();
				fileManager.writeResult(outputFolder, command, resultQuery1);
				
				break;
			case 2:  //Query2
				query = new Query(startTS, endTS, nRanking, splittedLanAL,hBaseUtils);
				String resultQuery2 = query.runQuery2();
				fileManager.writeResult(outputFolder, command, resultQuery2);
				break;
			case 3:  //Query3
				ArrayList<String> hBaseInsertedLangs= hBaseUtils.getLanguages();
				query = new Query(startTS, endTS, nRanking, hBaseInsertedLangs, hBaseUtils);
				String resultQuery3 = query.runQuery3();
				fileManager.writeResult(outputFolder, command, resultQuery3);
				break;
			case 4:  //Initialization
				//Inactivate and drop Table
				hBaseUtils.deleteHBaseTable();
				//Create table
				hBaseUtils.createHbaseTable();
				//Fill table:
				fileManager.getFileData(fileManager.getFiles(inputFolder), hBaseUtils);
				
				break;
			default:  
				
				System.out.println("Non valid command - Task Manager");
				return;
		}
	
 	}*/
}
