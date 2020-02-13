package control;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.Calendar;

import javax.security.auth.x500.X500Principal;
import javax.security.auth.x500.X500PrivateCredential;

import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.x509.*;

public class Utils {

    @SuppressWarnings("serial")
    private static class FixedRand extends SecureRandom {
        MessageDigest	sha;
        byte[]          state;

        FixedRand() {
            try {
                this.sha = MessageDigest.getInstance("SHA256", "BC");
                this.state = sha.digest();
            }
            catch (NoSuchAlgorithmException | NoSuchProviderException e) {
                throw new RuntimeException ("can't find SHA256");
            }
        }


        @Override
        public void nextBytes(byte[] bytes) {
            int off =0;
            sha.update(state);
            while (off < bytes.length) {
                state = sha.digest();
                if (bytes.length - off > state.length) 
                    System.arraycopy(state, 0, bytes, off, state.length);
                else
                    System.arraycopy(state, 0, bytes, off, bytes.length - off);

                off += state.length;
                sha.update(state);
            }
        }
    }

    /* Return a SecureRandom which produces the same value
     * return: fixed random
     */

    public static SecureRandom createFixedRandom () {
        return new FixedRand();
    }

    // create a random 1024 bit RSA key pair: par de chaves
    public static KeyPair generateRSAKeyPair() throws Exception {
        KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA", "BC");
        kpGen.initialize(1024, new SecureRandom());

        return kpGen.generateKeyPair();
    }

    private static final int VALIDITY_PERIOD = 7*24*60*60*1000; // one week

    // gera um certificado X509 V3 para usar como certificado raiz da AC.
    @SuppressWarnings("deprecation")
    public static X509Certificate gerarCertRaiz (KeyPair pair) throws Exception {
        X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();

        certGen.setSerialNumber(BigInteger.valueOf(1));
        certGen.setIssuerDN(new X500Principal ("CN = AC Certificado Raiz"));
        Calendar c = Calendar.getInstance();
        certGen.setNotBefore(c.getTime());
        c.add(Calendar.MINUTE, 20);    // 1 ano de validade
        certGen.setNotAfter(c.getTime());
        certGen.setSubjectDN(new X500Principal("CN = AC Certificado Raiz"));
        certGen.setPublicKey(pair.getPublic());
        certGen.setSignatureAlgorithm("SHA256WithRSA");
        
        certGen.addExtension(X509Extensions.BasicConstraints, true, new BasicConstraints(true));
        certGen.addExtension(X509Extensions.KeyUsage, true, 
                new KeyUsage(KeyUsage.digitalSignature | KeyUsage.keyEncipherment));
        certGen.addExtension(X509Extensions.ExtendedKeyUsage, true, 
                new ExtendedKeyUsage(KeyPurposeId.id_kp_serverAuth));
        

        return certGen.generateX509Certificate(pair.getPrivate(), "BC");
    }
    
    
    // gera um X500PrivateCredential para o certificado final: cliente
    public static X500PrivateCredential criaCertCredential (
            PrivateKey chaveAC, X509Certificate certAC, KeyPair pair, X509Certificate endCert) 
            throws Exception {
        
        return new X500PrivateCredential(endCert, pair.getPrivate(), "End");
    }

    
    public static KeyStore createKeyStore(X500PrivateCredential rootCredential, KeyPair pair, 
            X509Certificate cert) throws Exception {
        
        KeyStore store = KeyStore.getInstance("PKCS12", "BC");
        
        // initialize
        store.load(null, null);
        
        X500PrivateCredential endCredential = Utils.criaCertCredential(rootCredential.getPrivateKey(),
                rootCredential.getCertificate(), pair, cert);
        
        java.security.cert.Certificate[] chain = new java.security.cert.Certificate[2];
        
        chain[0] = endCredential.getCertificate();
//        chain[1] = interCredential.getCertificate();
        chain[1] = rootCredential.getCertificate();
        
        // set the entries
        store.setCertificateEntry(rootCredential.getAlias(), rootCredential.getCertificate());
        store.setKeyEntry(endCredential.getAlias(), endCredential.getPrivateKey(), null, chain);
        
        return store;
    }    

}


