package hbaseApp;

import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Map.Entry;

import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

public class Query {

	private HTable table;
	private long startTmp;
	private long endTmp;
	private int topN;
	private ArrayList<Map<String,Integer>> totalCounts;
	private ArrayList<String> languages;
	private HBaseUtils hBaseUtils;

	public Query (long startTmp, long endTmp, int topN, ArrayList<String> langs, HBaseUtils hBaseUtils){
		this.hBaseUtils=hBaseUtils;
		this.table = this.hBaseUtils.getHbaseTable();
		this.startTmp = startTmp;
		this.endTmp = endTmp;
		this.topN = topN;
		this.totalCounts = new ArrayList<Map<String,Integer>>();
		this.languages = langs;

	}

	//TODO: change into void printing into a file
	public String runQuery1() throws IOException{
		System.out.println("Starting Q1");
		String stringOutput;
		this.getCountsByLanguage();
		Iterator<String> keyIterator = this.totalCounts.get(0).keySet().iterator();
		stringOutput = this.createStringOutput(keyIterator,this.languages.get(0));
		System.out.println("Q1 Done.");
		return stringOutput;
	}

	public String runQuery2() throws IOException{
		System.out.println("Starting Q2");
		String stringOutput="";
		getCountsByLanguage();
		int indexLang=0;
		for(Map<String, Integer> langCounts : this.totalCounts){
			Iterator<String> keyIterator = langCounts.keySet().iterator();
			stringOutput = stringOutput + createStringOutput(keyIterator, this.languages.get(indexLang));
			indexLang++;
		}
		System.out.println("Q2 Done.");
		return stringOutput;
	}

	public String runQuery3() throws IOException{
		System.out.println("Starting Q3");
		String stringOutput;
		this.getAllCounts();
		Iterator<String> keyIterator = this.totalCounts.get(0).keySet().iterator();
		Iterator<Integer> valuesIterator = this.totalCounts.get(0).values().iterator();
		stringOutput = this.createStringOutputFreqs(keyIterator,valuesIterator,this.languages.get(0));
		System.out.println("Q3 Done.");
		return stringOutput;
	}

	private void getCountsByLanguage() throws IOException{
		for(String lang:this.languages){
			HashMap<String,Integer> auxLangCounts = new HashMap<String,Integer>();
			byte[] startKey = this.hBaseUtils.generateKey(startTmp, lang);
			byte[] endKey = this.hBaseUtils.generateKey(endTmp, lang);
			Scan scan = new Scan(startKey,endKey);
			ResultScanner rs = this.table.getScanner(scan);
			Result result = rs.next();
			while (result!=null && !result.isEmpty()){
				auxLangCounts = getResultMap(result, auxLangCounts);
				result = rs.next();
			}
			this.totalCounts.add(sortByValue(auxLangCounts));
		}
	}

	private void getAllCounts() throws IOException{
		HashMap<String,Integer> auxAllCounts = new HashMap<String,Integer>();
		for(String lang:this.languages){
			byte[] startKey = this.hBaseUtils.generateKey(this.startTmp, lang);
			byte[] endKey = this.hBaseUtils.generateKey(this.endTmp, lang);
			Scan scan = new Scan(startKey,endKey);
			ResultScanner rs = this.table.getScanner(scan);
			Result result = rs.next();
			while (result!=null && !result.isEmpty()){
				auxAllCounts = getResultMap(result, auxAllCounts);
				result = rs.next();
			}
		}
		this.totalCounts.add(sortByValue(auxAllCounts));
	}

	private HashMap<String,Integer> getResultMap (Result result, HashMap<String,Integer> counts){
		NavigableMap<byte[], byte[]> resultMap= result.getFamilyMap(this.hBaseUtils.getColumnFamily1());
		Entry<byte[],byte[]> resultEntry = resultMap.pollFirstEntry();
		while (resultEntry != null){
			String hashtag = Bytes.toString(resultEntry.getKey());
			int count = Bytes.toInt(resultEntry.getValue());
			if(counts.containsKey(hashtag)){
				int oldCount = counts.get(hashtag);
				counts.put(hashtag, oldCount + count);
			}
			else{
				counts.put(hashtag, count);
			}
			resultEntry = resultMap.pollFirstEntry();
		}
		return counts;
	}

	private String createStringOutput (Iterator<String> keyIterator, String lang){
		String stringQuery1 = "";
		if(keyIterator.hasNext()){
			for(int i=1;i<=topN;i++){
				if(keyIterator.hasNext()){
					stringQuery1 = stringQuery1 + lang + ", " + i + ", " + keyIterator.next() 
							+ ", " + startTmp + ", " + endTmp + "\n";
				}
			}
		}
		return stringQuery1;
	}

	private String createStringOutputFreqs(Iterator<String> keyIterator, Iterator<Integer> valueIterator, String lang){
		String stringQuery1 = "";
		if(keyIterator.hasNext()){
			for(int i=1;i<=topN;i++){
				if(keyIterator.hasNext() && valueIterator.hasNext()){
					stringQuery1 = stringQuery1 + i + ", " + keyIterator.next() + ", " + valueIterator.next()
							+ ", " + startTmp + ", " + endTmp + "\n";
				}
			}
		}
		return stringQuery1;
	}

	public static <K extends Comparable<? super K>, V extends Comparable<? super V>> Map<K, V> 
	sortByValue( Map<K, V> map )
	{
		List<Map.Entry<K, V>> list =
				new LinkedList<Map.Entry<K, V>>( map.entrySet() );
		Collections.sort( list, new Comparator<Map.Entry<K, V>>()
				{
			public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
			{
				int resultado;

				if(o1.getValue()!=o2.getValue()){
					resultado=(o2.getValue()).compareTo( o1.getValue());
				}
				else{
					Collator myCollator = Collator.getInstance();
					resultado = myCollator.compare(o1.getKey(), o2.getKey());
				}
				return resultado;
			}
				} );

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list)
		{
			result.put( entry.getKey(), entry.getValue() );
		}
		return result;
	}




}
