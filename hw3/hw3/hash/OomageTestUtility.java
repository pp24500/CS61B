package hw3.hash;

import java.util.Iterator;
import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        int N = oomages.size();
        int[] buckets = new int[M];
        for (int i = 0; i < M; i++) {
            buckets[i] = 0;
        }

        Iterator<Oomage> iter = oomages.iterator();
        while (iter.hasNext()) {
            int bucketNum = (iter.next().hashCode() & 0x7FFFFFFF) % M;
            buckets[bucketNum]++;
        }
        for (int j = 0; j < M; j++) {
            if (buckets[j] < N / 50 || buckets[j] > N / 2.5) {
                return false;
            }
        }
        return true;
    }
}
