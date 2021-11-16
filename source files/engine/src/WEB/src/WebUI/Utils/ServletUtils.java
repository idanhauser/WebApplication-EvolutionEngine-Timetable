package WebUI.Utils;

import evolution.engine.EvolutionEngine;

import WebUI.users.ClientsList;
import chat.engine.ChatManager;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class ServletUtils {
    /*
    Note how the synchronization is done only on the question and\or creation of the relevant managers and once they exists -
    the actual fetch of them is remained un-synchronized for performance POV
     */
    private static final String CHAT_MANAGER_ATTRIBUTE_NAME = "chatManager";
    private static final Object listLock = new Object();
    private static final Object chatManagerLock = new Object();
    private static final Object ClientLock = new Object();
    private static final Object AllenginesTableLock = new Object();


    public static ClientsList getClientList(ServletContext servletContext)
    {
        ClientsList usersList;
        synchronized (listLock) {
            usersList = (ClientsList) servletContext.getAttribute("engine");
            if (usersList == null) {
                usersList = new ClientsList();
                servletContext.setAttribute("engine", usersList);
            }
        }
        return usersList;
    }

    public static EnginesList getAllEnginesList(ServletContext servletContext)
    {
        EnginesList enginesList;
        synchronized (AllenginesTableLock) {
            enginesList = (EnginesList) servletContext.getAttribute("AllEngines");
            if (enginesList == null) {
                enginesList = new EnginesList();
                servletContext.setAttribute("AllEngines", enginesList);
            }
        }
        return enginesList;
    }

 /*  public static WebUI.users.Client getClient(ServletContext servletContext) {

        synchronized (ClientLock) {
            if (servletContext.getAttribute("WebUI.users.Client") == null) {
                servletContext.setAttribute("WebUI.users.Client", new WebUI.users.Client());
            }
        }
        return (WebUI.users.Client) servletContext.getAttribute("WebUI.users.Client");
    }*/

    public static ChatManager getChatManager(ServletContext servletContext) {
        synchronized (chatManagerLock) {
            if (servletContext.getAttribute(CHAT_MANAGER_ATTRIBUTE_NAME) == null) {
                servletContext.setAttribute(CHAT_MANAGER_ATTRIBUTE_NAME, new ChatManager());
            }
        }
        return (ChatManager) servletContext.getAttribute(CHAT_MANAGER_ATTRIBUTE_NAME);
    }

    public static int getIntParameter(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException numberFormatException) {
            }
        }
        return Integer.MIN_VALUE;
    }


}
