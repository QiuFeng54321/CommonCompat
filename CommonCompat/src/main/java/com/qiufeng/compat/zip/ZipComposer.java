package com.qiufeng.compat.zip;
import java.util.zip.*;
import java.io.*;
import com.qiufeng.compat.proxy.*;
import com.qiufeng.compat.*;

public class ZipComposer{
	public ZipOutputStream file;
	//DebugProxy proxy;
	public ZipComposer(OutputStream f){
		//proxy=new DebugProxy(this);
		file = new ZipOutputStream(f);
	}
	public void addFiles(File f) throws IOException{
		addFiles(f,"");
	}
	public void addFiles(File f, String base) throws IOException{  
		if(f.isDirectory()){  
            File[] fl = f.listFiles();  
			if(base!=null&&!base.equals(""))
            	addDirectory(base);
            base = base.length() == 0 ?"": (base.endsWith("/") ?base: base + "/");  
            for(int i = 0; i < fl.length; i++){  
                addFiles(fl[i], base + fl[i].getName());  
            }  
        }else{  
            addFile(base, new FileInputStream(f));
        }  
    }  
	public void addFile(String path, InputStream in) throws IOException{
		ZipEntry entry=new ZipEntry(path);
		file.putNextEntry(entry);
		for(int c=0;(c = in.read()) != -1;){
			file.write(c);
		}
		Const.currentLogListener.log("Successfully added file "+path+" to zip.");
		//in.close();
	}
	public void addDirectory(String dir) throws IOException{
		String path=dir;
		if(!path.endsWith("/")){
			path += "/";
		}
		ZipEntry entry=new ZipEntry(path);
		file.putNextEntry(entry);
		Const.currentLogListener.log("added directory "+dir+" to zip.");
	}
	public void finish() throws IOException{
		file.finish();
		file.close();
	}
}
