import java.util.List;

public interface MadSet {

    void setDistanceMeasure(DistanceMeasure measure) throws TooCloseException;

    void setMinDistanceAllowed(double minAllowed) throws TooCloseException;

    void addPoint(Point point) throws TooCloseException;

    List<Point> getPoints();

    List<Point> getSortedPoints(Point referencePoint);
}