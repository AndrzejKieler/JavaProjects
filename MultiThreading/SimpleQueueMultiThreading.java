import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class SimpleQueueMultiThreading {
    static Random generator = new Random();
    static volatile Queue<Integer> queue = new LinkedList<>(); //kolejka oznaczona volatile, w przeciwnym razie bylby problem z double-check locking lub z petlami ktore stale sprawdzaja stan/rozmiar obiektu

    public static void main(String[] args) {

        // Creating ThreadPool
        // Mozna tez jako drugi parametr dodac ThreadFactory co pozwala dodawac watki do puli i nazywac je
        ExecutorService executorService = Executors.newFixedThreadPool(8);

        // Zbiór wszystkich zleconych zadań
        List<Future> tasks = new LinkedList<>();

        addProducerThread(executorService, tasks);

        addConsumerThreads(executorService, tasks, 7);

        //Metoda inicjuje zamknięcie puli. Odtąd nie można do niej dodawać zadań, ale metoda nie czeka na zakończenie zadań wykonywanych. Nawet jeżeli wszystkie wątki skończyły pracę trzeba zrobić shutdowna by ExecutorService skończył pracę i nie były zlecane nowe zadania
        executorService.shutdown();

        //Metoda awaitTermination (najpowszechniejsza, gdy używane Runnable i wszystkie wątki puli mają się zakończyć) czeka aż wszystkie wątki zakończą swoją pracę, zostaną zabite lub minie czas podany w konstruktorze; metoda działa prawidłowo po shutdown()
        /*
            try{
                executorService.awaitTermination(10, TimeUnit.SECONDS);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            finally {
            if (!executor.isTerminated()) {
                System.err.println("cancel non-finished tasks");
            }
            //Metoda próbuje zabić wszystkie wątki puli nie czekając na wykonanie ich zadań, ale nie ma gwarancji na ich terminację
            executorService.shutdownNow();
            System.out.println("shutdown finished");
}
        */


        // Wywołanie na wybranych obiektach future metody get powoduje oczekiwanie na zwróconą przez wątek wartość, stąd jest to też sposób na czekanie na koniec ich wykonywania

        try {
            //System.out.println("future done? " + future.isDone()); -> jednorazowe sprawdzenie czy zadanie skończone
            // oczekiwanie na ukonczenie kazdego procesu z osobna / zwracanie warości funkcji call()
            tasks.get(0).get();
        } catch (Throwable thrown) {
            System.out.println("Error while waiting for thread completion");
        }

        List<Integer> kwadraty = new ArrayList<>();
        for (int i=1; i<=8; i++) {
            try {
                //System.out.println("future done? " + future.isDone()); -> jednorazowe sprawdzenie czy zadanie skończone
                // oczekiwanie na ukonczenie kazdego procesu z osobna / zwracanie warości funkcji call()
                // podanie arkumentow do metody get() gwarantuje, ze czekanie na odpowiedź wątków nie będzie trwać w nieskończoność
                kwadraty.add((Integer)tasks.get(i).get(1, TimeUnit.SECONDS));
            } catch (Throwable thrown) {
                System.out.println("Error while waiting for thread completion");
            }
        }

        // Wielowątkowość można wykorzystywać tworząc równoległy stream z kolekcji
        kwadraty.parallelStream().forEach(integer -> {
            System.out.println("Nowy kwadrat liczby to " + integer);
        });



        // Innym sposobem może być przekazanie do metody executorService.invokeAll() listy Call/Runables, a ta zwraca Listę futures dopiero po zakończeniu wszystkich wątków w kolejności jak w liście przesłanej
        /*
            ExecutorService WORKER_THREAD_POOL = Executors.newFixedThreadPool(10);

            List<Callable<String>> callables = Arrays.asList(
                    new DelayedCallable("fast thread", 100),
                    new DelayedCallable("slow thread", 3000));

            long startProcessingTime = System.currentTimeMillis();
            List<Future<String>> futures = WORKER_THREAD_POOL.invokeAll(callables);

            awaitTerminationAfterShutdown(WORKER_THREAD_POOL);

            long totalProcessingTime = System.currentTimeMillis() - startProcessingTime;

            assertTrue(totalProcessingTime >= 3000);

            String firstThreadResponse = futures.get(0).get();

            assertTrue("fast thread".equals(firstThreadResponse));

            String secondThreadResponse = futures.get(1).get();
            assertTrue("slow thread".equals(secondThreadResponse));
         */
        /*
            executor.invokeAll(callables)
                .stream()
                .map(future -> {
                    try {
                        return future.get();
                    }
                    catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                })
                .forEach(System.out::println);
         */

        // invokeAny() czeka tylko na jeden i zwraca obiekt zwracany przez Callable, prawdopodobnie najszybszy

        // Alternatywą do tego jest użycie CompletionService, przekazania do konstruktora new ExecutorCompletionService ExecutionService, dopiero wtedy zlecenie zadań CompletionServisowi, poczym wywołać na nim metodę take(). Ta zwraca kolejkę Futures w takiej kolejności jak zostały ukończone wątki co pozwala na ściąganie wyników od razu po zakończeniu najszybszego wątku. Metodę trzeba więc wywołać tyle razy ile jest wątków
        /*
                    CompletionService<String> service
                      = new ExecutorCompletionService<>(WORKER_THREAD_POOL);

                    List<Callable<String>> callables = Arrays.asList(
                      new DelayedCallable("fast thread", 100),
                      new DelayedCallable("slow thread", 3000));

                    for (Callable<String> callable : callables) {
                        service.submit(callable);
                    long startProcessingTime = System.currentTimeMillis();

                    Future<String> future = service.take();
                    String firstThreadResponse = future.get();
                    long totalProcessingTime
                      = System.currentTimeMillis() - startProcessingTime;

                    assertTrue("First response should be from the fast thread",
                      "fast thread".equals(firstThreadResponse));
                    assertTrue(totalProcessingTime >= 100
                      && totalProcessingTime < 1000);
                    LOG.debug("Thread finished after: " + totalProcessingTime
                      + " milliseconds");

                    future = service.take();
                    String secondThreadResponse = future.get();
                    totalProcessingTime
                      = System.currentTimeMillis() - startProcessingTime;

                    assertTrue(
                      "Last response should be from the slow thread",
                      "slow thread".equals(secondThreadResponse));
                    assertTrue(
                      totalProcessingTime >= 3000
                      && totalProcessingTime < 4000);
                    LOG.debug("Thread finished after: " + totalProcessingTime
                      + " milliseconds");

                    awaitTerminationAfterShutdown(WORKER_THREAD_POOL);
            */

        // Gdy wystarczy nam zakończenie konkretnych operacji w konkretnych wątkach można skorzystać z CauntDownLatch, który będąc globalnym polem będzie dekrementowany w ważnych miejscach. Tworząc jego instancję trzeba podać ile razy ma być zdekrementowany, a wywołanie na końcy metody latch.await(); powoduje oczekiwanie, aż latch osiągnie 0
        /*
            ExecutorService WORKER_THREAD_POOL
              = Executors.newFixedThreadPool(10);
            CountDownLatch latch = new CountDownLatch(2);
            for (int i = 0; i < 2; i++) {
                WORKER_THREAD_POOL.submit(() -> {
                    try {
                        // ...
                        latch.countDown();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
            }

            // wait for the latch to be decremented by the two remaining threads
            latch.await();
         */

        System.out.println("Proces zakończony pomyslnie");

    }

    private static void addConsumerThreads(ExecutorService executorService, List<Future> tasks, int n) {
        Future future;
        while(n>0) {
            future = executorService.submit(new Consumer());
            tasks.add(future);
            n--;
        }
    }

    private static void addProducerThread(ExecutorService executorService, List<Future> tasks) {
        // Aby delegować zadanie można używać metod execute i submit. Ta druga zwraca obiekt Future, który pozwala na zarządzanie danym wątkiem
        Future future  = executorService.submit(new Producer());
        tasks.add(future);
    }
}


class Producer implements Runnable{

    static volatile boolean done = false;

    // Tworzenie statycznej metody zsynchronizowanej czyli monitor polaczony jest z Producer.class
    static synchronized boolean isDone() {
        return done;
    }

    @Override
    public void run() {
        // Ustawianie nazwy pojedynczego watku
        Thread.currentThread().setName("ProducerThread");

        // 2s delay
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 1; i < 6; i++) {

            try {
                //Usypianie watku na pewien czas
                Thread.sleep(Duration.ofSeconds(App.generator.nextInt(3)).toMillis()/20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (App.queue) {
                App.queue.add(App.generator.nextInt(15));
                // wybudzenie jednego z zbioru powiadamianych wątków obiektu queue (ang. waiting set)
                App.queue.notify();
            }
            System.out.println("Watek " +  Thread.currentThread().getName() + " wygenerowal nowa liczbe po raz " + i);
        }
        done = true;
        // gdyby funkcja wait() w wątku Consumera nie miała argumentu (nie byłaby okresowo wybudzana) trzeba wybudzić wszystkie wątki po skończeniu pracy
        // tu też musi byc synchronizacja, bo oczekujace watki sa w bloku synchronized
        //synchronized (App.queue){ App.queue.notifyAll(); }
    }
}

class Consumer implements Callable<Integer>{

    @Override
    public Integer call() {
        Integer n;
        Integer kwadrat = Integer.valueOf(0);

        // nie trzeba tu przy isDone robic bloku synchronized bo funkcja ta jest zadeklarowana jako zsynchornizowana z monitorem Producer.class
        while (!Producer.isDone() || !App.queue.isEmpty()) {

            // dostep do queue jest zsynchronizowany, tzn że monitor powiazany z obiektem kolejki zablokowuje sie w chwili gdy jakis watek sie dobierze do kolejki (watek blokuje monitor)
            // obiektem polaczonym z monitorem moze byc obiekt klasy a nie obiekt instancji w przypadku korzystania z metod czy obiektow statycznych klasy
            synchronized (App.queue) {

                // Double-checked locking pattern - do wejścia w blok zsynchronizowany mogło coś się wydarzyć (stosowane w singletonie)
                while (App.queue.isEmpty()) {
                    try {

                        // Gdy wybudzi sie ostatni raz, a kolejka bedzie juz pusta
                        if(Producer.isDone()) return ((kwadrat>0)? kwadrat : Integer.valueOf(0));

                        // Dodanie aktualnego watku do zbioru powiadamianych wątków obiektu queue i przeniesienie go w stan WAITING na 1s, gdy brak argumentu to aż do wybudzenia
                        App.queue.wait(1000);
                        // Po wyjsciu z WAITING stan isEmpty() jest sprawdzany jezcze raz, gdyz specyfika Javy pozwala na tzw falszywe przebudzenia (ang. spurious wake-ups)

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                n = App.queue.poll();
                kwadrat = n*n;
            }

            // Pobieranie nazwy watku
            System.out.println("Watek " + Thread.currentThread().getName() + " obliczył kwadrat " + n + ", czyli " + n*n);
        }
        return kwadrat;
    }
}
