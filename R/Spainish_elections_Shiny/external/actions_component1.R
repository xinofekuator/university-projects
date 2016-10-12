provincePlot<-reactive({
  resultPlot<-c() #inizialisation (never used)
  provinceName <- unique(spainReduced.df$NAME_2)
  for (i in provinceName){
    #provinceCode <- unique(spainReduced.df$ID_2[which(spainReduced.df$NAME_2 == i)])
    provinceCode<-getCorrectProvinceCode(i)
    resultsEx <- electionData[electionData$year==  input$electionYear 
                              & electionData$province== provinceCode,]
    resultsEx[!is.na(resultsEx$seats),]
    colorValue <- resultsEx[which.max(resultsEx$seats),]$partyName
    spain2.df$colors[spain2.df$NAME_2 == i] <- colorValue
    spainReduced.df$colors[spainReduced.df$NAME_2 == i] <- colorValue
  }
  
  resultsEx <- electionData[electionData$year==  input$electionYear 
                            & electionData$province==1,]
  resultsEx[!is.na(resultsEx$seats),]
  resultsEx[which.max(resultsEx$seats),]$partyName
  
  if(input$province == "Ceuta"){
    ceuta <- spain2.df[spain2.df$CCA_2  %in% c("51"),]
    ceutaFiltered <-
      ceuta[which(ceuta$long < (-5.25) & ceuta$long > (-5.38)),]
    resultPlot <- getPlotMap (ceutaFiltered,"")
  }
  else if(input$province == "Melilla"){
    melilla <- spain2.df[spain2.df$CCA_2  %in% c("52"),]
    melillaFiltered <-
      melilla[which(melilla$long > -3.2 & melilla$long < -2.8),]
    resultPlot <- getPlotMap (melillaFiltered,"")
  }
  else if(input$province=="All"){
    islands <-  spain2.df[spain2.df$CCA_2  %in% c("35", "38"),]
    cities <- spain2.df[spain2.df$CCA_2  %in% c("51", "52"),]
    ceuta <- spain2.df[spain2.df$CCA_2  %in% c("51"),]
    ceutaFiltered <-
      ceuta[which(ceuta$long < (-5.25) & ceuta$long > (-5.38)),]
    melilla <- spain2.df[spain2.df$CCA_2  %in% c("52"),]
    melillaFiltered <-
      melilla[which(melilla$long > -3.2 & melilla$long < -2.8),]
    peninsula <-
      spainReduced.df[!(spainReduced.df$CCA_2  %in% c("35","38","51", "52")),]
    peninsulaFiltered <- peninsula[which(peninsula$lat > 35),]
    
    spainPlot <<- getPlotMap (peninsulaFiltered,"")
    canaryPlot <<- getBorderPlotMap (islands,"Canary islands")
    ceutaPlot <<- getBorderPlotMap (ceutaFiltered,"Ceuta")
    melillaPlot <<- getBorderPlotMap (melillaFiltered,"Melilla")
  }
  else{
    province <- spain2.df[spain2.df$NAME_1 %in% c(input$province),]
    resultPlot <- getPlotMap (province,"")
  }
  return (resultPlot)
})

output$mapSpain <- renderPlot({
  require(gridExtra)
  require(grid)
  if(input$province == "All"){  
    provincePlot()
    grid.newpage() # Open a new page on grid device
    pushViewport(viewport(layout = grid.layout(15, 7)))
    print(spainPlot, vp = viewport(layout.pos.row = 1:14, layout.pos.col = 1:7))
    print(canaryPlot, vp = viewport(layout.pos.row = 11:15, layout.pos.col = 5:7))
    print(melillaPlot, vp = viewport(layout.pos.row = 13:15, layout.pos.col = 3:4))
    print(ceutaPlot, vp = viewport(layout.pos.row = 13:15, layout.pos.col = 1:2))
  }
  else{
    provincePlot()
  }
})

filteredResults <- reactive({
  if(input$province == "All"){  
    localResults<-electionData[electionData$year==input$electionYear 
                               & is.na(electionData$community) &
                                 is.na(electionData$province) &
                                 !is.na(electionData$seats),]
    others <- sum(localResults[!localResults$partyName=="Others" & localResults$seats <=5,]$seat)
    localResults$seats[localResults$partyName == "Others"] <- localResults$seats[localResults$partyName == "Others"] + others
    filteredResults <- localResults[localResults$partyName == "Others" | localResults$seats >5 ,]
  }
  else {
    if(input$province == "Ceuta" ){provinceCode<-18}
    else if(input$province == "Melilla" ){provinceCode<-19}
    else{
      provinceCode <- unique(spainReduced.df$ID_1[which(spainReduced.df$NAME_1 == input$province)])
      provinceCode<-getCorrectCode(provinceCode)
    }
    localResults<-electionData[electionData$year==input$electionYear 
                               & electionData$community == provinceCode &
                                 !is.na(electionData$seats),]
    if(length(unique(localResults$province))>2){
      localResults<-localResults[is.na(localResults$province),]
    }
    localResults <- localResults[!is.na(localResults$partyName),]
    filteredResults <- localResults[localResults$seats >0,]
  }
  return (filteredResults)
})

output$pieChart <- renderPlot({
  filteredData <- filteredResults()
  slices <- filteredData$seat
  lbls <- filteredData$partyName
  colorsPie<-c()
  for(i in lbls){
    colorsPie<-c(colorsPie,colorsPalette[i])
  } 
  pie(slices, labels = slices, col=colorsPie)
  legend("topleft", lbls, cex=0.78, fill=colorsPie)
})

output$provinceText <- renderText({ 
  input$province
})

output$summaryTable <- renderDataTable({
  filteredData <- filteredResults()
  table<-filteredData[,c("partyName","number","seats","percentage")]
},   options=list(
  paging = FALSE,
  searching = FALSE))

output$barChart <- renderPlot({
  if(input$province != "All"){ 
    if(input$province == "Ceuta" ){provinceCode<-18}
    else if(input$province == "Melilla" ){provinceCode<-19}
    else{
      provinceCode <- unique(spainReduced.df$ID_1[which(spainReduced.df$NAME_1 == input$province)])
      provinceCode<-getCorrectCode(provinceCode)
    }
    provincesIndex <- unique(electionData$province[electionData$community==provinceCode])
    provincesIndex <- provincesIndex[!is.na(provincesIndex)]
    features<- input$checkGroup1
    bar.dataframe<- as.data.frame(matrix(nrow = length(provincesIndex), ncol = length(features)))
    names(bar.dataframe)<- features
    provincesNames<-c()
    for(i in provincesIndex){
      pName<- Drug$communityName[Drug$codeProvince==i]
      pName <- pName[!is.na(pName)]
      provincesNames <- c(provincesNames,pName)
      if(i %in% Drug$codeProvince){index <- which(Drug$codeProvince==i)}
      else{
        #astur,balrs,cantabria,madrid,murcia,navarra,la rioja
        old<-c(3,4,6,13,14,15,17) #from Drug
        #unique(electionData[,c("community","province")])
        new<-c(3,4,6,12,13,15,16) #from electionData
        newP<-c(33,7,39,28,31,30,26)
        index<-which(Drug$codeCommunity==old[which(newP==i)])
      }
      year<-which(names(Drug)==input$electionYear)
      featuresVector<-c()
      if( "Drug" %in% features){featuresVector<- c(featuresVector,Drug[index,year])}
      if( "Alcohool" %in% features){featuresVector<-c(featuresVector,Alcohool[index,year])}
      #if( "Pension" %in% features){featuresVector<-c(featuresVector,Pension[index,year])}
      if( "Prisonners" %in% features){featuresVector<-c(featuresVector,Prisonners[index,year])}
      if( "NoEducated" %in% features){featuresVector<-c(featuresVector,NoEducated[index,year])}
      if( "Educated" %in% features){featuresVector<-c(featuresVector,Educated[index,year])}
      bar.dataframe[which(provincesIndex==i),] <- featuresVector
    }
    featuresPalette<-topo.colors(length(features))
    barplot(t(as.matrix(bar.dataframe)), beside=TRUE,col=featuresPalette,
            legend.text = TRUE, 
            args.legend = list(x = "topright", bty = "n", legend=features, fill=featuresPalette,
                               xpd=TRUE))
  }
})


#Correct an error of the dataset (actually the wrongCode is the right one)
#wrong codes from http://www.ine.es/en/daco/daco42/codmun/cod_ccaa_en.htm
#b<-unique(electionData[,c(1,2)])
#a<-unique(spainReduced.df[,c(6,7,12,9)])
getCorrectCode <- function (wrongCode){
  if (wrongCode==1){rightCode=1}
  else if (wrongCode==2){rightCode=2}
  else if (wrongCode==3){rightCode=6}
  else if (wrongCode==4){rightCode=7}
  else if (wrongCode==5){rightCode=8}
  else if (wrongCode==6){rightCode=9}
  else if (wrongCode==7){rightCode=-1}#ceuta+melilla
  else if (wrongCode==8){rightCode=12}
  else if (wrongCode==9){rightCode=13}
  else if (wrongCode==10){rightCode=17}
  else if (wrongCode==11){rightCode=10}
  else if (wrongCode==12){rightCode=11}
  else if (wrongCode==13){rightCode=4}
  else if (wrongCode==14){rightCode=5}
  else if (wrongCode==15){rightCode=16}
  else if (wrongCode==16){rightCode=14}
  else if (wrongCode==17){rightCode=3}
  else if (wrongCode==18){rightCode=15}
  
  return (rightCode)
}

getCorrectProvinceCode <- function(province){
  provincesNames <- unique(spainReduced.df$NAME_2)
  correctedCodes = c(4,44,50,39,
                     2,13,16,19,
                     45,5,11,9,
                     24,34,37,40,
                     42,47,49,8,
                     17,14,25,43,
                     51,52,28,31,
                     3,12,46,6,
                     18,10,15,27,
                     32,36,7,35,
                     38,26,21,1,
                     20,48,33,30,
                     23,29,41,22)
  index <- which (provincesNames == province)
  return (correctedCodes[index])
}