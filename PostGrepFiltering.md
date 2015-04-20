# Why filtering a grep result? #

From Grep4j 1.6.2 you can filter the result/s of your greps.

This feature can be useful if you want to extract different kind of information from the same grep result/s without paying the price of another remote connection.


## Example ##

Let say you have to grep a remote log, in order to know:

  1. How many "update operations" were sent to a server.

  1. How many updates for each customer-id arrived in the server.

  1. What kind of update for each user.

  1. How many updates were sent at 10am.

The log looks like this:

```
...
2012-05-30 08:48:52 INFO [org.consumer.AnMDB] (main) User Marco customer-id:12345  operation=update received
2012-05-30 08:48:53 INFO [org.consumer.UserDAO] (main) User Marco customer-id:12345 stored successfully
...
2012-05-30 10:10:11 INFO [org.consumer.AnMDB] (main) User Andrea customer-id:12346  operation=update received
2012-05-30 10:10:12 INFO [org.consumer.UserDAO] (main) User Andrea customer-id:12346 stored successfully
...
and so on... thousands of other updates.
```


now, with one single grep you can collect all the information you need:

```
import static org.grep4j.core.Grep4j.grep;
import static org.grep4j.core.Grep4j.constantExpression;
import static org.grep4j.core.Grep4j.regularExpression;
import static org.grep4j.core.fluent.Dictionary.on;
...

//Obtaining all the sensitive information in the log
GrepResults results = grep(constantExpression("customer-id"), on(profile)));

//this will return the total number of updates 
System.out.println("Total occurrences found : " + results.filterBy(regularExpression("operation(.*)update")).totalLines());

//this will return the total number of updates for the customer-id 12345
System.out.println("Total occurrences found : " + results.filterBy(constantExpression("customer-id:12345")).filterBy(regularExpression("operation(.*)update")).totalLines());

//this will return the number of updates between 10am and 11am
System.out.println("Total occurrences found : " + results.filterBy(constantExpression("2012-05-30 10")).totalLines());

//This will filter the result in order to print only Andrea updates
for (GrepResult singleResult : results.filterBy(constantExpression("Andrea"))) {
   System.out.println(singleResult.totalLines());        
   System.out.println(singleResult);    
}

//This will filter only the Andrea result in order to print only when we stored Andrea changes successfully
for (GrepResult singleResult : results.filterBy(constantExpression("Andrea")).filterBy(constantExpression("stored successfully"))) {  
   System.out.println(singleResult.totalLines());        
   System.out.println(singleResult);    
}

```

You can filter the `GrepResults` as well as the single `GrepResult` and you can filter using regular expression using the `.filterBy(regularExpression("regular expression"))` method.