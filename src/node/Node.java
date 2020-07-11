package node;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import constants.Constants;
import constants.Message;
import constants.Pair;
import constants.ValPair;


public class Node {
    String ip;
    boolean part;
    String port;
    String parent;
    Map<String, Boolean> children;
    int expected_msg;
    Map<String, Boolean> proc_known;
    Map<Pair, Boolean> channels_known;
    String[] neighbors;
    Map<String, Boolean> message_inbox;
    ValPair[] valset;

    //Constructor, when creating a node
    public Node(String ip, String port, String[] neighbors) {
        this.ip = ip;
        this.port = port;
        this.neighbors = neighbors;
        this.part = false;
        this.parent = "";
        this.proc_known = new HashMap<String, Boolean>();
        this.channels_known = new HashMap<Pair, Boolean>();
        this.message_inbox = new HashMap<String, Boolean>();
        this.valset = new ValPair[100];
        this.expected_msg = 0;

        this.proc_known.put(port, true); // initialize the pc neighbors
        for (String neighbor : this.neighbors) {
            Pair pair = new Pair(port, neighbor);
            this.channels_known.put(pair, true);

        }
    }
    // to start the process of creating a rooted tree
    public void start(){
        this.parent = this.port;
        this.children = new HashMap<String, Boolean>(); // initialize
        this.expected_msg = this.neighbors.length;

        Message msgout = new Message(this.port, Constants.SEND_GO, "Message to send");

        for(String neighbor: this.neighbors){
            try {
                this.send_msg(msgout, neighbor);
            } catch (IOException e) {
                System.out.println("Error sending message");
                e.printStackTrace();
            }

        }
    }
    public  void send_msg(Message message, String destination) throws IOException {
        try {   // try to create a connection with another process by sending a message
            Socket socket = new Socket(this.ip, Integer.parseInt(destination));
            ObjectOutput outputStream =new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(message);


        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error sending message....");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


    }
    public void lisen_on_port() throws IOException {
        try{
            Socket s = new Socket(this.ip, Integer.parseInt(this.port));
            System.out.println("Starting node on " + this.ip + " "+ this.port);
            ObjectInputStream objectInputStream = new ObjectInputStream(s.getInputStream());

            Message msg = (Message) objectInputStream.readObject();

            System.out.println("The message is " + msg.get_data());
            s.close();

            switch (msg.get_intent()){
                case SEND_GO -> this.Go(msg);
                case SEND_BACK -> this.Back(msg);
            }



        } catch (IOException| ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    public void Go(Message msg) throws IOException {
        if(this.parent.equals("")) { // has not been initialized
            this.parent =msg.get_source();
            this.expected_msg = this.neighbors.length-1;

            if (this.expected_msg == 0){
                Message msgout = new Message(this.port, Constants.SEND_BACK, this.valset);
                this.send_msg(msgout, this.parent); //send messagage out , might have IO exeception

            }
            else{
                Message msout = new Message(this.port, Constants.SEND_GO,msg.get_data());
                for(String neighbor:this.neighbors){
                    if(!neighbor.equals(msg.get_source())){
                        this.send_msg(msout, neighbor);
                    }

                }
            }

        }
        else{
            Message msgout = new Message(this.port, Constants.SEND_BACK, (ValPair[]) null);
            this.send_msg(msgout,  msg.get_source());
        }

    }
    public void Back(Message msg) throws IOException {
        this.expected_msg -=1;

        if (msg.getValset() != null) {
            this.children.put(msg.get_source(),true);

            for(ValPair pair: msg.getValset()){
                int index = this.valset.length+1;
                this.valset[index] = pair;
            }
        }
        if (this.expected_msg == 0){
            if(!this.port.equals(this.parent)){
                Message msgout = new Message(this.port, Constants.SEND_BACK, this.valset);
                this.send_msg(msgout, this.parent);
            }
        }
    }

}
