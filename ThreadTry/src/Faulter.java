public class Faulter {
    String error_message;
    public Faulter(String error_message) {
        this.error_message = error_message;
    }

    public Exception fault() {
        if (this.error_message == "NullPointer") {
            return new NullPointerException();
        } else if (this.error_message == )
    }
}