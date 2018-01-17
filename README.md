RIMI-CLI-LITE
===========================
**RIMI-CLI-LITE** is a light weight pure java command line console.
It provides a very simple command prompt interface to use.
It is easy to create your own commands and use them.

*   The JRE flavor requires JDK 1.8 or higher.

## Quick Start

Run application by Java.

```	Bash
$ java -jar rimi-cli-lite.jar

------------------------------------------------------------
-  WELCOME
------------------------------------------------------------
console > 
```
	
Type 'help' to see the list of all available commands.

```	Bash
console > help

Available commands : 

exec                 Run a system command or shell script
exit                 Exit the shell.
log                  The command of tracing application logging

console >
```
	
Type 'help command' to see more details of specified command.
	
```	Bash
console > help log
	
Usage : log [command] [options]
	
Type 'log' to see this list.

log set                             Configure the general settings of log commands
log snap  name [description]        Create or replace a named snapshot of current log status
log diff  name [name]               Export the difference between two named snapshots

console > 	
```

## Develop Guideline

### Custom ComponentScan

The console application scans the package **rimi.ritsai.cli** default. 
You can also scan your own package by creating a configuration class under that package.

```Java
@Configuration
@ComponentScan(basePackages = { "example.mypackage" })
public class MyConfig {
	
}
```
### Create your own command.

To create your own command, you just need to implement the interface **ConsoleCommand** and mark it with **@Command** annotation.

```	Java
@Command(name = "log diff", group = "log", usage = "log diff stageA [stageB]", description = "Compare the two stages and export the difference")
public class LogDiff implements ConsoleCommand {
	@Override
	public void run(Console console, String... arguments) throws IllegalArgumentException, SystemException {
		// put your codes here
	}
}
```

### Provide help information for your command.

The console loads your command document by this pattern **META-INF/shell/docs/{CommandName}.txt**.
The **CommandName** is the value of defined in the  **@Command** annotation.

e.g.
```Bash
META-INF/shell/docs/help.txt
```

So you just putting a UTF8 text document for your command under the specified folder is enough.

If you want to provide a version for your locale language. You need to create a **{CommandName}_{Locale}.txt** file.

e.g.
```Bash
META-INF/shell/docs/help_zh_CN.txt
```


 
