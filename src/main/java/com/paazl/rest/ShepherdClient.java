package com.paazl.rest;

import com.paazl.gui.GuiInterface;
import com.paazl.model.SheepOrderDto;
import com.paazl.service.ShepherdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShepherdClient {
    private ShepherdService shepherdService;

    @Autowired
    public ShepherdClient(ShepherdService shepherdService, GuiInterface guiInterface) {
        this.shepherdService = shepherdService;

        guiInterface.addOrderRequestListener(numberOfSheeps -> {
            guiInterface.addServerFeedback("Number of sheep to order: " + numberOfSheeps + ".");
            String messageToDisplay = makeOrder(numberOfSheeps);
            guiInterface.addServerFeedback(messageToDisplay);
        });
    }

    private String makeOrder(int numberOfSheeps) {
        String messageToDisplay;
        try {
            SheepOrderDto orderDto = shepherdService.orderSheeps(numberOfSheeps);
            messageToDisplay = orderDto.getOrderedSheeps().size() + " sheeps are successfully ordered.";
        } catch (RuntimeException e) {
            messageToDisplay = e.getMessage();
        }
        return messageToDisplay;
    }

}
