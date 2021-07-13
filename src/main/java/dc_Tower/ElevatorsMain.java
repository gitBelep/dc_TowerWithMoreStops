package dc_Tower;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ElevatorsMain {

    public static void main(String[] args) throws InterruptedException {
        List<Request> requestList = fillRequestList();
        ElevatorPool pool = new ElevatorPool();
        int i = 0;

        while (LocalTime.now().isBefore(LocalTime.of(22, 01))) {
            while (!pool.getRequests().isEmpty()) {
                Elevator nearestElevator = pool.getNearestFreeElevatorOrNull();
                if (nearestElevator != null) {
                    pool.liftPerson(nearestElevator);
                } else {
                    break;
                }
            }
            pool.dispositionOfElevators();

            if (i < requestList.size()) {      //Requests are coming & they have to be stored
                pool.addRequest(requestList.get(i));
                i++;
            }
        }
    }

    private static List<Request> fillRequestList() {
        try {
            return List.of(
                    new Request(0, 5),
                    new Request(0, 3),
                    new Request(0, 5),
                    new Request(0, 40),
                    new Request(0, 15),
                    new Request(0, 14),
                    new Request(0, 14),
                    new Request(0, 6),
                    new Request(20, 0),
                    new Request(21, 0),
                    new Request(0, 16),
                    new Request(0, 30),
                    new Request(0, 17),
                    new Request(14, 0),
                    new Request(6, 0),
                    new Request(0, 16),
                    new Request(0, 30),
                    new Request(0, 17),
                    new Request(0, 41),
                    new Request(0, 15),
                    new Request(0, 13),
                    new Request(0, 40),
                    new Request(0, 2),
                    new Request(13, 0),
                    new Request(40, 0)
            );
        } catch (ScanningException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

}
