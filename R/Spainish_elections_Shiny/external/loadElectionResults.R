#1993 - 2008
path = "data/"

electionYears <- c("1993", "1996","2000", "2004", "2008")

electionData <- data.frame(V1=integer(), 
                           V2=integer(),
                           V3=integer(),
                           V4=factor(),
                           V5=integer(),
                           V6=numeric(),
                           V7=integer(),
                           stringsAsFactors=FALSE) 

for (i in 1:length(electionYears)){
  assign("auxData",
         read.csv(paste(path,electionYears[i],".csv",sep=""), header=FALSE))
  electionData<-rbind(electionData, auxData)
}


#Rename the column names and transform to the right class

#lapply(electionData,class)
names(electionData)<-c("community","province","year","info","number","percentage","seats")
electionData$community <- as.factor(electionData$community)
electionData$province <- as.factor(electionData$province)
electionData$year <- as.factor(electionData$year)
electionData$info <- as.factor(electionData$info)

#Get the summary for the results in all the provinces
#empty<- electionData[is.na(electionData$community),]

#join by (year,community,province)

grep("\\((.)+\\)",unique(electionData$info),value = TRUE)
unique(electionData$info)
pat <- "(.*)(\\(.+\\))"
sub(pat, "", unique(electionData$info)[grepl(pat, unique(electionData$info))])

electionData["partyName"] <- as.character(electionData$info)
electionData["partyID"] <- as.character(electionData$info)
electionData["partyColor"] <- as.character(electionData$info)

electionParties <- unique(electionData$info)
partiesId <- c(NA,NA,NA,NA,NA,"PSOE","PP","IU","CiU","EAJ-PNV","CC","HB","ERC","PAR",
               "EA-EUE","UV","CDS","BNG","PA","Others","PSC-PSOE","IC","PSE-EE/PSOE",
               "UPN-PP","EA","ChA","PP-PAR","IC-EV","PSOE-prog","IU-EUiA","IC-V",
               "PP-UPM","Na-Bai","ICV-EUiA","PIB","PSM-EN-EU-EV-ERC","IUN-NEB","CC-PNC",
               "UPyD","CA")
partiesColors <- c(NA,NA,NA,NA,NA,"PSOE","PP","IU","CiU","EAJ-PNV","CC","HB","ERC","PAR",
               "EA-EUE","UV","CDS","BNG","PA","Others","PSC-PSOE","IC","PSE-EE/PSOE",
               "UPN-PP","EA","ChA","PP-PAR","IC-EV","PSOE-prog","IU-EUiA","IC-V",
               "PP-UPM","Na-Bai","ICV-EUiA","PIB","PSM-EN-EU-EV-ERC","IUN-NEB","CC-PNC",
               "UPyD","CA")

for (i in 1:length(electionParties)){
  electionData$partyName[electionData$partyID == electionParties[i]] <- partiesId[i]
  electionData$partyColor[electionData$partyID == electionParties[i]] <- partiesId[i]
}

electionData["communityName"] <- as.numeric(electionData$community)
communityNames<- c("All",unique(spainReduced.df$NAME_1),"Ceuta","Melilla")
# 
# electionData$partyID <- 
# electionData$partyColor <- 

#add color columnn to the geoData
#for each number of province get the party
#add that to the 
# 
# resultsEx <- electionData[electionData$year==2008 & electionData$province==1,]
# resultsEx[!is.na(resultsEx$seats),]
# resultsEx[which.max(resultsEx$seats),]

