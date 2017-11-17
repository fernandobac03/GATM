/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clusterga;

import static clusterga.data.clusters;

/**
 *
 * @author ucuenca
 */
public class Utilities {
    public double[] individual2double(Individual individual){
     double[] doubleindividual = new double[individual.size()];

      for (int i = 0; i < individual.size(); i++) {
            doubleindividual[i] = individual.getGene(i);
            
      }
      return doubleindividual;
    }
    
    public String arrayDoubleToString(double[] value)
    {
        String stringClusters = "";
        for (int k=0; k<value.length; k++) {
                    stringClusters+=" " + (int)value[k];    
        }
        return stringClusters;
    }
}
