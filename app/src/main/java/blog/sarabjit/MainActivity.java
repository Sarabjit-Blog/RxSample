package blog.sarabjit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "RxSample";
    private static final String SAY_HELLO = "Hello From Sarab";
    private Observable<String> myObservable;
    private DisposableObserver<String> mDisposableObserver;
    private DisposableObserver<String> mDisposableObserver2;
    private CompositeDisposable mCompositeDisposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCompositeDisposable = new CompositeDisposable();
        myObservable = Observable.just(SAY_HELLO);
        myObservable.subscribeOn(AndroidSchedulers.mainThread());

        mDisposableObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                Log.d(TAG, "OnNext");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "OnError");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "OnComplete");
            }
        };

        mCompositeDisposable.add(mDisposableObserver);
        myObservable.subscribe(mDisposableObserver);


        mDisposableObserver2 = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                Log.d(TAG, "OnNext-Observer-2");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "OnError-Observer-2");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "OnComplete-Observer-2");

            }
        };
        mCompositeDisposable.add(mDisposableObserver2);
        myObservable.subscribe(mDisposableObserver2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }
}
