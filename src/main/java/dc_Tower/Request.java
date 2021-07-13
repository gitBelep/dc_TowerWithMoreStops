package dc_Tower;

public class Request {
    private final int currentFloor;
    private final int destinationFloor;
    private final Direction direction;

    public Request(int currentFloor, int destinationFloor) {
        checkData(currentFloor, destinationFloor);
        this.currentFloor = currentFloor;
        this.destinationFloor = destinationFloor;
        if (destinationFloor == 0) {
            this.direction = Direction.DOWN;
        } else {
            this.direction = Direction.UP;
        }
    }


    public int getCurrentFloor() {
        return currentFloor;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    private void checkData(int currentFloor, int destinationFloor) {
        if (currentFloor < 0 || 55 < currentFloor) {
            throw new ScanningException("Check card scanner");
        }
        if (destinationFloor < 0 || 55 < destinationFloor) {
            throw new ScanningException("Check ID card");
        }
        if (destinationFloor != 0 && currentFloor != 0) {
            throw new ScanningException("Invalid direction!");
        }
    }

}
