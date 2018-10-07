package com.qiufeng.compat.zip;
import kellinwood.security.zipsigner.*;
import java.io.*;
import java.security.*;
import com.qiufeng.compat.*;

public class ZipSignerUtils
{
	public static void sign(String in,String out) throws IllegalAccessException, ClassNotFoundException, InstantiationException, IOException, GeneralSecurityException{
		Const.currentLogListener.warn("signing File:"+in+" to output:"+out);
		ZipSigner s=new ZipSigner();
		s.setKeymode(ZipSigner.KEY_TESTKEY);
		s.signZip(in,out);
		Const.currentLogListener.warn("signed "+in+" to file:"+out);
	}
}
