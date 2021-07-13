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
 //       Thread.sleep(1);    //other way "Elevator is null" because seems to be busy
    }

    @Test
    void testGetNearestFreeElevator1() throws InterruptedException {
        pool.addRequest(new Request(0, 2));
        pool.addRequest(new Request(0, 3));
        pool.liftPerson(pool.getNearestFreeElevatorOrNull());
        pool.liftPerson(pool.getNearestFreeElevatorOrNull());
        Thread.sleep(10_000);

        pool.addRequest(new Request(6, 0));
        pool.liftPerson(pool.getNearestFreeElevatorOrNull());   //id1 is the nearest, needs 1 sec to floor 6 then 6 sec to floor 0.
        Thread.sleep(8_000);

        List<Elevator> elevators = pool.getElevators();
        assertEquals(9, elevators.get(0).getCurrentPos());
        assertEquals(0, elevators.get(1).getCurrentPos());
    }

    @Test
    void testGetNearestFreeElevator() throws InterruptedException {
        pool.addRequest(new Request(0, 9));
        pool.addRequest(new Request(0, 5));
        pool.liftPerson(pool.getNearestFreeElevatorOrNull());    //id0 is on floor 9 & will be free in 9 sec
        pool.liftPerson(pool.getNearestFreeElevatorOrNull());    //id1 is on floor 5 & will be free in 5 sec
        Thread.sleep(10_000);

        pool.addRequest(new Request(6, 0));
        pool.liftPerson(pool.getNearestFreeElevatorOrNull());   //id1 is the nearest, needs 1 sec to floor 6 then 6 sec to floor 0.
        Thread.sleep(8_000);

        List<Elevator> elevators = pool.getElevators();
        assertEquals(9, elevators.get(0).getCurrentPos());
        assertEquals(0, elevators.get(1).getCurrentPos());
    }

    @Test
    void testFor8thRequestGetNoElevatorButNull() throws InterruptedException {
        pool.addRequest(new Request(0, 4));
        pool.addRequest(new Request(0, 5));
        pool.addRequest(new Request(0, 3));
        pool.addRequest(new Request(0, 4));
        pool.addRequest(new Request(0, 5));
        pool.addRequest(new Request(0, 1)); //id5 will be free in 1 sec
        pool.addRequest(new Request(0, 3));
        pool.liftPerson(pool.getNearestFreeElevatorOrNull());
        pool.liftPerson(pool.getNearestFreeElevatorOrNull());
        pool.liftPerson(pool.getNearestFreeElevatorOrNull());
        pool.liftPerson(pool.getNearestFreeElevatorOrNull());
        pool.liftPerson(pool.getNearestFreeElevatorOrNull());
        pool.liftPerson(pool.getNearestFreeElevatorOrNull());
        pool.liftPerson(pool.getNearestFreeElevatorOrNull());

        assertEquals(0, pool.getRequests().size());

        pool.addRequest(new Request(0, 2));

        assertEquals(1, pool.getRequests().size());
        assertNull(pool.getNearestFreeElevatorOrNull());    //no elevators are free

        Thread.sleep(2_000);

        pool.liftPerson(pool.getNearestFreeElevatorOrNull()); //id5 is free, needs 1 sec down & 2 sec to 2nd floor.

        Thread.sleep(4_000);

        List<Elevator> elevators = pool.getElevators();

        assertEquals(4, elevators.get(0).getCurrentPos());
        assertEquals(5, elevators.get(1).getCurrentPos());
        assertEquals(3, elevators.get(2).getCurrentPos());
        assertEquals(4, elevators.get(3).getCurrentPos());
        assertEquals(5, elevators.get(4).getCurrentPos());
        assertEquals(2, elevators.get(5).getCurrentPos());
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
