public class Main {
    static String classLevelVariable = "This is a class level variable";
    
    // create two methods 'printName' and 'printAge'
    // pass name and age as parameters
    public static void main(String[] args) {
        int age = 16;
        String name = "Jonathan";
        printName(name);
        printAge(age);
    }
    public static void printName(String name) {
        System.out.println("Hello, " + name);
    }
    public static void printAge(int age) {
        System.out.println("You are " + age + " years old");
    }
    public static void displayMessage(String message) { 
        System.out.println(message);
    }
}
