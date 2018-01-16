rimi-cli-lite
===========================
This is a light weight java command line console.

# Quick Start

Run application by Java.

	java -jar rimi-cli-lite.jar
	
Type 'help' to see the list of all available commands.

```	Bash
	console > help
	
	Available commands : 
	
	exec                 Run a system command or shell script
	exit                 Exit the shell.
	log                  The command of tracing application loggings

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

	

