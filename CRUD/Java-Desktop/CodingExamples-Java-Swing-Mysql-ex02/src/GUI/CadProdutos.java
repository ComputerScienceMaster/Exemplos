package GUI;

import DAO.ProdutoDAO;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Produto;

public class CadProdutos extends javax.swing.JFrame {

    public CadProdutos() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        LblTitulo = new javax.swing.JLabel();
        LblDes = new javax.swing.JLabel();
        LblQtd = new javax.swing.JLabel();
        LblPre = new javax.swing.JLabel();
        txtDescricaoProodutoCadastrar = new javax.swing.JTextField();
        txtQuantidadeProdutoCadastrar = new javax.swing.JTextField();
        txtPrecoProdutoCadastrar = new javax.swing.JTextField();
        BtnCadastrarProduto = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        LblTitulo1 = new javax.swing.JLabel();
        LblDes1 = new javax.swing.JLabel();
        LblQtd1 = new javax.swing.JLabel();
        LblPre1 = new javax.swing.JLabel();
        txtDescricaoProdutoAlterar = new javax.swing.JTextField();
        txtQuantidadeProdutoAlterar = new javax.swing.JTextField();
        txtPrecoProdutoAlterar = new javax.swing.JTextField();
        btnAlterarProduto = new javax.swing.JButton();
        LblDes3 = new javax.swing.JLabel();
        txtIdProdutoAlterar = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        LblTitulo2 = new javax.swing.JLabel();
        LblDes2 = new javax.swing.JLabel();
        txtIdParaExcluir = new javax.swing.JTextField();
        btnExcluirProduto = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProduto = new javax.swing.JTable();
        btnBuscarTodos = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        LblTitulo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        LblTitulo.setForeground(new java.awt.Color(51, 51, 255));
        LblTitulo.setText("Cadastro de Produtos");

        LblDes.setText("Descrição");

        LblQtd.setText("Quantidade");

        LblPre.setText("Preço");

        BtnCadastrarProduto.setText("Cadastrar");
        BtnCadastrarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCadastrarProdutoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(LblPre)
                        .addGap(51, 51, 51)
                        .addComponent(txtPrecoProdutoCadastrar))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LblQtd)
                            .addComponent(LblDes))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtQuantidadeProdutoCadastrar)
                            .addComponent(txtDescricaoProodutoCadastrar))))
                .addGap(84, 84, 84))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(223, 223, 223)
                .addComponent(BtnCadastrarProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(171, 171, 171)
                .addComponent(LblTitulo)
                .addContainerGap(270, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(LblTitulo)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LblDes)
                    .addComponent(txtDescricaoProodutoCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LblQtd)
                    .addComponent(txtQuantidadeProdutoCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(LblPre)
                    .addComponent(txtPrecoProdutoCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(BtnCadastrarProduto)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        LblTitulo1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        LblTitulo1.setForeground(new java.awt.Color(51, 51, 255));
        LblTitulo1.setText("Alterar Produto");

        LblDes1.setText("Descrição");

        LblQtd1.setText("Quantidade");

        LblPre1.setText("Preço");

        txtDescricaoProdutoAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescricaoProdutoAlterarActionPerformed(evt);
            }
        });

        btnAlterarProduto.setText("Alterar produto");
        btnAlterarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarProdutoActionPerformed(evt);
            }
        });

        LblDes3.setText("id");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(203, 203, 203)
                .addComponent(btnAlterarProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 293, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(191, 191, 191)
                .addComponent(LblTitulo1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(LblPre1)
                        .addGap(51, 51, 51)
                        .addComponent(txtPrecoProdutoAlterar))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LblQtd1)
                            .addComponent(LblDes1)
                            .addComponent(LblDes3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIdProdutoAlterar)
                            .addComponent(txtQuantidadeProdutoAlterar)
                            .addComponent(txtDescricaoProdutoAlterar))))
                .addGap(84, 84, 84))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LblTitulo1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LblDes3)
                    .addComponent(txtIdProdutoAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LblDes1)
                    .addComponent(txtDescricaoProdutoAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LblQtd1)
                    .addComponent(txtQuantidadeProdutoAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(LblPre1)
                    .addComponent(txtPrecoProdutoAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(btnAlterarProduto)
                .addContainerGap())
        );

        LblTitulo2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        LblTitulo2.setForeground(new java.awt.Color(51, 51, 255));
        LblTitulo2.setText("Excluir produto");

        LblDes2.setText("id do Produto");

        btnExcluirProduto.setText("Excluir");
        btnExcluirProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirProdutoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(215, 215, 215)
                .addComponent(btnExcluirProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LblDes2)
                .addGap(18, 18, 18)
                .addComponent(txtIdParaExcluir)
                .addGap(84, 84, 84))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(191, 191, 191)
                .addComponent(LblTitulo2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(LblTitulo2)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LblDes2)
                    .addComponent(txtIdParaExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnExcluirProduto)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblProduto.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblProduto);

        btnBuscarTodos.setText("Buscar");
        btnBuscarTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarTodosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane1))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(276, 276, 276)
                .addComponent(btnBuscarTodos)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(btnBuscarTodos)
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(223, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnCadastrarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCadastrarProdutoActionPerformed

        Produto prod = new Produto();
        ProdutoDAO dao = new ProdutoDAO();

        prod.setDescricao(txtDescricaoProodutoCadastrar.getText().trim());
        prod.setQtd(Integer.valueOf(txtQuantidadeProdutoCadastrar.getText()));
        prod.setPreco(Double.valueOf(txtPrecoProdutoCadastrar.getText()));

        if (dao.inserir(prod)) {
            JOptionPane.showMessageDialog(this, "Produto cadastrado com êxito");
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar o Produto.\n", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_BtnCadastrarProdutoActionPerformed

    private void btnAlterarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarProdutoActionPerformed
        Produto prod = new Produto();
        ProdutoDAO dao = new ProdutoDAO();
        prod.setId(Integer.parseInt(txtIdProdutoAlterar.getText()));
        prod.setDescricao(txtDescricaoProdutoAlterar.getText().trim());
        prod.setQtd(Integer.valueOf(txtQuantidadeProdutoAlterar.getText()));
        prod.setPreco(Double.valueOf(txtPrecoProdutoAlterar.getText()));

        if (dao.alterar(prod)) {
            JOptionPane.showMessageDialog(this, "Produto alterado com êxito");

        } else {
            JOptionPane.showMessageDialog(this, "Erro ao alterar o Produto.\n", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAlterarProdutoActionPerformed

    private void btnExcluirProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirProdutoActionPerformed
        ProdutoDAO dao = new ProdutoDAO();
        int idProdutoExcluir = Integer.parseInt(txtIdParaExcluir.getText());
        if (dao.excluir(idProdutoExcluir)) {
            JOptionPane.showMessageDialog(this, "Produto excluido com êxito");
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao excluir o Produto.\n", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnExcluirProdutoActionPerformed

    private void txtDescricaoProdutoAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescricaoProdutoAlterarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescricaoProdutoAlterarActionPerformed

    private void btnBuscarTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarTodosActionPerformed

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("id");
        modelo.addColumn("descricao");
        modelo.addColumn("quantidade");
        modelo.addColumn("preco");

        ProdutoDAO p = new ProdutoDAO();
        ArrayList<Produto> array = p.buscarTodos();

        for (Produto j : array) {
            System.out.println(j.getDescricao());
            Object[] dado = {j.getId(),j.getDescricao(), j.getQtd(), j.getPreco()};
            modelo.addRow(dado);
        }

        tblProduto.setModel(modelo);

    }//GEN-LAST:event_btnBuscarTodosActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CadProdutos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnCadastrarProduto;
    private javax.swing.JLabel LblDes;
    private javax.swing.JLabel LblDes1;
    private javax.swing.JLabel LblDes2;
    private javax.swing.JLabel LblDes3;
    private javax.swing.JLabel LblPre;
    private javax.swing.JLabel LblPre1;
    private javax.swing.JLabel LblQtd;
    private javax.swing.JLabel LblQtd1;
    private javax.swing.JLabel LblTitulo;
    private javax.swing.JLabel LblTitulo1;
    private javax.swing.JLabel LblTitulo2;
    private javax.swing.JButton btnAlterarProduto;
    private javax.swing.JButton btnBuscarTodos;
    private javax.swing.JButton btnExcluirProduto;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblProduto;
    private javax.swing.JTextField txtDescricaoProdutoAlterar;
    private javax.swing.JTextField txtDescricaoProodutoCadastrar;
    private javax.swing.JTextField txtIdParaExcluir;
    private javax.swing.JTextField txtIdProdutoAlterar;
    private javax.swing.JTextField txtPrecoProdutoAlterar;
    private javax.swing.JTextField txtPrecoProdutoCadastrar;
    private javax.swing.JTextField txtQuantidadeProdutoAlterar;
    private javax.swing.JTextField txtQuantidadeProdutoCadastrar;
    // End of variables declaration//GEN-END:variables
}
