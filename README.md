# What's this
This is a docset for Splunk Search Reference. Hopefully this will be helpful for Mac users who also use dash and Alfred.

Last update: May 2017, with latest doc for Splunk 6.6.0
# What's dash
[Dash](http://kapeli.com/dash) is a great tool to check any documentations: javadoc, Objective C docset, nodejs and others.

If you purchase the powpack of Alfred, you can use keyboard to quickly search any document. With this SPL docset, you can quickly figure out which splunk commands you need, even you cannot fully spell it

# For most of you, use this out of box!
After you install Dash, simply clone or download this project, and open the Dash preference, add the spl.docset. Then you can use spl:cmdName to search any command.

![screenshot](http://jzhong.s3.amazonaws.com/splunk_java_sdk_docset.png)

# For developers, you can update the docset by yourself
The main.groovy is developed with JVM language Groovy.So you need instal JVM and Groovy if you want to 
change code or update the docset by yourself.

For Mac OS X user, simply run brew install groovy to get 2.x groovy support.

Sorry, many of the following steps are possibily able to be automated. Next time~

## Step 0: Clean up out-of-date HTML files
* Delete all html files under spl.docset/Contents/Resources/Documents

## Step 1: Update ListOfSearchCommands
* Go http://docs.splunk.com/Documentation/Splunk/6.6.0/SearchReference/ListOfSearchCommands
* Save the HTML source as spl.docset/Contents/Resources/Documents/main.html
* Make sure you've logged in, then click the Edit link on the left hand. Copy the wiki markup to wiki/ListOfSearchCommands.txt

## Step 2: Update CommonEvalFunctions
* Go http://docs.splunk.com/Documentation/Splunk/latest/SearchReference/CommonEvalFunctions 
* Save the HTML source as spl.docset/Contents/Resources/Documents/1_CommonEvalFunctions.html
* Click the Edit link on the left hand. Copy the wiki markup to wiki/CommonEvalFunctions.txt

## Step 3: Update CommonStatsFunctions
* Go http://docs.splunk.com/Documentation/Splunk/latest/SearchReference/CommonStatsFunctions 
* Save the HTML source as spl.docset/Contents/Resources/Documents/2_CommonStatsFunctions.html
* Click the Edit link on the left hand. Copy the wiki markup to wiki/CommonStatsFunctions.txt

## Step 4: Prepare the SQLite database
* Open the SQLite database file(spl.docset/Contents/Resources/docSet.dsidx) with a SQLite client(or any JDBC DB tool, such as [DbVisualizer](http://www.dbvis.com)
* Empty the table via ``` DELETE from searchIndex ```

## Step 5: Grab the HTML docs and generate SQL
* Run ```groovy main``` with a number for the doc generation step, i.e.
	* ```groovy main 1```
	* ```groovy main 2```
	* ```groovy main 3```

Got the SQL command, run those INSERT command to add records to SQLite.