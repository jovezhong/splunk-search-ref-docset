void genSearchCommands(){
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
		//println "Handling $name : $file"
		sql<<"INSERT OR IGNORE INTO searchIndex(name, type, path) VALUES ('${name}', 'Command', '${file}.html#${name}');"
		def html=new URL("${urlPrefix}${file}").getText()
		def f=new File("spl.docset/Contents/Resources/Documents/${file}.html")
		f.createNewFile()
		f.setText(html)
	}
	sql.each{println it}
}
//genSearchCommands()
void genEvalFunctions(){
	def searchWiki=new File('wiki/CommonEvalFunctions.txt').getText()
	def m=searchWiki=~/\|-\n\| <code>(.+)<\/code>/
	//println m
	//println m.size()
	//m.each{println it}
	def functions=[]
	m.each{
		def str=it[1]//e.g. abs(X)
		functions << str.substring(0,str.indexOf('('))
	}
	def sql=[]
	functions.each{
		sql<<"INSERT OR IGNORE INTO searchIndex(name, type, path) VALUES ('${it}', 'Function', '1_CommonEvalFunctions.html');"
	}
	sql.each{println it}
}
//genEvalFunctions()
void genStatsFunctions(){
	def searchWiki=new File('wiki/CommonStatsFunctions.txt').getText()
	def m=searchWiki=~/\|-\n\|<code>(.+)<\/code>/
	//println m
	//println m.size()
	//m.each{println it}
	def functions=[]
	m.each{
		def str=it[1]//e.g. abs(X)
		functions << str.substring(0,str.indexOf('('))
	}
	def sql=[]
	functions.each{
		sql<<"INSERT OR IGNORE INTO searchIndex(name, type, path) VALUES ('${it}', 'Function', '2_CommonStatsFunctions.html');"
	}
	sql.each{println it}
}
genStatsFunctions()