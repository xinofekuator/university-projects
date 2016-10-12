package hbaseApp;


import java.io.IOException;

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
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.util.Bytes;

public class testBase {

	public static void main(String[] args) throws MasterNotRunningException, ZooKeeperConnectionException, IOException {
		// TODO Auto-generated method stub

		Configuration conf = HBaseConfiguration.create();
		
		HBaseAdmin admin = new HBaseAdmin(conf);
		
		byte[] TABLE = Bytes.toBytes("PEOPLE");
		byte[] CF = Bytes.toBytes("ColumnFamily");
		
		//HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(TABLE));
		//HColumnDescriptor family = new HColumnDescriptor(CF);
		//tableDescriptor.addFamily(family);
		//admin.createTable(tableDescriptor);

		HConnection conn = HConnectionManager.createConnection(conf);
		
		HTable table = new HTable(TableName.valueOf(TABLE),conn);
			
		byte[] key = Bytes.toBytes("KEY");
		
		byte[] value = Bytes.toBytes("VALUE");
		
		byte[] column = Bytes.toBytes("COLUMN");
		
		Put put = new Put(key);
		
		Get get= new Get(key);
		Result result = table.get(get);
		
		byte[] valueResult=result.getValue(Bytes.toBytes("ColumnFamily"),  Bytes.toBytes("COLUMN"));
		System.out.println("Result = " + Bytes.toString(valueResult));
		//put.add(CF, column, value);
		//table.put(put);
		//HTable.put(List<Put> puts); 
		
	}

}
