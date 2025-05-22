import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Connection connection = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            connection = DB.getConnection();
            st = connection.createStatement();
            rs = st.executeQuery("SELECT Data, Abertura, Máxima, Mínima, " +
                    "Fechamento FROM dollar_quotes_db.dolar ORDER BY Data DESC LIMIT 140");
            DateTimeFormatter sdfInput = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            DateTimeFormatter sdfOutput = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            List<OHLC> cotacoes = new ArrayList<>();
            int rowCount = 0;
            while (rs.next()) {
                rowCount++;
                String dataStr = rs.getString("Data");
                LocalDateTime localDateTime = LocalDateTime.parse(dataStr, sdfInput);
                double abertura = rs.getDouble("Abertura");
                double maxima = rs.getDouble("Máxima");
                double minima = rs.getDouble("Mínima");
                double fechamento = rs.getDouble("Fechamento");
                String formattedDate = localDateTime.format(sdfOutput);
                System.out.printf("Linha %d: %s | %.2f | %.2f | %.2f | %.2f%n",
                        rowCount, formattedDate, abertura, maxima, minima, fechamento);

                OHLC ohlc = new OHLC(localDateTime, abertura, maxima, minima, fechamento);
                cotacoes.add(ohlc);
                                            }
            System.out.println("Total de linhas processadas: " + rowCount);
            OHLCManager manager = new OHLCManager(cotacoes);
            double mediaMovel = manager.calcularMediaMovel(115);
            System.out.printf("Média móvel inicial (115 períodos): %.2f%n", mediaMovel);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
            DB.closeConnection();
        }
    }
}