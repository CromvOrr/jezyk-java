import java.util.*;

class WatkowyEksperymentator implements BadaczKostekDoGry {

    private int limitWatkow;
    private ThreadFactory fabryka;
    private Integer currentLimitWatkow = 0;
    private final Queue<Rzuty> queue = new LinkedList<>();
    private final Map<Integer, Map<Integer, Integer>> privateHistogram = new HashMap<>();

    @Override
    public void dozwolonaLiczbaDzialajacychWatkow(int limitWatkow) {
        this.limitWatkow = limitWatkow;
    }

    @Override
    public void fabrykaWatkow(ThreadFactory fabryka) {
        this.fabryka = fabryka;
    }

    @Override
    public int kostkaDoZbadania(KostkaDoGry kostka, int liczbaRzutow) {

        int identyfikator = kostka.hashCode();

        synchronized (currentLimitWatkow) {
            if (currentLimitWatkow < limitWatkow) {
                currentLimitWatkow++;
                Thread watek = fabryka.getThread(new Rzuty(identyfikator, kostka, liczbaRzutow));
                watek.start();
            } else {
                synchronized (queue) {
                    queue.add(new Rzuty(identyfikator, kostka, liczbaRzutow));
                }
            }
        }
        return identyfikator;
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

    class Rzuty implements Runnable {
        int currentLiczbaRzutow = 0;
        private final KostkaDoGry kostka;
        private final int identyfikator, liczbaRzutow;
        Map<Integer, Integer> currentHistogram = new HashMap<>();

        public Rzuty(int identyfikator, KostkaDoGry kostka, int liczbaRzutow) {
            this.identyfikator = identyfikator;
            this.kostka = kostka;
            this.liczbaRzutow = liczbaRzutow;
        }

        @Override
        public void run() {

            while (currentLiczbaRzutow < liczbaRzutow) {
                currentLiczbaRzutow++;
                int rzut = kostka.rzut();
                currentHistogram.put(rzut, currentHistogram.getOrDefault(rzut, 0) + 1);
            }

            synchronized (privateHistogram) {
                privateHistogram.put(identyfikator, currentHistogram);
            }

            synchronized (currentLimitWatkow) {
                if (currentLimitWatkow < limitWatkow - 1 && !queue.isEmpty()) {
                    synchronized (queue) {
                        Rzuty tmp = queue.poll();
                        Thread watek = fabryka.getThread(tmp);
                        watek.start();
                    }
                }
                currentLimitWatkow--;
            }
        }
    }
}