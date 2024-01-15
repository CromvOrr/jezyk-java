import java.util.Map;
import java.util.concurrent.ExecutorService;

public interface LeniwyBadaczKostekDoGry {

    public void fabrykaWatkow(ExecutorService executorService);

    public int kostkaDoZbadania(KostkaDoGry kostka, int liczbaRzutow);

    public boolean badanieKostkiZakonczono(int identyfikator);

    public Map<Integer, Integer> histogram(int identyfikator);
}