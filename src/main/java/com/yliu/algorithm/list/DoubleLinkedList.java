package com.yliu.algorithm.list;

/**
 * 双向链表
 */
public class DoubleLinkedList {
    private static class Node
    {
        Object value;
        Node prev = this;
        Node next = this;

        Node(Object v)
        {
            value = v;
        }

        public String toString()
        {
            return value.toString();
        }
    }
    private Node head = new Node(null); // 头节点
    private int size; // 链表大小
    // 以下是接口方法

    //添加到链表表头
    public boolean addFirst(Object o)
    {
        addAfter(new Node(o), head);
        return true;
    }
    //将元素添加到链表表尾
    public boolean addLast(Object o)
    {
        addBefore(new Node(o), head);
        return true;
    }

    public boolean add(Object o)
    {
        return addLast(o);
    }
    //将元素添加到指定位置
    public boolean add(int index, Object o)
    {
        addBefore(new Node(o), getNode(index));
        return true;
    }
    //移除指定位置
    public boolean remove(int index)
    {
        removeNode(getNode(index));
        return true;
    }
    //移除链表表头元素
    public boolean removeFirst()
    {
        removeNode(head.next);
        return true;
    }
    //移除链表表尾元素
    public boolean removeLast()
    {
        removeNode(head.prev);
        return true;
    }
    //取到指定位置的元素值
    public Object get(int index)
    {
        return getNode(index).value;
    }
    //返回链表的大小
    public int size()
    {
        return size;
    }

    public String toString()
    {
        StringBuffer s = new StringBuffer("[");
        Node node = head;
        for (int i = 0; i < size; i++)
        {
            node = node.next;
            if (i > 0)
                s.append(", ");
            s.append(node.value);
        }
        s.append("]");
        return s.toString();
    }
    //以下是实现方法

    //查找链表元素
    private Node getNode(int index)
    {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        Node node = head.next;
        for (int i = 0; i < index; i++)
            node = node.next;
        return node;
    }
    //在某元素之前添加元素
    private void addBefore(Node newNode, Node node)
    {
        newNode.prev = node.prev;
        newNode.next = node;
        newNode.next.prev = newNode;
        newNode.prev.next = newNode;
        size++;
    }
    //在某元素之后添加元素
    private void addAfter(Node newNode, Node node)
    {
        newNode.prev = node;
        newNode.next = node.next;
        newNode.next.prev = newNode;
        newNode.prev.next = newNode;
        size++;
    }
    //移除特定元素
    private void removeNode(Node node)
    {
        node.next.prev = node.prev;
        node.prev.next = node.next;
        node.prev = null;
        node.next = null;
        size--;
    }
}
