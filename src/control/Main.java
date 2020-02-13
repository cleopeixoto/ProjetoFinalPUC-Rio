package control;

import java.security.Security;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.Usuario;
import view.Login;

/**
 *
 * @author cleolaptop
 */
public class Main {
    static String status = "Não conectou";
    public static Connection conexao = null;
    public static PreparedStatement ps;
    public static Usuario usuario;
    
    
    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     * @throws java.lang.NullPointerException
     */
    public static void main(String args[]) throws Exception, NullPointerException {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        // Create and display the form
        java.awt.EventQueue.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }

    // testa se o provider, BouncyCastle nesse caso, etá instalado
    public static void providerTest() {
        String providerName = "BC";

        if (Security.getProvider(providerName) == null) {
            System.out.println(providerName + " provider  not installed");
        } else {
            System.out.println(providerName + " provider is installed");
        }
    }

    
    
    
    /* ***************** CONECTANDO COM O BANDO DE DADOS ****************** */


    //Método de Conexão//
    public static Connection getConexaoMySQL() {
        //Connection connection = null;          //atributo do tipo Connection
        if(conexao == null){
            try {

                // Carregando o JDBC Driver padrão
                String driverName = "com.mysql.cj.jdbc.Driver";

                Class.forName(driverName);

                // Configurando a nossa conexão com um banco de dados//
                String serverName = "localhost:3306";    //caminho do servidor do BD
                String mydatabase = "projeto";        //nome do seu banco de dados
                String url = "jdbc:mysql://" + serverName + "/" + mydatabase + "?useSSL=false&useTimezone=true&serverTimezone=UTC&allowPublicKeyRetrieval=true";
                String username = "projeto";        //nome de um usuário de seu BD      
                String password = "projeto";      //sua senha de acesso
                conexao = DriverManager.getConnection(url, username, password);

                // Testa sua conexão//  
                if (conexao != null) {
                    status = ("STATUS--->Conectado com sucesso!");
                    //System.out.println(status);
                } else {
                    status = ("STATUS--->Não foi possivel realizar conexão");
                    //System.out.println(status);
                }

                //return connection;

            }
            //Driver não encontrado
            catch (ClassNotFoundException e) {
                System.out.println("O driver expecificado nao foi encontrado.");
                return null;

            } //Não conseguindo se conectar ao banco
            catch (SQLException e) {
                System.out.println("Nao foi possivel conectar ao Banco de Dados.\n" + e.getMessage());
                return null;
            }
        }
        return conexao;
    }

    //Método que fecha sua conexão//
    public static boolean fecharConexao() {
        try {
            getConexaoMySQL().close();
            conexao = null;
            return true;
        }
        
        catch (SQLException e) {
            return false;
        }
    }

}
