package it.polimi.ingsw.model.devcard;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;

import java.util.ArrayList;

public class Production {
    private final String productionName;
    private ArrayList<Resource> input;
    private ArrayList<Resource> output;


    public Production(String productionName, ArrayList<Resource> input, ArrayList<Resource> output) {
        this.productionName = productionName;
        this.input = input;
        this.output = output;
    }

    public void setInput(ArrayList<Resource> input) {
        this.input = input;
    }

    //per non ripetere il codice si potrebbe passare direttamente l'array di risorse come argomento
    public boolean checkInputResource(Player player){

        boolean finded=false, goout=false;
        //creo una copia dell'array di required resources
        ArrayList<Resource> requirements = new ArrayList<>(input);

        //controllo una per una se le risorse richieste sono presenti nel warehouse
        //-> se disponibile la sostituisco con white resource (nell'array copia)

        //controllo first floor
        while (!finded && !goout) {
            for (Resource res : requirements){
                if (player.getPersonalBoard().getWarehouse().getFirstFloor().getStoredResource().isPresent()){
                    if (player.getPersonalBoard().getWarehouse().getFirstFloor().getStoredResource().get().getType().equals(res.getType())){
                        if (player.getPersonalBoard().getWarehouse().getFirstFloor().getStoredResource().get().getQnt() >= res.getQnt() && !finded) {
                            finded = true;
                            res.setType(ResourceType.WHITERESOURCE);
                            res.setQnt(0);
                        }
                    }
                }
            }
            goout=true;
        }

        finded=false;
        goout=false;

        //controllo second floor
        while (!finded && !goout) {
            for (Resource res : requirements){
                if (player.getPersonalBoard().getWarehouse().getSecondFloor().getStoredResource().isPresent()){
                    if (player.getPersonalBoard().getWarehouse().getSecondFloor().getStoredResource().get().getType().equals(res.getType())){
                        if (player.getPersonalBoard().getWarehouse().getSecondFloor().getStoredResource().get().getQnt() >= res.getQnt() && !finded) {
                            finded = true;
                            res.setType(ResourceType.WHITERESOURCE);
                            res.setQnt(0);
                        }
                    }
                }
            }
            goout=true;
        }

        finded=false;
        goout=false;

        //controllo third floor
        while (!finded && !goout) {
            for (Resource res : requirements){
                if (player.getPersonalBoard().getWarehouse().getThirdFloor().getStoredResource().isPresent()){
                    if (player.getPersonalBoard().getWarehouse().getThirdFloor().getStoredResource().get().getType().equals(res.getType())){
                        if (player.getPersonalBoard().getWarehouse().getThirdFloor().getStoredResource().get().getQnt() >= res.getQnt() && !finded) {
                            finded = true;
                            res.setType(ResourceType.WHITERESOURCE);
                            res.setQnt(0);
                        }
                    }
                }
            }
            goout=true;
        }

        finded=false;
        goout=false;

        //controllo strongbox
        while(!finded && !goout){
            for (Resource res : requirements){
                if (player.getPersonalBoard().getWarehouse().getStrongBox().checkAvailabilityStrongBox(res) && !finded){
                    finded = true;
                    res.setType(ResourceType.WHITERESOURCE);
                    res.setQnt(0);
                }
            }
            goout=true;
        }

        //se tutti gli elementi dell'array di copia sono white resource ritorna true
        return requirements.parallelStream()
                .allMatch(o -> o.getType().equals(ResourceType.WHITERESOURCE));

    }
}
