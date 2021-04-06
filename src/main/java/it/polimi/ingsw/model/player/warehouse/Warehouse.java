package it.polimi.ingsw.model.player.warehouse;

import it.polimi.ingsw.model.resources.Resource;

import java.util.ArrayList;
import java.util.NoSuchElementException;

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
           if (getFirstFloor().getStoredResource().get().getType().equals(res.getType())) {
               if (getFirstFloor().getStoredResource().get().getQnt() > 1) {
                   if (getStrongBox().checkAvailabilityStrongBox(res))
                       getStrongBox().removeResourceStrongBox(res);
                   else
                       throw new NoSuchElementException("Resource not Available");
               }
               else getFirstFloor().getStoredResource().empty();
           }
           if (getSecondFloor().getStoredResource().get().getType().equals(res.getType())) {
               if (getSecondFloor().getStoredResource().get().getQnt() < res.getQnt()) {
                   if (getStrongBox().checkAvailabilityStrongBox(res))
                       getStrongBox().removeResourceStrongBox(res);
                   else
                       throw new NoSuchElementException("Resource not Available");
               }
               else getSecondFloor().getStoredResource().get().setQnt(getSecondFloor().getStoredResource().get().getQnt() - res.getQnt());
           }
           if (getThirdFloor().getStoredResource().get().getType().equals(res.getType())) {
               if (getThirdFloor().getStoredResource().get().getQnt() < res.getQnt()) {
                   if (getStrongBox().checkAvailabilityStrongBox(res))
                       getStrongBox().removeResourceStrongBox(res);
                   else
                       throw new NoSuchElementException("Resource not Available");
               }
               else getThirdFloor().getStoredResource().get().setQnt(getSecondFloor().getStoredResource().get().getQnt() - res.getQnt());;
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
