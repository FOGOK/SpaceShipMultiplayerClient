package com.fogok.dataobjects.utils.libgdxexternals;

import java.util.Comparator;

/** Provides methods to sort arrays of objects. Sorting requires working memory and this class allows that memory to be reused to
 * avoid allocation. The sorting is otherwise identical to the Arrays.sort methods (uses timsort).<br>
 * <br>
 * Note that sorting primitive arrays with the Arrays.sort methods does not allocate memory (unless sorting large arrays of char,
 * short, or byte).
 * @author Nathan Sweet */
public class Sort {
    static private Sort instance;

    private TimSort timSort;
    private ComparableTimSort comparableTimSort;

    public <T> void sort (Array<T> a) {
        if (comparableTimSort == null) comparableTimSort = new ComparableTimSort();
        comparableTimSort.doSort((Object[])a.items, 0, a.size);
    }

    public <T> void sort (T[] a) {
        if (comparableTimSort == null) comparableTimSort = new ComparableTimSort();
        comparableTimSort.doSort(a, 0, a.length);
    }

    public <T> void sort (T[] a, int fromIndex, int toIndex) {
        if (comparableTimSort == null) comparableTimSort = new ComparableTimSort();
        comparableTimSort.doSort(a, fromIndex, toIndex);
    }

    public <T> void sort (Array<T> a, Comparator<? super T> c) {
        if (timSort == null) timSort = new TimSort();
        timSort.doSort((Object[])a.items, (Comparator)c, 0, a.size);
    }

    public <T> void sort (T[] a, Comparator<? super T> c) {
        if (timSort == null) timSort = new TimSort();
        timSort.doSort(a, c, 0, a.length);
    }

    public <T> void sort (T[] a, Comparator<? super T> c, int fromIndex, int toIndex) {
        if (timSort == null) timSort = new TimSort();
        timSort.doSort(a, c, fromIndex, toIndex);
    }

    /** Returns a Sort instance for convenience. Multiple threads must not use this instance at the same time. */
    static public Sort instance () {
        if (instance == null) instance = new Sort();
        return instance;
    }
}

