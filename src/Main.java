import java.sql.*;
import java.text.SimpleDateFormat;

public class Main {
    public static void main(String[] args) {
        Connection connection = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            connection = DB.getConnection();
            st= connection.createStatement();
            rs = st.executeQuery("SELECT * FROM dollar_quotes_db.dolar ORDER BY Data ASC");
            SimpleDateFormat sdfInput = new SimpleDateFormat("yyyyMMddHHmmss");
            SimpleDateFormat sdfOutput = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            int rowCount = 0;
            while (rs.next()) {
                rowCount++;
                String dataStr = rs.getString("Data");
                String data = sdfOutput.format(sdfInput.parse(dataStr));
                double abertura = rs.getDouble("Abertura");
                double maxima = rs.getDouble("Máxima");
                double minima = rs.getDouble("Mínima");
                double fechamento = rs.getDouble("Fechamento");
                System.out.printf("Linha %d: %s | %.2f | %.2f | %.2f | %.2f%n",
                        rowCount, data, abertura, maxima, minima, fechamento);
            }
            System.out.println("Total de linhas processadas: " + rowCount);
        } catch (SQLException | java.text.ParseException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
            DB.closeConnection();
        }
    }
}