package com.example.ducvu212.demomvvm.data.source.local;

import com.example.ducvu212.demomvvm.utils.Constant;
import io.reactivex.Maybe;
import java.util.ArrayList;
import java.util.List;

class GetKeyTrendCollection {
    static Maybe<List<String>> getKeyTrend() {
        List<String> trends = new ArrayList<>();
        trends.add(Constant.BEAUTIFUL_SCENE);
        trends.add(Constant.CITY);
        trends.add(Constant.GIRL);
        trends.add(Constant.NATURE);
        trends.add(Constant.SCHOOL);
        trends.add(Constant.SNEAKER);
        trends.add(Constant.VIETNAM);
        trends.add(Constant.WINTER);
        trends.add(Constant.WIND);
        return Maybe.create(emitter -> {
            emitter.onSuccess(trends);
            emitter.onComplete();
        });
    }
}
