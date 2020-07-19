package com.company;
import node.Node;
import java.io.IOException;
import java.net.*;



public class Main {

    public static void main(String[] args) throws IOException {
        String ip = "127.0.0.1";

        Node node1  = new Node(ip,"8000",new String[] {"8001","8006","8007"});
        Node node2  = new Node(ip,"8001", new String[]{"8000", "8002", "8007"});
        Node node3  = new Node(ip,"8002", new String[]{"8001", "8003"});
        Node node4  = new Node(ip,"8003", new String[]{"8002","8007","8004","8005"});
        Node node5  = new Node(ip,"8004", new String[]{"8005","8003"});
        Node node6  = new Node(ip,"8005", new String[] {"8003", "8004","8006"});
        Node node7  = new Node(ip,"8006", new String[]{"8000","8007","8003","8005"});
        Node node8  = new Node(ip,"8007", new String[]{"8007","8000","8001","8003","8006"});

        node1.lisen_on_port();
        node2.lisen_on_port();
        node3.lisen_on_port();
        node4.lisen_on_port();
        node5.lisen_on_port();
        node6.lisen_on_port();
        node7.lisen_on_port();
        node8.lisen_on_port();




        node1.start();








    }
}
