public class Main {
    static String classLevelVariable = "This is a class level variable";
    
    public static void main(String[] args) {
        int age = 16;
        String name = "Jonathan";
        String location = "Cincinnati, OH";
        printName(name);
        printAge(age);
        printLocation(location);
    }
    public static void printName(String name) {
        System.out.println("Hello, " + name);
    }
    public static void printAge(int age) {
        System.out.println("You are " + age + " years old");
    }
    public static void printLocation(String location) { 
        System.out.println("You are located in " + location);
    }
    public static void displayMessage(String message) { 
        System.out.println(message);
    }
}
