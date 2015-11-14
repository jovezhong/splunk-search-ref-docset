# What's this
This is a docset for Splunk Search Reference. Hopefully this will be helpful for Mac users who also use dash and Alfred.

Last update: Nov 2015, with latest doc for Splunk 6.3.1
# What's dash
Dash (http://kapeli.com/dash) is a great tool to check any documentations: javadoc, Objective C docset, nodejs and others.

If you purchase the powpack of Alfred, you can use keyboard to quickly search any document. With this SPL docset, you can quickly figure out which splunk commands you need, even you cannot fully spell it
# How to use
After you install Dash, simply clone or download this project, and open the Dash preference, add the spl.docset. Then you can use spl:cmdName to search any command.

![screenshot](http://jzhong.s3.amazonaws.com/splunk_java_sdk_docset.png)

# How the code works and how you can update the docset
The main.groovy is developed with JVM language Groovy.So you need instal JVM and Groovy if you want to 
change code or update the docset by yourself.

For Mac OS X user, simply run brew install groovy to get 2.x groovy support.

Sorry, many of the following steps are possibily able to be automated. Next time~

## Update the 3 txt files under wiki folder
Those 3 files will list the key splunk commands. You can go splunk website to get the latest content by trying to edit the wiki
* http://docs.splunk.com/Documentation/Splunk/latest/SearchReference/CommonEvalFunctions
* http://docs.splunk.com/Documentation/Splunk/latest/SearchReference/CommonStatsFunctions
* http://docs.splunk.com/Documentation/Splunk/latest/SearchReference/ListOfSearchCommands

## Save the 2 HTML pages
Save the HTML source of http://docs.splunk.com/Documentation/Splunk/latest/SearchReference/CommonEvalFunctions to 1_CommonEvalFunctions.html

Save the HTML source of http://docs.splunk.com/Documentation/Splunk/latest/SearchReference/CommonStatsFunctions to 2_CommonStatsFunctions.html

## Prepare the docset
Read https://kapeli.com/docsets#dashDocset to know what're the key steps.
You don't need change Info.plist, just need update the HTML files and change the SQLite database.
* Remove all html files under spl.docset/Resources/Documents
* Open the SQLite database with a SQLite client(or any JDBC DB tool, such as DbVisualizer), then clean all rows by 
	* DELETE from searchIndex

then run 'groovy main' with a number for the doc generation step, i.e.


groovy main 1

groovy main 2

groovy main 3

Got the SQL command, run those INSERT command to add records to SQLite.