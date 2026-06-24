public class Main {
    static String classLevelVariable = "This is a class level variable";
    
    public static void main(String[] args) {
        
        displayMessage("first custom message");
    }
    public static void displayMessage(String message) { 
        System.out.println(message);
    }
}
