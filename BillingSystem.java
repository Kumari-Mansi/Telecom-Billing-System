import java.util.Scanner;

public class BillingSystem {

    private static final Scanner sc = new Scanner(System.in);

    public static void mainMenu() {
        while (true) {
            System.out.println("\n--- Telecom Billing System ---");
            System.out.println("1) Initialize DB (create tables)");
            System.out.println("2) Add Customer");
            System.out.println("3) Record Usage (customer)");
            System.out.println("4) Compute Due & Generate Invoice");
            System.out.println("5) List Customers");
            System.out.println("6) List Invoices");
            System.out.println("7) Mark Invoice Paid");
            System.out.println("0) Exit");
            System.out.print("Choose: ");
            String choice = sc.nextLine().trim();
            try {
                switch (choice) {
                    case "1": DBHelper.initDatabase(); break;
                    case "2": cmdAddCustomer(); break;
                    case "3": cmdRecordUsage(); break;
                    case "4": cmdGenerateInvoice(); break;
                    case "5": DBHelper.listCustomers(); break;
                    case "6": DBHelper.listInvoices(); break;
                    case "7": cmdMarkInvoicePaid(); break;
                    case "0": System.out.println("Bye"); return;
                    default: System.out.println("Invalid choice");
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    private static void cmdAddCustomer() {
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Mobile: ");
        String mobile = sc.nextLine();
        System.out.print("Address: ");
        String address = sc.nextLine();
        int id = DBHelper.addCustomer(name, mobile, address);
        if (id != -1) System.out.println("Customer added. ID = " + id);
        else System.out.println("Failed to add customer.");
    }

    private static void cmdRecordUsage() {
        System.out.print("Customer ID: ");
        int cid = Integer.parseInt(sc.nextLine());
        System.out.print("Usage units (e.g., minutes/MB): ");
        double units = Double.parseDouble(sc.nextLine());
        System.out.print("Unit rate (per unit): ");
        double rate = Double.parseDouble(sc.nextLine());
        DBHelper.recordUsage(cid, units, rate);
    }

    private static void cmdGenerateInvoice() {
        System.out.print("Customer ID to invoice: ");
        int cid = Integer.parseInt(sc.nextLine());
        double due = DBHelper.computeDueAmount(cid);
        System.out.printf("Total due: %.2f%n", due);
        if (due <= 0.0) {
            System.out.println("No due amount, aborting.");
            return;
        }
        System.out.print("Create invoice? (y/n): ");
        String ans = sc.nextLine().trim().toLowerCase();
        if (ans.equals("y")) {
            int invId = DBHelper.createInvoice(cid, due);
            if (invId != -1) System.out.println("Invoice created. ID: " + invId);
        } else {
            System.out.println("Cancelled.");
        }
    }

    private static void cmdMarkInvoicePaid() {
        System.out.print("Invoice ID to mark paid: ");
        int id = Integer.parseInt(sc.nextLine());
        DBHelper.markInvoicePaid(id);
    }
}
