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
        return new ArrayList<>(list); // Cópia defensiva;
    }
    // Adiciona uma nova cotação e mantém a lista com no máximo 140 linhas
    public void adicionarCotacao(OHLC ohlc) {
        list.add(0, ohlc); // Adiciona no início (mais recente)
        if (list.size() > 140) {
            list.remove(list.size() - 1); // Remove a mais antiga
        }
    }

    // Calcula a média móvel dos fechamentos para a janela especificada
    public double calcularMediaMovel(int janela) {
        if (list.size() < janela) {
            return 0.0; // Ou lance uma exceção, se preferir
        }
        double soma = 0.0;
        for (int i = 0; i < janela; i++) {
            soma += list.get(i).getFechamento();
        }
        return soma / janela;
    }
}