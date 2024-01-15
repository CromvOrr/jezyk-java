public interface PlayerController {
    public void move(Direction direction) throws OnFire, Flooded, Wall, Exit;
}