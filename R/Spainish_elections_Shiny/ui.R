source('external/aux_ui.R')

shinyUI(#fluidPage(
  #fluidRow(align="center",titlePanel("Spanish elections tool", windowTitle = "Spanish elections")),
  #component1(),
  navbarPage("Election tool",
              tabPanel("Results",component1()),
              tabPanel("Demographics",component2())
              )
)
