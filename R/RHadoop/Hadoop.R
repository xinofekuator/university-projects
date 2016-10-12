#Authors
#Ignacio Amaya and Justin Vailhere

#Loading the libraries
library(rhdfs)
library(rmr2)

hdfs.init()		

####EXERCISE 1: Computing a histogram with MapReduce####

minmax = function(input,output){
  # Defining minmax Map functon 
  wc.map = function(., lines){
    numbers=unlist(strsplit(x = lines, split = " "))
    max.value=max(as.numeric(numbers))
    min.value=min(as.numeric(numbers))
    c.keyval(keyval( 1 , min.value), keyval( 1 , max.value) )
  }
  
  # Defining minmax Reduce function 
  wc.reduce = function(key, value ){
    write(key,file='/home/hadoop/log.txt',append = TRUE)
    write('\n',file='/home/hadoop/log.txt',append = TRUE)
    write(value,file='/home/hadoop/log.txt',append = TRUE)
    c.keyval(keyval( 'min' , min(value)), keyval( 'max' , max(value)) )
  }
  
  # Defining MapReduce parameters by calling mapreduce function 
  mapreduce(input=input,
            output = output,
            input.format = "text",
            map = wc.map,
            reduce = wc.reduce,
            combine = F)
  
}

selectBins = function(input,output,n,value.min,value.max){
  
  bin<-function(number,n){
    if(number!=value.max){sol=(number-value.min)/((value.max-value.min)/n)}
    else{sol=n-1}
    return(floor(sol))
  }
  # Defining selectBins Map functon 
  wc.map = function(., lines){
    numbers=unlist(strsplit(x = lines, split = " "))
    partial.bins=sapply(as.numeric(numbers),bin,n)
    keyval( partial.bins , 1)
  }
  
  # Defining selectBins Reduce function 
  wc.reduce = function(key, value ){
    keyval(key,sum(value))
  }
  
  # Defining MapReduce parameters by calling mapreduce function 
  mapreduce(input=input,
            output = output,
            input.format = "text",
            map = wc.map,
            reduce = wc.reduce,
            combine = F)
}

#Function that uses both previous map reduce functions to create the histogram
#given the number of bins n and the file containing the numbers
hadoop.histogram <- function (file,n){
  hdfs.histFolder	=	file.path('/user/hadoop',	'histogram')
  hdfs.mkdir(hdfs.histFolder)
  hdfs.outFolder=file.path(hdfs.histFolder,	'out')
  hdfs.outFolder2=file.path(hdfs.histFolder,	'out2')	
  hdfs.dataFolder=file.path(hdfs.histFolder,	'data')	
  hdfs.put(file,	hdfs.dataFolder)
  
  out = minmax(hdfs.dataFolder,hdfs.outFolder)
  out_minmax=from.dfs(out)
  
  min.selected=out_minmax$val[out_minmax$key=="min"]
  max.selected=out_minmax$val[out_minmax$key=="max"]
  out2=selectBins(hdfs.dataFolder,hdfs.outFolder2,n,min.selected,max.selected)
  out_bins=from.dfs(out2)
  hdfs.rm(hdfs.histFolder)
  return(out_bins)
}

#Create a file with 100 random numbers to test our code
random.numbers=runif(100, min=-100, max=100)
write(random.numbers, file = "/home/hadoop/100numbers.txt")

#Change this value to change the number of bins
N=10

result=hadoop.histogram('/home/hadoop/100numbers.txt',N)

#Only using R
file.values=scan(file = '/home/hadoop/100numbers.txt')
pass.value=(max(file.values)-min(file.values))/N
hist.values=hist(file.values,breaks=seq(min(file.values),max(file.values),pass.value))
freqs=hist.values$counts

#Checking the values are the same 
#(this won't work if there's a 0 in some bin because we would have 
#that value missing in the hadoop result)
result$val==freqs

#Plotting both results
par(mfrow=c(1,2))
hist.values=hist(file.values,breaks=seq(min(file.values),max(file.values),pass.value))
barplot(result$val,main="Hadoop histogram")

####EXERCISE 2: Comparing the GDP of several countries with the profit of apple in 2012####

#Apple profit we use to compare the gdp of the countries
apple.profit = 156508

appleCompare.map<- function(key,value) {
  new.key=ifelse(value < apple.profit,'less than apple','more than apple')
  keyval(new.key, 1)
}

appleCompare.reduce<- function(key,value) {
  keyval(key, sum(value))
}

gdp <- read.csv('/home/hadoop/ReallyCleanGDPfile.csv')
gdp.input=keyval(gdp$Code,gdp$GDP)
gdp.input=to.dfs(gdp.input2)

result = mapreduce(input=gdp.input,map = appleCompare.map,reduce = appleCompare.reduce)
result=from.dfs(result)
result

#Plot to check the results
par(mfrow=c(1,1))
plot(gdp$Code, gdp$GDP,type = "h",ylab="GDP",xlab="Countries",
     main="Plot of the countries' GDP (red line : Apple profit)")
abline(h=apple.profit,col="red")


#Checking the results (doing the same thing in R without the map reduce)
myData <-read.csv( "/home/hadoop/ReallyCleanGDPfile.csv" , header=TRUE)
all.gdp <-myData$GDP
length(all.gdp)
#We can see that the results we obtain this way are the same.
length(all.gdp[all.gdp < apple.profit])
length(all.gdp[all.gdp > apple.profit])
# 191 = 134 + 57

