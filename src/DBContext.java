import java.sql.Connection;
import java.sql.DriverManager;

public class DBContext {
    public Connection getConnection() throws Exception {
        // Lưu ý: InstanceName là SQLEXPRESS
        String url = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyNVL;"
                + "instanceName=SQLEXPRESS;" // Quan trọng với bản Express
                + "encrypt=true;trustServerCertificate=true";
        
        String user = "sa";
        String pass = "123456"; // Mật khẩu bạn vừa đổi ở bước trên
        
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(url, user, pass);
    }

    public static void main(String[] args) {
        try {
            if (new DBContext().getConnection() != null) {
                System.out.println("Kết nối thành công tới SQLEXPRESS!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}