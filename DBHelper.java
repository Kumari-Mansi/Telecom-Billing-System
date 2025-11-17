import java.sql.*;
import java.time.LocalDate;

public class DBHelper {
    private static final String URL = "jdbc:sqlite:billing.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void initDatabase() {
        String createCustomer = "CREATE TABLE IF NOT EXISTS customer (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "mobile TEXT," +
                "address TEXT" +
                ");";

        String createUsage = "CREATE TABLE IF NOT EXISTS usage_record (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "customer_id INTEGER," +
                "usage_units REAL," +
                "unit_rate REAL," +
                "usage_date TEXT," +
                "FOREIGN KEY(customer_id) REFERENCES customer(id)" +
                ");";

        String createInvoice = "CREATE TABLE IF NOT EXISTS invoice (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "customer_id INTEGER," +
                "invoice_date TEXT," +
                "amount REAL," +
                "paid INTEGER DEFAULT 0," +
                "FOREIGN KEY(customer_id) REFERENCES customer(id)" +
                ");";

        try (Connection conn = getConnection();
             Statement st = conn.createStatement()) {
            st.execute(createCustomer);
            st.execute(createUsage);
            st.execute(createInvoice);
            System.out.println("Database initialized (billing.db).");
        } catch (SQLException e) {
            System.err.println("DB init error: " + e.getMessage());
        }
    }

    public static int addCustomer(String name, String mobile, String address) {
        String sql = "INSERT INTO customer(name,mobile,address) VALUES(?, ?, ?);";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setString(2, mobile);
            ps.setString(3, address);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("addCustomer error: " + e.getMessage());
        }
        return -1;
    }

    public static void recordUsage(int customerId, double units, double rate) {
        String sql = "INSERT INTO usage_record(customer_id, usage_units, unit_rate, usage_date) VALUES(?, ?, ?, ?);";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ps.setDouble(2, units);
            ps.setDouble(3, rate);
            ps.setString(4, LocalDate.now().toString());
            ps.executeUpdate();
            System.out.println("Usage recorded.");
        } catch (SQLException e) {
            System.err.println("recordUsage error: " + e.getMessage());
        }
    }

    public static double computeDueAmount(int customerId) {
        String sql = "SELECT SUM(usage_units * unit_rate) as total FROM usage_record WHERE customer_id = ?;";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("total");
                }
            }
        } catch (SQLException e) {
            System.err.println("computeDueAmount error: " + e.getMessage());
        }
        return 0.0;
    }

    public static int createInvoice(int customerId, double amount) {
        String sql = "INSERT INTO invoice(customer_id, invoice_date, amount) VALUES(?, ?, ?);";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, customerId);
            ps.setString(2, LocalDate.now().toString());
            ps.setDouble(3, amount);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("createInvoice error: " + e.getMessage());
        }
        return -1;
    }

    public static void markInvoicePaid(int invoiceId) {
        String sql = "UPDATE invoice SET paid = 1 WHERE id = ?;";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, invoiceId);
            ps.executeUpdate();
            System.out.println("Invoice marked paid.");
        } catch (SQLException e) {
            System.err.println("markInvoicePaid error: " + e.getMessage());
        }
    }

    public static void listCustomers() {
        String sql = "SELECT id, name, mobile FROM customer;";
        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            System.out.println("Customers:");
            while (rs.next()) {
                System.out.printf("%d: %s (%s)%n", rs.getInt("id"), rs.getString("name"), rs.getString("mobile"));
            }
        } catch (SQLException e) {
            System.err.println("listCustomers error: " + e.getMessage());
        }
    }

    public static void listInvoices() {
        String sql = "SELECT i.id, c.name, i.invoice_date, i.amount, i.paid FROM invoice i JOIN customer c ON c.id = i.customer_id ORDER BY i.invoice_date DESC;";
        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            System.out.println("Invoices:");
            while (rs.next()) {
                System.out.printf("Invoice %d | %s | %s | %.2f | Paid: %s%n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("invoice_date"),
                        rs.getDouble("amount"),
                        rs.getInt("paid") == 1 ? "Yes" : "No");
            }
        } catch (SQLException e) {
            System.err.println("listInvoices error: " + e.getMessage());
        }
    }
}
