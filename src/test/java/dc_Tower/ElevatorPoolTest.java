package dc_Tower;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ElevatorPoolTest {
    private ElevatorPool pool;

    @BeforeEach
    void init() throws InterruptedException {
        pool = new ElevatorPool();
    }

    @Test
    void testAddThreeToTheSameElevator() throws InterruptedException {
        pool.addRequest(new Request(0, 3));
        pool.addRequest(new Request(0, 3));
        pool.addRequest(new Request(0, 5));

        assertEquals(0, pool.getElevators().get(0).getNumberOfPassangers());

        for (int i = 0; i < 8; i++) {
            while (!pool.getRequests().isEmpty()) {
                Elevator nearestElevator = pool.getNearestFreeElevatorOrNull();
                if (nearestElevator != null) {
                    pool.liftPerson(nearestElevator);
                } else {
                    break;
                }
            }

            if (i == 0) {

                assertEquals(0, pool.getElevators().get(0).getCurrentPos());

            }
            pool.dispositionOfElevators();
        }
        assertEquals(0, pool.getElevators().get(0).getNumberOfPassangers());
        assertEquals(5, pool.getElevators().get(0).getCurrentPos());
    }

    @Test
    void testNearestFreeElevatorTakesMorePassangers() throws InterruptedException {
        List<Request> r = List.of(
                new Request(0, 4), //I assume, passanger will be taken from elevator Nr:0
                new Request(0, 4), //1
                new Request(0, 2), //2
                new Request(0, 2), //3
                new Request(0, 2), //4
                new Request(0, 2), //5
                new Request(0, 2), //6
                new Request(7, 0), //0 !
                new Request(9, 0), //1 !
                new Request(2, 0), //2
                new Request(7, 0), //0 !
                new Request(0, 3)); //3

        for (int i = 0; i < 25; i++) {
            if (i < r.size()) {
                pool.addRequest(r.get(i));
            }
            while (!pool.getRequests().isEmpty()) {
                Elevator nearestElevator = pool.getNearestFreeElevatorOrNull();
                if (nearestElevator != null) {
                    pool.liftPerson(nearestElevator);
                } else {
                    break;
                }
            }
            pool.dispositionOfElevators();
        }

        List<Elevator> elev = pool.getElevators();
        assertEquals(0, elev.get(0).getCurrentPos());
        assertEquals(0, elev.get(1).getCurrentPos());
        assertEquals(0, elev.get(2).getCurrentPos());
        assertEquals(3, elev.get(3).getCurrentPos());
        assertEquals(2, elev.get(4).getCurrentPos());
        assertEquals(2, elev.get(5).getCurrentPos());
        assertEquals(2, elev.get(6).getCurrentPos());
    }

    @Test
    void testFalseDataForRequest() {
        ScanningException se1 = assertThrows(ScanningException.class, () -> new Request(-7, 15));
        assertEquals("Check card scanner", se1.getMessage());

        ScanningException se2 = assertThrows(ScanningException.class, () -> new Request(0, 91));
        assertEquals("Check ID card", se2.getMessage());

        ScanningException se3 = assertThrows(ScanningException.class, () -> new Request(4, 15));
        assertEquals("Invalid direction!", se3.getMessage());
    }

}
