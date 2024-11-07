package client.ui;

import client.domain.ClientController;

public interface ClientView {
    void showMessage(String message);
    void disconnectedFromServer();
    void setClientController(ClientController clientController);


}
