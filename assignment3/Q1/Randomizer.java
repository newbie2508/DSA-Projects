/*
 * DO NOT EDIT THIS FILE
 */

import java.util.Random;

public class Randomizer {
    /*
    * An object randomizer of class Randomizer has been initialized in the SkipList class.
     It has a function binaryRandomGen, which returns true or false in a (pseudo) random manner.
      While inserting a node, you should initialize its height to 1, and using randomizer.binaryRandomGen(), 
      you should iteratively check whether its height should be increased. 
      If the node already has a height larger than the skip list height, 
      stop and do not call the binaryRandomGen() again. Using this procedure is necessary, 
      not adhering may cause inconsistency in output.
    */    
    private Random rnd;

    Randomizer(){
        rnd = new Random(106);
    }

    boolean binaryRandomGen(){
        return rnd.nextBoolean();
    }
}
