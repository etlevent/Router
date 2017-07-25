package cherry.android.router.api.request;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import cherry.android.router.api.RouteRule;
import cherry.android.router.api.utils.Utils;

/**
 * Created by ROOT on 2017/7/25.
 */

public class FragmentRequest<T> extends AbstractRequest<T> {

    public FragmentRequest(@NonNull RouteRule rule) {
        super(rule);
    }

    public FragmentRequest(@NonNull String uri, @NonNull RouteRule rule) {
        super(uri, rule);
    }

    public FragmentRequest(Class<?> destination) {
        super(destination);
    }

    @Override
    public T invoke() {
        try {
            if (!Utils.isFragment(this.destination))
                throw new IllegalArgumentException("parameter must be android.app.Fragment or android.support.v4.app.Fragment");
            Constructor constructor = destination.getConstructor();
            Object object = constructor.newInstance();
            if (object instanceof Fragment) {
                ((Fragment) object).setArguments(this.arguments);
            } else if (object instanceof android.support.v4.app.Fragment) {
                ((android.support.v4.app.Fragment) object).setArguments(this.arguments);
            }
            return (T) object;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        throw new UnsupportedOperationException("cannot create Fragment with uri=" + this.uri);
    }

    @Override
    public void request() {

    }
}