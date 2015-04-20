# Supported Grep Options #

From Grep4j 1.6.2 you can grep using the most common grep options.
Here is the list of options available:

  * `extraLinesAfter(int number-of-lines)`
-A, --after-context=NUM   print NUM lines of trailing context
  * `extraLinesBefore(int number-of-lines)`
-B, --before-context=NUM  print NUM lines of leading context
  * `extraLinesBeforeAndAfter(int number-of-lines)`
-C, --context=NUM         print NUM lines of output context
  * `onlyMatching() `
-o, --only-matching       show only the part of a line matching PATTERN
  * `ignoreCase()`
-i, --ignore-case         ignore case distinctions
  * `invertMatch()`
-v, --invert-match        select non-matching lines
  * `countMatches()`
-c, --count               print only a count of matching lines per FILE
  * `withFileName()`
-H, --with-filename       print the filename for each match
  * `maxMatches(int number-of-matches)`
-m, --max-count=NUM       stop after NUM matches
  * `lineNumber()`
-n, --line-number         print line number with output lines
  * `filesNotMatching()`
-L, --files-without-match  print only names of FILEs containing no match
  * `filesMatching()`
-l, --files-with-matches  print only names of FILEs containing matches
  * `grepVersion()`
--version, the version of grep
  * `maxSshConnections(int number-of-connections)`
set maximum ssh connections
  * `onlyLastLines(int number-of-lines)`
-n, --lines=N            output the last N lines
  * `onlyLastBytes(int number-of-bytes)`
-c, --bytes=N            output the last N bytes
  * `onlyFirstLines(int number-of-lines)`
-n, --lines=N            output the first N lines
  * `onlyFirstBytes(int number-of-bytes)`
-c, --bytes=N            output the first N bytes
  * `recursive()`
-R, -r, --recursive   equivalent to --directories=recurse
  * `onlyFilesWhenRecursing(String pattern)`
-include=FILE\_PATTERN  search only files that match FILE\_PATTERN
  * `excludeFilesWhenRecursing(String pattern)`
--exclude=FILE\_PATTERN  skip files and directories matching FILE\_PATTERN
  * `excludeDirectoriesWhenRecursing(String pattern)`
--exclude-dir=PATTERN  directories that match PATTERN will be skipped.

## Example ##

```
import static org.grep4j.core.Grep4j.grep;
import static org.grep4j.core.Grep4j.constantExpression;
import static org.grep4j.core.fluent.Dictionary.on;
import static org.grep4j.core.fluent.Dictionary.options;
import static org.grep4j.core.fluent.Dictionary.option;
import static org.grep4j.core.fluent.Dictionary.with;
import static org.grep4j.core.options.Option.ignoreCase;
import static org.grep4j.core.options.Option.extraLinesAfter;
import static org.grep4j.core.options.Option.onlyMatching;
import static org.grep4j.core.options.Option.excludeDirectoriesWhenRecursing;
import static org.grep4j.core.options.Option.excludeFilesWhenRecursing;
import static org.grep4j.core.options.Option.onlyFilesWhenRecursing;
import static org.grep4j.core.options.Option.recursive;

...
//Single option
GrepResults results = grep(constantExpression("Marco"), on(localProfile()),		     
                          with(option(extraLinesAfter(20))));

assertThat(results.totalLines(), is(1));



//Multiple options
GrepResults results = grep(constantExpression("mArCo"),        on(localProfile()),		     
                          with(
                               options(
                                       onlyMatching(),
                                       ignoreCase()
                                       )
                               )
                            );

assertThat(results.toString(), is("Marco"));

//Recursive options
Profile remoteJBossFolder = ProfileBuilder.newBuilder()
				.name("a remote folder")
				.filePath("/opt/ops/logs/jboss")
				.onRemotehost("172.xx.xx.xx")
				.credentials("user", "password")
				.build();

GrepResults results = grep(regularExpression(PATTERN), on(remoteJBossFolder),
				with(
					options(
						recursive(),
						onlyFilesWhenRecursing("server.log*"),
						excludeDirectoriesWhenRecursing("*-gui"),
						excludeFilesWhenRecursing("*.2013-03-0*")
						)
					)
				);

assertThat(results.totalLines(), is(10));

```