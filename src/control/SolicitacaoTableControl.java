package control;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JTable;


import static control.Main.conexao;
import static control.Main.ps;

import model.Solicitacao;
import model.TabeliaoTableModel;

import view.InfoSolicitacao;
import view.Login;
import view.PrincipalTabeliao;

/**
 *
 * @author Cleo Peixoto
 */
public class SolicitacaoTableControl implements MouseListener {
    
    PrincipalTabeliao frame;
    
    public SolicitacaoTableControl (PrincipalTabeliao frame){
        this.frame = frame;
    }
    
    
    public static ArrayList<Solicitacao> obterSolicitacoes() throws SQLException{
        ArrayList<Solicitacao> sol = new ArrayList();
        String sql = "SELECT * FROM solicitacao";

        ps = conexao.prepareStatement(sql);
        ResultSet result = ps.executeQuery();

        while (result.next()) {
            sol.add(new Solicitacao(
                result.getInt(1),
                result.getInt(2),
                result.getString(3),
                result.getString(4),
                result.getString(5),
                result.getString(6)
            )
            );
        }
        return sol;
    }
    
    public static void atualizarSolicitacoes (InfoSolicitacao frame, int id, String status) throws SQLException{
        String sql = "UPDATE solicitacao SET statusSol=? where id_sol=?";
        ps = conexao.prepareStatement(sql);
        
        ps.setString(1, status);
        ps.setInt(2, id);
        ps.execute();
        
        frame.dispose();
        new PrincipalTabeliao().setVisible(true);
        
        
        // ELIMINA A SOLICITAÇÃO DA TABELA PENDENTE
        String sqlPendente = "DELETE FROM pendente WHERE id_solicitacao="+id;
        ps = conexao.prepareStatement(sqlPendente);
        ps.execute();
    }
    
    
    public static void logoutBotao (JFrame frame) {
        Main.fecharConexao();
        frame.setVisible(false);
        new Login().setVisible(true);
    }
    
    
    
    
    
    
    /* métodos automáticos */

    @Override
    public void mouseClicked(MouseEvent e) {
       if(e.getClickCount() > 1){
           try {
               JTable jTable = (JTable) e.getSource();
               TabeliaoTableModel solicitacao = (TabeliaoTableModel) jTable.getModel();
               int row = jTable.getSelectedRow();
               Solicitacao sol = solicitacao.getSolicitacao(row);
               
               InfoSolicitacao infoSol = new InfoSolicitacao(sol.getId(), sol.getId_cliente(),
                       sol.getData(), sol.getTipo(), sol.getStatus(), sol.getSerial());
               infoSol.setID(sol.getId());
               infoSol.setID_Cliente(sol.getId_cliente());
               infoSol.setData(sol.getData());
               infoSol.setTipo(sol.getTipo());
               infoSol.setStatus(sol.getStatus());
               infoSol.setSerial(sol.getSerial());
               infoSol.setVisible(true);
               frame.dispose();
           } catch (SQLException | IOException ex) {
               Logger.getLogger(SolicitacaoTableControl.class.getName()).log(Level.SEVERE, null, ex);
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
