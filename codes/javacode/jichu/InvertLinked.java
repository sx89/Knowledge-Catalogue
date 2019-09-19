package javacode.jichu;

/**
 * @author sunxu93@163.com
 * @date 19/9/19/019 11:12
 */
public class InvertLinked {
    private Node func (Node head){
        if (head == null) {
            return null;
        }
        Node cur = head;
        Node next = null;
        Node temp = null;
        while (cur != null) {
            next = cur.next;
            cur.next= temp;
            temp = cur;
            cur = next;
        }
       return temp;
    }

    public static void main(String[] args) {
        Node a = new Node(1);
        Node b = new Node(2);
        Node c = new Node(3);
        a.next =b;
        b.next =c;
        Node func =a;
        while (func != null) {
            System.out.println(func.val);
            func = func.next;
        }

        func = new InvertLinked().func(a);
        while (func != null) {
            System.out.println(func.val);
            func = func.next;
        }

    }
}

class Node{
    public Node(int val) {
        this.val =val;
    }
    Node next;
    int val;
}

