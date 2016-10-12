package twitterApp;

import java.awt.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true) 
public class Tweet {
	
	@JsonProperty("text")
	public String text;
	@JsonProperty("lang")
	public String lang;
	@JsonProperty("timestamp_ms")
	public String timestamp_ms;
	@JsonProperty("hashtags")
	public ArrayList<String> hashtags;
	
	/*
	 * Version 1: Parsing the tweet with splits.
	public String getHashtagList(){
		System.out.println("Text = " + this.text);
		String hashtagList="";
		String[] splittedText= this.text.split(" ");
		
		for(int i=0; i<splittedText.length; i++){
			if(splittedText[i].startsWith("#")){
				if(hashtagList.isEmpty()){
					hashtagList=splittedText[i];
				}else{
					hashtagList=hashtagList+splittedText[i];
				}
			}
		}
		if(hashtagList.isEmpty()){
			hashtagList="empty";
		}
		return hashtagList;
	}*/

	/*
	 * Version 2: Parsing the tweet with patterns.
	public String getHashtagList(){
		String hashtagList="";
		
		Pattern MY_PATTERN = Pattern.compile("#(\\w+|\\W+)");
		Matcher mat = MY_PATTERN.matcher(this.text);
		ArrayList<String> str=new ArrayList<String>();
		while (mat.find()) {
		  //System.out.println(mat.group(1));
		  str.add(mat.group(1));
		}
		for(int i = 0; i<str.size(); i++){
			hashtagList=hashtagList+"#"+str.get(i);
		}
		
		if(hashtagList.isEmpty()){
			hashtagList="empty";
		}
		
		return hashtagList;
	}*/
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getTimestamp_ms() {
		return timestamp_ms;
	}

	public void setTimestamp_ms(String timestamp_ms) {
		this.timestamp_ms = timestamp_ms;
	}

	public ArrayList<String> getHashtags() {
		return hashtags;
	}

	public void setHashtags(ArrayList<String> hashtags) {
		this.hashtags = hashtags;
	}
	
	/*
	public boolean limitReached(){
		if(this.track!=null){
			return true;
		}
		
		return false;
		
	}*/

}
