public enum Direction {

    NORTH {
        @Override
        public Position step(Position currentPosition) {
            return new Position(currentPosition.col(), currentPosition.row() + 1);
        }
    },

    SOUTH {
        @Override
        public Position step(Position currentPosition) {
            return new Position(currentPosition.col(), currentPosition.row() - 1);
        }
    },

    EAST {
        @Override
        public Position step(Position currentPosition) {
            return new Position(currentPosition.col() + 1, currentPosition.row());
        }

    },

    WEST {
        @Override
        public Position step(Position currentPosition) {
            return new Position(currentPosition.col() - 1, currentPosition.row());
        }
    };

    abstract public Position step(Position currentPosition);
}