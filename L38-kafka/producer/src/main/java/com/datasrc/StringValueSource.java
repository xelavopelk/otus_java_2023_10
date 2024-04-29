package com.datasrc;

import com.datasrc.model.StringValue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

@SuppressWarnings("java:S2095")
public class StringValueSource implements ValueSource {
    private final AtomicLong nextValue = new AtomicLong(1);

    private final Consumer<StringValue> valueConsumer;

    public StringValueSource(Consumer<StringValue> valueConsumer) {
        this.valueConsumer = valueConsumer;
    }

    @Override
    public void generate() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(() -> valueConsumer.accept(makeValue()), 0, 1, TimeUnit.SECONDS);
    }

    private StringValue makeValue() {
        var id = nextValue.getAndIncrement();
        return new StringValue(id, "stVal:" + id);
    }
}
