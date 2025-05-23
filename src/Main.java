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
            rs = st.executeQuery("SELECT Date, Open, Highest, Lowest, " +
                    "Closing FROM dollar_quotes_db.dolar ORDER BY Date DESC");
            DateTimeFormatter sdfInput = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            DateTimeFormatter sdfOutput = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            List<OHLC> quotes = new ArrayList<>();
            int rowCount = 0;
            while (rs.next()) {
                rowCount++;
                String dateStr = rs.getString("Date");
                LocalDateTime localDateTime = LocalDateTime.parse(dateStr, sdfInput);
                double Open = rs.getDouble("Open");
                double Highest = rs.getDouble("Highest");
                double Lowest = rs.getDouble("Lowest");
                double Closing = rs.getDouble("Closing");
                String formattedDate = localDateTime.format(sdfOutput);
                System.out.printf("Line %d: %s | %.2f | %.2f | %.2f | %.2f%n",
                        rowCount, formattedDate, Open, Highest, Lowest, Closing);

                OHLC ohlc = new OHLC(localDateTime, Open, Highest, Lowest, Closing);
                quotes.add(ohlc);
                                            }
            System.out.println("Total line processed: " + rowCount);
            OHLCManager manager = new OHLCManager(quotes);
            double movingAverage = manager.calculateMovingAverage(115);
            System.out.printf("Moving Average (115 period): %.2f%n", movingAverage);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
            DB.closeConnection();
        }
    }
}