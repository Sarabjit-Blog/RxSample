package blog.sarabjit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "RxSample";
    private static final String SAY_HELLO = "Hello From Sarab";
    private Observable<String> myObservable;
    private Observer<String> myObserver;
    private Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myObservable = Observable.just(SAY_HELLO);
        myObservable.subscribeOn(AndroidSchedulers.mainThread());
        myObserver = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
                Log.d(TAG, "On Subscribe");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "On Next");
                Toast t = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER, 0, 0);
                t.show();
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "On Error");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "On Complete");
            }
        };
        myObservable.subscribe(myObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposable.dispose();
    }
}
