source('external/loadMapFiles.R',local=TRUE)
source('external/loadElectionResults.R',local=TRUE)
source('external/loadFeature.R',local=TRUE)

component1 <- function() {
  fluidRow(
    column(8,align="center",
           selectInput("electionYear", label = h5("Select election year"), 
                       choices = c("1993", "1996","2000", "2004", "2008"), 
                       selected = "1993"),
           h3(textOutput("provinceText")),
           plotOutput("mapSpain"),
           br(),
           dataTableOutput("summaryTable")),
    column(4,align="center",
           selectInput("province", label = h5("Select community"), 
                       choices = c("All",unique(spainReduced.df$NAME_1),"Ceuta","Melilla")[-8], 
                       selected = "All"),
           tabsetPanel(
             tabPanel("Results", plotOutput("pieChart")), 
             tabPanel("Features", h2(""),"Features in each province: pick a community",
                      plotOutput("barChart"),
                      checkboxGroupInput("checkGroup1", label = h4("Demographic features"), 
                                         choices = list("Drug dependencie" = "Drug", "Alcohool dependencie" = "Alcohool", "Prisonners"="Prisonners","No educated"="NoEducated","Educated"="Educated"),
                                         selected = "Drug"))
             )     
           ))
}

component2 <- function() {
  tabPanel("Component 2",
           sidebarLayout(
             position = 'right',
             sidebarPanel(
               
               radioButtons("radio", label =("Choose of the format of the output"),
                            choices = list("Percentage" = "p", "Value" = "v","Normalized"="n"), 
                            selected = "v"),
               
               
               selectInput("com", 
                           label = "Choose a place to display",
                           choices = as.vector(unique((subset(Drug,is.na(Drug$codeProvince)))$communityName))[-c(1,2)],
                           selected = "Huesca"),
               
               
               selectInput("var", 
                           label = "Choose a party to display",
                           #choices = as.vector(unique(electionData$info)),
                           choices = unique(electionData$partyName[!is.na(electionData$partyName)]),
                           selected = NULL),
               
               
               checkboxGroupInput("checkGroup", label = h3("Feature(s) to compare"), 
                                  choices = list("Drug dependencie" = "Drug", "Alcohool dependencie" = "Alcohool", "Constitutive pensions"="Pension", "Prisonners"="Prisonners","No educated"="NoEducated","Educated"="Educated"),
                                  selected = "Drug"),
               p("Attention : compare with features only available with normalize value")
               
             ),
             
             
             
             
             # Show a plot of the generated distribution
             mainPanel(plotOutput("Theplot"))
           ))
  
}