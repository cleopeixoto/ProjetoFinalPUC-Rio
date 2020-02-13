package control;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.Arrays;


import view.Cadastro;
import view.Login;
import static control.Main.conexao;
import static control.Main.ps;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Cleo Peixoto
 */
public class CadastroControl {
    
    public static void realizaCadastro (Cadastro frame, String login, String nome, String rg, String cpf, String email, String pais, String estado, String cidade, char[] senha, char[] confirmaSenha) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        
        String passw = criptografaSenha(Arrays.toString(senha));
        String confirmaPassw = criptografaSenha(Arrays.toString(confirmaSenha));
        
        


        if (login.isEmpty() || nome.isEmpty() || rg.isEmpty() || cpf.isEmpty() || passw.isEmpty() ||
                confirmaPassw.isEmpty())
            JOptionPane.showMessageDialog(null, "Preencha os campos adequadamente", "Erro", JOptionPane.ERROR_MESSAGE);
        else {
            if (validarEmail(email) == false) {
                JOptionPane.showMessageDialog(null, "Preencha o email corretamente", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            else {
            
                if (!Arrays.equals(senha, confirmaSenha))
                    JOptionPane.showMessageDialog(null, "As senhas não coincidem", "Erro", JOptionPane.ERROR_MESSAGE);
                else {
                    Main.getConexaoMySQL();

                    String sqlDuplicadas = "SELECT login, rg, cpf FROM usuario Where login='" + login +
                            "' or rg='" + rg + "' or cpf='" + cpf + "'";
                    //String sqlDuplicadas = "SELECT login, rg, cpf FROM usuario WHERE login=?";
                    ps = conexao.prepareStatement(sqlDuplicadas);
                    ResultSet result = ps.executeQuery();
                    //ps.close();
                    //conexao.close();

                    int cont = 0;
                    while (result.next())
                        cont ++;

                    if (cont != 0)
                        JOptionPane.showMessageDialog(null, "Este usuário já existe", "Erro", JOptionPane.ERROR_MESSAGE);   
                    else {
                        String sql = "INSERT INTO usuario" + "(login, senha, nome, rg, cpf, email, pais, estado, cidade)" + 
                            "VALUES (?,?,?,?,?,?,?,?,?)";
                        ps = conexao.prepareStatement(sql);

                        ps.setString(1, login);
                        ps.setString(2, passw);
                        ps.setString(3, nome);
                        ps.setString(4, rg);
                        ps.setString(5, cpf);
                        ps.setString(6, email);
                        ps.setString(7, pais);
                        ps.setString(8, estado);
                        ps.setString(9, cidade);

                        ps.execute();


                        JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso");
                        frame.setVisible(false);
                        new Login().setVisible(true);
                    }
                    Main.fecharConexao();
                }
            }
        }           
        
        //JOptionPane.showMessageDialog(null, "ERRO");
        
    }
    
    static boolean validarEmail (String email) {
        boolean isEmailIdValid = false;
        
        if (email != null && email.length() > 0) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) { 
                isEmailIdValid = true;
            }
        }
        return isEmailIdValid;
    }
    
    public static String criptografaSenha (String original) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest algorithm = MessageDigest.getInstance("SHA256");
        byte messageDigest[] = algorithm.digest(original.getBytes("UTF-8"));

        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {
          hexString.append(String.format("%02X", 0xFF & b));
        }
        String senha = hexString.toString();
        
        return senha;
    }
    
    
}
