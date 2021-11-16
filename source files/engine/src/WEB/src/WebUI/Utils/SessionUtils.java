package WebUI.Utils;

import WebUI.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static WebUI.Constants.SYMBOL;

public class SessionUtils {

    public static String getUsername(HttpServletRequest request)
    {
        String ret = null;
        HttpSession session = request.getSession(false);

        if(session != null)
        {
            Object sessionAttribute = session.getAttribute(Constants.USERNAME);
            if(sessionAttribute != null) ret = sessionAttribute.toString();
        }

        return ret;
    }

    public static String getStockForTrade (HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Object sessionAttribute = session != null ? session.getAttribute(SYMBOL) : null;
        return sessionAttribute != null ? sessionAttribute.toString() : null;
    }

    public static void clearSession (HttpServletRequest request) {
        request.getSession().invalidate();
    }
}