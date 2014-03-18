package com.snvent.core.http.route;

import com.snvent.core.http.dispatcher.factory.HttpDispatcherFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Constructed route for dispatching request
 *
 * @author vicont
 */
public class ConstructedRoute {

    /**
     * Raw route object
     */
    private Route route;

    /**
     * Built pattern for matching
     */
    private String builtPattern;

    /**
     * Available params list
     */
    private List<String> availableParams = new ArrayList<String>();

    /**
     * Constructor
     *
     * @param route Raw route
     */
    public ConstructedRoute(Route route) {
        Pattern pn = Pattern.compile(":(\\w+)");
        Matcher matcher = pn.matcher(route.getPattern());
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            availableParams.add(matcher.group(1));
            matcher.appendReplacement(sb, "(\\\\w+)");
        }
        matcher.appendTail(sb);

        this.builtPattern = sb.toString();
        this.route = route;
    }

    /**
     * Returns true if URI matches route pattern
     *
     * @param uri URI
     * @return true if, and only if, a prefix of the input sequence matches this matcher's pattern
     */
    public boolean matches(String uri) {
        Pattern pn = Pattern.compile(this.builtPattern);
        Matcher matcher = pn.matcher(uri);
        return matcher.lookingAt();
    }

    /**
     * Find params in URI
     *
     * @param uri URI
     * @return Params map
     */
    public Map<String, String> findParams(String uri) {
        Map<String, String> params = new HashMap<String, String>();

        Pattern pn = Pattern.compile(this.builtPattern);
        Matcher matcher = pn.matcher(uri);

        if (matcher.find()) {
            for (int i = 0; i < this.availableParams.size(); i++) {
                String paramName = this.availableParams.get(i);
                String paramValue = matcher.group(i + 1);
                params.put(paramName, paramValue);
            }
        }

        return params;
    }

    /**
     * Retrieve dispatcher factory class
     *
     * @return Dispatcher factory
     */
    public Class<? extends HttpDispatcherFactory> getDispatcherFactoryClass() {
        return this.route.getDispatcherFactoryClass();
    }

}
