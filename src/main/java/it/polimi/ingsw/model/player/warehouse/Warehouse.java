package it.polimi.ingsw.model.player.warehouse;

import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

public class Warehouse {
    private FirstFloor firstFloor;
    private SecondFloor secondFloor;
    private ThirdFloor thirdFloor;
    private StrongBox strongBox;

    public Warehouse(FirstFloor firstFloor, SecondFloor secondFloor, ThirdFloor thirdFloor, StrongBox strongBox) {
        this.firstFloor = firstFloor;
        this.secondFloor = secondFloor;
        this.thirdFloor = thirdFloor;
        this.strongBox = strongBox;
    }

    public FirstFloor getFirstFloor() {
        return firstFloor;
    }

    public SecondFloor getSecondFloor() {
        return secondFloor;
    }

    public ThirdFloor getThirdFloor() {
        return thirdFloor;
    }

    public StrongBox getStrongBox() {
        return strongBox;
    }


    //serve un remove che rimuove le risorse quando servono al player
    //se la risorsa non è in uno dei floor controlla strongBox e la rimuove da li altrimenti lancia un eccezione

    public void removeResources(ArrayList<Resource> resourcesToRemove) {
       for (Resource res : resourcesToRemove) {
           if (getFirstFloor().getStoredResource().isPresent() && getFirstFloor().getStoredResource().get().getType().equals(res.getType())) {

                       if (res.getQnt()==1) {
                           getFirstFloor().setStoredResource(Optional.empty());
                       }
                       else {
                           res.setQnt(res.getQnt()-1);
                           if (getStrongBox().checkAvailabilityStrongBox(res)){
                               getStrongBox().removeResourceStrongBox(res);
                               getFirstFloor().setStoredResource(Optional.empty());
                           } else
                               throw new NoSuchElementException("Resource " + res.toString() + " not Available");
                       }

           }
           else if (getSecondFloor().getStoredResource().isPresent() && getSecondFloor().getStoredResource().get().getType().equals(res.getType())) {

                   if (getSecondFloor().getStoredResource().get().getQnt() == 1) {
                       if (res.getQnt()==1){
                           getSecondFloor().setStoredResource(Optional.empty());
                           res.setQnt(0);
                       }//a
                       else if (res.getQnt()>=2){
                           res.setQnt(res.getQnt()-1);
                           getSecondFloor().setStoredResource(Optional.empty());
                       }//b,c
                       else throw new RuntimeException();
                   } //1
                   else if(getSecondFloor().getStoredResource().get().getQnt() == 2){
                       if (res.getQnt()==1){
                           getSecondFloor().setStoredResource(res);
                           res.setQnt(0);
                       }//a
                       else if (res.getQnt()==2){
                           getSecondFloor().setStoredResource(Optional.empty());
                           res.setQnt(0);
                       }//b
                       else {
                           res.setQnt(res.getQnt()-2);
                           getSecondFloor().setStoredResource(Optional.empty());
                       }//c
                   } //2

                   if (getStrongBox().checkAvailabilityStrongBox(res)){
                       getStrongBox().removeResourceStrongBox(res);
                   } else
                       throw new NoSuchElementException("Resource " + res.toString() + " not Available");


           }
           else if (getThirdFloor().getStoredResource().isPresent() && getThirdFloor().getStoredResource().get().getType().equals(res.getType())) {


                   if (getThirdFloor().getStoredResource().get().getQnt()==1){
                       if (res.getQnt()==1){
                           getThirdFloor().setStoredResource(Optional.empty());
                           res.setQnt(0);
                       }
                       else if(res.getQnt()>=2){
                           res.setQnt(res.getQnt()-1);
                           getThirdFloor().setStoredResource(Optional.empty());
                       }
                       else throw new RuntimeException();
                   }//1
                   else if (getThirdFloor().getStoredResource().get().getQnt()==2){
                       if (res.getQnt()==1){
                           getThirdFloor().setStoredResource(res);
                           res.setQnt(0);
                       }
                       else if(res.getQnt()==2){
                           getThirdFloor().setStoredResource(Optional.empty());
                           res.setQnt(0);
                       }
                       else if (res.getQnt()>=3){
                           getThirdFloor().setStoredResource(Optional.empty());
                           res.setQnt(res.getQnt()-2);
                       }
                       else throw new RuntimeException();
                   }//2
                   else if (getThirdFloor().getStoredResource().get().getQnt()==3){
                       if (res.getQnt()==1){
                           res.setQnt(2);
                           getThirdFloor().setStoredResource(res);
                           res.setQnt(0);
                       }
                       else if (res.getQnt()==2){
                           res.setQnt(1);
                           Optional<Resource> tmp = Optional.of(new Resource(res.getQnt(), res.getType()));
                           getThirdFloor().setStoredResource(tmp);
                           res.setQnt(0);
                       }
                       else if (res.getQnt()>=3){
                           getThirdFloor().setStoredResource(Optional.empty());
                           res.setQnt(res.getQnt()-3);
                       }
                       else throw new RuntimeException();
                   }
                   if (getStrongBox().checkAvailabilityStrongBox(res)){
                       getStrongBox().removeResourceStrongBox(res);
                   } else
                       throw new NoSuchElementException("Resource " + res.toString() + " not Available");

           }
           else {
               if (getStrongBox().checkAvailabilityStrongBox(res)){
                   getStrongBox().removeResourceStrongBox(res);
               } else
                   throw new NoSuchElementException("Resource " + res.toString() + " not Available");
           }
        }
    }

    //aggiungi risorse dal mercato ai floor

    public void addResourcesToFloor(ArrayList<Resource> resourcesToAdd) {
        for (Resource res : resourcesToAdd) {
            if (firstFloor.checkCorrectPlacement(res)) {
                if (getFirstFloor().getStoredResource().isEmpty())
                    getFirstFloor().setStoredResource(res);
                else
                    getFirstFloor().getStoredResource().get().setQnt(getFirstFloor().getStoredResource().get().getQnt() + res.getQnt());
            }
            else if (secondFloor.checkCorrectPlacement(res)) {
                if (getSecondFloor().getStoredResource().isEmpty())
                    getSecondFloor().setStoredResource(res);
                else
                    getSecondFloor().getStoredResource().get().setQnt(getSecondFloor().getStoredResource().get().getQnt() + res.getQnt());
            } else if (thirdFloor.checkCorrectPlacement(res)) {
                if (getThirdFloor().getStoredResource().isEmpty()) {
                    getThirdFloor().setStoredResource(res);
                } else
                    getThirdFloor().getStoredResource().get().setQnt(getThirdFloor().getStoredResource().get().getQnt() + res.getQnt());
            }
        }
    }

}
