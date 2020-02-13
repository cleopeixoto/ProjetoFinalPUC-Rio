package control;

import static control.Main.conexao;
import static control.Main.ps;
//import static control.Main.usuario;

//import model.Cliente;
import view.Login;
import view.PrincipalCliente;
import view.NovaSolicitacao;


import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.KeyPair;
import java.security.cert.CRLException;
import java.security.cert.CertStore;
import java.security.cert.X509Certificate;
//import java.security.cert.CertPath;
import java.security.cert.CertificateFactory;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509CRLSelector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Vector;
//import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import javax.crypto.KeyGenerator;

import javax.security.auth.x500.X500Principal;
import javax.security.auth.x500.X500PrivateCredential;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
//import model.Tabeliao;
//import model.Usuario;

//import org.bouncycastle.asn1.*;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.pkcs.Attribute;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x509.*;
//import org.bouncycastle.asn1.x509.Extension.*;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.X509Extension;
import org.bouncycastle.x509.X509V3CertificateGenerator;
import org.bouncycastle.x509.extension.AuthorityKeyIdentifierStructure;
//import org.bouncycastle.x509.X509V1CertificateGenerator;
import org.bouncycastle.x509.X509V2CRLGenerator;
import org.bouncycastle.jce.PKCS10CertificationRequest;
import org.bouncycastle.util.io.pem.*;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.openssl.PEMWriter;







/**
 *
 * @author cleolaptop
 */
public class ClienteControl {
    
    static int id_sol;
    
    static String idClienteCERT;
    static String emissorCERT;
    static String nomeComumCERT;
    static String organizacaoCERT;
    static String unidadeOrgCERT;
    static String cidadeCERT;
    static String estadoCERT;
    static String paisCERT;
    static String chaveSecreta;
    
    static X500PrivateCredential certCredential;
    static KeyPair pair_cli;
    
    
    // BOTAO "SOLICITAR"
    public static void solicitarBotao (PrincipalCliente frame) {
        frame.setVisible(false);
        new NovaSolicitacao().setVisible(true);
    }

    
    
    
    
    
    
    /*   ******************* EMISSÃO DE CERTIFICADO ********************** */
    
    public static void solicitaEmissaoCert (int idSol, int id_cli) throws Exception, PemGenerationException {
        id_sol = idSol;
        X509Certificate[] chain = geraCertificado(id_cli);
        
/*        
        PemObject pemObject0 = new PemObject("CERTIFICATE", chain[0].getEncoded()); // CERT CLIENTE
        PemObject pemObject1 = new PemObject("CERTIFICATE", chain[1].getEncoded()); // CERT AC
        PemWriter pemWrt = new PemWriter (new OutputStreamWriter(System.out));
        pemWrt.writeObject(pemObject0);
        pemWrt.writeObject(pemObject1);
*/


        StringWriter certCliente = new StringWriter();
        try (PEMWriter pemWrt0 = new PEMWriter (certCliente)) {
            pemWrt0.writeObject(chain[0]);
        }

        StringWriter certAC = new StringWriter();
        try (PEMWriter pemWrt1 = new PEMWriter (certAC)) {
            pemWrt1.writeObject(chain[1]);
        }

        
        
        
        
        /****************** INSERINDO NO BANCO AS INFORMACOES DO CERTIFICADO ****************/
        String sql = "INSERT INTO certificado (id_cliente, id_sol, versao, numeroSerial, algoritmoAss, nomeEmissor, validoDE, validoATE, nomeComum, organizacao, unidadeOrg, cidade, estado, pais, publicKey, statusCert, certCliente) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        ps = conexao.prepareStatement(sql);
        
        ps.setInt(1, id_cli);
        ps.setInt(2, id_sol);
        ps.setString(3, Integer.toString(chain[0].getVersion())); // int to string
        ps.setString(4, String.valueOf(chain[0].getSerialNumber())); // bigInteger to string
        ps.setString(5, chain[0].getSigAlgName()); // algoritmo assinatura
        ps.setString(6, emissorCERT); // nome Emissor
        String dataBefore = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(chain[0].getNotBefore());
        String dataAfter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(chain[0].getNotAfter());
        ps.setString(7, dataBefore); // valido a partir de
        ps.setString(8, dataAfter); // valido até
        ps.setString(9, nomeComumCERT);
        ps.setString(10, organizacaoCERT);
        ps.setString(11, unidadeOrgCERT);
        ps.setString(12, cidadeCERT);
        ps.setString(13, estadoCERT);
        ps.setString(14, paisCERT);
        ps.setString(15, chain[0].getPublicKey().toString());
        ps.setString(16, "Ativo");
        ps.setString(17, certCliente.toString()); // Cliente
        
        ps.execute();
        
        
        
        
        geraPacoteP12(chain[0].getSerialNumber().toString(), chain[0]);
        
    }
    
    static void geraPacoteP12 (String serial, X509Certificate cert) throws Exception {
        
        KeyStore store = Utils.createKeyStore(certCredential, pair_cli, cert);
        char[] password = chaveSecreta.toCharArray();
        
        store.store(new FileOutputStream("D:/Documentos/Projeto Final/Projetos/projetoFinal/projetoFinal/Certificados/"+serial+".p12"), password);
    }
    
    
    public static void downloadCert (String serial) throws IOException {    
        Path pastaCert = Paths.get("D:/Documentos/Projeto Final/Projetos/projetoFinal/projetoFinal/Certificados/"+serial+".p12");
        Path pastaCertDestino = Paths.get("C:/Temp/"+serial+".p12");

        //copy source to target using Files Class
        Files.copy(pastaCert, pastaCertDestino);   
        
        JOptionPane.showMessageDialog(null, "Seu certificado encontra-se em C:/Temp");

    }
  
    
    
    
    private static X500Principal buildSubjectCliente(int id_cliente) throws SQLException {
        StringBuilder nameBuilder = new StringBuilder();
        
        String sqlPendente = "SELECT nomeComum, organizacao, unidadeOrg, chaveSecreta FROM pendente where id_cliente=" + id_cliente + " and id_solicitacao=" + id_sol;
        ps = conexao.prepareStatement(sqlPendente);
        ResultSet resultPendente = ps.executeQuery();
        
        while (resultPendente.next()) { 
            nameBuilder.append("CN=").append(resultPendente.getString(1));
            nameBuilder.append(" O=").append(resultPendente.getString(2));
            nameBuilder.append(" OU=").append(resultPendente.getString(3));
            
            nomeComumCERT = resultPendente.getString(1);
            organizacaoCERT = resultPendente.getString(2);
            unidadeOrgCERT = resultPendente.getString(3);
            chaveSecreta = resultPendente.getString(4);
        }
        
        String sql = "SELECT pais, estado, cidade FROM usuario where id=" + id_cliente;
        ps = conexao.prepareStatement(sql);
        ResultSet result = ps.executeQuery();
        
        while (result.next()) { 
            nameBuilder.append(" C=").append(result.getString(1)); // pais
            nameBuilder.append(" S=").append(result.getString(2)); // state
            nameBuilder.append(" L=").append(result.getString(3)); // cidade
            
            paisCERT = result.getString(1);
            estadoCERT = result.getString(2);
            cidadeCERT = result.getString(3);
        }
        
        return new X500Principal(nameBuilder.toString());
   }
    
    
    public static PKCS10CertificationRequest geraCSR (KeyPair pair, int id) throws Exception {
        // create a SubjectAlternativeNAme extension value
        //GeneralName subjectAltName = new GeneralName (GeneralName.rfc822Name, "test@test.test");
         
        String sql = "SELECT * FROM usuario where id=" + id;
        ps = conexao.prepareStatement(sql);
        ResultSet result = ps.executeQuery();
        String email="";
        
        while (result.next())
            email = result.getString(7);
       
        GeneralName subjectAltName = new GeneralName (GeneralName.rfc822Name, email);

        // create the extensions object and add it as an attribute
        Vector oids = new Vector ();
        Vector values = new Vector ();

        oids.add(X509Extensions.SubjectAlternativeName);
        values.add(new X509Extension(false, new DEROctetString(subjectAltName)));

        X509Extensions extensions = new X509Extensions (oids, values);

        Attribute attribute = new Attribute (PKCSObjectIdentifiers.pkcs_9_at_extensionRequest, new DERSet(extensions));

        return new PKCS10CertificationRequest (
                        "SHA256WithRSA",
                        buildSubjectCliente(id),
                        //new X500Principal("CN = Requested Test Certificate"),
                        pair.getPublic(),
                        new DERSet(attribute),
                        pair.getPrivate()
                        );

    }
    
    
    public static X509Certificate[] geraCertificado(int id) throws Exception, IllegalArgumentException {
        // criando a solicitação de certificado (CSR)
        KeyPair pair = Utils.generateRSAKeyPair();
        PKCS10CertificationRequest request = geraCSR(pair, id);
        
        // criando o certificado da AC
        KeyPair         acPair = Utils.generateRSAKeyPair();
        X509Certificate certAC = Utils.gerarCertRaiz(acPair);
        //X509Certificate certAC = geraCertificadoX509V3(acPair); // certificado da AC        
        
        // validando a CSR
        if (!request.verify("BC")) {
            System.out.println("Falha ao verificar a solicitação de certificado");
            System.exit(1);
        }
        
        // criando o certificado do cliente utilizando as informações da solicitação
        X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();
        
        //Construindo o certificado do cliente:
        certGen.setSerialNumber(BigInteger.valueOf(System.currentTimeMillis()));
        
        /******* definindo emissor como nome do cliente! **********/
        String sql = "SELECT nome FROM usuario WHERE id="+id;
        ps = conexao.prepareStatement(sql);
        ResultSet result = ps.executeQuery();
        String issuer="";
        if (result.next())
            issuer = result.getString(1);
        
        emissorCERT = issuer;
 
        certGen.setIssuerDN(new X500Principal("CN="+issuer));
        //certGen.setIssuerDN(certAC.getSubjectX500Principal());
        
        // ************
        
        Calendar c = Calendar.getInstance();
        certGen.setNotBefore(c.getTime());
        c.add(Calendar.MINUTE, 20);    // 20 min validade 
        certGen.setNotAfter(c.getTime());
        
        certGen.setSubjectDN(buildSubjectCliente(id));
        certGen.setPublicKey(request.getPublicKey());
        certGen.setSignatureAlgorithm("SHA256WithRSA");
        certGen.addExtension(X509Extension.authorityKeyIdentifier, false, new AuthorityKeyIdentifierStructure(certAC));
        certGen.addExtension(X509Extension.subjectKeyIdentifier, false, 
                new JcaX509ExtensionUtils().createSubjectKeyIdentifier(request.getPublicKey("BC")) );
                //SubjectKeyIdentifierStructure(request.getPublicKey("BC")));
        certGen.addExtension(X509Extensions.BasicConstraints, true, new BasicConstraints(false));
        certGen.addExtension(X509Extensions.KeyUsage, true, 
                new KeyUsage(KeyUsage.digitalSignature | KeyUsage.keyEncipherment));
        certGen.addExtension(X509Extensions.ExtendedKeyUsage, true, new ExtendedKeyUsage(KeyPurposeId.id_kp_serverAuth));
        
        // extração do atributo da solicitação
        ASN1Set attributes = request.getCertificationRequestInfo().getAttributes();
        
        for (int i=0; i != attributes.size(); i++) {
            Attribute attr = Attribute.getInstance(attributes.getObjectAt(i));
            
            // processando a solicitacao de extensao
            if (attr.getAttrType().equals(PKCSObjectIdentifiers.pkcs_9_at_extensionRequest)) {
                X509Extensions extensions = X509Extensions.getInstance(attr.getAttrValues().getObjectAt(0));
                Enumeration e = extensions.oids();
                
                while (e.hasMoreElements()) {
                    ASN1ObjectIdentifier oid = (ASN1ObjectIdentifier)e.nextElement();
                    X509Extension       ext = extensions.getExtension(oid);
                    certGen.addExtension(oid, ext.isCritical(), ext.getValue().getOctets());
                }
            }
        }
        
        X509Certificate certCliente = certGen.generateX509Certificate(acPair.getPrivate());
        
        certCredential = Utils.criaCertCredential(acPair.getPrivate(), certAC, pair, certCliente);
        pair_cli = pair;
        
        System.out.println("Certificado da AC: \n" + certAC);
        System.out.println("\n\nCertificado do cliente: " + certCliente + "\n\n");
        
        return new X509Certificate[] { certCliente, certAC };
    }
    
    
    
    /*
    *   ******************* RENOVAÇÃO DE CERTIFICADO **********************
    */
    public static void solicitaRenovacaoCert (int idSol, int id_cliente, BigInteger serial) throws Exception {
//         String sqlPendente = "SELECT nomeComum, organizacao, unidadeOrg FROM pendente where id_cliente=" + id_cliente + " and id_solicitacao=" + id_sol;
        
        String sql = "SELECT nomeComum, organizacao, unidadeOrg FROM certificado WHERE numeroSerial="+serial;
        ps = conexao.prepareStatement(sql);
        ResultSet result = ps.executeQuery();
        
        String nomeComumREN="", orgREN="", unOrgREN="";
        
        while (result.next()) {
            nomeComumREN = result.getString(1);
            orgREN = result.getString(2);
            unOrgREN = result.getString(3);
        }
        
        String sqlNovaSol = "INSERT INTO pendente (id_cliente, id_solicitacao, nomeComum, organizacao, unidadeOrg) VALUES (?,?,?,?,?)";
        ps = conexao.prepareStatement(sqlNovaSol);
        
        ps.setInt(1, id_cliente);
        ps.setInt(2, idSol);
        ps.setString(3, nomeComumREN);
        ps.setString(4, orgREN);
        ps.setString(5, unOrgREN);
        
        ps.execute();
        
        
        solicitaRevogacaoCert(idSol, serial); // revoga o certificado atual
        solicitaEmissaoCert(idSol, id_cliente); // emite um novo certificado com os mesmos dados do anterior
    }
    
    
    
    
    
    
    
    
    
    /*   ******************* REVOGAÇÃO DE CERTIFICADO ********************** */
    
        
    /* produz uma CRL que contém a revogação do cerficiado cujo serial number=revokedSerialNumber, emitido pelo principal X500
    */
    public static void solicitaRevogacaoCert (int idSol, BigInteger revokedSerialNumber) throws Exception {
        //criando as chaves da CA e do Certificado
        KeyPair 	caPair              = Utils.generateRSAKeyPair();
        X509Certificate caCert              = Utils.gerarCertRaiz(caPair);

        // criando a CRL e revogando o certificado de número de série=revokedSerialNumber
        X509CRL crl = criaCRL(caCert, caPair.getPrivate(), revokedSerialNumber);
        
        
        /*********** Revogando serial usando a classe CertificateFactory: **********/
        // codificando e reconstruindo-o
        ByteArrayInputStream bIn = new ByteArrayInputStream (crl.getEncoded());
        CertificateFactory fact = CertificateFactory.getInstance("X.509", "BC");
        crl = (X509CRL)fact.generateCRL(bIn);
        /****************************************/
        
        /* **********  Retrieving a CRL from a CertStore ******** */
        // place the CRL into a CertStore
        CollectionCertStoreParameters params = new CollectionCertStoreParameters(Collections.singleton(crl));
        CertStore store = CertStore.getInstance("Collection", params, "BC");
        X509CRLSelector selector = new X509CRLSelector();
        selector.addIssuerName(caCert.getSubjectX500Principal().getEncoded());
        Iterator it = store.getCRLs(selector).iterator();
        /* ************************ */
        
        if (it.hasNext()) {

            // verificando a CRL
            crl.verify(caCert.getPublicKey(), "BC");

            // checando se a CRL revogou o cert de número de série = revokedSerialNumber
            X509CRLEntry entry = crl.getRevokedCertificate(revokedSerialNumber);
            System.out.println("\nDETALHES DA REVOGAÇÃO:");
            System.out.println("Certificate number: " + entry.getSerialNumber());
            System.out.println("Issuer: " + crl.getIssuerDN());
            //System.out.println("Issuer: " + crl.getIssuerX500Principal()); // Issuer da AC
            
            baixaCRL(crl, entry.getSerialNumber(), new Date()); // adiciona cert ao arquivo .crl
        }
        
        /********** ATUALIZANDO STATUS DO CERTIFICADO **********/
        String sql = "UPDATE certificado SET statusCert=? where numeroSerial=?";
        ps = conexao.prepareStatement(sql);
        ps.setString(1, "Revogado");
        ps.setString(2, revokedSerialNumber.toString());
        ps.execute();
    }
    
    public static X509CRL criaCRL (X509Certificate caCert, PrivateKey	caKey, BigInteger revokedSerialNumber) 
            throws Exception {

        X509V2CRLGenerator  crlGen  = new X509V2CRLGenerator();
        Date                now     = new Date();

        // poderia usar cert.setIssuerDN(certCliente.getIssuerX500Principal());
        crlGen.setIssuerDN(caCert.getSubjectX500Principal());
        crlGen.setThisUpdate(now);
        crlGen.setNextUpdate(new Date(now.getTime() + 100000));
        crlGen.setSignatureAlgorithm("SHA256WithRSA");
        crlGen.addCRLEntry(revokedSerialNumber, now, CRLReason.privilegeWithdrawn);

        /* aqui adicionamos extensões providenciando um link para voltar ao certificado da AC e assinando um número à essa CRL e então gerando a CRL, assinando-a com a chave privada do emissor */
        crlGen.addExtension(X509Extensions.AuthorityKeyIdentifier, false, new AuthorityKeyIdentifierStructure(caCert));
        crlGen.addExtension(X509Extensions.CRLNumber, false, new CRLNumber(BigInteger.valueOf(1)));

        return crlGen.generateX509CRL(caKey, "BC");
    }
    
    static void baixaCRL(X509CRL crl, BigInteger serial, Date dataRev) throws IOException, CRLException {
            byte[] CRLF = new byte[] {'\r', '\n'};
            String listaCRL = Base64.getMimeEncoder(64, CRLF).encodeToString(crl.getEncoded());       
            
            FileWriter fileWriter = new FileWriter("C:/Temp/CRL.crl", true); // true para adicionar
            try (PrintWriter printWriter = new PrintWriter(fileWriter)) {
                printWriter.println("\n");
                printWriter.println("\nSerial número: "+serial.toString()+" - "+dataRev.toString()); // nova linha
                printWriter.println("-----BEGIN X509 CRL-----");
                printWriter.println("\n"+listaCRL+"\n");
                printWriter.println("-----END X509 CRL-----");
                printWriter.println("\n");
                printWriter.close();
            }
    }
    
    public static void mostraCertificadosRevogados () throws SQLException, IOException {        
        String s = "C:/Temp/CRL.crl"; 
        Runtime.getRuntime().exec("C:/Windows/system32/notepad.exe " + s);
    }
    
    
    
    
    
    
    
    
    
    
  
    
    /*************** MÉTODO ADICIONAL
     * @param frame ***********/
    
    public static void logoutBotao (JFrame frame) {
        Main.fecharConexao();
        frame.setVisible(false);
        new Login().setVisible(true);
    }


}
