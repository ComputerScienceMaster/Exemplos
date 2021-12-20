package simple.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Bookstore extends javax.swing.JFrame {

    public Bookstore() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btnSaveBook = new javax.swing.JButton();
        txtNameBook = new javax.swing.JTextField();
        txtPriceBook = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtViewData = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Exemplo de inserção no banco:");

        btnSaveBook.setText("Clique neste botão para inserir");
        btnSaveBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveBookActionPerformed(evt);
            }
        });

        txtViewData.setColumns(20);
        txtViewData.setRows(5);
        jScrollPane1.setViewportView(txtViewData);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(176, 176, 176)
                .addComponent(btnSaveBook)
                .addContainerGap(176, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNameBook, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPriceBook, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(17, 17, 17))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNameBook, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPriceBook, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnSaveBook)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveBookActionPerformed
        String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://localhost/bookstore";
        String USER = "root";
        String PASS = "86111411";

        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = (Statement) conn.createStatement();
            String sql;
            sql = "INSERT INTO book (nameBook, priceBook) VALUES ('" + txtNameBook.getText() + "'," + txtPriceBook.getText() + ")";
            stmt.execute(sql);

            String sqlQuery = "SELECT * FROM book";
            ResultSet rs = stmt.executeQuery(sqlQuery);

            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                int idBook = rs.getInt("idBook");
                String nameBook = rs.getString("nameBook");
                float priceBook = rs.getFloat("priceBook");
                
                //Display values
                System.out.print("ID: " + idBook);
                System.out.print(", name: " + nameBook);
                System.out.print(", Price: " + priceBook);
                txtViewData.append("");
                txtViewData.append("id: " + idBook + "\n namebook: " + nameBook + "\n pricebook: " + priceBook + "\n\n\n");
            }
            //STEP 6: Clean-up environment
            rs.close();

            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnSaveBookActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Bookstore().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSaveBook;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtNameBook;
    private javax.swing.JTextField txtPriceBook;
    private javax.swing.JTextArea txtViewData;
    // End of variables declaration//GEN-END:variables
}
