import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme; // Để hết lỗi StandardChartTheme
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import java.awt.Font; // Để hết lỗi Font
import java.awt.BorderLayout; // Để hết lỗi BorderLayout
import java.sql.*;
import javax.swing.table.DefaultTableModel;
// Nếu bạn để file DBContext ở package khác thì phải import nó vào đây
// Ví dụ: import database.DBContext;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Admin
 */
public class ThongKeFrame extends javax.swing.JFrame {

  public void loadBieuDoTop5() {
    // 1. Tạo bộ dữ liệu
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    try {
        // 2. Kết nối SQL (Đạt kiểm tra lại tên lớp DBContext của mình nhé)
        Connection conn = new DBContext().getConnection();

        // Truy vấn lấy Top 5 vật liệu có tổng giá trị cao nhất
        String sql = "SELECT TOP 5 tenNVL, (soLuong * donGia) AS ThanhTien "
                + "FROM NGUYEN_VAT_LIEU ORDER BY ThanhTien DESC";

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            // Thêm dữ liệu vào biểu đồ
            dataset.addValue(rs.getDouble("ThanhTien"), "Giá trị", rs.getString("tenNVL"));
        }
        conn.close();
    } catch (Exception e) {
        e.printStackTrace();
    }

    // 3. Tạo biểu đồ Bar Chart
    JFreeChart barChart = ChartFactory.createBarChart(
            "TOP 5 VẬT LIỆU GIÁ TRỊ CAO NHẤT", // Tiêu đề
            "Tên Vật Liệu", // Cột X
            "Tổng Tiền (VNĐ)", // Cột Y
            dataset,
            PlotOrientation.VERTICAL,
            false, true, false);

    // --- FIX LỖI FONT TIẾNG VIỆT ---
    StandardChartTheme chartTheme = (StandardChartTheme) StandardChartTheme.createJFreeTheme();
    chartTheme.setExtraLargeFont(new Font("Arial", Font.BOLD, 16)); 
    chartTheme.setLargeFont(new Font("Arial", Font.PLAIN, 13));      
    chartTheme.setRegularFont(new Font("Arial", Font.PLAIN, 12));    
    chartTheme.apply(barChart);

    // --- PHẦN QUAN TRỌNG: ĐỔI MÀU RIÊNG CHO TỪNG CỘT ---
    org.jfree.chart.plot.CategoryPlot plot = barChart.getCategoryPlot();
    
    // Tạo Renderer mới để ghi đè màu sắc cho từng cột giống hình mẫu Đạt muốn
    org.jfree.chart.renderer.category.BarRenderer renderer = new org.jfree.chart.renderer.category.BarRenderer() {
        @Override
        public java.awt.Paint getItemPaint(int row, int column) {
            // Mảng màu sắc đa dạng
            java.awt.Color[] colors = {
                new java.awt.Color(79, 129, 189),  // Xanh dương
                new java.awt.Color(155, 187, 89),  // Xanh lá
                new java.awt.Color(247, 150, 70),  // Cam
                new java.awt.Color(192, 80, 77),   // Đỏ
                new java.awt.Color(128, 100, 162)  // Tím
            };
            return colors[column % colors.length];
        }
    };

    plot.setRenderer(renderer); 
    plot.setBackgroundPaint(java.awt.Color.WHITE); // Đặt nền trắng cho sạch
    renderer.setShadowVisible(false); // Tắt bóng đổ để màu sắc rực rỡ hơn

    // 4. Hiển thị lên giao diện (Đạt kiểm tra tên jPanel1 của mình nhé)
    ChartPanel chartPanel = new ChartPanel(barChart);
    pnlBieuDo.removeAll(); // Nếu Đạt chưa đổi tên Panel thì sửa thành jPanel1.removeAll()
    pnlBieuDo.setLayout(new BorderLayout());
    pnlBieuDo.add(chartPanel, BorderLayout.CENTER);
    pnlBieuDo.validate(); 
}
public void loadTable() {
        DefaultTableModel model = (DefaultTableModel) tblThongKe.getModel();
        model.setRowCount(0); 
        try {
            Connection conn = new DBContext().getConnection();
            String sql = "SELECT maNVL, tenNVL, soLuong, donGia, (soLuong * donGia) AS ThanhTien FROM NGUYEN_VAT_LIEU";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Object[] row = {
                    rs.getString("maNVL"),
                    rs.getString("tenNVL"),
                    rs.getInt("soLuong"),
                    rs.getDouble("donGia"),
                    rs.getDouble("ThanhTien")
                };
                model.addRow(row);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Creates new form ThongKeFrame
     */
    public ThongKeFrame() {
        initComponents();
        loadBieuDoTop5(); // <--- Thêm vào đây để nó tự chạy khi mở Form
        loadTable();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        pnlBieuDo = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblThongKe = new javax.swing.JTable();
        btnThoat = new javax.swing.JButton();

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Thống kê");
        setBackground(new java.awt.Color(204, 204, 255));

        pnlBieuDo.setBackground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout pnlBieuDoLayout = new javax.swing.GroupLayout(pnlBieuDo);
        pnlBieuDo.setLayout(pnlBieuDoLayout);
        pnlBieuDoLayout.setHorizontalGroup(
            pnlBieuDoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 65, Short.MAX_VALUE)
        );
        pnlBieuDoLayout.setVerticalGroup(
            pnlBieuDoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 255));
        jLabel7.setText("THỐNG KÊ");

        tblThongKe.setBackground(new java.awt.Color(153, 255, 255));
        tblThongKe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "maNVL", "tenNVL", "soLuong", "donGia"
            }
        ));
        tblThongKe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblThongKeMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblThongKe);

        btnThoat.setBackground(new java.awt.Color(204, 255, 204));
        btnThoat.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnThoat.setText("Thoát");
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(556, 556, 556)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 14, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnThoat))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(293, 293, 293)
                        .addComponent(pnlBieuDo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(35, 35, 35)
                .addComponent(pnlBieuDo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnThoat)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed

        // 1. Khởi tạo lại màn hình chính (MainFrame)
        // Lưu ý: Đạt kiểm tra chính xác tên Class của màn hình trong ảnh là gì nhé
        MainFrame main = new MainFrame();

        // 2. Hiển thị màn hình chính lên
        main.setVisible(true);
        main.setLocationRelativeTo(null); // Để nó hiện giữa màn hình

        // 3. Đóng (giải phóng) cửa sổ hiện tại (ThongKeframe)
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnThoatActionPerformed

    private void tblThongKeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThongKeMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblThongKeMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ThongKeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ThongKeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ThongKeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ThongKeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ThongKeFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnThoat;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnlBieuDo;
    private javax.swing.JTable tblThongKe;
    // End of variables declaration//GEN-END:variables
}
