package world;

import bee.Drone;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The queen's chamber is where the mating ritual between the queen and her
 * drones is conducted.  The drones will enter the chamber in order.
 * If the queen is ready and a drone is in here, the first drone will
 * be summoned and mate with the queen.  Otherwise the drone has to wait.
 * After a drone mates they perish, which is why there is no routine
 * for exiting (like with the worker bees and the flower field).
 *
 * @author Sean Strout @ RIT CS
 * @author Donald Craig
 * email: dwc9402@rit.edu
 */
public class QueensChamber {
    private ConcurrentLinkedQueue<Drone> droneQueue;
    private BeeHive hive;

    /**
     * Creates the Queen's Chamber.
     * Initially there are no drones present and Queen is not ready to mate.
     */
    public QueensChamber(){
        droneQueue = new ConcurrentLinkedQueue<>();
    }





}