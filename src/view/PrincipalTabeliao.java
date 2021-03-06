/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.sql.SQLException;

import control.SolicitacaoTableControl;

import model.TabeliaoTableModel;

/**
 *
 * @author cleolaptop
 */
public class PrincipalTabeliao extends javax.swing.JFrame {

    /**
     * Creates new form PrincipalTabeliao
     * @throws java.sql.SQLException
     */
    public PrincipalTabeliao() throws SQLException {
        initComponents();
        
        tabelaSol.setModel(new TabeliaoTableModel());
        tabelaSol.addMouseListener(new SolicitacaoTableControl(this));
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel_AcessoTabeliao = new javax.swing.JPanel();
        jLabel_BemVindo = new javax.swing.JLabel();
        jButton_Logout = new javax.swing.JButton();
        jLabel_SolicitacoesDisponiveis = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelaSol = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PrincipalTabeliao");

        jLabel_BemVindo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel_BemVindo.setText("Bem vindo");

        jButton_Logout.setText("Logout");
        jButton_Logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_LogoutActionPerformed(evt);
            }
        });

        jLabel_SolicitacoesDisponiveis.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel_SolicitacoesDisponiveis.setText("Solicitações disponíveis:");

        javax.swing.GroupLayout jPanel_AcessoTabeliaoLayout = new javax.swing.GroupLayout(jPanel_AcessoTabeliao);
        jPanel_AcessoTabeliao.setLayout(jPanel_AcessoTabeliaoLayout);
        jPanel_AcessoTabeliaoLayout.setHorizontalGroup(
            jPanel_AcessoTabeliaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_AcessoTabeliaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_AcessoTabeliaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_AcessoTabeliaoLayout.createSequentialGroup()
                        .addComponent(jLabel_BemVindo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 304, Short.MAX_VALUE)
                        .addComponent(jButton_Logout))
                    .addGroup(jPanel_AcessoTabeliaoLayout.createSequentialGroup()
                        .addComponent(jLabel_SolicitacoesDisponiveis)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel_AcessoTabeliaoLayout.setVerticalGroup(
            jPanel_AcessoTabeliaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_AcessoTabeliaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_AcessoTabeliaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_BemVindo)
                    .addComponent(jButton_Logout))
                .addGap(18, 18, 18)
                .addComponent(jLabel_SolicitacoesDisponiveis)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        tabelaSol.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tabelaSol);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel_AcessoTabeliao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel_AcessoTabeliao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_LogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_LogoutActionPerformed
        // TODO add your handling code here:
        SolicitacaoTableControl.logoutBotao(this);
    }//GEN-LAST:event_jButton_LogoutActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_Logout;
    private javax.swing.JLabel jLabel_BemVindo;
    private javax.swing.JLabel jLabel_SolicitacoesDisponiveis;
    private javax.swing.JPanel jPanel_AcessoTabeliao;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tabelaSol;
    // End of variables declaration//GEN-END:variables
}
