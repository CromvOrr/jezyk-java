import java.util.*;

class WspanialyEksperymentator implements Eksperymentator {

    private KostkaDoGry cube;
    private long timeSet;

    @Override
    public void użyjKostki(KostkaDoGry kostka) {
        cube = kostka;
    }

    @Override
    public void czasJednegoEksperymentu(long czasEksperymentu) {
        timeSet = czasEksperymentu;
    }

    @Override
    public double szansaNaWyrzucenieWDowolnejKolejności(Set<Integer> oczka) {

        int val, successfulCounter = 0, generalCounter = 0;
        long start = System.currentTimeMillis();

        while (System.currentTimeMillis() - start < timeSet) {

            Set<Integer> iWasHere = new HashSet<Integer>();
            for (int i = 0; i < oczka.size(); i++) {
                val = cube.rzut();
                iWasHere.add(val);
            }

            if (iWasHere.equals(oczka)) {
                successfulCounter++;
            }
            generalCounter++;
        }
        return (double) successfulCounter / generalCounter;
    }

    @Override
    public double szansaNaWyrzucenieKolejno(List<Integer> sekwencja) {

        long start = System.currentTimeMillis();
        int val, successfulCounter = 0, generalCounter = 0;
        List<Integer> iWasHere = new ArrayList<>();

        while (System.currentTimeMillis() - start < timeSet) {

            for (int i = 0; i < sekwencja.size(); i++) {
                val = cube.rzut();
                iWasHere.add(val);
            }

            if (iWasHere.equals(sekwencja)) {
                successfulCounter++;
            }
            iWasHere.clear();
            generalCounter++;
        }
        return (double) successfulCounter / generalCounter;
    }

    @Override
    public Map<Integer, Double> szansaNaWyrzucenieOczek(int liczbaKostek) {

        long start = System.currentTimeMillis();
        int val, sum = 0, generalCounter = 0;
        int[] basicCounter = new int[(liczbaKostek * 6) + 1];
        Map<Integer, Double> iWasHere = new HashMap<>();

        while (System.currentTimeMillis() - start < timeSet) {

            for (int i = 0; i < liczbaKostek; i++) {
                val = cube.rzut();
                sum += val;
            }

            generalCounter++;
            basicCounter[sum]++;
            sum = 0;
        }

        for (int i = liczbaKostek; i < basicCounter.length; i++) {
            iWasHere.put(i, (double) basicCounter[i] / generalCounter);
        }
        return iWasHere;
    }
}