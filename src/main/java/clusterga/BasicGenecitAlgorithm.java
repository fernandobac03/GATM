/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clusterga;

/**
 *
 * @author ucuenca
 */
public class BasicGenecitAlgorithm {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        FitnessCalc.setSolution("1111000000000000000000000000000000000000000000000000000000001111");
        Population myPop = new Population(100, true);
        //se asocian los elementos al centroide mas cercano
        System.out.println("Poblacion Inicia: \n" + myPop.toString());
        data cluster = new data();
        
        int generationCount = 0;
        //while (myPop.getFittest().getFitness() < FitnessCalc.getMaxFitness()) {
        while (generationCount<100) {
            cluster.generateClusters(myPop);
            generationCount++;
            //System.out.println("Generation: " + generationCount + " Fittest: " + myPop.getFittest().getFitness());
            myPop = Algorithm.evolvePopulation(myPop);
        }
        //System.out.println("Solution found!");
        //System.out.println("Generation: " + generationCount);
        //System.out.println ("Genes:");
        //System.out.println(myPop.getFittest());
        
        cluster.getFinalClusters();
    }
}
