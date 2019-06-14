package utility;

import java.util.Random;

public class RandomNumberGenerator {

    public static int generateRandomNumber(int min, int max){
        int result = 0;

        Random rand = new Random();
        if(min<max) {
            result = rand.nextInt(max) % (max - min) + min;
        }else{
            System.out.println("min must less than max");
        }

        return result;
    }
}
