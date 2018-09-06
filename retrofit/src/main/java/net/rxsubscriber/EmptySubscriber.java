package net.rxsubscriber;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * 空操作，不对结果做任何处理
 */
public class EmptySubscriber implements Subscriber {

    @Override
    public void onSubscribe(Subscription s) {

    }

    @Override
    public void onNext(Object o) {

    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onComplete() {

    }
}
