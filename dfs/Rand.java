import java.util.ArrayList;
import java.util.Random;

public class Rand {

	static Random r = new Random(); //PRNG

	public Rand() {}

	//return a random int
	public static int randomInt(int x) {
		return r.nextInt(x);
	}

	//pick a random Object from given ArrayList
	public static Object pickRandom(ArrayList al) {
		return al.get(randomInt(al.size()));
	}
}
