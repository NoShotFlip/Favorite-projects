
package Hotel_Toad;

import org.junit.Test;
import org.junit.Before;
import java.text.ParseException;
import static org.junit.Assert.*;

public class PaymentTest {

    private Payment customerPayment;

    @Before
    public void setUp() throws Exception {
        customerPayment = new Payment();
    }

    @Test
    public void calculateBillForSingle() throws ParseException {
        Reservation reservation = new Reservation( 2, 1, "08/17/2023", "08/22/2023", "Single", "");
        String bill = customerPayment.calculateBill(reservation);
        assertEquals("\nRoom Price: $1,250.00", bill);
    }

    @Test
    public void calculateBillForDouble() throws ParseException {
        Reservation reservation = new Reservation( 2, 2, "08/05/2023", "08/08/2023", "Double", "");
        String bill = customerPayment.calculateBill(reservation);
        assertEquals("\nRoom Price: $1,800.00", bill);
    }

    @Test
    public void calculateBillForTriple() throws ParseException {
        Reservation reservation = new Reservation( 2, 3, "08/22/2023", "08/25/2023", "Triple", "");
        String bill = customerPayment.calculateBill(reservation);
        assertEquals("\nRoom Price: $4,410.00", bill);
    }

    @Test
    public void calculateBillForSuite() throws ParseException {
        Reservation reservation = new Reservation( 2, 0, "08/29/2023", "08/31/2023", "Suite", "");
        String bill = customerPayment.calculateBill(reservation);
        assertEquals("\nRoom Price: $27,600.00", bill);
    }

}
