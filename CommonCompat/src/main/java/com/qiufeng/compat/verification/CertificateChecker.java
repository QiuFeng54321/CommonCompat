package com.qiufeng.compat.verification;

import android.content.pm.*;
import java.io.*;
import java.security.cert.*;
import javax.security.auth.x500.*;
import android.app.*;
import android.content.pm.PackageManager.*;

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
}
