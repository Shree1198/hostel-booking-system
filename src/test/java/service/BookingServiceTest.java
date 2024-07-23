package service;

import com.shree.hostelbooking.HostelAccommodationManagerApplication;
import com.shree.hostelbooking.controller.BookingController;
import com.shree.hostelbooking.repository.BookingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = HostelAccommodationManagerApplication.class)
public class BookingServiceTest {

    private BookingController bookingController;

    @Test
    public void getAllBookingsTest() {
        Assertions.assertEquals(4, 2 + 2);
        Assertions.assertNotNull(bookingController.getAllBookings());
    }
}
