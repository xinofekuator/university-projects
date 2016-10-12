package hbaseApp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.nio.charset.Charset;
import java.util.NavigableMap;
import java.util.ArrayList;
import java.util.Map.Entry;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.RetriesExhaustedWithDetailsException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;

public class HBaseUtils {

	public Configuration conf;
	public HBaseAdmin admin;
	public HConnection conn;
	public byte[] TABLENAME;
	public byte[] CFNAME1;
	public byte[] CFNAME2;
	public HTable table;
	public HTableDescriptor tableDescriptor;
	public HColumnDescriptor family1;
	public HColumnDescriptor family2;
	
	
	//This class contains all the functions that interact with HBase
	public HBaseUtils() throws MasterNotRunningException, ZooKeeperConnectionException, IOException{
		this.conf = HBaseConfiguration.create();
		this.admin = new HBaseAdmin(this.conf);
		this.conn = HConnectionManager.createConnection(this.conf);
		this.TABLENAME = Bytes.toBytes("HBaseTable");
		this.CFNAME1 = Bytes.toBytes("HashtagInformation");
		this.CFNAME2 = Bytes.toBytes("Languages");
		this.family1 = new HColumnDescriptor(this.CFNAME1);
		this.family2 = new HColumnDescriptor(this.CFNAME2);
		this.table = new HTable(TableName.valueOf(this.TABLENAME),this.conn);
		this.tableDescriptor = new HTableDescriptor(TableName.valueOf(this.TABLENAME));
		this.tableDescriptor.addFamily(this.family1);
		this.tableDescriptor.addFamily(this.family2);
		
	}
	
	public HTable getHbaseTable(){
		return this.table;
	}
		
	public byte[] getColumnFamily1(){
		return this.CFNAME1;
	}
	
	public byte[] getColumnFamily2(){
		return this.CFNAME2;
	}
	//Inserts a line into Hbase
	//splitted: TS, Lan, Hash1, count1, Hash2, count2, Hash3, count3
	public void insertLineInHBase(String[] splittedLine) throws IOException{
		
		long ts=Long.parseLong(splittedLine[0], 10);
		String lan=splittedLine[1];
		String hash1 = splittedLine[2];
		int count1 = Integer.parseInt(splittedLine[3]);
		String hash2 = splittedLine[4];
		int count2 = Integer.parseInt(splittedLine[5]);
		String hash3 = splittedLine[6];
		int count3 = Integer.parseInt(splittedLine[7]);
		
		byte[] tsBytes= Bytes.toBytes(ts);
		byte[] lanBytes= Bytes.toBytes(lan);
		byte[] hash1Bytes = Bytes.toBytes(hash1);
		byte[] count1Bytes = Bytes.toBytes(count1);
		byte[] hash2Bytes = Bytes.toBytes(hash2);
		byte[] count2Bytes = Bytes.toBytes(count2);
		byte[] hash3Bytes = Bytes.toBytes(hash3);
		byte[] count3Bytes = Bytes.toBytes(count3);
		byte[] key = generateKey(ts,lan);
		Put put;
		
		if(count1>0){
			put = new Put(key);
			put.add(this.CFNAME1, hash1Bytes, count1Bytes);
			this.table.put(put);
			
			//testInsertedHBase(key, this.CFNAME1, hash1Bytes, count1Bytes);
			if(count2>0){
				put = new Put(key);
				put.add(this.CFNAME1, hash2Bytes, count2Bytes);
				this.table.put(put);
				
				//testInsertedHBase(key, this.CFNAME1, hash2Bytes, count2Bytes);
				if(count3>0){
					put = new Put(key);
					put.add(this.CFNAME1, hash3Bytes, count3Bytes);
					this.table.put(put);
					
					//testInsertedHBase(key, this.CFNAME1, hash3Bytes, count3Bytes);
				}
			}
		}

	}

    public void insertLangsInHBase(ArrayList<String> totalLangs) throws IOException{
    	byte[] key = this.getLangsKey();
    	int phantomValue=1; //No value needed for keeping languages. Using 0
    	Put put;
    	
    	for(int i = 0; i<totalLangs.size(); i++){
    		put = new Put(key);
    		String currentLang= totalLangs.get(i);
    		byte[] currentLangBytes = Bytes.toBytes(currentLang);
    		put.add(this.CFNAME2, currentLangBytes, Bytes.toBytes(phantomValue));
    		this.table.put(put);
			//testInsertedHBase(key, this.CFNAME2, currentLangBytes, Bytes.toBytes(phantomValue));
    	}
    }
	public void testInsertedHBase(byte[] key, byte[] CfName, byte[] col, byte[] value) throws IOException{

		
		//Getting to test last hashtag introduced
		Get get= new Get(key);
		Result result = this.table.get(get);
		
		byte[] valueResult=result.getValue(CfName,  col);
		System.out.println("Used Key = " + key);
		System.out.println("Introduced to HBASE: Hashtag = " + Bytes.toString(col) + " Count = " + Bytes.toInt(value));
		System.out.println("Received from HBASE: Hashtag = " + Bytes.toString(col) + " Count = " + Bytes.toInt(valueResult));
		
	}
	
	byte[] generateKey(long timestamp,String lang) {
		byte[] key = new byte[10];
		byte[] byteTimestamp = Bytes.toBytes(timestamp);
		byte[] byteLang = Bytes.toBytes(lang);
		System.arraycopy(byteLang,0,key,0,byteLang.length);
		System.arraycopy(byteTimestamp,0,key,2,byteTimestamp.length);
		return key;
	}

	byte[] getLangsKey(){
		byte[] key = new byte[10];
		for (int i = 0; i < 10; i++){
			key[i] = (byte)-255;
		}
		return key;
	}
	//This function checks if the table exists. If it is, it will disabled it and delete it.
	//Otherwhise it does nothing.
	public void deleteHBaseTable() throws IllegalArgumentException, IOException{
		if(this.admin.tableExists(this.TABLENAME)){
			this.admin.disableTable(this.TABLENAME);
			System.out.println("Table disabled correctly");
			this.admin.deleteTable(this.TABLENAME);
			System.out.println("Table deleted correctly");
		}
	}
	
	public void createHbaseTable() throws IllegalArgumentException, IOException{
		this.admin.createTable(this.tableDescriptor);
		System.out.println("Table created correctly");
	}
	
	//Obtains languages from HBase Table
	public ArrayList<String> getLanguages() throws IOException{
		ArrayList<String> languages = new ArrayList<String>();
		Get get = new Get(getLangsKey());
		get.addFamily(this.CFNAME2);
		Result result = table.get(get);
		if (result != null && !result.isEmpty()){
			NavigableMap<byte[],byte[]> resultMap = result.getFamilyMap(this.CFNAME2);		
			Entry<byte[],byte[]> resultEntry = resultMap.pollFirstEntry();
			while (resultEntry != null){
				String lang = Bytes.toString(resultEntry.getKey());
				languages.add(lang);
				resultEntry = resultMap.pollFirstEntry();
			}
		}
		return languages;
	}

}
