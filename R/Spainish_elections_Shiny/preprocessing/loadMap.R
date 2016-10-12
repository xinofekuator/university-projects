spain2 <- readRDS("GADM_2.8_ESP_adm2.rds", refhook = NULL)
#spain2 <- getData("GADM", country = "ESP", level = 2)
#spain2 <- spTransform(spain2, CRS("+init=epsg:2135"))
## Extract polygon corners and merge with shapefile data
spain2@data$id <- rownames(spain2@data)
spain2.ff <- fortify(spain2)
spain2.df <- merge(spain2@data, spain2.ff, by = "id", all.y = TRUE)

spainReduced.df <- spain2.df
spainReduced.df$lat <-
  sapply(spain2.df$lat, function(r) {
    round(r,digits = 2)
  })
spainReduced.df$long <-
  sapply(spain2.df$long, function(r) {
    round(r,digits = 2)
  })

drops <- c("order")
spainReduced.df<-spainReduced.df[,!(names(spain2.df) %in% drops)]
spainReduced.df<-unique(spainReduced.df)

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

getPlotMap = function(dataMap,title) {
  resultPlot <- ggplot() +
    geom_polygon(
      data = dataMap, aes(
        x = long, y = lat, group = group,
        fill = NAME_2
      ),
      color = alpha("black", 1 / 2), show_guide = FALSE
    ) +
    theme(axis.line=element_blank(),axis.text.x=element_blank(),
              axis.text.y=element_blank(),axis.ticks=element_blank(),
              #axis.title.x=element_blank(),
              axis.title.y=element_blank(),legend.position="none",
              panel.background=element_blank(),panel.border=element_blank(),panel.grid.major=element_blank(),
              panel.grid.minor=element_blank(),plot.background=element_blank(),
              panel.border = element_rect(colour = "black", fill=NA, size=3)) +
    labs(x = title) +
    coord_map("polyconic") +
    scale_fill_manual(values = sample(colours(),129))
  
  return (resultPlot)
}

spainPlot <- getPlotMap (peninsulaFiltered,"")
canaryPlot <- getPlotMap (islands,"Canary islands")
ceutaPlot <- getPlotMap (ceutaFiltered,"Ceuta")
melillaPlot <- getPlotMap (melillaFiltered,"Melilla")
cityPlot <-  getPlotMap (cities,"")
