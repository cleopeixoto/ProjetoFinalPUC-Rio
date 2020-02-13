package control;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;

import model.Certificado;
import model.ClienteTableModel;
import view.InfoCertificado;
import view.PrincipalCliente;
import static control.Main.conexao;
import static control.Main.ps;
import static control.Main.usuario;

/**
 *
 * @author Cleo Peixoto
 */
public class CertificadoTableControl implements MouseListener {
    
    PrincipalCliente frame;
    
    public CertificadoTableControl (PrincipalCliente frame){
        this.frame = frame;
    }
    
    
    // recebe o id do cliente
    public static ArrayList<Certificado> obterCertificados() throws SQLException{
        ArrayList<Certificado> sol = new ArrayList();
        String sql = "SELECT id_cert, versao, nomeEmissor, validoATE, nomeComum, statusCert FROM certificado WHERE id_cliente="+usuario.getID();
        //int id = usuario.getID();
        
        ps = conexao.prepareStatement(sql);
        //ps.setInt(1, id);
        ResultSet result = ps.executeQuery();

        while (result.next()) {
            sol.add(new Certificado(
                result.getInt(1),
                result.getString(2),
                result.getString(3),
                result.getString(4),
                result.getString(5),
                result.getString(6)
            )
            );
        }
        return sol;
    }
    
    
    public static String analisaSolCert (int id) throws SQLException {
        String sql = "SELECT numeroSerial FROM certificado WHERE id_cert="+id;
        ps = conexao.prepareStatement(sql);
        ResultSet result = ps.executeQuery();
        String serial = "";
        if (result.next())
            serial = result.getString(1);
        
        sql = "SELECT tipo FROM solicitacao WHERE serialCert="+serial;
        ps = conexao.prepareStatement(sql);
        result = ps.executeQuery();
        String tipo = "";
        if (result.next())
            tipo = result.getString(1);        

        //System.out.println(tipo);
        
        return tipo;
    }
    
    
    
    /* métodos automáticos */

    @Override
    public void mouseClicked(MouseEvent e) {
       if(e.getClickCount() > 1){
           try {
               JTable jTable = (JTable) e.getSource();
               ClienteTableModel certificado = (ClienteTableModel) jTable.getModel();
               int row = jTable.getSelectedRow();
               Certificado cert = certificado.getCertificado(row);
               
               InfoCertificado infoCert = null; 
               infoCert = new InfoCertificado(cert.getID(), cert.getVersao(), cert.getEmissor(),
                       cert.getValidade(),cert.getNomeComum(), cert.getStatus());
               infoCert.setID(cert.getID());
               infoCert.setVersao(cert.getVersao());
               infoCert.setEmissor(cert.getEmissor());
               infoCert.setValidoAte(cert.getValidade());
               infoCert.setNomeComum(cert.getNomeComum());
               infoCert.setStatus(cert.getStatus());
               
               
               String sql = "SELECT id_cliente, numeroSerial, algoritmoAss, validoDE, organizacao, unidadeOrg, cidade, estado, pais, publicKey FROM certificado WHERE id_cert="+cert.getID();
               ps = conexao.prepareStatement(sql);
               
               ResultSet result = ps.executeQuery();
               while (result.next()) {
                   infoCert.setID_cliente(result.getInt(1));
                   infoCert.setSerial(result.getString(2));
                   infoCert.setAlgoAss(result.getString(3));
                   infoCert.setValidoDe(result.getString(4));
                   infoCert.setOrganizacao(result.getString(5));
                   infoCert.setUnOrg(result.getString(6));
                   infoCert.setCidade(result.getString(7));
                   infoCert.setEstado(result.getString(8));
                   infoCert.setPais(result.getString(9));
                   infoCert.setChavePublica(result.getString(10));
               }
               
               
               infoCert.setVisible(true);
               frame.dispose();
           } catch (SQLException ex) {
               Logger.getLogger(CertificadoTableControl.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
