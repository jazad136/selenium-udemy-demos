public class Main {
    static String classLevelVariable = "This is a class level variable";
    public static void main(String[] args) {
        String greeting = "Hello World!";
        int number = 5;
        boolean isJavaFun = true;
        
        System.out.println(greeting);
        System.out.println(number);
        System.out.println(isJavaFun);
        
        displayMessage();
        System.out.println(classLevelVariable);
    }
    public static void displayMessage() { 
        String localMessage = "This is a local variable";
        System.out.println(localMessage);
        System.out.println(classLevelVariable);
    }
}
