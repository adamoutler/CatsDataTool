/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adamoutler.cats_data_extracter;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation class for Application parameters. This is called by the
 * applet or application launcher to create the startup parameters for the
 * given class.  /javafx-ui-common/src/com/sun/javafx/application/ParametersImpl.java
 */
public class AppParams {

    private List<String> rawArgs = new ArrayList<>();
    private Map<String, String> namedParams;
    private List<String> unnamedParams = new ArrayList<>();

    private List<String> readonlyRawArgs = null;
    private Map<String, String> readonlyNamedParams = null;
    private List<String> readonlyUnnamedParams = null;

  

    /**
     * Constructs an empty Parameters object.
     */
    public AppParams() {
        this.namedParams = new HashMap<>();
    }

    /**
     * Constructs an Parameters object from the specified list of arguments.
     * The list may be null.
     *
     * @param args list of command line arguments
     */
    public AppParams(List<String> args) {
        this.namedParams = new HashMap<>();
        if (args != null) {
            init(args);
        }
    }

    /**
     * Constructs an Parameters object from the specified array of unnamed
     * parameters. The array may be null.
     *
     * @param args array of command line arguments
     */
    public AppParams(String[] args) {
        this.namedParams = new HashMap<>();
        if (args != null) {
            init(Arrays.asList(args));
        }
    }

    /**
     * Constructs an Parameters object from the specified map of named
     * parameters.
     *
     * @param params a map of parameters from which to initialize this
     * object.
     * @param arguments args
     */
    public AppParams(Map params, String[] arguments) {
        this.namedParams = new HashMap<>();
        init(params, arguments);
    }

    /**
     * Initialize this Parameters object from the set of command line arguments.
     * Null elements are elided.
     *
     * @param args list of command line arguments
     */
    private void init(List<String>args) {
        for (String arg: args) {
            if (arg != null) {
                rawArgs.add(arg);
            }
        }
        computeNamedParams();
        computeUnnamedParams();
    }

    /**
     * Constructs an Parameters object from the specified map of named
     * parameters.
     *
     * @param params a map of parameters from which to initialize this
     * object.
     */
    private void init(Map params, String[] arguments) {
        for (Object key : params.keySet()) {
            if (validKey(key)) {
                Object value = params.get(key);
                if (value instanceof String) {
                    namedParams.put((String)key, (String)value);
                }
            }
        }

        computeRawArgs();
        if (arguments != null) {
            for (String arg : arguments) {
                unnamedParams.add(arg);
                rawArgs.add(arg);
            }
        }
    }

    /**
     * Validate the first character of a key. It is valid if it is a letter or
     * an "_" character.
     *
     * @param c the first char of a key string
     *
     * @return whether or not it is valid
     */
    private boolean validFirstChar(char c) {
        return Character.isLetter(c) || c == '_';
    }

    /**
     * Validate the key. A key is valid if it is a String object that starts
     * with a letter or "_" character and does not contain an "=" character.
     *
     * @param key Object representing a potential key
     *
     * @return true if key is a valid key, otherwise false
     */
    private boolean validKey(Object key) {
        if (key instanceof String) {
            String keyStr = (String)key;
            if (keyStr.length() > 0) {
                if (!keyStr.contains("=")) {
                    return validFirstChar(keyStr.charAt(0));
                }
            }
        }

        return false;
    }

    /**
     * Returns true if the specified string is a named parameter of the
     * form: --name=value
     *
     * @param arg the string to check
     *
     * @return true if the string matches the pattern for a named parameter.
     */
    private boolean isNamedParam(String arg) {
        if (arg.startsWith("--")) {
            return (arg.indexOf("=") > 2 && validFirstChar(arg.charAt(2)));
        } else {
            return false;
        }
    }

    /**
     * This method computes the list of unnamed parameters, by filtering the
     * list of raw arguments, stripping out the named parameters.
     */
    private void computeUnnamedParams() {
        for (String arg : rawArgs) {
            if (!isNamedParam(arg)) {
                unnamedParams.add(arg);
            }
        }
    }

    /**
     * This method parses the current array of raw arguments looking for
     * name,value pairs. These name,value pairs are then added to the map
     * for this parameters object, and are of the form: --name=value.
     */
    private void computeNamedParams() {
        for (String arg : rawArgs) {
            if (isNamedParam(arg)) {
                final int eqIdx = arg.indexOf("=");
                String key = arg.substring(2, eqIdx);
                String value = arg.substring(eqIdx + 1);
                namedParams.put(key, value);
            }
        }
    }

    /**
     * This method creates string representations of the name,value pairs in
     * the map for this Parameters object, and appends those strings to the
     * raw arguments array. The newly added strings are of the form:
     * "--name=value".
     */
    private void computeRawArgs() {
        ArrayList<String> keys = new ArrayList<>();
        keys.addAll(namedParams.keySet());
        Collections.sort(keys);
        for (String key : keys) {
            rawArgs.add("--" + key + "=" + namedParams.get(key));
        }
    }

  public List<String> getRaw() {
        if (readonlyRawArgs == null) {
            readonlyRawArgs = Collections.unmodifiableList(rawArgs);
        }
        return readonlyRawArgs;
    }

    public Map<String, String> getNamed() {
        if (readonlyNamedParams == null) {
            readonlyNamedParams = Collections.unmodifiableMap(namedParams);
        }
        return readonlyNamedParams;
    }

    public List<String> getUnnamed() {
        if (readonlyUnnamedParams == null) {
            readonlyUnnamedParams = Collections.unmodifiableList(unnamedParams);
        }
        return readonlyUnnamedParams;
    }



}
