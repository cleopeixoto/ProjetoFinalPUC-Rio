package model;

import control.CertificadoTableControl;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Cleo Peixoto
 */
public class ClienteTableModel extends AbstractTableModel {
    private final ArrayList <Certificado> certificados;
    private final String[] colunas = {"ID Cert", "Versão", "Validade", "Emissor", "Status"};
    
    public ClienteTableModel () throws SQLException {
        certificados = CertificadoTableControl.obterCertificados();
    }
    
    public Certificado getCertificado (int rowIndex){
        return certificados.get(rowIndex);
    }

    @Override
    public int getRowCount() {
        return certificados.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }
    
    @Override
    public String getColumnName(int columnIndex){
        return colunas[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        switch(columnIndex){
            case 0:
                return certificados.get(rowIndex).getID();
            case 1:
                return certificados.get(rowIndex).getVersao();
            case 2:
                return certificados.get(rowIndex).getValidade();
            case 3:
                return certificados.get(rowIndex).getEmissor();
            case 4:
                return certificados.get(rowIndex).getStatus();
        }
        return null;
    }

}
