/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clusterga;

import static clusterga.FitnessCalc.documentos;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.ml.distance.EuclideanDistance;

/**
 *
 * @author ucuenca
 */
public class data {
    // Calculate inidividuals fittness by comparing it to our candidate solution

    static double[][] clusters;
    static Utilities utilities = new Utilities();

    public data() {

    }

    public void generateClusters(Population population) {
        EuclideanDistance euclideandistance = new EuclideanDistance();
        double[][] asociaciones = new double[documentos.length][population.getIndividual(0).size()];
        for (int doc = 0; doc < documentos.length; doc++) {
            double mindist = 1000;
            double[] mascercano = new double[population.getIndividual(0).size()];
            for (int i = 0; i < population.size(); i++) {
                double[] cromosoma = new double[population.getIndividual(i).size()];
                for (int j = 0; j < population.getIndividual(i).size(); j++) {
                    cromosoma[j] = population.getIndividual(i).getGene(j);
                }
                double distan = euclideandistance.compute(documentos[doc], cromosoma);
                if (distan < mindist) {
                    mindist = distan;
                    mascercano = cromosoma;
                }

            }
            asociaciones[doc] = mascercano;
        }
        clusters = asociaciones;
    }

    public void getFinalClusters() {

        EuclideanDistance euclideandistance = new EuclideanDistance();
        ArrayList<double[]> finalClusters = new ArrayList<double[]>();
        finalClusters.add(clusters[0]);
        for (int k = 0; k < clusters.length; k++) {
            boolean existe = false;
            for (int i = 0; i < finalClusters.size(); i++) {
                //El centroide o uno muy cercano ya existe en la lista final
                if (euclideandistance.compute(clusters[k], finalClusters.get(i)) <3) {
                    existe = true;
                }
            }
            if (!existe) {
                finalClusters.add(clusters[k]);
            }
        }

        for (int t = 0; t < finalClusters.size(); t++) {
            System.out.println("Final clusters: " + utilities.arrayDoubleToString(finalClusters.get(t)));
        }
    }

    @Override
    public String toString() {

        String stringClusters = "";
        int i = 0;
        for (int k = 0; k < clusters.length; k++) {
            stringClusters += "\n Documento: " + i + " - Cluster: " + k + " -> ";
            for (int j = 0; j < clusters[k].length; j++) {
                stringClusters += " " + (int) clusters[k][j];
            }
            i++;
        }
        return stringClusters;
    }

}
