import java.util.List;

public class TooCloseException extends Exception {

    private static final long serialVersionUID = 4304209154421503650L;
    private final List<Point> removedPoints;

    public TooCloseException(List<Point> removedPoints) {
        this.removedPoints = removedPoints;
    }

    public List<Point> removePoints() {
        return removedPoints;
    }
}