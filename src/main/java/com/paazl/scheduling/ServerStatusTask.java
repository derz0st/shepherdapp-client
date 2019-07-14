package com.paazl.scheduling;

import com.paazl.gui.GuiInterface;
import com.paazl.model.SheepStatusesDto;
import com.paazl.service.ShepherdService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Component
public class ServerStatusTask {
    private final ShepherdService shepherdService;
    private final GuiInterface guiInterface;

    public ServerStatusTask(ShepherdService shepherdService, GuiInterface guiInterface) {
        this.shepherdService = shepherdService;
        this.guiInterface = guiInterface;
    }

    @Scheduled(cron="${scheduling.server_status.cron}")
    public void getServerStatus() {
        try {
            SheepStatusesDto sheepStatuses = shepherdService.getSheepStatuses();
            BigInteger currentBalance = shepherdService.getCurrentBalance();

            guiInterface.addServerFeedback("Server status... " + LocalDateTime.now().toString()
                    + ", healthy sheeps: " + sheepStatuses.getNumberOfHealthySheep()
                    + ", dead sheeps: " + sheepStatuses.getNumberOfDeadSheep()
                    + ", balance: " + currentBalance.toString());
        } catch (ResourceAccessException e) {
            guiInterface.addServerFeedback("Can not access the server: " + e.getMessage());
        } catch (RuntimeException e) {
            guiInterface.addServerFeedback("Error during request the server: " + e.getMessage());
        }
    }

}
