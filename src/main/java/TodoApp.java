//import java.util.Scanner;
//
//public class TodoApp {
//    public static void main(String[] args) {
//        TodoList list = new TodoList();
//        Scanner scanner = new Scanner(System.in);
//        boolean running = true;
//
//        while (running) {
//            System.out.println("\nTODO Menu:");
//            System.out.println("1. Vis oppgaver");
//            System.out.println("2. Legg til oppgave");
//            System.out.println("3. Merk oppgave som fullført");
//            System.out.println("4. Slett oppgave");
//            System.out.println("5. Avslutt");
//            System.out.print("Velg: ");
//            int choice = scanner.nextInt();
//            scanner.nextLine();
//
//            switch (choice) {
//                case 1:
//                    list.printTasks();
//                    break;
//                case 2:
//                    System.out.print("Beskrivelse: ");
//                    String desc = scanner.nextLine();
//                    list.addTask(desc);
//                    break;
//                case 3:
//                    System.out.print("Oppgave nr: ");
//                    int doneIndex = scanner.nextInt();
//                    list.markTaskDone(doneIndex);
//                    break;
//                case 4:
//                    System.out.print("Oppgave nr: ");
//                    int removeIndex = scanner.nextInt();
//                    list.removeTask(removeIndex);
//                    break;
//                case 5:
//                    running = false;
//                    break;
//                default:
//                    System.out.println("Ugyldig valg.");
//            }
//        }
//        scanner.close();
//    }
//}
