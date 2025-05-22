import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OHLC {

    private LocalDateTime data;
    private double abertura;
    private double maxima;
    private double minima;
    private double fechamento;

    public OHLC(LocalDateTime data, double abertura, double maxima, double minima, double fechamento) {
        this.data = data;
        this.abertura = abertura;
        this.maxima = maxima;
        this.minima = minima;
        this.fechamento = fechamento;
    }
    OHLCManager ohlcManager = new OHLCManager(new ArrayList<>());

    public LocalDateTime getData() {
        return data;
    }

    public double getAbertura() {
        return abertura;
    }

    public double getMaxima() {
        return maxima;
    }

    public double getMinima() {
        return minima;
    }

    public double getFechamento() {
        return fechamento;
    }



}
