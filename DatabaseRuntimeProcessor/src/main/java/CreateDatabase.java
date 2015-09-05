/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
/**
 *
 * @author JoséAlberto
 */
public class CreateDatabase {
    String keyword, 
            name;
    FileWriter prueba = null;
    FileWriter plan = null;
        PrintWriter pw = null;
    
    public CreateDatabase(String[] database){
        keyword = database[0] + " " + database[1];
        name = database[2];
    }
    public CreateDatabase(){
        
    }
    
    public void verifyExist(){
        //Recorrer algo para ver si existe se pondría en prueba
        boolean valor = true;
        if (valor){
            ejecutionPlan();
            incorporate();
        }else{
            System.out.println("La base de datos ya existe,verifique el nombre");
        }
    }
    
    public void ejecutionPlan(){
                try
        {
            plan = new FileWriter("C:/Users/JoséAlberto/Desktop/TemporalURSQL/plan.txt");
            pw = new PrintWriter(plan);
            System.out.print("Se crea un nuevo diagrama de bases de datos llamado:" + 
                    name + "que se debe guardar");
            pw.println("Se crea un nuevo diagrama de bases de datos llamado:" + 
                    name + " que se debe guardar");
 
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el prueba.
           if (null != plan)
              plan.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    }
    
    public void incorporate(){
        // la incorpora al motor y la guarda donde haya que guardarla
        
        try
        {
            prueba = new FileWriter("C:/Users/JoséAlberto/Desktop/TemporalURSQL/prueba.txt");
            pw = new PrintWriter(prueba);
            System.out.print(keyword +" "+ name);
            pw.println(keyword +" "+ name);
 
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el prueba.
           if (null != prueba)
              prueba.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    }
}
