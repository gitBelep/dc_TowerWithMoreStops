package dc_Tower;

import java.util.*;

public class Elevator {
    static final int TIME_PER_FLOOR = 1000;
    private final int id;
    private int currentPos;
    private Direction direction;
    private List<Integer> startFloors;
    private List<Integer> destinationFloors;
    private int numberOfPassangers;

    public Elevator(int id) {
        this.id = id;
        currentPos = 0;
        direction = Direction.UP;
        startFloors = new ArrayList<>(4);
        destinationFloors = new ArrayList<>(4);
    }


    public int getId() {
        return id;
    }

    public int getCurrentPos() {
        return currentPos;
    }

    public Direction getDirection() {
        return direction;
    }

    public List<Integer> getDestinationFloors() {
        return new ArrayList<>(destinationFloors);
    }

    public List<Integer> getStartFloors() {
        return new ArrayList<>(startFloors);
    }

    public int getNumberOfPassangers() {
        return numberOfPassangers;
    }

    public void setRequest(Request request) {
        direction = request.getDirection();
        startFloors.add(request.getCurrentFloor());
        startFloors.sort(Collections.reverseOrder());
        destinationFloors.add(request.getDestinationFloor());
        Collections.sort(destinationFloors);
    }

    public void travelOneStepOrCollectPassanger() {
        if (direction == Direction.DOWN) {
            travelOneStepDownOrCollectPassanger();
        } else {
            travelOneStepUpOrCollectPassanger();
        }
    }

    private void travelOneStepDownOrCollectPassanger() {
        if (!startFloors.isEmpty()) {               //passangers are still waiting
            if (currentPos == startFloors.get(0)) {
                for (Iterator<Integer> i = startFloors.iterator(); i.hasNext(); ) {
                    int value = i.next();
                    if (value == currentPos) {
                        i.remove();
                        numberOfPassangers++;
                    }
                }
            } else {
                moveElevator(startFloors.get(0));
            }
        } else {                                    //delivering passangers
            if (this.currentPos == 0) {
                numberOfPassangers = 0;
                direction = Direction.UP;
                destinationFloors = new ArrayList<>(4);
            } else {
                moveElevator(0);
            }
        }
    }

    private void travelOneStepUpOrCollectPassanger() {
        if (numberOfPassangers == 0) {
            if (currentPos == 0) {                  //everybody gets in
                numberOfPassangers = destinationFloors.size();
                startFloors = new ArrayList<>(4);
                moveElevator(destinationFloors.get(0));
            } else {
                moveElevator(0);
            }
        } else {                                    //with passengers
            if (!destinationFloors.isEmpty()) {
                if (currentPos == destinationFloors.get(0)) {
                    for (Iterator<Integer> i = destinationFloors.iterator(); i.hasNext(); ) {
                        int value = i.next();
                        if (value == currentPos) {
                            i.remove();
                            numberOfPassangers--;
                        }
                    }
                } else {
                    moveElevator(destinationFloors.get(0));
                }
            }
        }
    }

    private void moveElevator(int destination) {
        if (currentPos < destination) {
            currentPos++;
        } else {
            currentPos--;
        }
    }

}
