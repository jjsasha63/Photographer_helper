package red.com.pwh.exeption;

public class LocationNotFoundException extends IllegalArgumentException{

    public LocationNotFoundException() {
        super("The location wasn't found");
    }

    public LocationNotFoundException(String s) {
        super(s);
    }
}
