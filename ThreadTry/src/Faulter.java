public class Faulter {
    private String error_message;
    public Faulter(String error_message) {
        this.error_message = error_message;
    }

    public Exception fault() {
        if (this.error_message == null) {
            return new IllegalArgumentException();
        }
        if (this.error_message == "NullPointer") {
            return new NullPointerException();
        } else if (this.error_message == "ArrayIndexOutOfBounds") {
            return new ArrayIndexOutOfBoundsException();
        } else if (this.error_message == "ClassCast") {
            return new ClassCastException();
        }
        return new IllegalArgumentException();
    }
}
