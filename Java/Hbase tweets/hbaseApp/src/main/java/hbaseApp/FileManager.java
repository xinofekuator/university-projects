package hbaseApp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileManager {

	//This method contain all the functions that get and parse the input data files
	public FileManager(){
		//Empty Contructor
	}
	public void getInput(String inputFolder){
		getFiles(inputFolder);
	}
	
	public ArrayList<File> getFiles(String inputFolder){
		File dir = new File(inputFolder);
		File[] filesList = dir.listFiles();
		ArrayList<File> finalFileList = new ArrayList<File>();
		
		for (int i = 0; i<filesList.length; i++) {
		    if (filesList[i].isFile() && filesList[i].getName().endsWith(".out")) {
		        finalFileList.add(filesList[i]);
		    }
		}
		if(finalFileList.size()>0){
			return finalFileList;
		}else{
			System.out.println("No files to read in the specified Folder, ending program.");
			System.exit(0);
			return finalFileList;
		}
	}
	
	//This function calls the methods that fill HBase.
	//First fills the all the languages (insertLangsInHBase)
	//Then fills all the data line by line. (insertLineInHBase)
	public void getFileData(ArrayList<File> fileList, HBaseUtils hBaseUtils) throws IOException{
		String currentLine;
		//Contains line information from files
		//Format: TimeStamp, lan, hash1, count1, hash2, count2, hash3, count3
		String[] splittedLine; //Contains line information
		
		hBaseUtils.insertLangsInHBase(getTotalLangs(fileList));
		
		for(int i = 0; i<fileList.size(); i++){
			InputStream fis = new FileInputStream(fileList.get(i));
			InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
			BufferedReader br = new BufferedReader(isr);
			while ((currentLine = br.readLine()) != null) {
				splittedLine=currentLine.split(",");
				hBaseUtils.insertLineInHBase(splittedLine);
			}
			
		}
		
	}
	
	public ArrayList<String> getTotalLangs(ArrayList<File> fileList){
		ArrayList<String> totalLangs= new ArrayList<String>();
		
		for(int i = 0; i<fileList.size(); i++){
			//String currentFileName=fileList.get(i).getName();
			//String[] splittedFileName=currentFileName.split(".");
			totalLangs.add(fileList.get(i).getName());
		}
		
		return totalLangs;
	}
	
	public void writeResult(String outputFolder, int command, String queryResult) throws IOException{
		String outputFileName= "01_query"+command+".out";
		
	    String path = outputFolder  + "/" + outputFileName;
		try{
			try {
				FileOutputStream auxStream= new FileOutputStream(path, true);
				auxStream.close();
			} 
			catch(java.io.FileNotFoundException e){
				Path pathToFile = Paths.get(path);
				Files.createDirectories(pathToFile.getParent());
				Files.createFile(pathToFile);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path,true), "UTF-8")));
		//PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));
		writer.print(queryResult);
		writer.close();
	}
}
