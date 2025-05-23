import java.util.ArrayList;
import java.util.List;

public class OHLCManager {

    private List<OHLC> list;

    public OHLCManager(List<OHLC> list) {
        this.list = list;
    }

    public OHLCManager() {
    }

    public List<OHLC> getList() {
        return new ArrayList<>(list); // Defensive copy;
    }

    public double calculateMovingAverage(int janela) {
        if (list.size() < janela) {
            return 0.0;
        }
        double soma = 0.0;
        for (int i = 0; i < janela; i++) {
            soma += list.get(i).getClosing();
        }
        return soma / janela;
    }
}