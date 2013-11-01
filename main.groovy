def searchWiki=new File('wiki/ListOfSearchCommands.txt').getText()
def m = searchWiki =~ /<code>\[\[Documentation:Splunk:SearchReference:(.+)\]\]/
//println m
//println m.size()
//m.each{println it}
//System.exit 0
def commands=[:]
m.each{
	def str=it[1] //e.g.Abstract|abstract]
	//if(str.indexOf("associate")>0) println "str=$str"
	def index=str.indexOf('|')
	def end=str.length()
	if(str.indexOf(']')>0)
		end=str.indexOf(']')
	commands.put(str.substring(index+1,end),str.substring(0,index))
}
//commands.each{k,v->println "$k: $v"}
def urlPrefix='http://docs.splunk.com/Documentation/Splunk/latest/SearchReference/'
//INSERT OR IGNORE INTO searchIndex(name, type, path) VALUES ('abstract', 'Command', 'Abstract.html');
def sql=[]
commands.each{name,file->
	sql<<"INSERT OR IGNORE INTO searchIndex(name, type, path) VALUES ('${name}', 'Command', '${file}.html');"
	def html=new URL("${urlPrefix}${file}").getText()
	def f=new File("spl.docset/Contents/Resources/Documents/${file}.html")
	f.createNewFile()
	f.setText(html)
}
sql.each{println it}