package org.scenic.orchestrator.it;

import static java.util.stream.IntStream.range;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Test;

/**
 * Created by Jose on 01/10/19.
 */
public class Parallel {


    ExecutorService executor
            //= Executors.newSingleThreadExecutor();
            = Executors.newFixedThreadPool(20);


    @Test
    public void test() throws Exception {


        assertTrue(true);
        /*SquareCalculator squareCalculator = new SquareCalculator();

        Future<Integer> future1 = squareCalculator.calculate(10);
        Future<Integer> future2 = squareCalculator.calculate(100);

        while (!(future1.isDone() && future2.isDone())) {
            System.out.println(
                    String.format(
                            "future1 is %s and future2 is %s",
                            future1.isDone() ? "done" : "not done",
                            future2.isDone() ? "done" : "not done"
                    )
            );
            Thread.sleep(300);
        }

        Integer result1 = future1.get();
        Integer result2 = future2.get();

        System.out.println(result1 + " and " + result2);*/

        //List<Callable<String>> callables =
        List<Callable<String>> callables = range(1, 5)
                .boxed()
                .map(this.c())
                .collect(Collectors.toList());


        System.out.println("Start");long init = System.currentTimeMillis();
        List<Future<String>> exists = executor.invokeAll(callables);
        System.out.println("Finish " + (System.currentTimeMillis() -init ));

        for(Future<String> f : exists) {
            System.out.println(f.isDone());
        }
        System.out.println("Bye");



    }

    public Function<Integer, Callable<String>> c() {
        return i ->
                () -> {
                    try {
                        TimeUnit.MILLISECONDS.sleep(10_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "Task's execution";

                };
    }

    public class SquareCalculator {

        public Future<Integer> calculate(Integer input) {
            return executor.submit(() -> {
                Thread.sleep(1000);
                return input * input;
            });
        }
    }

}
