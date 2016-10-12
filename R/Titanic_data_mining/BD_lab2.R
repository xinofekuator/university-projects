#Markdown format (pass)
titanic <- read.csv("C:\\Users\\Ignacio\\Documents\\MIS DOCUMENTOS\\DATA SCIENCE MASTER (EIT DIGITAL)\\Big Data\\lab2-classification\\titanic\\titanic.csv", na.strings = "")

titanic$Survived=as.factor(titanic$Survived)
titanic$Pclass=as.factor(titanic$Pclass)
titanic$SibSp=as.factor(titanic$SibSp)
titanic$Parch=as.factor(titanic$Parch)

titanic_s1=subset.data.frame(titanic,select=c(Survived,Pclass,Sex,Age,SibSp,Parch,Fare,Embarked))

#177 NA in age and 2 NA in Embarked
titanic_cleaned=na.omit(titanic_s1,titanic_s1$Age,titanic_s1$Embarked)

library(caret)
set.seed(100)
partition=createDataPartition(titanic_cleaned$Survived,p = 0.8,list=FALSE)
train_set=titanic_cleaned[partition,]
test_set=titanic_cleaned[-partition,]
library(rpart)
random_tree=rpart(train_set)
library(rpart.plot)
prp(random_tree,extra=2)

testPred <- predict(random_tree, test_set,type="class")
confusionMatrix(testPred,test_set$Survived)
