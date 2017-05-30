import java.io.BufferedReader;
import java.io.InputStreamReader;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class RxReader {
    
    static Observable<String> lines(BufferedReader reader) {
        return Observable.<String>create(subscriber -> {
           String line;
           
           while ((line = reader.readLine()) != null) {
               subscriber.onNext(line);
               
               if (subscriber.isDisposed()) {
                   break;
               }
           }
           
           subscriber.onComplete();
        }).subscribeOn(Schedulers.io());
    }
    
    static Observable<String> linesFromInput() {
        return lines(new BufferedReader(new InputStreamReader(System.in)));
    }
}
