import java.util.*;

class NumberStatistics implements Statistics {

    private int side;
    private Map<Integer, Set<Position>> numbersAdded;
    private final Map<Integer, Map<Integer, Integer>> result = new HashMap<>();

    @Override
    public void sideLength(int length) {
        side = length;
    }

    @Override
    public void addNumbers(Map<Integer, Set<Position>> numberPositions) {
        numbersAdded = new HashMap<>(numberPositions);
    }

    /**
     * * Poniższy algorytm znajduje kwadraty wszystkich odległości danego punktu od punktu X
     * ! ALE! uwzględia przy tym również wszystkie powtórzenia - dlatego nie przechodzi testów ://
     */

    /*
    @Override
    public Map<Integer, Map<Integer, Integer>> neighbours(Position position, int maxDistanceSquared) {

        int howManyCircles = 11;
        int distance;
        Map<Integer, Integer> howManyNumbersForSquare;
        Position positionCorrected;

        int[][] INITIAL = new int[side][side];
        int[][] POSITIONS;

        POSITIONS = new int[side + (howManyCircles * 2 * side)][side + (howManyCircles * 2 * side)];

        positionCorrected = new Position(position.col() - 1 + (side * howManyCircles), position.row() - 1 + (side * howManyCircles));

        Set<Position> positionsSet;
        for (Integer iterator : numbersAdded.keySet()) {
            positionsSet = numbersAdded.get(iterator);
            for (Position iterato : positionsSet) {
                INITIAL[iterato.col() - 1][iterato.row() - 1] = iterator;
            }
        }

        for (int i = 0; i < POSITIONS.length; i++) {
            for (int j = 0; j < POSITIONS.length; j++) {
                POSITIONS[i][j] = INITIAL[i % (INITIAL.length)][j % (INITIAL.length)];
            }
        }

        int max = Integer.MIN_VALUE;
        for (int[] ints : POSITIONS) {
            for (int anInt : ints) {
                if (anInt > max) {
                    max = anInt;
                }
            }
        }

        for (int k = 1; k <= max; k++) {
            howManyNumbersForSquare = new HashMap<>();
            for (int i = 0; i < POSITIONS.length; i++) {
                for (int j = 0; j < POSITIONS.length; j++) {
                    if (POSITIONS[i][j] == k) {
                        distance = ((positionCorrected.col() - i) * (positionCorrected.col() - i)) + ((positionCorrected.row() - j) * (positionCorrected.row() - j));
                        if (distance <= maxDistanceSquared && distance != 0) {
                            howManyNumbersForSquare.put(distance, howManyNumbersForSquare.getOrDefault(distance, 0) + 1);
                        }
                    }
                }
            }
            result.put(k, howManyNumbersForSquare);
        }
        return result;
    }
    */
    @Override
    public Map<Integer, Map<Integer, Integer>> neighbours(Position position, int maxDistanceSquared) {

        int[] distance = new int[9];
        Map<Integer, Integer> howManyNumbersForSquare;
        Set<Position> positionsSet;
        int colX = position.col();
        int rowX = position.row();

        for (Integer iterator : numbersAdded.keySet()) {

            positionsSet = numbersAdded.get(iterator);
            howManyNumbersForSquare = new HashMap<>();

            for (Position iterato : positionsSet) {

                int col = iterato.col();
                int row = iterato.row();

                distance[0] = ((colX - col - side) * (colX - col - side)) + ((rowX - row + side) * (rowX - row + side));
                distance[1] = ((colX - col) * (colX - col)) + ((rowX - row + side) * (rowX - row + side));
                distance[2] = ((colX - col + side) * (colX - col + side)) + ((rowX - row + side) * (rowX - row + side));
                distance[3] = ((colX - col - side) * (colX - col - side)) + ((rowX - row) * (rowX - row));
                distance[4] = ((colX - col) * (colX - col)) + ((rowX - row) * (rowX - row));
                distance[5] = ((colX - col + side) * (colX - col + side)) + ((rowX - row) * (rowX - row));
                distance[6] = ((colX - col - side) * (colX - col - side)) + ((rowX - row - side) * (rowX - row - side));
                distance[7] = ((colX - col) * (colX - col)) + ((rowX - row - side) * (rowX - row - side));
                distance[8] = ((colX - col + side) * (colX - col + side)) + ((rowX - row - side) * (rowX - row - side));

                int distanceMin = Arrays.stream(distance).min().getAsInt();

                if (distanceMin <= maxDistanceSquared) {
                    howManyNumbersForSquare.put(distanceMin, howManyNumbersForSquare.getOrDefault(distanceMin, 0) + 1);
                }
            }
            result.put(iterator, howManyNumbersForSquare);
        }
        return result;
    }
}