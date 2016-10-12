library(lattice)
library(ggplot2)


output$Theplot  <- renderPlot({
  
  zero_range <- function(x) {
    if (length(x) == 1) return(TRUE)
    x <- range(as.numeric(x)) / mean(as.numeric(x))
    isTRUE(all.equal(x[1], x[2], tolerance = 0))
  }

    da <- input$checkGroup
  #input$com
  #input$var
  comName <- subset(Drug,Drug$communityName==input$com)
  comcode<-(comName$codeCommunity)
  #transform the input$var into the names justin used
#   namesParsed<-unique(electionData$partyName[!is.na(electionData$partyName)])
#   namesUnparsed<-unique(electionData$info[!is.na(electionData$info)])[-(1:5)]
#   inputVarTranformed<-namesUnparsed[which(namesParsed==electionData$info)]
#   
  dd = subset(electionData, electionData$partyName ==input$var)
  
  
  if(is.na(comcode)){
    dd = subset(dd, is.na(dd$community))
    ddDrug=subset(Drug,is.na(Drug$codeCommunity))
    ddAlcohool=subset(Alcohool,is.na(Alcohool$codeCommunity))
    ddPension=subset(Pension,is.na(Pension$codeCommunity))
    ddPrisonners=subset(Prisonners,is.na(Prisonners$codeCommunity))
    ddEducated=subset(Educated,is.na(Educated$codeCommunity))
    ddNoEducated=subset(NoEducated,is.na(NoEducated$codeCommunity))
    
  }else{
    dd = subset(dd, dd$community==comcode)
    ddDrug=subset(Drug,Drug$codeCommunity==comcode)
    ddAlcohool=subset(Alcohool,Alcohool$codeCommunity==comcode)
    ddPension=subset(Pension,Pension$codeCommunity==comcode)
    ddPrisonners=subset(Prisonners,Prisonners$codeCommunity==comcode)
    ddEducated=subset(Educated,Educated$codeCommunity==comcode)
    ddNoEducated=subset(NoEducated,NoEducated$codeCommunity==comcode)
  }
  
  
  
  if( !zero_range(subset(dd$province,!is.na(dd$province)))){
    dd = subset(dd,is.na(dd$province))
    ddDrug=subset(ddDrug,is.na(ddDrug$codeProvince))
    ddAlcohool=subset(ddAlcohool,is.na(ddAlcohool$codeProvince))
    ddPension=subset(ddPension,is.na(ddPension$codeProvince))
    ddPrisonners=subset(ddPrisonners,is.na(ddPrisonners$codeProvince))
    ddEducated=subset(ddEducated,is.na(Educated$codeProvince))
    ddNoEducated=subset(ddNoEducated,is.na(NoEducated$codeProvince))
    
  }
  
  if(input$radio=="p")
  {
    
    if(all(is.na(c(dd$percentage)))) 
    {plot(1, type="n", axes=F, xlab="", ylab="" )
      title(main = "No data on percentage available")
    } else{
      
      ggplot(dd,aes(year,percentage, group = 1))+ geom_point(size=2) + geom_line(color="red")
      
      
      
    }
  }else{
    if(input$radio=="v")
    {ggplot(dd,aes(year,number, group = 1))+ geom_point(size=2) + geom_line(color="red")
      
      
    }else{
      if(input$radio=="n"){
        if(all(is.na(c(dd$percentage)))) 
        {
          plot(1, type="n", axes=F, xlab="", ylab="" )
          title(main = "No data on percentage available")
        } else{
          
          dd2=dd
          dd2$NormalizePercentage = (dd$percentage-min(dd$percentage))/(max(dd$percentage)-min(dd$percentage))
          p <- ggplot(dd2,aes(year,NormalizePercentage, group = 1, colour="Feature"))+ geom_point(size=2,color="black") + geom_line(color="red")
          
          if(!length(input$checkGroup)==0){
            
            if("Drug" %in% input$checkGroup ){
              
              ddDrug=t(ddDrug[,4:22])
              yearT <- as.factor(c( 1991 , 1992 ,	 1993 ,	 1994 ,	 1995 ,	 1996 ,	 1997 ,	 1998 ,	 1999 ,	 2000 ,	 2001 ,	 2002 ,	 2003 ,	 2004 ,	 2005 ,	 2006 ,	 2007 ,	 2008	 ,  2009 ))
              
              ddDrugF<-data.frame(yearT,ddDrug)
              
              names(ddDrugF)<-c("year","NormalizePercentage")
              ddDrugF$NormalizePercentage= (as.numeric(ddDrugF$NormalizePercentage)-min(as.numeric(ddDrugF$NormalizePercentage)))/(max(as.numeric(ddDrugF$NormalizePercentage))-min(as.numeric(ddDrugF$NormalizePercentage)))
              
              p <- p+geom_line(aes(year,NormalizePercentage, group = 1,colour="Drug"),data = ddDrugF)
              
            }
            
            
            if( "Alcohool" %in% input$checkGroup){
              ddAlcohool=t(ddAlcohool[,4:22])
              yearT <- as.factor(c( 1991 , 1992 ,	 1993 ,	 1994 ,	 1995 ,	 1996 ,	 1997 ,	 1998 ,	 1999 ,	 2000 ,	 2001 ,	 2002 ,	 2003 ,	 2004 ,	 2005 ,	 2006 ,	 2007 ,	 2008	 ,  2009 ))
              
              ddAlcohoolF<-data.frame(yearT,ddAlcohool)
              
              names(ddAlcohoolF)<-c("year","NormalizePercentage")
              ddAlcohoolF$NormalizePercentage= (as.numeric(ddAlcohoolF$NormalizePercentage)-min(as.numeric(ddAlcohoolF$NormalizePercentage)))/(max(as.numeric(ddAlcohoolF$NormalizePercentage))-min(as.numeric(ddAlcohoolF$NormalizePercentage)))
              
              p <- p+geom_line(aes(year,NormalizePercentage, group = 1,colour="Alcohol"),data = ddAlcohoolF)#,color="green")
              
            }
            
            
            
            if( "Pension" %in% input$checkGroup){
              ddPension=t(ddPension[,4:22])
              yearT <- as.factor(c( 1991 , 1992 ,	 1993 ,	 1994 ,	 1995 ,	 1996 ,	 1997 ,	 1998 ,	 1999 ,	 2000 ,	 2001 ,	 2002 ,	 2003 ,	 2004 ,	 2005 ,	 2006 ,	 2007 ,	 2008	 ,  2009 ))
              
              ddPensionF<-data.frame(yearT,ddPension)
              
              names(ddPensionF)<-c("year","NormalizePercentage")
              ddPensionF$NormalizePercentage= (as.numeric(ddPensionF$NormalizePercentage)-min(as.numeric(ddPensionF$NormalizePercentage)))/(max(as.numeric(ddPensionF$NormalizePercentage))-min(as.numeric(ddPensionF$NormalizePercentage)))
              
              p <- p+geom_line(aes(year,NormalizePercentage, group = 1,colour="Pension"),data = ddPensionF)
              
            }
            
            if( "Prisonners" %in% input$checkGroup){
              ddPrisonners=t(ddPrisonners[,4:20])
              yearT <- as.factor(c(1993 ,	 1994 ,	 1995 ,	 1996 ,	 1997 ,	 1998 ,	 1999 ,	 2000 ,	 2001 ,	 2002 ,	 2003 ,	 2004 ,	 2005 ,	 2006 ,	 2007 ,	 2008	 ,  2009 ))
              
              ddPrisonnersF<-data.frame(yearT,ddPrisonners)
              
              names(ddPrisonnersF)<-c("year","NormalizePercentage")
              ddPrisonnersF$NormalizePercentage= (as.numeric(ddPrisonnersF$NormalizePercentage)-min(as.numeric(ddPrisonnersF$NormalizePercentage)))/(max(as.numeric(ddPrisonnersF$NormalizePercentage))-min(as.numeric(ddPrisonnersF$NormalizePercentage)))
              
              p <- p+geom_line(aes(year,NormalizePercentage, group = 1,colour="Prisonners"),data = ddPrisonnersF)
              
            }       
            
            
            if( "NoEducated" %in% input$checkGroup){
              ddNoEducated=t(ddNoEducated[,4:22])
              yearT <- as.factor(c( 1991 , 1992 ,	 1993 ,	 1994 ,	 1995 ,	 1996 ,	 1997 ,	 1998 ,	 1999 ,	 2000 ,	 2001 ,	 2002 ,	 2003 ,	 2004 ,	 2005 ,	 2006 ,	 2007 ,	 2008	 ,  2009 ))
              
              ddNoEducatedF<-data.frame(yearT,ddNoEducated)
              
              names(ddNoEducatedF)<-c("year","NormalizePercentage")
              ddNoEducatedF$NormalizePercentage= (as.numeric(ddNoEducatedF$NormalizePercentage)-min(as.numeric(ddNoEducatedF$NormalizePercentage)))/(max(as.numeric(ddNoEducatedF$NormalizePercentage))-min(as.numeric(ddNoEducatedF$NormalizePercentage)))
              
              p <- p+geom_line(aes(year,NormalizePercentage, group = 1,colour="NoEducated"),data = ddNoEducatedF)
              
            }
            
            
            if( "Educated" %in% input$checkGroup){
              ddEducated=t(ddEducated[,4:22])
              yearT <- as.factor(c( 1991 , 1992 ,	 1993 ,	 1994 ,	 1995 ,	 1996 ,	 1997 ,	 1998 ,	 1999 ,	 2000 ,	 2001 ,	 2002 ,	 2003 ,	 2004 ,	 2005 ,	 2006 ,	 2007 ,	 2008	 ,  2009 ))
              
              ddEducatedF<-data.frame(yearT,ddEducated)
              
              names(ddEducatedF)<-c("year","NormalizePercentage")
              ddEducatedF$NormalizePercentage= (as.numeric(ddEducatedF$NormalizePercentage)-min(as.numeric(ddEducatedF$NormalizePercentage)))/(max(as.numeric(ddEducatedF$NormalizePercentage))-min(as.numeric(ddEducatedF$NormalizePercentage)))
              
              p <- p+geom_line(aes(year,NormalizePercentage, group = 1,colour="Educated"),data = ddEducatedF)
              
            }
            
            
            
            
          }
          
          
          
          p <- p +  scale_colour_manual(values=c("black","orange","green","blue","purple","darkgreen"))
          
          p
          
          
        }
      }
    } 
  }
  
  
  
  
  
  
})