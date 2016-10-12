#Files generated using loadMap.R

load("spainNotFiltered.Rda")
load("spainFiltered.Rda")

spain2.df["colors"] <- as.character(spain2.df$ID_2)
spainReduced.df["colors"] <- as.character(spainReduced.df$ID_2)

# islands <-  spain2.df[spain2.df$CCA_2  %in% c("35", "38"),]
# cities <- spain2.df[spain2.df$CCA_2  %in% c("51", "52"),]
# ceuta <- spain2.df[spain2.df$CCA_2  %in% c("51"),]
# ceutaFiltered <-
#   ceuta[which(ceuta$long < (-5.25) & ceuta$long > (-5.38)),]
# melilla <- spain2.df[spain2.df$CCA_2  %in% c("52"),]
# melillaFiltered <-
# melilla[which(melilla$long > -3.2 & melilla$long < -2.8),]
# peninsula <-
#   spainReduced.df[!(spainReduced.df$CCA_2  %in% c("35","38","51", "52")),]
# peninsulaFiltered <- peninsula[which(peninsula$lat > 35),]

colorsPalette <- c("red","blue", "green3","darkgoldenrod2", "deeppink",
            "yellow","lightgoldenrod2","maroon4","lightskyblue4","yellow3"
            ,"darkseagreen3","darkslategray1","seagreen1",
            "lightblue2","grey70","firebrick3","darkorchid4","red4","dodgerblue2",
            "slateblue4","orangered1","turquoise1","greenyellow","red1","olivedrab3",
            "limegreen","navy","orchid","olivedrab","goldenrod4","chartreuse3",
            "springgreen","orange3","deeppink3","darkorange1")
partiesPalette<- c("PSOE","PP","IU","CiU","EAJ-PNV","CC","HB","ERC","PAR",
            "EA-EUE","UV","CDS","BNG","PA","Others","PSC-PSOE","IC","PSE-EE/PSOE",
            "UPN-PP","EA","ChA","PP-PAR","IC-EV","PSOE-prog","IU-EUiA","IC-V",
            "PP-UPM","Na-Bai","ICV-EUiA","PIB","PSM-EN-EU-EV-ERC","IUN-NEB","CC-PNC",
            "UPyD","CA")

#colortable? to map them??

names(colorsPalette)<-partiesPalette

getPlotMap = function(dataMap,title) {
  resultPlot <- ggplot() +
    geom_polygon(
      data = dataMap, aes(
        x = long, y = lat, group = group,
        fill = factor(colors)
      ),
      color = alpha("black", 1 / 2), show_guide = TRUE
    ) +
    theme(axis.line=element_blank(),axis.text.x=element_blank(),
          axis.text.y=element_blank(),axis.ticks=element_blank(),
          axis.title.x=element_blank(),
          axis.title.y=element_blank(),#legend.position="bottom",
          panel.background=element_blank(),panel.grid.major=element_blank(),
          panel.grid.minor=element_blank(),plot.background=element_blank()) +
    labs(x = title) +
    coord_map("polyconic") +
    scale_fill_manual(values=colorsPalette,guide = guide_legend(reverse = TRUE),
                      name = "Parties",
                      breaks = levels(factor(dataMap$colors)))
    #scale_fill_manual(values = sample(colours(),129))
  
  return (resultPlot)
}

getBorderPlotMap = function(dataMap,title) {
  resultPlot <- ggplot() +
    geom_polygon(
      data = dataMap, aes(
        x = long, y = lat, group = group,
        fill = factor(colors)
      ),
      color = alpha("black", 1 / 2), show_guide = FALSE
    ) +
    theme(axis.line=element_blank(),axis.text.x=element_blank(),
          axis.text.y=element_blank(),axis.ticks=element_blank(),
          #axis.title.x=element_blank(),
          axis.title.y=element_blank(),legend.position="none",
          panel.background=element_blank(),panel.grid.major=element_blank(),
          panel.grid.minor=element_blank(),plot.background=element_blank(),
          panel.border = element_rect(colour = "black", fill=NA, size=NULL)) +
    labs(x = title) +
    coord_map("polyconic") +
    # scale_fill_manual(values = sample(colours(),129))   
    scale_fill_manual(values=colorsPalette,guide = guide_legend(reverse = TRUE),
                      name = "Parties",
                      breaks = levels(factor(dataMap$colors)))
  return (resultPlot)
}

# spainPlot <- getPlotMap (peninsulaFiltered,"")
# canaryPlot <- getBorderPlotMap (islands,"Canary islands")
# ceutaPlot <- getBorderPlotMap (ceutaFiltered,"Ceuta")
# melillaPlot <- getBorderPlotMap (melillaFiltered,"Melilla")