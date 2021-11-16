package WebUI.users;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ClientsList
{
    Map<String, Client> usersList;

    public ClientsList()
    {
        usersList = new TreeMap<>();
    }


    public boolean isExist(String username)
    {
        return usersList.containsKey(username);
    }

    public void addClient(Client newClient)
    {
        usersList.put(newClient.getUsername(),newClient);
    }

    public Client getClient(String username)
    {
        return usersList.get(username);
    }

    public Set<String> getUsernameList()
    {
        return usersList.keySet();
    }

    public void deleteClient(String userToDelete)
    {
        if(isExist(userToDelete))   usersList.remove(userToDelete);
    }
}
