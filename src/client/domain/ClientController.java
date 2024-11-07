package client.domain;

import client.ui.ClientView;
import server.domain.ServerController;

public class ClientController {
    private boolean connected;
    private String name;
    private ClientView clientView;
    private ServerController serverController;

    public ClientController(ClientView clientView, ServerController serverController) {
        this.clientView = clientView;
        this.serverController = serverController;
        clientView.setClientController(this);
    }
    public boolean connectToServer(String name) {
        this.name = name;
        if (serverController.connectUser(this)) {
            showOnWindow("вы успешно подключились!\n");
            connected = true;
            String log = serverController.getHistory();
            if (log != null) {
                showOnWindow(log);
            }
            return true;
        } else {
            showOnWindow("подключения не удалось");
            return false;
        }
    }
    public void answerFromServer(String text) {
        showOnWindow(text);}

    public void disconnectedFromServer() {
        if (connected) {
            connected = false;
            clientView.disconnectedFromServer();
            showOnWindow("вы были отключены от сервера!");
        }
    }
    public void disconnectServer(){serverController.disconnectUser(this);
    }

    public void message(String text){
        if (connected) {
            if(!text.isEmpty()){
                serverController.message(name+": "+ text);
            }
        }else {
            showOnWindow("нет подключения к серверу");
        }

    }
    public void showOnWindow(String text){
        clientView.showMessage(text + "\n");
    }
    public String getName(){
        return name;
    }
}


