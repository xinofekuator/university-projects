path = "data/"

Drug <- read.csv(paste(path,"Drug.csv",sep = ""), header=FALSE)
Prisonners <- read.csv(paste(path,"prisonners.csv",sep = ""), header=FALSE)
Educated <- read.csv(paste(path,"Education.csv",sep = ""), header=FALSE)
NoEducated <- read.csv(paste(path,"No_Education.csv",sep = ""), header=FALSE)
Pension <- read.csv(paste(path,"constitutives_pension.csv",sep = ""), header=FALSE)
Alcohool <- read.csv(paste(path,"alcohool_dependencie.csv",sep = ""), header=FALSE)



#Rename the column names and transform to the right class
#lapply(Prisonners,class)
names(Drug)<-c("codeCommunity","codeProvince","communityName","1991","1992",	"1993",	"1994",	"1995",	"1996",	"1997",	"1998",	"1999",	"2000",	"2001",	"2002",	"2003",	"2004",	"2005",	"2006",	"2007",	"2008", "2009")
names(Prisonners)<-c("codeCommunity","codeProvince","communityName","1993",	"1994",	"1995",	"1996",	"1997",	"1998",	"1999",	"2000",	"2001",	"2002",	"2003",	"2004",	"2005",	"2006",	"2007",	"2008", "2009")
names(Educated)<-c("codeCommunity","codeProvince","communityName","1991","1992",	"1993",	"1994",	"1995",	"1996",	"1997",	"1998",	"1999",	"2000",	"2001",	"2002",	"2003",	"2004",	"2005",	"2006",	"2007",	"2008", "2009")
names(NoEducated)<-c("codeCommunity","codeProvince","communityName","1991","1992",	"1993",	"1994",	"1995",	"1996",	"1997",	"1998",	"1999",	"2000",	"2001",	"2002",	"2003",	"2004",	"2005",	"2006",	"2007",	"2008", "2009")
names(Pension)<-c("codeCommunity","codeProvince","communityName","1991","1992",	"1993",	"1994",	"1995",	"1996",	"1997",	"1998",	"1999",	"2000",	"2001",	"2002",	"2003",	"2004",	"2005",	"2006",	"2007",	"2008", "2009")
names(Alcohool)<-c("codeCommunity","codeProvince","communityName","1991","1992",	"1993",	"1994",	"1995",	"1996",	"1997",	"1998",	"1999",	"2000",	"2001",	"2002",	"2003",	"2004",	"2005",	"2006",	"2007",	"2008", "2009")

Drug$codeCommunity <- as.factor(Drug$codeCommunity)
Drug$codeProvince <- as.factor(Drug$codeProvince)
Drug$communityName <- as.factor(Drug$communityName)

Prisonners$codeCommunity <- as.factor(Prisonners$codeCommunity)
Prisonners$codeProvince <- as.factor(Prisonners$codeProvince)
Prisonners$communityName <- as.factor(Prisonners$communityName)

Educated$codeCommunity <- as.factor(Educated$codeCommunity)
Educated$codeProvince <- as.factor(Educated$codeProvince)
Educated$communityName <- as.factor(Educated$communityName)

NoEducated$codeCommunity <- as.factor(NoEducated$codeCommunity)
NoEducated$codeProvince <- as.factor(NoEducated$codeProvince)
NoEducated$communityName <- as.factor(NoEducated$communityName)

Pension$codeCommunity <- as.factor(Pension$codeCommunity)
Pension$codeProvince <- as.factor(Pension$codeProvince)
Pension$communityName <- as.factor(Pension$communityName)

Alcohool$codeCommunity <- as.factor(Alcohool$codeCommunity)
Alcohool$codeProvince <- as.factor(Alcohool$codeProvince)
Alcohool$communityName <- as.factor(Alcohool$communityName)