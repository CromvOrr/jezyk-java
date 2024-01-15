import java.util.Map;
import java.util.Set;

public interface Statistics {

    public void sideLength(int length);

    public void addNumbers(Map<Integer, Set<Position>> numberPositions);

    public Map<Integer, Map<Integer, Integer>> neighbours(Position position, int maxDistanceSquared);
}