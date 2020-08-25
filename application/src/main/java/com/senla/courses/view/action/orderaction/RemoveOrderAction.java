package com.senla.courses.view.action.orderaction;

import com.lib.utils.ConsoleHelper;
import com.senla.courses.autoservice.controller.OrderController;

public class RemoveOrderAction extends AbstractOrderAction {

    public RemoveOrderAction(OrderController orderController) {
        super(orderController);
    }

    @Override
    public void execute() {
        int orderId;
        ConsoleHelper.writeMessage("Введите номер заказа:");
        orderId = Integer.parseInt(ConsoleHelper.readString());

        if (orderController.removeOrder(orderId) == 1) {
            ConsoleHelper.writeMessage(String.format("Заказ №%d успешно удален", orderId));
        } else {
            ConsoleHelper.writeMessage("При удалении заказа произошла ошибка");
        }
    }
}
