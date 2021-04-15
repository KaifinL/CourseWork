package byow.lab13;
import org.junit.Assert;
import org.junit.Test;

public class lab13Tests {

    @Test
    public void test1() {
        MemoryGame module1 = new MemoryGame(30, 40, 239872);
        module1.flashSequence("I like you");
    }



}
