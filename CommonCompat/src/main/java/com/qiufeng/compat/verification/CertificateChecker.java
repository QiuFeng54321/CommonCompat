package com.qiufeng.compat.verification;

import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.util.*;
import java.io.*;
import java.security.cert.*;
import javax.security.auth.x500.*;

public class CertificateChecker
{
	X509Certificate certificate;
	X500Principal principal;
	public CertificateChecker(Signature[] s){
		certificate=getX509Certificate(s);
		principal=getPrincipal(certificate);
	}
	private static X509Certificate getX509Certificate(Signature[] signatures){
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            ByteArrayInputStream stream = new ByteArrayInputStream(signatures[0].toByteArray());
            X509Certificate cert = (X509Certificate) cf.generateCertificate(stream);
            return cert; 
        } catch (Exception e) {
        }
        return null;
    }
	private static X500Principal getPrincipal(X509Certificate cert){
		return cert.getIssuerX500Principal();
	}
	public boolean isValid(){
		try{
			certificate.checkValidity();
			return true;
		}catch(Exception e){
			return false;
		}
	}
	public static CertificateChecker createFromActivity(Activity a) throws PackageManager.NameNotFoundException{
		PackageInfo pi=a.getPackageManager().getPackageInfo(a.getPackageName(),64);
		Signature[] sigs=pi.signatures;
		return new CertificateChecker(sigs);
	}
	public boolean isExpectedCertification(String name){
		String pName=principal.getName();
		return pName.equals(name);
	}
	private static Signature[] getRawSignature(Context paramContext, String paramString) {
        if ((paramString == null) || (paramString.length() == 0)) {
            Log.e("Signature","getSignature, packageName is null");
            return null;
        }
        PackageManager localPackageManager = paramContext.getPackageManager();
        PackageInfo localPackageInfo;
        try {
            localPackageInfo = localPackageManager.getPackageInfo(paramString, PackageManager.GET_SIGNATURES);
            if (localPackageInfo == null) {
                Log.e("Signature","info is null, packageName = " + paramString);
                return null;
            }
        } catch (PackageManager.NameNotFoundException localNameNotFoundException) {
            Log.e("Signature","NameNotFoundException");
            return null;
        }
        return localPackageInfo.signatures;
    }

    public static String getMD5Signature(Context cx,String packageName) {
        Signature[] arrayOfSignature = getRawSignature(cx, packageName);
        if ((arrayOfSignature == null) || (arrayOfSignature.length == 0)) {
            Log.e("MD5Signature","signs is null");
            return null;
        }
		String str=new MD5().getMD5ofStr(arrayOfSignature[0].toCharsString());
        Log.i("MD5Signature",str);
		return str;
    }
}
