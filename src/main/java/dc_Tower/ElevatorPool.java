package dc_Tower;

import java.util.*;

public class ElevatorPool {
    private final List<Elevator> elevators = new ArrayList<>(7);
    private final Queue<Request> requests = new LinkedList<>();
    private final Set<Elevator> busyElevators = new HashSet<>();

    public ElevatorPool() {
        for (int i = 0; i < 7; i++) {
            elevators.add(new Elevator(i));
        }
    }

    public Elevator getNearestFreeElevatorOrNull() {
        Elevator nearestFreeElevator = null;
        int personsStartFloor = requests.peek().getCurrentFloor();
        for (Elevator actual : elevators) {
            if ((actual.getStartFloors().isEmpty() && actual.getDestinationFloors().isEmpty()) ||
                    (actual.getCurrentPos() == 0 && requests.peek().getDirection() == Direction.UP && actual.getNumberOfPassangers() < 5) || //max 5 person
                    (actual.getCurrentPos() != 0 &&
                            requests.peek().getDirection() == Direction.DOWN &&
                            actual.getDirection() == Direction.DOWN &&
                            actual.getNumberOfPassangers() < 5 &&
                            actual.getCurrentPos() >= requests.peek().getCurrentFloor())) {
                if (nearestFreeElevator == null) {
                    nearestFreeElevator = actual;
                }
                if (Math.abs(actual.getCurrentPos() - personsStartFloor) < Math.abs(nearestFreeElevator.getCurrentPos() - personsStartFloor)) {
                    nearestFreeElevator = actual;
                }
            }
        }
        return nearestFreeElevator;
    }

    public void liftPerson(Elevator elevator) {
        elevator.setRequest(requests.poll());
        busyElevators.add(elevator);
    }

    public void dispositionOfElevators() throws InterruptedException {
        for (Iterator<Elevator> i = busyElevators.iterator(); i.hasNext(); ) {
            Elevator elevator = i.next();
            if (!elevator.getDestinationFloors().isEmpty() || !elevator.getStartFloors().isEmpty()) {
                elevator.travelOneStepOrCollectPassanger();
            } else {
                i.remove();
            }
        }
        Thread.sleep(Elevator.TIME_PER_FLOOR);
    }

    public void addRequest(Request request) {
        requests.offer(request);
    }

    public Queue<Request> getRequests() {
        return new LinkedList<>(requests);
    }

    public List<Elevator> getElevators() {
        return new ArrayList<>(elevators);
    }

}
