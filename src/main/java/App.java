import java.util.*;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class App {
    static Random random;
    
    public static Observable<String> getGreeting() {
        return Observable.just("Hello World");
    }

    public static Observable<Integer> fakeUserInput() {
        return Observable.just(1,2,3,4,5,6);
    }
    
    public static Observable<Integer> fakeUserInput2() {
        return Observable.intervalRange(0, 10, 500, 500, TimeUnit.MILLISECONDS)
                .map(x -> random.nextInt(10));
    }
    
    public static Observable<Integer> basicMapExample() {
        return Observable.just(1, 2, 3)
                .map(x -> x * 10);
    }
    
    public static Observable<Integer> randomMapExample() {
        return Observable.just(1, 2, 3)
                .map(x -> random.nextInt(20));
    }
    
    public static Observable<Integer> randomConcatMapExample() {
        return Observable.intervalRange(0, 20, 500, 500, TimeUnit.MILLISECONDS)
                .concatMap(number -> Observable.just(random.nextInt(20))
                .delay(random.nextInt(500),  TimeUnit.MILLISECONDS));
    }
    
    public static Observable<Integer> randomFlatMapExample() {
        return Observable.intervalRange(0, 20, 500, 500, TimeUnit.MILLISECONDS)
                .flatMap(number -> Observable.just(random.nextInt(20))
                        .delay(random.nextInt(500),  TimeUnit.MILLISECONDS));
    }
    
    public static Observable<Integer> randomSwitchMapExample() {
        return Observable.intervalRange(0, 20, 500, 500, TimeUnit.MILLISECONDS)
                .switchMap(number -> Observable.just(random.nextInt(20))
                        .delay(random.nextInt(500),  TimeUnit.MILLISECONDS));
    }
    
    public static void main(String[] args) {
        random = new Random();
        System.out.println("getGreeting()");
        System.out.println("...");
        getGreeting()
                .subscribe(line -> System.out.println(line));

        System.out.println("___");
        System.out.println("fakeUserInput()");
        System.out.println("...");
        fakeUserInput()
                .subscribe(line -> System.out.println(line));

        System.out.println("___");
        System.out.println("fakeUserInput2()");
        System.out.println("...");
        fakeUserInput2()
                .blockingSubscribe(line -> System.out.println(line));

        System.out.println("___");
        System.out.println("basicMapExample()");
        System.out.println("...");
        basicMapExample()
                .subscribe(line -> System.out.println(line));

        System.out.println("___");
        System.out.println("randomMapExample()");
        System.out.println("...");
        randomMapExample()
                .subscribe(line -> System.out.println(line));

        System.out.println("___");
        System.out.println("randomConcatMapExample()");
        System.out.println("...");
        randomConcatMapExample()
                .blockingSubscribe(line -> System.out.println(line));

        System.out.println("___");
        System.out.println("randomFlatMapExample()");
        System.out.println("...");
        randomFlatMapExample()
                .blockingSubscribe(line -> System.out.println(line));

        System.out.println("___");
        System.out.println("randomSwitchMapExample()");
        System.out.println("...");
        randomSwitchMapExample()
                .blockingSubscribe(line -> System.out.println(line));

        System.out.println("___");
        System.out.println("fibs");
        System.out.println("...");
        fakeUserInput()
                .flatMapMaybe(x -> RxFibonacci.fibs().elementAt(x))
                .blockingSubscribe(line -> System.out.println(line));
    
        System.out.println("___");
        System.out.println("console reader");
        System.out.println("...");
        RxReader.linesFromInput()
                .map(line -> Integer.parseInt(line))
                .observeOn(Schedulers.trampoline())
                .flatMapMaybe(x -> RxFibonacci.fibs().elementAt(x))
                .blockingSubscribe(line -> System.out.println(line));
    }
}
