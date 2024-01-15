import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

class LeniwyEksperymentator implements LeniwyBadaczKostekDoGry {

    private ExecutorService executorService;
    private final Map<Integer, Map<Integer, Integer>> privateHistogram = new HashMap<>();

    @Override
    public void fabrykaWatkow(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public int kostkaDoZbadania(KostkaDoGry kostka, int liczbaRzutow) {

        int id = kostka.hashCode();
        executorService.submit(new Rzuty(kostka, liczbaRzutow, id));
        return id;
    }

    @Override
    public boolean badanieKostkiZakonczono(int identyfikator) {
        synchronized (privateHistogram) {
            return privateHistogram.get(identyfikator) != null;
        }
    }

    @Override
    public Map<Integer, Integer> histogram(int identyfikator) {
        synchronized (privateHistogram) {
            return privateHistogram.get(identyfikator);
        }
    }

    class Rzuty implements Callable<Integer> {
        private KostkaDoGry kostka;
        private Map<Integer, Integer> currentHistogram = new HashMap<>();
        private int liczbaRzutow, identyfikator, currentLiczbaRzutow = 0;

        Rzuty(KostkaDoGry kostka, int liczbaRzutow, int identyfikator) {
            this.kostka = kostka;
            this.liczbaRzutow = liczbaRzutow;
            this.identyfikator = identyfikator;
        }

        @Override
        public Integer call() throws Exception {

            while (currentLiczbaRzutow < liczbaRzutow) {
                currentLiczbaRzutow++;
                int rzut = kostka.rzut();
                currentHistogram.put(rzut, currentHistogram.getOrDefault(rzut, 0) + 1);
            }

            synchronized (privateHistogram) {
                privateHistogram.put(identyfikator, currentHistogram);
            }

            return null;
        }
    }
}