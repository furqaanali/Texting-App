import java.io.Serializable;

public class Info implements Serializable {

    private static final long serialVersionUID = 1L;
    String action;
    String message;
    String nickname;

    Info() {
        this.action = null;
        this.message = null;
        this.nickname = null;
    }
    Info(String action, String message, String nickname) {
        this.action = action;
        this.message = message;
        this.nickname = nickname;
    }

    void display() {
        System.out.println("Object Received\nAction:"+action+"\nMessage:"+message+"\nNickname:"+nickname+"\n\n");
    }

}
