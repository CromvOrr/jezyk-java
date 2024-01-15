import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Eksperymentator {

    public void użyjKostki(KostkaDoGry kostka);

    public void czasJednegoEksperymentu(long czasEksperymentu);

    public Map<Integer, Double> szansaNaWyrzucenieOczek(int liczbaKostek);

    public double szansaNaWyrzucenieKolejno(List<Integer> sekwencja);

    public double szansaNaWyrzucenieWDowolnejKolejności(Set<Integer> oczka);
}