import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class BlockingQueueMultiThreading {
    static Random generator = new Random();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(20);
        List<Future> tasks = new LinkedList<>();

        addProducerThread(executorService, tasks, queue);

        addConsumerThreads(executorService, tasks, 7, queue);


        executorService.shutdown();

        try {
            tasks.get(0).get();
        } catch (Throwable thrown) {
            System.out.println("Error while waiting for thread completion");
        }

        List<Integer> kwadraty = new ArrayList<>();
        for (int i=1; i<=7; i++) {
            try {
                kwadraty.add((Integer)tasks.get(i).get(1, TimeUnit.SECONDS));
            } catch (Throwable thrown) {
                System.out.println("Error while waiting for producer thread completion");
            }
        }

        kwadraty.parallelStream().forEach(integer -> {
            System.out.println("Nowy kwadrat liczby to " + integer);
        });

    }
    private static void addConsumerThreads(ExecutorService executorService, List<Future> tasks, int n, BlockingQueue<Integer> queue) {
        Future future;
        while(n>0) {
            future = executorService.submit(new Consumer(queue));
            tasks.add(future);
            n--;
        }
    }

    private static void addProducerThread(ExecutorService executorService, List<Future> tasks, BlockingQueue<Integer> queue) {
        Future future  = executorService.submit(new Producer(queue));
        tasks.add(future);
    }
}


class Producer implements Runnable {

    BlockingQueue<Integer> queue;

    Producer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("ProducerThread");

        Integer a = Integer.valueOf(0);
        // 2s delay
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 1; i < 6; i++) {

            try {
                //Usypianie watku na pewien czas
                Thread.sleep(Duration.ofSeconds(App.generator.nextInt(3)).toMillis() / 20);
                a=App.generator.nextInt(15);
                queue.put(a);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Watek " + Thread.currentThread().getName() + " wygenerowal nowa liczbe " + a + " po raz " + i);
        }

        // zamiast stawiać flagę, trzeba coś położyć na kolejkę żeby zasygnalizować koniec, inaczej take() zablokuje wątki
        try {
            queue.put(Integer.valueOf(525));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
class Consumer implements Callable<Integer>{

    BlockingQueue<Integer> queue;

    Consumer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }


    @Override
    public Integer call() {
        Integer n = Integer.valueOf(0);
        Integer kwadrat = Integer.valueOf(0);

        while (true) {

                    try {
                        // take() blokuje wątek aż do wybudzenia (pojawienia się elementu), stąd trzeba mu jakoś zasygnalizować elementem z kolejki że to koniec.
                        // można tu dać poll(time, TimeUnit), który by ściągał wartość z kolejki albo zwracał nulla i pętla by się zapętlała
                        n = queue.take();
                        if (n == 525) {
                            queue.put(Integer.valueOf(525));
                            break;
                        }
                        kwadrat = n*n;
                    } catch (InterruptedException e) {
                        //throw new RuntimeException(e);
                    }

            // Pobieranie nazwy watku
            System.out.println("Watek " + Thread.currentThread().getName() + " obliczył kwadrat " + n + ", czyli " + n*n);
        }
        return kwadrat;
    }

}


