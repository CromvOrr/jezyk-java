import java.util.Set;
import java.util.HashSet;

class Indiana implements Explorer {

    private PlayerController controller;
    private int underwaterMoves;

    @Override
    public void underwaterMovesAllowed(int moves) {
        underwaterMoves = moves;
    }

    @Override
    public void setPlayerController(PlayerController controller) {
        this.controller = controller;
    }

    private enum myDirections {
        Forward {
            @Override
            public Direction getDirection(Direction direction) {
                return direction;
            }
        },
        Left {
            @Override
            public Direction getDirection(Direction direction) {
                switch (direction) {
                    case NORTH:
                        direction = Direction.WEST;
                        break;
                    case WEST:
                        direction = Direction.SOUTH;
                        break;
                    case SOUTH:
                        direction = Direction.EAST;
                        break;
                    case EAST:
                        direction = Direction.NORTH;
                }
                return direction;
            }
        },
        Right {
            @Override
            public Direction getDirection(Direction direction) {
                switch (direction) {
                    case NORTH:
                        direction = Direction.EAST;
                        break;
                    case EAST:
                        direction = Direction.SOUTH;
                        break;
                    case SOUTH:
                        direction = Direction.WEST;
                        break;
                    case WEST:
                        direction = Direction.NORTH;
                }
                return direction;
            }
        },
        Backward {
            @Override
            public Direction getDirection(Direction direction) {
                switch (direction) {
                    case EAST:
                        direction = Direction.WEST;
                        break;
                    case WEST:
                        direction = Direction.EAST;
                        break;
                    case NORTH:
                        direction = Direction.SOUTH;
                        break;
                    case SOUTH:
                        direction = Direction.NORTH;
                }
                return direction;
            }
        };

        abstract public Direction getDirection(Direction last_position);
    }

    private final Set<Position> visited = new HashSet<Position>(), dontGoThere = new HashSet<Position>();
    private boolean risk_water = false;

    private void retreat(Direction direction) {
        try {
            controller.move(myDirections.Backward.getDirection(direction));
        } catch (Exception e) {
        }
    }

    private boolean attemptMove(Position position, Direction direction, int currentUnderwater) {

        if (visited.contains(direction.step(position)) || dontGoThere.contains(direction.step(position))) {
            return false;
        }

        boolean visited_water = false;
        try {
            position = direction.step(position);
            visited.add(position);
            controller.move(direction);
        } catch (Wall e) {
            dontGoThere.add(position);
            return false;
        } catch (Flooded e) {
            visited_water = true;
            currentUnderwater++;
            if (!risk_water && currentUnderwater >= (underwaterMoves / 2)) {
                retreat(direction);
                return false;
            }
        } catch (OnFire e) {
            dontGoThere.add(position);
            retreat(direction);
            return false;
        } catch (Exit e) {
            return true;
        }
        if (!visited_water) {
            currentUnderwater = 0;
        }

        if (attemptMove(position, myDirections.Forward.getDirection(direction), currentUnderwater)) {
            return true;
        } else if (attemptMove(position, myDirections.Left.getDirection(direction), currentUnderwater)) {
            return true;
        } else if (attemptMove(position, myDirections.Right.getDirection(direction), currentUnderwater)) {
            return true;
        }

        retreat(direction);
        return false;
    }

    @Override
    public void findExit() {
        Position startingPosition = new Position(0, 0);
        if (attemptMove(startingPosition, Direction.NORTH, 0)) {
            return;
        } else if (attemptMove(startingPosition, Direction.EAST, 0)) {
            return;
        } else if (attemptMove(startingPosition, Direction.SOUTH, 0)) {
            return;
        } else if (attemptMove(startingPosition, Direction.WEST, 0)) {
            return;
        }

        risk_water = true;
        visited.clear();
        if (attemptMove(startingPosition, Direction.NORTH, 0)) {
            return;
        } else if (attemptMove(startingPosition, Direction.EAST, 0)) {
            return;
        } else if (attemptMove(startingPosition, Direction.SOUTH, 0)) {
            return;
        } else if (attemptMove(startingPosition, Direction.WEST, 0)) {
            return;
        }

    }
}