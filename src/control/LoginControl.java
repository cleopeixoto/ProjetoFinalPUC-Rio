package control;


import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;


import static control.Main.conexao;
import static control.Main.ps;
import static control.Main.usuario;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import model.Cliente;
import model.Tabeliao;

import view.Cadastro;
import view.Login;
import view.PrincipalCliente;
import view.PrincipalTabeliao;

public class LoginControl {
       
    // BOTAO "REALIZAR LOGIN"
    public static void realizaLogin (String login, char[] senha, Login frame) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {   
        
        String passw = new String(senha);
        if (login.length() > 0 && passw.length()>0) {
            // se login=admin e senha=admin: PrincipalTabeliao
            if ("admin".equals(login) && passw.equals("admin")) {
                Main.getConexaoMySQL();
                frame.setVisible(false);
                usuario = new Tabeliao("admin");
                new PrincipalTabeliao().setVisible(true);
            }

            // SE NÃO:
            else {
                passw = CadastroControl.criptografaSenha(Arrays.toString(senha));
                Main.getConexaoMySQL();
                String sql = "SELECT * FROM usuario WHERE login=? and senha=?";
                //String sql = "SELECT * FROM usuario Where login='" + login + "' and senha='" + passw + "'";
                ps = conexao.prepareStatement(sql);
                
                ps.setString(1, login);
                ps.setString(2, passw);

                try (ResultSet result = ps.executeQuery()) {
                    if (result.next()) {
                        int idBD = result.getInt(1);
                        String nomeBD = result.getString(4);
                        String rgBD = result.getString(5);
                        String cpfBD = result.getString(6);
                        String emailBD = result.getString(7);
                        String paisBD = result.getString(8);
                        String estadoBD = result.getString(9);
                        String cidadeBD = result.getString(10);
                        

                        Cliente cliente = new Cliente(login);
                        cliente.setID(idBD);
                        cliente.setSenha(senha);
                        cliente.setNome(nomeBD);
                        cliente.setRG(rgBD);
                        cliente.setCPF(cpfBD);
                        cliente.setEmail(emailBD);
                        cliente.setPais(paisBD);
                        cliente.setEstado(estadoBD);
                        cliente.setCidade(cidadeBD);

                        usuario = cliente;

                        // login-senha coincidem: PrincipalCliente
                        frame.setVisible(false);
                        new PrincipalCliente().setVisible(true);
                        
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Usuario ou senha inválidos", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else
            JOptionPane.showMessageDialog(null, "Preencha os campos de login e senha", "Erro", JOptionPane.ERROR_MESSAGE);
        
    }
    
    
    // BOTAO "CADASTRE-SE"
    public static void cadastroBotao (Login frame) {
        frame.setVisible(false);
        new Cadastro().setVisible(true);
    }
    
}
