package world;

import bee.Drone;
import bee.Queen;

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

    /**
     *
     * @param drone The drone bee entering the chamber
     */
    public synchronized void enterChamber(Drone drone){
        System.out.println("*QC* " + drone + " enters chamber");
        droneQueue.add(drone);
        if(drone.equals(droneQueue.peek()) && hive.hasResources()){
            droneQueue.remove(drone);
            drone.setMated();
        }else{
            try {
                this.wait(Queen.MATE_TIME_MS);
                this.notify();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("*QC* " + drone + " leaves chamber");
    }

    /**
     * When the queen is ready, they will summon the next drone from the
     * collection (if at least one is there). The queen will mate with the
     * first drone and display a message:
     * *QC* Queen mates with {bee}
     *
     * It is the job of the queen if mating to notify all of the waiting
     * drones so that the first one can be selected since we can't control
     * which drone will unblock. Doing a notify will lead to deadlock if
     * the drone that unblocks is not the front one.
     */
    public synchronized void summonDrone(){
        if(!droneQueue.isEmpty()){
            Drone drone = droneQueue.poll();
            System.out.println("*QC* Queen mates with " + drone);
            droneQueue.notifyAll();
        }
    }

    /**
     * At the end of the simulation the queen uses this routine repeatedly to
     * dismiss all the drones that were waiting to mate.
     */
    public synchronized void dismissDrone(){
        for(Drone drone : droneQueue){
            droneQueue.remove(drone);
        }
    }

    /**
     *
     * @return If there is still a drone waiting
     */
    public synchronized boolean hasDrone(){
        if(!droneQueue.isEmpty()){
            return true;
        }else
            return false;
    }
}