package dc_Tower;

import java.util.*;

public class Elevator {
    static final int TIME_PER_FLOOR = 500;
    private final int id;
    private int currentPos;
    private Direction direction;
  //  private Request request;
    //    private boolean withPerson;
    //   private boolean busy;
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

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

//    public boolean isWithPerson() {
//        return withPerson;
//    }

//    public void setWithPerson(boolean withPerson) {
//        this.withPerson = withPerson;
//    }

//    public Request getRequest() {
//        return request;
//    }
//

    public List<Integer> getDestinationFloors() {
        return new ArrayList<>(destinationFloors);
    }

    public void setRequest(Request request) {
        setDirection(request.getDirection());
        //       setBusy(true);
//        withPerson = getCurrentPos() == request.getCurrentFloor();
        startFloors.add(request.getCurrentFloor());
        Collections.sort(startFloors);
        destinationFloors.add(request.getDestinationFloor());
        destinationFloors.sort(Comparator.reverseOrder());
//        this.request = request;
    }

    public List<Integer> getStartFloors() {
        return new ArrayList<>(startFloors);
    }

//    public boolean isBusy() {
//        return busy;
//    }
//
//    public void setBusy(boolean busy) {
//        this.busy = busy;
//    }
//

    public int getNumberOfPassangers() {
        return numberOfPassangers;
    }

//    public void setNumberOfPassangers(int numberOfPassangers) {
//        this.numberOfPassangers = numberOfPassangers;
//    }
//
    public void travelOneStep() {
        if (direction == Direction.DOWN) {
            travelOneStepDown();

        } else {
            travelOneStepUp();
        }
    }

    private void travelOneStepDown() {
        if (!startFloors.isEmpty()) {             //empty elevator
            System.out.println(getId()+"one down empty");
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
        } else {                             //with passengers
            System.out.println(getId()+"one down with");
            if (this.currentPos == 0) {
                numberOfPassangers = 0;
                direction = Direction.UP;
                destinationFloors = new ArrayList<>(4);
            } else {
                moveElevator(0);
            }
        }
    }

    private void travelOneStepUp() {
        if(!startFloors.isEmpty()){          //empty elevator
            System.out.println(getId()+"one up empty");
            if(currentPos == 0){
                numberOfPassangers = destinationFloors.size();
                startFloors = new ArrayList<>(4);
            } else {
                moveElevator(0);
            }
        } else{                              //with passengers
            System.out.println(getId()+"one up with");
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

    private void moveElevator(int destination) {
        System.out.println(getId()+"move");
        if (currentPos < destination) {
            currentPos++;
        } else {
            currentPos--;
        }
    }

}
