package cn.edu.jlu.examsystem.common.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author WangZeying 2020/9/5 20:49
 */
public class ConvertUtils {

    private ConvertUtils() {
    }

    public static <T, R> List<T> extractList(Collection<R> collection, Function<? super R, T> func) {
        return collection.stream().map(func).collect(Collectors.toList());
    }

    public static <C, K, U> Map<K, U> extractMap(Collection<C> collection,
                                                 Function<? super C, ? extends K> keyMapper,
                                                 Function<? super C, ? extends U> valueMapper) {
        return collection.stream().collect(Collectors.toMap(keyMapper, valueMapper));
    }

}
