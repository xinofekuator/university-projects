package twitterApp;

import java.awt.List;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
//import java.util.stream.Collectors;

import org.scribe.builder.*;
import org.scribe.builder.api.*;
import org.scribe.model.*;
import org.scribe.oauth.*;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class TwitterStreamProducer extends Thread {

    private static final String STREAM_URI = "https://stream.twitter.com/1.1/statuses/filter.json";
    public String apiKey;
    public String apiSecret;
    public String tokenValue;
    public String tokenSecret;
    public String mode;
    public String filePath;
    public String kafkaBroker;
    final String TOPIC = "master2015";
    Properties properties;
    ProducerConfig producerConfig;
    int tweetSentCount=0;
    int hashSentCount=0;
    int tweetRead=0; //
    int jaHashCount=0;
    int koHashCount=0;
    int esHashCount=0;
    int enHashCount=0;
    
    public TwitterStreamProducer(String apiKey, String apiSecret, String tokenValue, String tokenSecret, String mode, String filePath, String kafkaBroker){
        this.apiKey=apiKey;
        this.apiSecret=apiSecret;
        this.tokenValue=tokenValue;
        this.tokenSecret=tokenSecret;
        this.mode=mode;
        this.filePath=filePath;
        this.kafkaBroker=kafkaBroker;
        setKafkaProducer(this.kafkaBroker);
    	
    }
    
    public void run(){
		int printCounter=500; //When to print a notification
    	if(this.mode.equals("2")){
    		while(true){
    			try{

    				// Enter your consumer key and secret below
    				OAuthService service = new ServiceBuilder()
    						.provider(TwitterApi.class)
    						.apiKey(this.apiKey)
    						.apiSecret(this.apiSecret)
    						.build();

    				// Set your access token
    				Token accessToken = new Token(this.tokenValue,  this.tokenSecret);

    				// Let's generate the request
    				System.out.println("Connecting to Twitter Public Stream");
    				OAuthRequest request = new OAuthRequest(Verb.POST, STREAM_URI);
    				request.addHeader("version", "HTTP/1.1");
    				request.addHeader("host", "stream.twitter.com");
    				request.setConnectionKeepAlive(true);
    				request.addHeader("user-agent", "Twitter Stream Reader");
    				request.addBodyParameter("track", "java,twitter"); // Set keywords you'd like to track here CHANGE THIS.
    				service.signRequest(accessToken, request);
    				Response response = request.send();

    				// Create a reader to read Twitter's stream
    				BufferedReader reader = new BufferedReader(new InputStreamReader(response.getStream()));

    				String line;
    				while ((line = reader.readLine()) != null) {
    					try {
    						//System.out.println(line);
    						if(isValidTweet(line)==false){
    							//System.out.println("Invalid Tweet received, ignoring it");
    						}else{
    							String finalTweetInfo=getTweetInfo(line);
    							
    							
    							if(!finalTweetInfo.equals("empty")){
    								//System.out.println("Final Info = "+ finalTweetInfo);
    								
    	                    		while(sendToKafka(finalTweetInfo)==false){
    	                    			System.out.println("Problem sending information to kafka, sleeping 15 seconds before re-trying");
    	                    			Thread.sleep(15000);
    	                    		}
    	                    		
    	                    		if(this.tweetRead==printCounter){
    	                    			System.out.println("Read " + this.tweetRead + "Tweets");
    	                    			printCounter=printCounter+500;
    	                    		}
    							}
    						}
    					} catch (InterruptedException e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					}

    				}
    			}
    			catch (IOException ioe){//
    				ioe.printStackTrace();
    			}
    		}

    	}else{
    		System.out.println("Starting to read tweets from file:" + this.filePath);
    		ArrayList<String> tweets = new ArrayList<>();
    		String line;
    		try {
    			InputStream fis = new FileInputStream(this.filePath);
				InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
				BufferedReader br = new BufferedReader(isr);
				while ((line = br.readLine()) != null) {
					try {
						//Thread.sleep(2000);
						//System.out.println("Line = "+line);
						this.tweetRead= tweetRead+1;
						//System.out.println("read Tweets = " + this.tweetRead);
						
							
					    if(isValidTweet(line)==false){
							//System.out.println("Invalid Tweet received, ignoring it");
					    	Thread.sleep(1);
						}else{
							String finalTweetInfo=getTweetInfo(line);
							
							if(!finalTweetInfo.equals("empty")){
								//System.out.println("Final Info = "+ finalTweetInfo);
								this.tweetSentCount=this.tweetSentCount+1;
								
	                    		while(sendToKafka(finalTweetInfo)==false){
	                    			System.out.println("Problem sending information to kafka, sleeping 15 seconds before re-trying");
	                    			Thread.sleep(15000);
	                    		}
	                    		if(this.tweetRead==printCounter){
	                    			System.out.println("Read " + this.tweetRead + "Tweets");
	                    			printCounter=printCounter+500;
	                    		}
	                    		//System.out.println("tweets Sent = " + this.tweetSentCount);
	                    		//System.out.println("hash Sent = " + this.hashSentCount);
							}
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				System.out.println("No more tweets on file.");
			} catch (java.io.FileNotFoundException e) { 
				// TODO Auto-generated catch block
				System.out.println("Could not find the file on the given directory");
				return;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
        
    }
    
    //This method checks if the tweet contains created_at
    public boolean isValidTweet(String jsonTweet){
    	//System.out.println("isValidTweet = " + jsonTweet.toLowerCase().contains("created_at".toLowerCase()));
    	return jsonTweet.toLowerCase().contains("created_at".toLowerCase());

    }
    
    //This method checks if we reach a twitter api limit
    public boolean limitReached(String jsonTweet){
    	return jsonTweet.toLowerCase().contains("limit".toLowerCase());
    }
    
    //This method gets the tweet language, hashtags and timestamp. If no hashtag, returns empty.
    public String getTweetInfo(String jsonTweet){

    	//This method received a tweet in json format and returns a String with the format:
    	//language, timestamp, list of hashtags.
    	 ObjectMapper objectMapper = new ObjectMapper();
         objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
         //ObjectMapper mapper = new ObjectMapper(new JsonFactory()); 
         Tweet tweet;
         String hashtagList="";
         String finalTweetInfo="";
         
		 try {
			tweet = objectMapper.readValue(jsonTweet, Tweet.class);
			
			
			Map<String, Object> tweet2 = objectMapper.readValue(jsonTweet, new TypeReference<HashMap<String,  Object>>(){});
			
			Map<String, Object> entities = (HashMap<String, Object>) tweet2.get("entities");
			ArrayList<HashMap<String, Object>> hashTagEntries = null;
			
			if (entities != null) {
				hashTagEntries = (ArrayList<HashMap<String, Object>>) entities.get("hashtags");
			}
			
			if (hashTagEntries != null && hashTagEntries.size() > 0) {
				for (Map<String, Object> hashTagEntry : hashTagEntries) {
					
					String hashTag = hashTagEntry.get("text").toString();
					//System.out.println("Jackson parser hashtag = " + hashTag);
					hashtagList = hashtagList + "#" + hashTag;
					this.hashSentCount=this.hashSentCount+1;
					
					if(tweet.lang.equals("ja")){
						this.jaHashCount=this.jaHashCount+1;
					}else if(tweet.lang.equals("ko")){
						this.koHashCount=this.koHashCount+1;
					}else if(tweet.lang.equals("en")){
						this.enHashCount=this.enHashCount+1;
					}else if(tweet.lang.equals("es")){
						this.esHashCount=this.esHashCount+1;
					}
					/*
					
					System.out.println("jaHashSent = "+this.jaHashCount);
					System.out.println("koHashSent = "+this.koHashCount);
					System.out.println("enHashSent = "+this.enHashCount);
					System.out.println("esHashSent = "+this.esHashCount);
					System.out.println("Filtered by language Total = " + (this.jaHashCount+this.koHashCount+this.enHashCount+this.esHashCount));
				  */
				}
			}
			
			if(hashtagList.isEmpty()){
				hashtagList="empty";
			}
			
			//hashtagList =tweet.getHashtagList();
	        finalTweetInfo =tweet.lang+", "+tweet.timestamp_ms + ", "+ hashtagList;
	        
	        if(hashtagList.equals("empty")){
	        	return "empty";
	        }
	        
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
         //System.out.println("Text: "+ tweet.text);
         //System.out.println("Lang: "+ tweet.lang);
         //System.out.println("TimeStamp: "+ tweet.timestamp_ms);
         //System.out.println("List of Hashtag: "+tweet.getHashtagList());
		
		return finalTweetInfo;
    	
    
    }
    
    //This method initializes the kafka producer
    public void setKafkaProducer(String kafkaBroker){
    	//KAFKA Producer Settings
        properties = new Properties();
        properties.put("metadata.broker.list",kafkaBroker);
        properties.put("serializer.class","kafka.serializer.StringEncoder");
        producerConfig = new ProducerConfig(properties);
    }
    
    //This method sends messages to Kafka.
    public boolean sendToKafka(String finalTweetInfo){
    	//Send a msg to Kafka
        kafka.javaapi.producer.Producer<String,String> producer = new kafka.javaapi.producer.Producer<String, String>(producerConfig);
        KeyedMessage<String, String> message =new KeyedMessage<String, String>(TOPIC,finalTweetInfo);
        //System.out.println("Sending msg to Kafka.");
        try{
    	   producer.send(message);
           //System.out.println("Msg sent succesfully.");
        }catch(kafka.common.FailedToSendMessageException k){
        	producer.close();
        	return false;
        }
        producer.close();
        return true;
    }
}