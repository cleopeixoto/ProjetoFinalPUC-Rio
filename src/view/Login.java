/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import control.LoginControl;

/**
 *
 * @author cleolaptop
 */
public class Login extends javax.swing.JFrame {

    /**
     * Creates new form login
     */
    public Login() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel_AcessoPanel = new javax.swing.JPanel();
        jLabel_Acesso = new javax.swing.JLabel();
        jLabel_NomeDeUsuario = new javax.swing.JLabel();
        jLabel_Senha = new javax.swing.JLabel();
        login = new javax.swing.JTextField();
        senha = new javax.swing.JPasswordField();
        jButton_Send = new javax.swing.JButton();
        jPanel_CadastroPanel = new javax.swing.JPanel();
        jLabel_Cadastro = new javax.swing.JLabel();
        jButton_Cadastro = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");

        jPanel_AcessoPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel_AcessoPanel.setToolTipText("");

        jLabel_Acesso.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel_Acesso.setText("Página de Acesso");

        jLabel_NomeDeUsuario.setText("Login:");

        jLabel_Senha.setText("Senha:");

        login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginActionPerformed(evt);
            }
        });

        jButton_Send.setText("Realizar login");
        jButton_Send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SendActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_AcessoPanelLayout = new javax.swing.GroupLayout(jPanel_AcessoPanel);
        jPanel_AcessoPanel.setLayout(jPanel_AcessoPanelLayout);
        jPanel_AcessoPanelLayout.setHorizontalGroup(
            jPanel_AcessoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_AcessoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_AcessoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel_AcessoPanelLayout.createSequentialGroup()
                        .addComponent(jLabel_Senha)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(senha))
                    .addGroup(jPanel_AcessoPanelLayout.createSequentialGroup()
                        .addComponent(jLabel_NomeDeUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(login, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton_Send, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
            .addGroup(jPanel_AcessoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_AcessoPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel_Acesso)
                    .addContainerGap(62, Short.MAX_VALUE)))
        );
        jPanel_AcessoPanelLayout.setVerticalGroup(
            jPanel_AcessoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_AcessoPanelLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel_AcessoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_NomeDeUsuario)
                    .addComponent(login, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_AcessoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_Senha)
                    .addComponent(senha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton_Send)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel_AcessoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_AcessoPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel_Acesso)
                    .addContainerGap(102, Short.MAX_VALUE)))
        );

        jPanel_CadastroPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel_Cadastro.setText("Ainda não tem cadastro?");

        jButton_Cadastro.setText("Cadastre-se");
        jButton_Cadastro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_CadastroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_CadastroPanelLayout = new javax.swing.GroupLayout(jPanel_CadastroPanel);
        jPanel_CadastroPanel.setLayout(jPanel_CadastroPanelLayout);
        jPanel_CadastroPanelLayout.setHorizontalGroup(
            jPanel_CadastroPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_CadastroPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_CadastroPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_Cadastro)
                    .addComponent(jButton_Cadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel_CadastroPanelLayout.setVerticalGroup(
            jPanel_CadastroPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_CadastroPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_Cadastro)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_Cadastro)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel_AcessoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel_CadastroPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel_AcessoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel_CadastroPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel_AcessoPanel.getAccessibleContext().setAccessibleName("LoginForm");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    private void jButton_SendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SendActionPerformed
        try {
            LoginControl.realizaLogin(login.getText(), senha.getPassword(), this);
            //.realizaLogin(login.getText(), senha.getPassword(), this);
            
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jButton_SendActionPerformed

    private void loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_loginActionPerformed

    private void jButton_CadastroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_CadastroActionPerformed
        // TODO add your handling code here:
        //PrincipalControl.cadastroBotao(this);
        LoginControl.cadastroBotao(this);
    }//GEN-LAST:event_jButton_CadastroActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_Cadastro;
    private javax.swing.JButton jButton_Send;
    private javax.swing.JLabel jLabel_Acesso;
    private javax.swing.JLabel jLabel_Cadastro;
    private javax.swing.JLabel jLabel_NomeDeUsuario;
    private javax.swing.JLabel jLabel_Senha;
    private javax.swing.JPanel jPanel_AcessoPanel;
    private javax.swing.JPanel jPanel_CadastroPanel;
    private javax.swing.JTextField login;
    private javax.swing.JPasswordField senha;
    // End of variables declaration//GEN-END:variables
}
