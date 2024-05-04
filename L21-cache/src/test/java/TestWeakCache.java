
import org.junit.jupiter.api.Test;
import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.MyCache;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestWeakCache {
    @Test
    public void happyPath() throws InterruptedException {
        HwCache<Integer, String> cache = new MyCache<>();

        {
            var key1 = Integer.valueOf(1000); //вот тут прикол из-за кеширования integer. если будет 1, то из кеша не удалится. хехехех :)
            var key2 = Integer.valueOf(2000);
            cache.put(key1, "test1");
            cache.put(key2, "test2");
            key1 = null;
        }
        System.gc();
        Thread.sleep(100);
        assertEquals(1, (int) cache.size());
    }
}
