package model;

import control.SolicitacaoTableControl;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Cleo Peixoto
 */
public class TabeliaoTableModel extends AbstractTableModel {
    private final ArrayList <Solicitacao> solicitacoes;
    private final String[] colunas = {"ID", "ID Cliente", "Data da Solicitação", "Tipo", "Status"};
    
    public TabeliaoTableModel () throws SQLException {
        solicitacoes = SolicitacaoTableControl.obterSolicitacoes();
    }
    
    public Solicitacao getSolicitacao (int rowIndex){
        return solicitacoes.get(rowIndex);
    }

    @Override
    public int getRowCount() {
        return solicitacoes.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        //String dataSol = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
        //Format formatter = new SimpleDateFormat("dd/MM/yyyy");

        switch(columnIndex){
            case 0:
                return solicitacoes.get(rowIndex).getId();
            case 1:
                return solicitacoes.get(rowIndex).getId_cliente();
            case 2:
                return solicitacoes.get(rowIndex).getData();
            case 3:
                return solicitacoes.get(rowIndex).getTipo();
            case 4:
                return solicitacoes.get(rowIndex).getStatus();
        }
        return null;
    }
    
    @Override
    public String getColumnName(int columnIndex){
        return colunas[columnIndex];
    }

    
}