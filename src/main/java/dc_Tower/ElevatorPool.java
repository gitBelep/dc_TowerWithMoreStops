package dc_Tower;

import java.util.*;

public class ElevatorPool {
    private final List<Elevator> elevators = new ArrayList<>(7);
    private final Queue<Request> requests = new LinkedList<>();
    private final List<Elevator> busyElevators = new LinkedList<>();

    public ElevatorPool() {
        for (int i = 0; i < 7; i++) {
            elevators.add(new Elevator(i));
        }
    }

    public Elevator getNearestFreeElevatorOrNull() {
        Elevator nearestFreeElevator = null;
//        if (requests.isEmpty()) {
//            return null;
//        }
        int personsStartFloor = requests.peek().getCurrentFloor();
        for (Elevator actual : elevators) {
            if ( (actual.getStartFloors().isEmpty() && actual.getDestinationFloors().isEmpty()) ||
                    (actual.getCurrentPos() == 0 && requests.peek().getDirection() == Direction.UP && actual.getNumberOfPassangers() < 5) || //max 5 person
                    (actual.getCurrentPos() != 0 &&
                            requests.peek().getDirection() == Direction.DOWN &&
                            actual.getDirection() == Direction.DOWN &&
                            actual.getNumberOfPassangers() < 5 &&
                            (actual.getCurrentPos() >= requests.peek().getCurrentFloor() || actual.getNumberOfPassangers() == 0) )) {
                if (nearestFreeElevator == null) {
                    nearestFreeElevator = actual;
                }
                if (Math.abs(actual.getCurrentPos() - personsStartFloor) < Math.abs(nearestFreeElevator.getCurrentPos() - personsStartFloor)) {
                    nearestFreeElevator = actual;
                }
            }
        }
        System.out.println("NFree:"+ nearestFreeElevator.getId()+", ePos"+nearestFreeElevator.getCurrentPos()+", emb db"+nearestFreeElevator.getNumberOfPassangers());
        return nearestFreeElevator;
    }

    public void liftPerson(Elevator elevator) {
//        System.out.println("set req");
        elevator.setRequest(requests.poll());
        busyElevators.add(elevator);
    }

    public void dispositionOfElevators() throws InterruptedException {
//        System.out.println("dispo");
        for (Iterator<Elevator> i = busyElevators.iterator(); i.hasNext(); ) {
            Elevator value = i.next();
            if (!value.getDestinationFloors().isEmpty() && !value.getStartFloors().isEmpty()) {
                value.travelOneStep();
            }else{
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
