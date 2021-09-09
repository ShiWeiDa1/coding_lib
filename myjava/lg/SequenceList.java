package myjava.lg;

import java.io.Serializable;
import java.util.Arrays;

/**
 *@className: SequenceList
 *@description:
 *@author: Regan_alex
 *@date: 2021/9/9 23:10
 *@version: V1.0
 **/
public class SequenceList<T> implements Serializable {
    private static final long serialVersionUID = 3713925340773281105L;
    /**
     * 默认数组容量
     */
    public static final int DEFAULT_SIZE = 1 << 4;

    /**
     *当前列表的元素个数
     */
    private int size;

    /**
     * 保存数组的长度
     */
    private int capacity;

    /**
     * 数组用于保存集合中的对象
     */
    private Object[] elementData;

    // 初始化集合
    public SequenceList() {
        capacity = DEFAULT_SIZE;
        elementData = new Object[capacity];
    }

    public SequenceList(T item) {
        this();
        elementData[0] = item;
        size++;
    }

    public SequenceList(T item, int initCapacity) {
        capacity = 1;
        // 设置大于initCapacity 最近的 2的n次方
        while (capacity < initCapacity) {
            capacity <<= 1;
        }
        elementData = new Object[capacity];
        elementData[0] = item;
        size++;
    }

    /**
     * 获取集合长度
     * @return
     */
    public int length() {
        return size;
    }

    /**
     * 获取集合中索引为i的元素
     * @param index
     * @throws IndexOutOfBoundsException
     */
    public T get(int index) {
        checkRange(index);
        return (T) elementData[index];
    }

    /**
     * 查找指定元素的索引,未找到返回 -1
     * @param item 
     */
    public int locate(T item) {
        for (int i = 0; i < elementData.length; i++) {
            if (elementData[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 指定位置插入一个元素
     * @param item
     * @param index 
     */
    public void insert(T item, int index) {
        checkRange(index);
        ensureCapacity(size + 1);
        // 向后移动数据
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = item;
        ++size;
    }

    /**
     * 在集合的结尾处添加一个元素
     * @param item
     */
    public void add(T item) {
        insert(item, size);
    }

    /**
     * 删除指定索引处的元素
     * @param index 
     */
    public T delete(int index) {
        checkRange(index);
        T oldVal = (T) elementData[index];
        // 移动的个数
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }
        // 清空末尾元素
        elementData[--size] = null;
        return oldVal;
    }

    /**
     * 删除最后的元素
     * @return
     */
    public T remove() {
        return delete(size);
    }

    /**
     * 判断集合是否为空
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 清空集合
     */
    public void clear() {
        Arrays.fill(elementData, null);
        size = 0;
    }

    /**
     * 扩容 
     * @param minCapacity
     */
    private void ensureCapacity(int minCapacity) {
        // 扩容
        while (minCapacity < capacity) {
            capacity <<= 1;
        }
        elementData = Arrays.copyOf(elementData, capacity);
    }

    private void checkRange(int index) {
        // check index
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index has bounds");
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(elementData);
    }
}