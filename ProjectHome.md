# What is Grep4j? #

Grep4j is a simple API made to centralise and facilitate the search of expressions within  remote or local files, **in Unix environments**.
For Windows support click [here](http://code.google.com/p/grep4j/wiki/WindowSupport)

# Maven #
```
<dependency>
    <groupId>com.googlecode.grep4j</groupId>
    <artifactId>grep4j</artifactId>
    <version>1.8.7</version>
</dependency>
```

# Usage #

## Profile ##
In grep4j a profile is the grep target context.
The profile contains information such as the name of the file, the path of the file to grep and the host and the credentials to connect to either local or remote machine.
```

Profile remoteProfile = ProfileBuilder.newBuilder()
                                     .name("Remote server log")
                                     .filePath("/opt/log/server.log")
                                     .onRemotehost("172.xx.xx.xx")
                                     .credentials("user", "password")
                                     .build();

Profile localProfile = ProfileBuilder.newBuilder()
                                     .name("Local server log")
		                     .filePath("/opt/log/server.log")
		                     .onLocalhost()
		                     .build();

Profile remoteProfileWithPublicKey = ProfileBuilder.newBuilder()
		                     .name("Another remote server log")
		                     .filePath("/path/to/file/filename.txt")
		                     .onRemotehost("172.x.x.x")
		                     .userAuthPrivateKeyLocation("/home/user/.ssh/id_dsa")
		                     .withUser("user")
		                     .build();

```



## Using Grep4j to grep local or remote files ##

You can use Grep4j to obtain grep information across multiple local/remote files in an easy and fluent way:
```
import static org.grep4j.core.Grep4j.grep;
import static org.grep4j.core.Grep4j.constantExpression;
import static org.grep4j.core.fluent.Dictionary.on;
...

//Obtaining the global result
GrepResults results = grep(constantExpression("Expression_to_grep"), on(remoteProfile,localProfile));
System.out.println("Grep results : " + results);
System.out.println("Total lines found : " + results.totalLines());
System.out.println("Total Execution Time : " + results.getExecutionTime());


//processing the single grep result for each profile
for (GrepResult singleResult : results) {	
   System.out.println(singleResult.getProfileName());	
   System.out.println(singleResult.getFileName());	
   System.out.println(singleResult.getExecutionTime());	
   System.out.println(singleResult);
}
```

## Using Grep4j in your tests ##

You can use Grep4j to make your acceptance/integration tests more **fluent** and **easy**.

Let's say you need to test that after starting your network of servers, no errors or exceptions will be found in the log:

```
import static org.grep4j.core.Grep4j.grep;
import static org.grep4j.core.Grep4j.constantExpression;
import static org.grep4j.core.fluent.Dictionary.on;
import static org.grep4j.core.fluent.Dictionary.executing;
...

assertThat(executing(grep(constantExpression("ERROR"), on(profile1,profile2))).totalLines(), is(0));
assertThat(executing(grep(constantExpression("Exception"), on(profile1,profile2))).totalLines(), is(0));

```

Here you want to check that a particular init method was triggered:

```
import static org.grep4j.core.Grep4j.grep;
import static org.grep4j.core.Grep4j.constantExpression;
import static org.grep4j.core.fluent.Dictionary.on;
import static org.grep4j.core.fluent.Dictionary.executing;
...

assertThat(executing(grep(constantExpression("Init resources ended succesfully"), on(aProfile))).totalLines(), is(1));
```

Or you want to check that the flow of data is passing correctly through a group of specified servers:

```
import static org.grep4j.core.Grep4j.grep;
import static org.grep4j.core.Grep4j.constantExpression;
import static org.grep4j.core.fluent.Dictionary.on;
import static org.grep4j.core.fluent.Dictionary.executing;
...

assertThat(executing(grep(constantExpression("Message 1234 received"), on(profiles))).totalLines(), is(1));
```

# Testing distributed applications #

It's often the case that an application is distributed across different remote machines. This to promote scalability, performance, etc.
The problem with distributed technology is that it's hard to cover the application with end to end acceptance and integration tests.
Example:

### Context ###

  * The GUI runs in the guiServer(172.1.1.1);
  * The distribution system runs on distributionServer(172.1.1.2);
  * Consumer 1 runs on consumer1Server(172.1.1.3)
  * Consumer 2 runs on consumer2Server(172.1.1.4)
  * Consumer 3 runs on consumer3Server(172.1.1.5)

### Logs ###
  1. When a User is created in the **GUI**:
  * 2012-05-30 08:48:49,885 INFO  [org.app.Sender] (main) User Marco id:12345 sent to distribution server
  1. When the **distributionServer** receives the message:
  * 2012-05-30 08:48:50 INFO  [org.dist.AnMDB] (main) User Marco id:12345 received
  * 2012-05-30 08:48:51 INFO  [org.dist.Distributor] (main) User Marco id:12345 sent to Consumer one
  * 2012-05-30 08:48:51 INFO  [org.dist.Distributor] (main) User Marco id:12345 sent to Consumer two
  * 2012-05-30 08:48:51 INFO  [org.dist.Distributor] (main) User Marco id:12345 sent to Consumer three
  1. When each **consumer** receives the message:
  * 2012-05-30 08:48:52 INFO  [org.consumer.AnMDB] (main) User Marco id:12345 received
  * 2012-05-30 08:48:53 INFO  [org.consumer.UserDAO] (main) User Marco id:12345 stored successfully


### Problem ###
You want to test that the creation of the User with id 12345 **goes correctly through all the steps** in the system reaching all the consumers.

### Solution with Grep4j ###
```
import static org.grep4j.core.Grep4j.grep;
import static org.grep4j.core.Grep4j.constantExpression;
import static org.grep4j.core.fluent.Dictionary.on;
import static org.grep4j.core.fluent.Dictionary.executing;
...

Profile guiProfile = create the Profile for your guiServer
Profile distributionProfile = create the Profile for your distributionServer
List<Profile> consumersProfiles = create the Profiles for your 3 consumers

//Assert that the GUI has sent the create message to the distribution server:
assertThat(executing(grep(constantExpression("12345 sent to distribution server"), on(guiProfile))).totalLines(), is(1));

//Assert that the distribution application received the message:
assertThat(executing(grep(constantExpression("User Marco id:12345 received"), on(distributionProfile))).totalLines(), is(1));

//Assert that the distribution application distributed the message to all the 3 consumers:
assertThat(executing(grep(constantExpression("User Marco id:12345 sent to Consumer"), on(distributionProfile))).totalLines(), is(3));

//Assert that all the 3 consumers received the message:
assertThat(executing(grep(constantExpression("User Marco id:12345 received"), on(consumersProfiles))).totalLines(), is(3));

//Assert that all the 3 consumers save the user correctly:
assertThat(executing(grep(constantExpression("User Marco id:12345 stored sucessfully"), on(consumersProfiles))).totalLines(), is(3));

```

## Regex support ##

In the case you need a more dynamic way to search in a file, Grep4j supports regex:
```
import static org.grep4j.core.Grep4j.grep;
import static org.grep4j.core.Grep4j.regularExpression;
import static org.grep4j.core.fluent.Dictionary.on;
import static org.grep4j.core.fluent.Dictionary.executing;
...

assertThat(executing(grep(regularExpression("Marco(.*)stored"), on(profile))).totalLines(), is(1));
```

## Grep with lines after, before or both ##

If you need to include more lines in your grep result:
```
import static org.grep4j.core.Grep4j.grep;
import static org.grep4j.core.Grep4j.constantExpression;
import static org.grep4j.core.options.Option.extraLinesAfter;
import static org.grep4j.core.options.Option.extraLinesBefore;
import static org.grep4j.core.fluent.Dictionary.on;
import static org.grep4j.core.fluent.Dictionary.with;
import static org.grep4j.core.fluent.Dictionary.option;
import static org.grep4j.core.fluent.Dictionary.options;
...

GrepResults results = grep(constantExpression("string-to-search"), on(profile), with(option(extraLinesAfter(100))));
System.out.println("Total lines found : " + results.totalLines());
System.out.println("Total lines found : " + results.filterBy("another expression within 100 lines after").totalLines());

GrepResults results = grep(constantExpression("string-to-search"), on(profile), with(option(extraLinesBefore(100))));
System.out.println("Total lines found : " + results.totalLines());
System.out.println("Total lines found : " + results.filterBy("another expression within 100 lines before").totalLines());

GrepResults results = grep(constantExpression("string-to-search"), on(profile), with(options(extraLinesBefore(100), extraLinesAfter(100))));
System.out.println("Total lines found : " + results.totalLines());
System.out.println("Total lines found : " + results.filterBy("another expression within 100 lines before and 100 after").totalLines());
```

See all the Grep options available in the [Grep4j Options page](http://code.google.com/p/grep4j/wiki/GrepOptions)

# Fluent mirror methods #
In Grep4j we are focused on the readability of the code.
Because of this we introduced a fluent Dictionary class(org.grep4j.core.fluent.Dictionary) which contains only **fluent mirror methods** to be used in order to improve the readability of your code.
A fluent mirror method is a simple static method returning the same object passed. For example:
```
public static <T> T executing(T type) {
		return type;
	}
```
and you can use this method as follows:
```
import static org.grep4j.core.Grep4j.grep; 
import static org.grep4j.core.Grep4j.constantExpression;
import static org.grep4j.core.fluent.Dictionary.on;
import static org.grep4j.core.fluent.Dictionary.executing;
import static org.grep4j.core.fluent.Dictionary.with;
...

assertThat(executing(grep(with(constantExpression("Marco")), on(profile))).totalLines(), is(1));

```