package com.qiufeng.compat.zip;
import java.util.zip.*;
import java.io.*;
import java.util.*;
import com.qiufeng.compat.*;

public class ZipDecomposer
{
	public ZipInputStream file;
	//public HashMap<String,InputStream> tree;
	public ZipDecomposer(InputStream is){
		file=new ZipInputStream(is);
	}
	public void decompose(File f)throws IOException,IllegalArgumentException{
		if(f.isFile())
			throw new IllegalArgumentException("File must be a directory.");
		Const.currentLogListener.warn("started decomposing file:"+f.getPath());
		ZipEntry entry;
		while((entry=file.getNextEntry())!=null){
			if(entry.isDirectory()){
				File dir=new File(f,entry.getName());
				dir.mkdirs();
				Const.currentLogListener.log("created directory "+dir.getPath());
			}else{
				File fi=new File(f,entry.getName());
				FileOutputStream fos=new FileOutputStream(fi);
				for(int i=0;(i=file.read())!=-1;){
					fos.write(i);
				}
				fos.close();
				Const.currentLogListener.log("created file "+fi.getPath());
			}
			file.closeEntry();
		}
		//file.reset();
		Const.currentLogListener.warn("zip decomposed.");
	}
	public HashMap<String,InputStream> generateTree() throws IOException{
		ZipEntry entry;
		HashMap<String,InputStream> h=new HashMap<>();
		while((entry=file.getNextEntry())!=null){
			if(entry.isDirectory())continue;
			StringBuilder sb=new StringBuilder();
			for(int i=0;(i=file.read())!=-1;){
				sb.append((char)i);
			}
			h.put(entry.getName(),new ByteArrayInputStream(sb.toString().getBytes()));
		}
		return h;
	}
	public InputStream getInputStream(String path) throws IOException{
		return generateTree().get(path);
	}
}
