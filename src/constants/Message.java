package constants;
public class Message {
    String source;
    Constants intent;
    ValPair [] valset;
    String data;

    public Message(String source, Constants intent, ValPair[] valset, String data){
        this.source = source;
        this.intent = intent;
        this.valset = valset;
        this.data = data;
    }

    public Message(String port, Constants sendBack, ValPair[] valset) {
        this.source =port;
        this.intent =sendBack;
        this.valset =valset;

    }

    public Message(String port, Constants sendGo, String data) {
        this.source = port;
        this.intent =sendGo;
        this.data = data;

    }

    public String printInfo(){
        return "Message Info: { Origin:" + this.source + ", Intent:"+ this.intent+"\n";
    }
    public String get_data(){return this.data;}
    public String get_source(){return this.source;}
    public Constants get_intent(){return this.intent;}


}
