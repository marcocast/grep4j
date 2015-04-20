# Grep Profiles with wildcard #

Usually, you associate one `Profile` with one specific file, but it's also possible to associate multiple files to the same `profile` using wildcard in the file path.

When grepping on a Profile with a wildcard, Grep4j will run an LS command first. It will then run separate greps and finally return a GrepResult for each file found in the LS.

## Example ##

```
import static org.grep4j.core.Grep4j.grep;
import static org.grep4j.core.Grep4j.constantExpression;
import static org.grep4j.core.fluent.Dictionary.on;
import static org.grep4j.core.fluent.Dictionary.with;
import static org.grep4j.core.fluent.Dictionary.option;
import static org.grep4j.core.options.Option.filesMatching;

//creating a profile with wildcard *
Profile remoteProfile = ProfileBuilder.newBuilder()
            .name("Remote server log")
            .filePath("/opt/log/server.log*")
            .onRemotehost("172.xx.xx.xx")
            .credentials("user", "password")
            .build();


System.out.println(grep(constantExpression("ERROR"),on(remoteProfile),with(option(filesMatching()))));

```

The output of this will be something like:
```
/opt/jboss/server/default/log/server.log.2012-07-01
/opt/jboss/server/default/log/server.log.2012-07-02
/opt/jboss/server/default/log/server.log.2012-07-03
/opt/jboss/server/default/log/server.log.2012-07-04
/opt/jboss/server/default/log/server.log.2012-07-05
/opt/jboss/server/default/log/server.log.2012-07-06
/opt/jboss/server/default/log/server.log.2012-07-10
/opt/jboss/server/default/log/server.log
```

All will be done in separated threads.