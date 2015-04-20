# Introduction #

Grep Java Exception with the full stack trace.

# Details #

The following grep is to retrieve the full stack trace of an Exception in java.

```
import static org.grep4j.core.Grep4j.grep;
import static org.grep4j.core.Grep4j.regularExpression;
import static org.grep4j.core.fluent.Dictionary.on;
...

System.out.println(grep(regularExpression("WARN\\|ERROR\\|FATAL\\|Exception\\|at.*\\.java\\:.*"), on(profile)));
```