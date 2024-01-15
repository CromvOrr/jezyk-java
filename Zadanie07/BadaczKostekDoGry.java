import java.util.Map;

public interface BadaczKostekDoGry {

    public void dozwolonaLiczbaDzialajacychWatkow(int limitWatkow);

    public void fabrykaWatkow(ThreadFactory fabryka);

    public int kostkaDoZbadania(KostkaDoGry kostka, int liczbaRzutow);

    public boolean badanieKostkiZakonczono(int identyfikator);

    public Map<Integer, Integer> histogram(int identyfikator);
}