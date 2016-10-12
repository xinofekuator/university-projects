package twitterApp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class TwitterStreamServer {
	
    public static void main(String[] args){

    	String mode; //2 = Real tweets. 1=From file.
    	String apiKey;
    	String apiSecret;
    	String tokenValue;
    	String tokenSecret;
    	String kafkaBroker;
    	String filePath;
    	
        if(args.length == 0){
        	
        	System.out.println("No Parameters received. Format: Mode apiKey apiSecret tokenValue tokenSecret kafkaBrokerUrl filePath");
        	return;
        	
        	/*
        	mode="1"; //2 = Real tweets. 1=From file.
        	apiKey="iDeGJvOF0X3nmsI7vWQdShba8";
        	apiSecret="FmLCVAoTnwPBCCT1MhCFH9rf4hcsH6Qea8K7zCFhHEQQwFYDi0";
        	tokenValue = "34494124-lazhbItBoO5ZGbbMhWN6eGZJ6bfPpuFLxjLJbmF2L";
        	tokenSecret = "UJqHyXn8MwTf1LgjNsGJfkxS26wAD2xuPVrFWHXmVaIzH";
        	kafkaBroker="localhost:9092";
        	filePath= "/Users/Hugo/Documents/workspace/tweets.txt";*/
        	
        }else if(args.length == 7){
        	mode=args[0]; //2 = Real tweets. 1=From file.
        	apiKey=args[1];
        	apiSecret=args[2];
        	tokenValue =args[3];
        	tokenSecret=args[4];
        	kafkaBroker=args[5];
        	filePath=args[6];
        }else{
        	System.out.println("Parameters not correct. Format: Mode apiKey apiSecret tokenValue tokenSecret kafkaBrokerUrl filePath");
        	return;
        }
        
        final TwitterStreamProducer streamConsumer = new TwitterStreamProducer(apiKey,apiSecret,tokenValue,tokenSecret, mode, filePath,kafkaBroker); // final because we will later pull the latest Tweet
        streamConsumer.start();
        
        
    }
}