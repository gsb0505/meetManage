/**
　　* write log at web client ,use javascript
　　*/
function Logger(FileName,logFile){
if(logFile!=null)
this.logFileName=logFile;
else
this.logFileName="c:\\cardlog.txt"; //default log file 
this.Prior=0;//0:ALL,1:DEBUG,2:INFO,3:WARN,4:ERROR,5:FATAL,6:OFF
this.FileName=FileName;
this.debug=writeDebug;
this.info=writeInfo;
this.warn=writeWarn;
this.error=writeError;
this.fatal=writeFatal;
this.writeLogFuc=writeLog;
this.isDebugEnabled =function(){return checkDebug(this.Prior,1);}
this.isInfoEnabled =function(){return checkDebug(this.Prior,2);} 
this.isWarnEnabled =function(){return checkDebug(this.Prior,3);} 
this.isErrorEnabled =function(){return checkDebug(this.Prior,4);}
this.isFatalEnabled =function(){return checkDebug(this.Prior,5);} 
} 
function checkDebug(DPri,pri){
if(pri>=DPri)
return true; 
else
return false;
}
function writeDebug(info,ex){
if(!this.isDebugEnabled())
return;
this.writeLogFuc("Debug",info,ex);
}
function writeInfo(info,ex){
if(!this.isDebugInfo())
return;
this.writeLogFuc("Info",info,ex);
}
function writeWarn(info,ex){
if(!this.isDebugWarn())
return;
this.writeLogFuc("Warn",info,ex);
}
function writeError(info,ex){
if(!this.isErrorEnabled())
return; 
this.writeLogFuc("Error",info,ex);
}
function writeFatal(info,ex){
if(!this.isFatalEnabled())
return; 
this.writeLogFuc("Fatal",info,ex);
}
function writeLog(prids,info,ex){
try{
if(this.fso==null)
this.fso=new ActiveXObject("Scripting.FileSystemObject");
}catch(ex2){
alert(ex2);
}
if(!this.fso.FileExists(this.logFileName)){
this.logFile=a;
var a = this.fso.CreateTextFile(this.logFileName);
a.Close(); 
}
var a = this.fso.OpenTextFile(this.logFileName,8);
var s="";
d = new Date(); 
s += d.getYear() +"-"; 
s += (d.getMonth() + 1) +"-"; 
s += d.getDate() + " "; 
var c = ":";
s += d.getHours() + c;
s += d.getMinutes() + c;
s += d.getSeconds() + c;
s += d.getMilliseconds();
a.WriteLine(s+" "+prids+" ("+this.FileName+") - ["+(info==null?"":info)+"]");
if(ex!=null)
a.WriteLine("- "+ex+"");
a.Close(); 
}
