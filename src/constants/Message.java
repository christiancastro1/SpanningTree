package constants;
public class Message {
    String source;
    String intent;
    ValPair [] valset;
    String data;

    Message(String source, String intent, ValPair [] valset, String data){
        this.source = source;
        this.intent = intent;
        this.valset = valset;
        this.data = data;
    }
    public String printInfo(){
        return "Message Info: { Origin:" + this.source + ", Intent:"+ this.intent+"\n";
    }


}
