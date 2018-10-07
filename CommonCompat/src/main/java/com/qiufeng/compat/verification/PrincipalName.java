package com.qiufeng.compat.verification;
import java.util.*;

public class PrincipalName
{
	public static enum Values{ 
		name("CN"),
		company("OU"),
		team("O"),
		city("L"),
		province("ST"),
		zipCode("C");
		public String target;
		public Values(String target){
			this.target=target;
		}
	}
	public static class Generator{
		public HashMap<Values,String> attr=new HashMap<Values,String>();
		public void add(Values v,String s){
			attr.put(v,s);
		}
		public void remove(Values v){
			attr.remove(v);
		}
		public String generate(){
			StringBuilder sb=new StringBuilder();
			for(Values v : Values.values()){
				if(attr.containsKey(v)){
					sb.append(v.target+"="+attr.get(v));
					sb.append(",");
				}
			}
			if(sb.length()>=1){
				sb.deleteCharAt(sb.length()-1);
			}
			return sb.toString();
		}
	}
	public static class Parser{
		HashMap<Values,String> attr=new HashMap<Values,String>();
		public static Parser parse(String str){
			Parser p=new Parser();
			char[] cs=str.toCharArray();
			boolean state=false;//false=key,true=value
			StringBuilder key=new StringBuilder(),value=new StringBuilder();
			
			for(char c : cs){
				if(c=='='){
					state=true;
				}else if(c==','){
					for(Values v : Values.values()){
						if(v.target.equals(key.toString())){
							p.attr.put(v,value.toString());
							key=new StringBuilder();
							value=new StringBuilder();
							break;
						}
					}
				}else{
					if(state){
						value.append(c);
					}else{
						key.append(c);
					}
				}
			}
			return p;
		}
	}
}
