package cz.hvolkova.packageDelivery;

/**
 * Enumeration with possible types of entered commands in console
 *
 * @author Elena Hvolkova
 */
public enum ConsoleParameterEnum {
    NEW_PCG("-new"), NEW_PCG_SHORT("-n"),
    FILE_INITIAL("-init"), FILE_INITIAL_SHORT("-i"),
    FILE_FEES("-file"), FILE_FEES_SHORT("-f"),
    QUIT("-quit"), QUIT_SHORT("-q");

    private final String argument;

    ConsoleParameterEnum(String argument) {
        this.argument = argument;
    }

    public String getArgument() {
        return argument;
    }

    public boolean equalsArgument(String inputArgument) {
        return argument.equals(inputArgument);
    }
}
