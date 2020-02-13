package control;

import static control.CadastroControl.criptografaSenha;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


import static control.Main.conexao;
import static control.Main.ps;
import static control.Main.usuario;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import static javafx.application.Platform.exit;

import model.Cliente;

import view.InfoCertificado;
import view.PrincipalCliente;
import view.NovaSolicitacao;

/**
 *
 * @author Cleo Peixoto
 */
public class SolicitacaoControl {
    
    static int id_cli;
    static String serial;
    static int id_pendente;
    

    // BOTÃO BROWSE IMAGE:
    public static File escolherImagem (NovaSolicitacao frame, File file, JLabel imagemDoc, JTextField imagemEscolhida) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int res = chooser.showOpenDialog(frame);
        if (res == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
            //String path = file.getAbsolutePath();
            ImageIcon imageIcon = new ImageIcon (file.getAbsolutePath());
            Rectangle rec = imagemDoc.getBounds();
            Image scaledImage = imageIcon.getImage().getScaledInstance(rec.width, rec.height, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(scaledImage);
            imagemDoc.setIcon(imageIcon);
            imagemEscolhida.setText(file.getName());
        }
        else {
            JOptionPane.showMessageDialog(null, "Nenhuma imagem selecionada");
        }
        
        return file;
    }
    
    /**
     *
     * @param frame
     * @param file
     * @param caminhoImg
     * @param imagemEscolhida
     * @return 
     * @throws IOException
     */
    public static String salvarImagem (NovaSolicitacao frame, File file, String caminhoImg, String imagemEscolhida) throws IOException {
        BufferedImage img;
        img = ImageIO.read(file);
        caminhoImg = "D:/Documentos/Projeto Final/Projetos/projetoFinal/projetoFinal/Comprovantes/"
                +imagemEscolhida;
        String format = "JPG";
        ImageIO.write(img, format, new File(caminhoImg));
        JOptionPane.showMessageDialog(null, "Imagem salva com sucesso");
        
        return caminhoImg;
    }
    
    
    public static void mostraImagem (JLabel img, int id_sol) 
            throws SQLException, IOException {
        String sql = "SELECT imagem FROM pendente WHERE id_solicitacao="+id_sol;
        ps = conexao.prepareStatement(sql);
        ResultSet res = ps.executeQuery();
        String path = "";
        if (res.next())
            path = res.getString(1);
        
        
        ImageIcon imageIcon = new ImageIcon (path);
        Rectangle rec = img.getBounds();
        Image imagem = imageIcon.getImage().getScaledInstance(rec.width, rec.height, Image.SCALE_SMOOTH);
        //Image imagem = imageIcon.getImage();
        imageIcon = new ImageIcon(imagem);
        img.setIcon(imageIcon);
    }

    
    // BOTAO "Enviar Solicitacao"
    public static void enviaSolicitacaoEmissao (NovaSolicitacao frame, String org, String unOrg, 
            String tipo, String caminhoImagem, char[] chaveSecreta, char[] confirmaChaveSecreta)
             throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        
        if (org.isEmpty() || unOrg.isEmpty() || chaveSecreta.equals("") || confirmaChaveSecreta.equals(""))
            JOptionPane.showMessageDialog(null, "Preencha os campos adequadamente", "Erro", JOptionPane.ERROR_MESSAGE);
        
        else {
            if (!Arrays.equals(chaveSecreta, confirmaChaveSecreta)) {
                JOptionPane.showMessageDialog(null, "As chaves não coincidem", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            else {
                // mostra opção pra confirmar se quer o nome criado no cadastro : showOptionDialog
                int escolha = JOptionPane.showOptionDialog(null, "Você deseja criar o certificado com o mesmo nome de cadastro?",
                        "Confirma nome certificado", JOptionPane.YES_NO_CANCEL_OPTION, 
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                
                String chaveSecretaCripto = criptografaSenha (Arrays.toString(chaveSecreta));

                String sql = "INSERT INTO pendente (id_cliente, nomeComum, organizacao, unidadeOrg, imagem, chaveSecreta) values (?,?,?,?,?,?)";
                ps = conexao.prepareStatement(sql);

                ps.setInt(1, usuario.getID());
                ps.setString(3, org);
                ps.setString(4, unOrg);
                ps.setString(5, caminhoImagem);
                ps.setString(6, chaveSecretaCripto);

                if (escolha == JOptionPane.NO_OPTION) {
                    String nome = JOptionPane.showInputDialog("Digite o nome para emissão do certificado:");
                    if (nome.isEmpty()) //quando aperta cancel, ele retorna null
                        System.exit(0);

                    ps.setString(2, nome);
                    ps.execute();

                    String sqlTemp = "SELECT id_pendente FROM pendente ORDER BY id_pendente DESC LIMIT 1";
                    ps = conexao.prepareStatement(sqlTemp);
                    ResultSet resultTemp = ps.executeQuery();
                    if (resultTemp.next())
                        id_pendente = resultTemp.getInt(1);

                    realizaSolicitacao(tipo);

                }

                else {
                    ps.setString(2, ((Cliente)usuario).getNome());
                    ps.execute();

                    String sqlTemp = "SELECT id_pendente FROM pendente ORDER BY id_pendente DESC LIMIT 1";
                    ps = conexao.prepareStatement(sqlTemp);
                    ResultSet resultTemp = ps.executeQuery();
                    if (resultTemp.next())
                        id_pendente = resultTemp.getInt(1);

                    realizaSolicitacao(tipo);
                }



                JOptionPane.showMessageDialog(null, 
                    "Solicitação enviada com sucesso! Aguarde aprovação do tabelião");
                frame.setVisible(false);
                new PrincipalCliente().setVisible(true);
            }
        }
    }
    
    public static void enviaSolicitacaoRenovacao (InfoCertificado frame, int id_cliente, String serial_cert, String validade) throws SQLException, ParseException {        
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dataAtual = new Date();
        Date dataCert = sdf.parse(validade);
        
        if (dataCert.compareTo(dataAtual) > 0) { // dataCert está antes da dataAtual
            JOptionPane.showMessageDialog(null, "O seu certificado ainda está ativo.");
        }
        
        else {
            id_cli = id_cliente;
            serial = serial_cert;
            realizaSolicitacao("Renovacao");

            JOptionPane.showMessageDialog(null, "Solicitação enviada com sucesso! Aguarde aprovação do tabelião");
            frame.setVisible(false);
            new PrincipalCliente().setVisible(true);
        }
    }

    public static void enviaSolicitacaoRevogacao (InfoCertificado frame, int id_cliente, String serial_cert) throws SQLException {
        id_cli = id_cliente;
        serial = serial_cert;
        realizaSolicitacao("Revogacao");
        
        JOptionPane.showMessageDialog(null, "Solicitação enviada com sucesso! Aguarde aprovação do tabelião");
        frame.setVisible(false);
        new PrincipalCliente().setVisible(true);
    }

    
    // este método auxiliar realiza uma solicitacao (pedido) no banco de dados
    static void realizaSolicitacao (String tipo) throws SQLException {
        int id_usuario;
        if ("Emissao".equals(tipo)) {
            id_usuario = usuario.getID();
            serial = null;
        }
        else
            id_usuario = id_cli;
        
        String dataSol = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
        //String tipo = "Emissao";
        String statusSol = "Pendente";
        
        String sql = "INSERT INTO solicitacao (id_usuario, dataSol, tipo, statusSol, serialCert) VALUES (?,?,?,?,?)";
        ps = conexao.prepareStatement(sql);
        
        ps.setInt(1, id_usuario);
        ps.setString(2, dataSol);
        ps.setString(3, tipo);
        ps.setString(4, statusSol);
        ps.setString(5, serial);
        
        ps.execute();
        
        
        if ("Emissao".equals(tipo)) {
            String sqlTemp = "SELECT id_sol FROM solicitacao ORDER BY id_sol DESC LIMIT 1";
            ps = conexao.prepareStatement(sqlTemp);
            ResultSet resultTemp = ps.executeQuery();
            int idSolicitacao=0;
            if (resultTemp.next())
                idSolicitacao = resultTemp.getInt(1);
            
            String sqlTemp2="UPDATE pendente SET id_solicitacao="+idSolicitacao+" WHERE id_pendente="+id_pendente;
            ps = conexao.prepareStatement(sqlTemp2);
            ps.execute();
            
        }
    }
    
    
    public static void analisaSolicitacao (int idSol, int id_cliente) throws SQLException, Exception {
        String sql = "SELECT tipo, serialCert FROM solicitacao WHERE id_sol=" + idSol + " and id_usuario="+id_cliente;
        ps = conexao.prepareStatement(sql);
        ResultSet result = ps.executeQuery();
        String tipo = "";
        
        while (result.next()) {
            tipo = result.getString(1);
            serial = result.getString(2);
        }
        
        if (null != tipo) switch (tipo) {
            case "Emissao":
                ClienteControl.solicitaEmissaoCert(idSol, id_cliente);
                break;
            case "Renovacao":
                ClienteControl.solicitaRenovacaoCert(idSol, id_cliente, new BigInteger(serial));
                break;
            case "Revogacao":
                ClienteControl.solicitaRevogacaoCert(idSol, new BigInteger(serial));
                break;
            default:
                break;
        }
    }
   
}
