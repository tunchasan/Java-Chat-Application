package chat.app.Server;

import chat.app.Database.MessageDB;
import chat.app.Models.User;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {
    
    private User user;
    // Handles all commands redirection
    private ServerRouter router;
    
    ClientHandler(Socket socket) throws IOException {
        // Initialize user socket
        this.user = new User(socket);
        // Initialize server router
        this.router = new ServerRouter(user);
    }
    
    public ServerRouter getRouter() {
        return router;
    }
    
    public User getUser() {
        return user;
    }
    
    @Override
    public void run() {
         System.out.println(MessageManager.serverResponseFormatter("Connected: " + user.getSocket()));
             try {
                  // Set client name as unique
                  router.Route("/assignName");
                  // Now successful name has been chosen , we can start core process to manage client requests.
                  while(true) {
                      String receiver = user.getReader().nextLine();
                      
                      boolean loop = router.Route(receiver);
                      
                      if (loop == false)
                          break;
                  }
                  
             } catch (Exception e) {
                  System.out.println("Error:" + user.getSocket()); } 
                  
             finally {
             try {
                    user.getSocket().close();
                  } catch (Exception e) { }
                  System.out.println("Closed: " + user.getSocket());
         }         
    }  
}