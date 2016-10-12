## Avoid scientific notation
options(scipen = 12)
## Load required packages
## scales needed for the alpha function
lib <- c("raster", "rgdal", "ggplot2", "scales")
sapply(lib, function(x) require(x, character.only = TRUE))

shinyServer(function(input,output){
  source('external/actions_component1.R',local=TRUE)
  source('external/actions_component2.R',local=TRUE)
  #source('external/actions_component3.R',local=TRUE)
  #source('external/actions_component4.R',local=TRUE)
  #source('external/actions_navpanel.R',local=TRUE)
})