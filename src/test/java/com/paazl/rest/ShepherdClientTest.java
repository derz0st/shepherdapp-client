package com.paazl.rest;

import com.paazl.gui.GuiInterface;
import com.paazl.gui.OrderRequestListener;
import com.paazl.model.SheepDto;
import com.paazl.model.SheepOrderDto;
import com.paazl.service.ShepherdService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShepherdClientTest {
    @Captor
    private ArgumentCaptor<OrderRequestListener> listenerCaptor = ArgumentCaptor.forClass(OrderRequestListener.class);
    @Mock
    private ShepherdService shepherdService;
    @Mock
    private GuiInterface guiInterface;
    @InjectMocks
    private ShepherdClient subject;

    @Test
    public void shepherdClient_whenOrderSuccesfull_displayNumberOfCreatedSheeps() {
        SheepOrderDto sheepOrderDto = new SheepOrderDto();
        sheepOrderDto.setOrderedSheeps(Arrays.asList(new SheepDto(), new SheepDto()));

        when(shepherdService.orderSheeps(anyInt())).thenReturn(sheepOrderDto);

        verify(guiInterface).addOrderRequestListener(listenerCaptor.capture());

        OrderRequestListener orderRequestListener = listenerCaptor.getValue();
        orderRequestListener.orderRequest(2);

        verify(guiInterface).addServerFeedback("Number of sheep to order: 2.");
        verify(shepherdService).orderSheeps(2);
        verify(guiInterface).addServerFeedback("2 sheeps are successfully ordered.");
    }

    @Test
    public void shepherdClient_whenOrderNotSuccesfull_displayErrorMessage() {
        when(shepherdService.orderSheeps(anyInt())).thenThrow(new RuntimeException("Error Message"));

        verify(guiInterface).addOrderRequestListener(listenerCaptor.capture());

        OrderRequestListener orderRequestListener = listenerCaptor.getValue();
        orderRequestListener.orderRequest(2);

        verify(guiInterface).addServerFeedback("Number of sheep to order: 2.");
        verify(shepherdService).orderSheeps(2);
        verify(guiInterface).addServerFeedback("Error Message");
    }
}
