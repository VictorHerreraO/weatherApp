package com.soyvictorherrera.myhome.domain;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by vHerrera on 04/02/2018.
 */

@SuppressWarnings("[unchecked]")
public abstract class BaseUseCase {

    private Subscription mSubscription = Subscriptions.empty();

    public void execute(Subscriber observer) {
        mSubscription = buildObservableUseCase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void unsubscribe() {
        if (!mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    protected abstract Observable buildObservableUseCase();

}
