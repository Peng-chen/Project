package tools;

import java.util.HashSet;
import java.util.Random;

public class RandCreate {

	public static Object[] create(int Length, int min, int max) {
		Random random = new Random();
		HashSet<Integer> hashSet = new HashSet<Integer>();
		Object[] values = new Object[Length];
		while (hashSet.size() < Length) {

			int one = random.nextInt(max - min) + min;

			hashSet.add(one);
		}

		values = hashSet.toArray();

		return values;

	}

}
