   // PLACEHOLDER CRISTI
   // Jan> Pliz remove. it doesn't even work

import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;

public class TestMain {
   
      // File Path required for FileManagement.
   private static final String FILEPATH = "C:/Users/crist/OneDrive/Documents/GitKraken/HotelPlaza/Logs";
   
      // File Manager
   private static FileManagement fm;
   
      // Arrays used by the date formatting methods.
   private static int[] monthList = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
   private static String[] monthName = { "jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec" };
   
      // ArrayLists
   private static ArrayList<Booking> bookingList = new ArrayList<Booking>();
   private static ArrayList<Room> roomList = new ArrayList<Room>();
   private static ArrayList<Guest> guestList = new ArrayList<Guest>();
   private static ArrayList<Staff> staffList = new ArrayList<Staff>();
   private static ArrayList<Booking> aBookingList = new ArrayList<Booking>();
   
   
      // MAIN METHOD
   public static void main ( String[] args ) 
                  throws FileNotFoundException, IOException, Exception {
      
      fm = new FileManagement ( FILEPATH );
      
      aBookingList = fm.loadBookings ( true );
      bookingList = fm.loadBookings( false );
      roomList = fm.loadRooms();
      staffList = fm.loadStaff();
      guestList = fm.loadGuests();
      
      String[] address = new String[3];
      address[0] = "street multiple word";
      address[1] = "city perhaps";
      address[2] = "postal code";
      
         // Room Testing    
      // Setting required values for room class to work.
      Room.setBasePrice(100);
      Room.setPricePerBed(200);
      Room.setFloorMultiplier(1.05);
         
      //resetCalendar ( 511 );
      
         // Booking Testing
      int startDate = 11;
      int endDate = 19;
      int daysExtend = 3;
      boolean hasInternet = true;
      int bookingCount = 4;
      int roomID = 501;
      
         // Archive Bookings
         
      // Booking ab = bookingList.get(0);
//       Booking ab1 = bookingList.get(2);
//       aBookingList.add(ab);
//       aBookingList.add(ab1);
      
         // Guest Creation
       /*
      Guest g = new Guest("Bob Hell", "Strongest Boi", "123456-1234", address, "01233210", "word_of_passage", 5 );
      g.setAddress ( address );
      guestList.add(g);      
      Guest g2 = new Guest("Rob Heaven", "Strongest Dog Man", "123456-1234", address, "01233210", "word_of_passage", 6 ); 
      g2.setAddress ( address );
      guestList.add(g2);
      
      for ( int i = 0; i < guestList.size(); i ++ )
      {
         System.out.println ( guestList.get( i ).fileFormatString() );
      }
       */
         // Staff Creation
      
      Staff s = new Staff( "Bobert Rob", "Strong Of Them All", "123456-1234", "type:dir", address, "01233210", "pass_the_word", 5, 10, 23000, 6 );
      Staff s2 = new Staff( "Robert Bob", "Strong Pollo", "123456-1234", "type:rec", address, "01233210", "pass_the_word", 6, 10, 23000, 6 );  
      staffList.add(s);
      staffList.add(s2); 
      
         // Room Creation
         
     //  Room r = new Room ( 399, 1 );
//       roomList.add(r);
//       Room r2 = new Room ( 723, 99999 );
//       roomList.add(r2);
     
         // Booking.
     //  System.out.println ( "Is bookable : " + isBookable ( roomID, startDate, endDate ) );
//       
//       if ( isBookable ( roomID, startDate, endDate ) )
//       {
//          createBooking ( bookingCount, "userID", roomID, startDate, endDate, hasInternet );
//       }
      
         // Extending.
      // int bookingPos = findBooking ( bookingCount );
//       int roomID = bookingList.get(bookingPos).getRoomID();
//       int roomPos = findRoom ( roomID );
//       
//       int[] calendar = roomList.get(roomPos).getCalendar();
//       
//       System.out.println ( "Is Extendable : " + bookingList.get(bookingPos).isExtendable ( daysExtend, calendar ) );
//       
//       if ( bookingList.get(bookingPos).isExtendable ( daysExtend, calendar ) ) 
//       {
//          extendBooking ( bookingCount, roomID, daysExtend );
//       }
      
      fm.saveBookings ( aBookingList, true );
      fm.saveBookings(bookingList, false);
      fm.saveRooms(roomList);
      fm.saveGuests(guestList);
      fm.saveStaff(staffList);   
   }
   
      // Booking Related Methods
   
   // Creates a booking.
   public static void createBooking ( int bookingCount, String userID, int roomID,
                                      int startDate, int endDate, boolean hasInternet ) // May have too many arguments (?)
                     throws FileNotFoundException
   {
      int roomPos = findRoom ( roomID ); 
      int roomPrice = roomList.get(roomPos).getPrice();
      int[] calendar = roomList.get(roomPos).getCalendar();
      
      for ( int i  = startDate - 1; i < endDate; i ++ )
      {
         calendar[i] = bookingCount;
      }
      
      roomList.get(roomPos).setCalendar( calendar );
      
      Booking booking = new Booking ( bookingCount, roomID, userID, startDate, endDate, roomPrice, hasInternet );
      
      bookingList.add( booking ); 
      
      fm.saveRooms(roomList);
      fm.saveBookings(bookingList, false);
      fm.saveGuests(guestList);
      fm.saveStaff(staffList);
   }
   
   // Extends a booking.
   public static void extendBooking ( int bookingCount, int roomID, int days )
                     throws FileNotFoundException   
   {
      int roomPos = findRoom ( roomID );
      int bookingPos = findBooking ( bookingCount );
      
      int[]calendar = roomList.get(roomPos).getCalendar();
      int startDate = bookingList.get(bookingPos).getEndDate();
      int endDate = startDate + days;
      
      for ( int i = startDate; i < endDate; i ++ )
      {
         calendar[i] = bookingCount;
      } 
      
      bookingList.get(bookingPos).setEndDate(endDate);
      bookingList.get(bookingPos).setIsExtended(true);
      roomList.get(roomPos).setCalendar(calendar); 
      
      fm.saveRooms(roomList);
      fm.saveBookings(bookingList, false);
   }
      
   // Checks if a room is available for a given period of time.  
   public static boolean isBookable ( int roomID, int startDate, int endDate ) 
   {
      int roomPos = findRoom ( roomID );
      int[] calendar = roomList.get(roomPos).getCalendar();
      for ( int i = startDate - 1; i < endDate; i ++ ) {
         if ( calendar[i] != 0 )
         {
            return false;
         }
      }
      return true;
   }
   
         // Calendar Methods & Date Formatting
   
   // Resets the calendar of a given room.
   public static void resetCalendar ( int roomID ) 
                     throws FileNotFoundException
   {
      int roomPos = findRoom ( roomID );
      int[] calendar = new int[365];
      roomList.get(roomPos).setCalendar(calendar);
      fm.saveRooms(roomList);
   }
   
   public static int dateNumber ( int month, int day )
   {
      int days = 0;
      for ( int i = 0; i < month - 1; i ++ ) 
      {
         days += monthList[i];
      }
      days += day;
      return days;
   } 
   
   public static String dateFormat ( int days ) 
   {
      int sum = monthList[0];
      int monthNumber = 0;
      int number = 0;
      for ( int i = 1; i < 12; i ++ )
      {
         number = monthList[i];
         if ( days > sum )
         {
            sum += number;
            monthNumber ++;
         }
         else 
         {
            break;  
         }
      }
      number = monthList[monthNumber];
      int dayNumber;
      if ( monthNumber == 0 )
      {
         dayNumber = days;
      }
      else 
      {
         dayNumber = number - (sum - days);
      }
      return dayNumber + "/" + monthName[monthNumber]; 
   }
   
      // Helper Methods - perhaps in information class ? 
      
   // Finds a room by ID.
   public static int findRoom ( int roomID ) 
   {
      int roomPos = -1;
      for ( int i = 0; i < roomList.size(); i ++ )
      {
         if ( roomList.get(i).getRoomID() == roomID )
         {
            roomPos = i;
            break;
         }
      }
      return roomPos;
   }
   // Find a booking by ID/Count
   public static int findBooking ( int bookingID ) 
   {
      int bookingPos = -1;
      for ( int i = 0; i < bookingList.size(); i ++ )
      {
         if ( bookingList.get(i).getBookingID() == bookingID )
         {
            bookingPos = i;
            break;
         }
      }
      return bookingPos;
   }

}     
