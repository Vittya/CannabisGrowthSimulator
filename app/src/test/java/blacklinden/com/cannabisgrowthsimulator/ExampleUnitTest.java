package blacklinden.com.cannabisgrowthsimulator;

import org.junit.Test;



import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    float x = 0.1f;
    @Test
    public void addition_isCorrect() {
        assertEquals(25, sizeInGramsNormalizer(1));
    }


    private int sizeInGramsNormalizer(int sizeInGrams){

        return (20+5)+ (40/4 - (25)) * ((sizeInGrams - 1) / (18 - 1));
    }
}