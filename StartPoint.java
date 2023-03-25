import javafx.application.Application;

public class StartPoint {

    public static void main(String args[])
    {
        Application.launch(NotePad.class);

       Thread one = new Thread() {
            public void run() {
                Application.launch(NotePad.class);
            }  
        };
        
        one.start();
    }


}
