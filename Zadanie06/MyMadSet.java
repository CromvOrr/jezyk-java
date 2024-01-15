import java.util.*;

class MyMadSet implements MadSet {

    private double forbiddenDistance;
    private DistanceMeasure distanceMeasure;
    private final Set<Point> allPoints = new HashSet<>(), toBeRemoved = new HashSet<>();

    private static class pointComparator implements Comparator<Point> {
        private final Point referencePoint;
        private final DistanceMeasure newMeasurement;

        pointComparator(Point referencePoint, DistanceMeasure newMeasurement) {
            this.referencePoint = referencePoint;
            this.newMeasurement = newMeasurement;
        }

        @Override
        public int compare(Point a, Point b) {
            double distanceA = newMeasurement.distance(a, referencePoint);
            double distanceB = newMeasurement.distance(b, referencePoint);

            if (distanceA < distanceB) {
                return -1;
            } else if (distanceA > distanceB) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    @Override
    public void setDistanceMeasure(DistanceMeasure measure) throws TooCloseException {

        toBeRemoved.clear();
        distanceMeasure = measure;

        for (Point i : allPoints) {
            for (Point j : allPoints) {
                if (distanceMeasure.distance(i, j) <= forbiddenDistance && i != j) {
                    toBeRemoved.add(j);
                }
            }
        }

        for (Point i : toBeRemoved) {
            allPoints.remove(i);
        }

        if (!toBeRemoved.isEmpty()) {
            throw new TooCloseException(new ArrayList<>(toBeRemoved));
        }
    }

    @Override
    public void setMinDistanceAllowed(double minAllowed) throws TooCloseException {

        toBeRemoved.clear();
        forbiddenDistance = minAllowed;

        for (Point i : allPoints) {
            for (Point j : allPoints) {
                if (distanceMeasure.distance(i, j) <= forbiddenDistance && i != j) {
                    toBeRemoved.add(j);
                }
            }
        }

        for (Point i : toBeRemoved) {
            allPoints.remove(i);
        }

        if (!toBeRemoved.isEmpty()) {
            throw new TooCloseException(new ArrayList<>(toBeRemoved));
        }
    }

    @Override
    public void addPoint(Point point) throws TooCloseException {

        toBeRemoved.clear();
        allPoints.add(point);

        for (Point i : allPoints) {
            for (Point j : allPoints) {
                if (distanceMeasure.distance(i, j) <= forbiddenDistance && i != j) {
                    toBeRemoved.add(j);
                }
            }
        }

        for (Point i : toBeRemoved) {
            allPoints.remove(i);
        }

        if (!toBeRemoved.isEmpty()) {
            throw new TooCloseException(new ArrayList<>(toBeRemoved));
        }
    }

    @Override
    public List<Point> getPoints() {
        return new ArrayList<>(allPoints);
    }

    @Override
    public List<Point> getSortedPoints(Point referencePoint) {
        List<Point> sortedPoints = new ArrayList<>(allPoints);
        sortedPoints.sort(new pointComparator(referencePoint, distanceMeasure));
        return sortedPoints;
    }
}