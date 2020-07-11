import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
    Node(String ip, String port, String[] neighbors) {
        this.ip = ip;
        this.port = port;
        this.neighbors = neighbors;
        this.part = false;
        this.parent = "";
        this.children = new HashMap<String, Boolean>();
        this.proc_known = new HashMap<String, Boolean>();
        this.channels_known = new HashMap<Pair, Boolean>();
        this.message_inbox = new HashMap<String, Boolean>();
        this.valset = new ValPair[100];
        this.expected_msg = 0;

        this.proc_known.put(port, true); // initialize the pc neighbors
        for (int i = 0; i < this.neighbors.length; i++) {
            Pair pair = new Pair(port, this.neighbors[i]);
            this.channels_known.put(pair, true);

        }

    }
}
