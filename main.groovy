void genSearchCommands(){
	//download the content from http://docs.splunk.com/Documentation/Splunk/latest/SearchReference/ListOfSearchCommands
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
		def html=new URL("${urlPrefix}${file}").getText()//download the HTML code
		def f=new File("spl.docset/Contents/Resources/Documents/${file}.html")
		f.createNewFile()
		f.setText(html)
	}
	sql.each{println it}
}
void genEvalFunctions(){
	//source: http://docs.splunk.com/Documentation/Splunk/latest/SearchReference/CommonEvalFunctions
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
void genStatsFunctions(){
	//read the content of the stats function
	//source http://docs.splunk.com/Documentation/Splunk/latest/SearchReference/CommonStatsFunctions
	def searchWiki=new File('wiki/CommonStatsFunctions.txt').getText()
	def m=searchWiki=~/<code>(.+)<\/code>/  //|<code>avg(X)</code>
	//def m=searchWiki=~/\|- valign="top" \|\n |<code>(\S+)<\/code>/  //|<code>avg(X)</code>
	//println m
	//println m.size()
	//m.each{println it}
	def functions=[]
	m.each{
		def str=it[1]//e.g. abs(X)
		//println "checking Str: $str"
		if(str.contains("[[Documentation")) return
		if(!str.contains("X")) return
		if(!str.contains("(")) return
		//println "Str: $str"
		//need handle median(X), c(X) &#124; count(X), p<X>(Y) &#124; perc<X>(Y) &#124; exactperc<X>(Y) &#124; upperperc<X>(Y)
		functions << str.substring(0,str.indexOf('(')).trim()-"<X>"
		while(str.contains("&#124;")){
			str=str.substring(str.indexOf("&#124;")+6)
			functions << str.substring(0,str.indexOf('(')).trim()-"<X>"
		}
	}
	def sql=[]
	functions.each{
		sql<<"INSERT OR IGNORE INTO searchIndex(name, type, path) VALUES ('${it}', 'Method', '2_CommonStatsFunctions.html');"
	}
	sql.each{println it}
}
if(args.length!=1){
	println 'Help: require 1 paramter 1/2/3 for generating step 1/2/3'
}else{
	switch (Integer.parseInt(args[0])){
		case 1:genSearchCommands();break
		case 2:genEvalFunctions();break
		case 3:genStatsFunctions()
	}
}
