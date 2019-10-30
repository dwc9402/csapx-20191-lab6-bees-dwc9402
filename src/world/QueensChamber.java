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
    private boolean queenReady;
    private boolean canMate;
    private BeeHive hive;

    /**
     * Creates the Queen's Chamber.
     * Initially there are no drones present and Queen is not ready to mate.
     */
    public QueensChamber(){
        droneQueue = new ConcurrentLinkedQueue<>();
        queenReady = false;
        canMate =true;
    }

    /**
     * The fun (frustrating) routine.
     * A drone enters the chamber. The first thing you should display is:
     *
     * *QC* {bee} enters chamber
     *
     * The bees should be stored in some queue like collection. If the queen is
     * ready and this drone is at the front of the collection, they are allowed
     * to mate. Otherwise they must wait. The queen isn't into any of this kinky
     * multiple partner stuff so while she is mating with a drone, she is not
     * ready to mate again. When the drone leaves this method, display the message:
     *
     * *QC* {bee} leaves chamber
     * @param drone The drone bee entering the chamber
     */
    public synchronized void enterChamber(Drone drone){
        System.out.println("*QC* " + drone + " enters chamber");
        droneQueue.add(drone);
        while (!queenReady || !droneQueue.peek().equals(drone)){
            try{
                wait();
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        droneQueue.remove();
        queenReady = false;
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
            Drone drone = droneQueue.peek();
            this.queenReady = true;
            drone.setMated();
            System.out.println("*QC* Queen mates with " + drone);
            notifyAll();
        }
    }

    /**
     * At the end of the simulation the queen uses this routine repeatedly to
     * dismiss all the drones that were waiting to mate.
     */
    public synchronized void dismissDrone(){
        queenReady = true;
        notifyAll();
    }

    /**
     * Method to check if any drones are waiting in the chamber.
     * @return If there is still a drone waiting
     */
    public synchronized boolean hasDrone(){
        if (droneQueue.isEmpty()){
            return false;
        } else {
            return true;
        }
    }
}