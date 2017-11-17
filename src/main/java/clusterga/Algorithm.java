/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clusterga;

import org.apache.commons.math3.ml.distance.EuclideanDistance;

/**
 *
 * @author ucuenca
 */
public class Algorithm {

    /* GA parameters */
    private static final double uniformRate = 0.5;
    private static final double mutationRate = 0.015;
    private static final int tournamentSize = 2;
    private static final boolean elitism = false;
    public static Utilities utilities = new Utilities();

    /* Public methods */
    // Evolve a population
    public static Population evolvePopulation(Population pop) {
        Population newPopulation = new Population(pop.size(), false);

        // Keep our best individual
        if (elitism) {
            newPopulation.saveIndividual(0, pop.getFittest());
        }

        // Crossover population
        int elitismOffset;
        if (elitism) {
            elitismOffset = 1;
        } else {
            elitismOffset = 0;
        }
        // Loop over the population size and create new individuals with
        // crossover
        for (int i = elitismOffset; i < pop.size(); i++) {
            Individual indiv1 = tournamentSelection(pop);
            Individual indiv2 = neighbourSelection(indiv1, pop);
            Individual newIndiv = crossover(indiv1, indiv2);
            newPopulation.saveIndividual(i, newIndiv);
        }

        // Mutate population
        for (int i = elitismOffset; i < newPopulation.size(); i++) {
            mutate(newPopulation.getIndividual(i));
        }

        return newPopulation;
    }

    // Crossover individuals
    private static Individual crossover(Individual indiv1, Individual indiv2) {
        EuclideanDistance distance = new EuclideanDistance();
        double[] cromosoma1 = new double[indiv1.size()];
        double[] cromosoma2 = new double[indiv2.size()];

        for (int i = 0; i < indiv1.size(); i++) {
            cromosoma1[i] = indiv1.getGene(i);
            cromosoma2[i] = indiv2.getGene(i);

        }
        Individual newSol = new Individual();
        if (distance.compute(cromosoma1, cromosoma2) < 3) {
            int cutpoint = indiv1.size() / 2;
            for (int i = 0; i < cutpoint; i++) {
                newSol.setGene(i, indiv2.getGene(i));
            }
            for (int i = cutpoint; i < indiv1.size(); i++) {
                newSol.setGene(i, indiv1.getGene(i));
            }
        }
        return newSol;

//        Individual newSol = new Individual();
//        // Loop through genes
//        
//        for (int i = 0; i < indiv1.size(); i++) {
//            // Crossover
//            if (Math.random() <= uniformRate) {
//                newSol.setGene(i, indiv1.getGene(i));
//            } else {
//                newSol.setGene(i, indiv2.getGene(i));
//            }
//        }
//        return newSol;
    }

    // Mutate an individual
    private static void mutate(Individual indiv) {
        // Loop through genes
        for (int i = 0; i < indiv.size(); i++) {
            if (Math.random() <= mutationRate) {
                // Create random gene
                byte gene = (byte) Math.round(Math.random());
                indiv.setGene(i, gene);
            }
        }
    }

    // Select individuals for crossover
    private static Individual tournamentSelection(Population pop) {
        // Create a tournament population
        Population tournament = new Population(tournamentSize, false);
        // For each place in the tournament get a random individual
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.size());
            tournament.saveIndividual(i, pop.getIndividual(randomId));
        }
        // Get the fittest
        Individual fittest = tournament.getFittest();
        return fittest;
    }

    //Select second individual for crossover (will be a neighbour)
    private static Individual neighbourSelection(Individual firstindividual, Population pop) {
        // Create a tournament population
        EuclideanDistance euclideandistance = new EuclideanDistance();
        
        double mindistance = euclideandistance.compute(utilities.individual2double(firstindividual), utilities.individual2double(pop.getIndividual(0)));
        int neighbourposition=0;
        for (int i = 0; i < pop.size(); i++) {
            double calcdistance=euclideandistance.compute(utilities.individual2double(firstindividual), utilities.individual2double(pop.getIndividual(i)));
            if (calcdistance < mindistance) {
                mindistance= calcdistance;
                neighbourposition=i;
            }
        }
        // Get the fittest
        return pop.getIndividual(neighbourposition);
    }

}
