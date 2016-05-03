package edu.wctc.ajs.ajsmidtermapp.event;

import edu.wctc.ajs.ajsmidtermapp.exception.NullOrEmptyArgumentException;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Session Listener creates and destroys new sessions. keeps a session count as
 * well.
 *
 * @author Alyson
 */
public class SessionListener implements HttpSessionListener {

    private static int sessionOpenCount = 0;
    private static final String SESSION_CREATED = "Session created: ";
    private static final String SESSION_ENDED = "Session ended: ";

    //attributes
    private static final String SESSION_OPEN_COUNT = "sessionOpenCount";
    
    /**
     * Creates new session and increments the sessions counter.
     * @param se session event.
     */
    @Override
    public final void sessionCreated(HttpSessionEvent se) {
        if (se == null) {
            throw new NullOrEmptyArgumentException();
        } else {
            sessionOpenCount++;
            se.getSession().getServletContext().setAttribute(SESSION_OPEN_COUNT, SessionListener.sessionOpenCount);

            String sessID = se.getSession().getId();
            System.out.println(SESSION_CREATED + sessID);
        }
    }

    /**
     * Destroys a session and decrements the sessions counter.
     * @param se session event.
     */
    @Override
    public final void sessionDestroyed(HttpSessionEvent se) {
        if (se == null) {
            throw new NullOrEmptyArgumentException();
        } else {
            sessionOpenCount--;
            se.getSession().getServletContext().setAttribute(SESSION_OPEN_COUNT, SessionListener.sessionOpenCount);
            String sessID = se.getSession().getId();
            System.out.println(SESSION_ENDED + sessID);
        }
    }

}
