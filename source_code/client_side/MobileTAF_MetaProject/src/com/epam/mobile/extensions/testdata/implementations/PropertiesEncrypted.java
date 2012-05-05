package com.epam.mobile.extensions.testdata.implementations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/*
 * The original link: 
 * 		https://www.owasp.org/index.php/How_to_encrypt_a_properties_file
 * 
 * Refactor code, create unit tests and update classes.
 */
@SuppressWarnings("serial")
public class PropertiesEncrypted extends Properties {

	private static BASE64Decoder decoder = new sun.misc.BASE64Decoder();
	private static BASE64Encoder encoder = new sun.misc.BASE64Encoder();
	private Cipher encrypter, decrypter;
	private static byte[] salt = { (byte) 0x03 }; // make up your own

	public PropertiesEncrypted(String password) throws Exception {
		PBEParameterSpec ps = new PBEParameterSpec(salt, 20);
		SecretKeyFactory kf = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
		SecretKey k = kf.generateSecret(new PBEKeySpec(password.toCharArray()));
		encrypter = Cipher.getInstance("PBEWithMD5AndDES/CBC/PKCS5Padding");
		decrypter = Cipher.getInstance("PBEWithMD5AndDES/CBC/PKCS5Padding");
		encrypter.init(Cipher.ENCRYPT_MODE, k, ps);
		decrypter.init(Cipher.DECRYPT_MODE, k, ps);
	}

	public String getProperty(String key) {
		try {
			return decrypt(super.getProperty(key));
		} catch (Exception e) {
			throw new RuntimeException("Couldn't decrypt property");
		}
	}

	public synchronized Object setProperty(String key, String value) {
		try {
			return super.setProperty(key, encrypt(value));
		} catch (Exception e) {
			throw new RuntimeException("Couldn't encrypt property");
		}
	}

	private synchronized String decrypt(String str) throws Exception {
		byte[] dec = decoder.decodeBuffer(str);
		byte[] utf8 = decrypter.doFinal(dec);
		return new String(utf8, "UTF-8");
	}

	private synchronized String encrypt(String str) throws Exception {
		byte[] utf8 = str.getBytes("UTF-8");
		byte[] enc = encrypter.doFinal(utf8);
		return encoder.encode(enc);
	}

	// usage: java EncryptedProperties test.properties password
	public static void main(String[] args) throws Exception {
		File f = new File(args[0]);
		PropertiesEncrypted ep = new PropertiesEncrypted(args[1]);
		try {
			if (f.exists()) {
				FileInputStream in = new FileInputStream(f);
				ep.load(in);
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			String key = null;
			do {
				System.out.print("Enter key: ");
				key = br.readLine();
				System.out.print("Enter value: ");
				String value = br.readLine();
				if (key.length() > 0 && value.length() > 0) {
					ep.setProperty(key, value);
				}
			} while (key != null && key.length() > 0);

			FileOutputStream out = new FileOutputStream(f);
			ep.store(out, "Encrypted Properties File");
			out.close();

			System.out.println("Dump");
			Iterator<Object> i = ep.keySet().iterator();
			while (i.hasNext()) {
				String k = (String) i.next();
				String value = (String) ep.get(k);
				System.out.println("   " + k + "=" + value);
			}
		} catch (Exception e) {
			System.out.println("Couldn't access encrypted properties file: "
					+ f.getAbsolutePath());
			e.printStackTrace();
		}
	}
}
