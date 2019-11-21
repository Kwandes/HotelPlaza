import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
import java.util.Properties;
import java.util.InputMismatchException;

public class FileManagement
{
      // Attributes
   
   private String filePath;
   private MainFrame mf;
   private Information info;
   private Properties config;
   
   private String bookingListPath;
   private String archivedBookingListPath;
   private String roomListPath;
   private String guestListPath;
   private String staffListPath;
   private String logPath;
   private String calendarPath;
   private String counterListPath;
   
   // Logging
   private boolean printLogToConsole;
   private boolean printDebugToConsole;   // Exceptions
      
      // Constructors
      
   public FileManagement ( String filePath ) 
   {
      this.filePath = filePath;
      this.counterListPath = filePath + "/counters.txt";
      this.bookingListPath = filePath + "/bookings.txt";
      this.archivedBookingListPath = filePath + "/archived_bookings.txt";
      this.roomListPath = filePath + "/rooms.txt";
      this.guestListPath = filePath + "/guests.txt";
      this.staffListPath = filePath + "/staff.txt";
      this.logPath = filePath + "/log.txt";
      this.calendarPath = filePath + "/calendar.txt";
   }

   
   public FileManagement (MainFrame mfRef, String filePath, boolean printLog, boolean printDebug) 
   {
      this.filePath = filePath;
      this.counterListPath = filePath + "/counters.txt";
      this.bookingListPath = filePath + "/bookings.txt";
      this.archivedBookingListPath = filePath + "/archived_bookings.txt";
      this.roomListPath = filePath + "/rooms.txt";
      this.guestListPath = filePath + "/guests.txt";
      this.staffListPath = filePath + "/staff.txt";
      this.logPath = filePath + "/log.txt";
      this.calendarPath = filePath + "/calendar.txt";
      this.mf = mfRef;
      this.printLogToConsole = printLog;
      this.printDebugToConsole = printDebug;
   }
   
   public FileManagement (MainFrame mfRef, Properties config, boolean printLog, boolean printDebug) 
   {
      this.config = config;
      this.counterListPath = config.getProperty("counterListPath");
      this.bookingListPath = config.getProperty("bookingListPath");
      this.archivedBookingListPath = config.getProperty("archivedBookingListPath");
      this.roomListPath = config.getProperty("roomListPath");
      this.guestListPath = config.getProperty("guestListPath");
      this.staffListPath = config.getProperty("staffListPath");
      this.logPath = config.getProperty("logPath");
      this.calendarPath = config.getProperty("calendarPath");
      this.mf = mfRef;
      this.printLogToConsole = printLog;
      this.printDebugToConsole = printDebug;
   }
   
      // Methods
   public void appendToFile ( String log, boolean isException) // Appends a given line to the bottom of the logs.txt file
   {
      File file = new File ( logPath );
      try
      {
         file.createNewFile();
         FileWriter fw = new FileWriter ( file, true );
         PrintWriter out = new PrintWriter ( fw );
         out.println ( log );
         out.flush();
         out.close();
      }
      catch (Exception e){ System.out.println(e);};
      
      if(this.printLogToConsole)
      {
         if(isException)
         {
            if(this.printDebugToConsole) System.out.println(log);
         }
         else
         {
            System.out.println(log);
         }  
      }
   }
   
   public void appendToFile ( String log) // Unit testing log
   {
      File file = new File ( logPath );
      try
      {
         file.createNewFile();
         FileWriter fw = new FileWriter ( file, true );
         PrintWriter out = new PrintWriter ( fw );
         out.println ( log );
         out.flush();
         out.close();
      }
      catch (Exception e){ System.out.println(e);};
      
      System.out.println(log);
   }
      // Loaders
      
   /*
      Loaders return an array containing the objects of all the data that has been saved in a specific file.
      There is a loadFileName() method for all the files that are being used.
         - In the case of the bookings, they can be either active or archived and the loadBookings
           method takes in a boolean parameter 'isArchived' to help decide where to load the array from.
   */
   
   public Information loadData(Information info) throws Exception, FileNotFoundException, NullPointerException, InputMismatchException
   {
      if(info.loadBookings)
      {
         mf.createLog("FM>:Loading BookingList", Log.Type.INFO);
         info.bookingList = loadBookings(false);
      }
      else info.bookingList = null;
      
      if(info.loadArchive)
      {
         mf.createLog("FM>:Loading Archived Bookings", Log.Type.INFO);
         info.archivedBookingList = loadBookings(true);
      }
      else info.archivedBookingList = null;
      
      if(info.loadRooms)
      {
         mf.createLog("FM>:Loading RoomList", Log.Type.INFO);
         info.roomList = loadRooms();
      }
      else info.roomList = null;
      
      if(info.loadGuests)
      {
         mf.createLog("FM>:Loading GuestList", Log.Type.INFO);
         info.guestList = loadGuests();
      }
      else info.guestList = null;
      
      if(info.loadStaff)
      {
         mf.createLog("FM>:Loading StaffList", Log.Type.INFO);
         info.staffList = loadStaff();
      }
      else info.staffList = null;
      
      if (info.loadCounters)
      {
         mf.createLog("FM>:Loading CounterList", Log.Type.INFO);
         info.counterList = loadCounters();
      }
      else info.counterList = null;
      
      return info;
   }   
     
      // Rooms
   public ArrayList<Room> loadRooms () 
                           throws FileNotFoundException 
   {
      File file = new File ( roomListPath );
      File calFile = new File ( calendarPath );
      Room room = new Room();
      ArrayList<Room> array = new ArrayList<Room>();
      Scanner in = new Scanner ( file );
      Scanner calIn = new Scanner ( calFile );
      
      while ( in.hasNext () ) 
      {  
         room = new Room();
         room.setRoomID (in.nextInt());
         room.setFloor ( in.nextInt());
         room.setBeds ( in.nextInt());
         room.setPrice ( in.nextInt()); 
         int[] calendar = new int[365];
         for ( int i = 0; i < calendar.length; i ++ )
         {
            calendar[i] = calIn.nextInt();
         }
         room.setCalendar ( calendar );
         array.add ( room );
      }
            
      return array;
   }
      // Guests 
   public ArrayList<Guest> loadGuests () 
                           throws FileNotFoundException 
   {
      File file = new File ( guestListPath );
      Guest guest = new Guest();
      ArrayList<Guest> array = new ArrayList<Guest>();
      Scanner in = new Scanner ( file );
      String[] address = new String[3];
            
      while ( in.hasNext () ) 
      { 
         guest = new Guest();
         guest.setFirstName (loadWords(in));
         guest.setLastName (loadWords(in));
         guest.setCpr (in.next());
         in.next();
         guest.setType (in.next());
         in.next();
         address[0] = loadWords(in);
         address[1] = loadWords(in);
         address[2] = loadWords(in);
         guest.setAddress ( address );
         guest.setPhoneNr ( in.next());
         in.next();
         guest.setPassword ( in.next());
         in.next();
         guest.setID ( in.next());
         in.next();
         guest.setAccessLevel ( in.nextInt());
         in.next();
         guest.setGuestDays ( in.nextInt());
         in.next();
         guest.setMoneySpent ( in.nextDouble());
         array.add ( guest );
      }
            
      return array;
   }
   
      // Staff
   public ArrayList<Staff> loadStaff () 
                           throws FileNotFoundException 
   {
      File file = new File ( staffListPath );
      Staff staff = new Staff();
      ArrayList<Staff> array = new ArrayList<Staff>();
      Scanner in = new Scanner ( file );    
      String[] address = new String[3];  
      while ( in.hasNext () ) 
      {
         staff = new Staff();
         staff.setFirstName (loadWords(in));
         staff.setLastName (loadWords(in));
         staff.setCpr (in.next());
         in.next();
         staff.setType (in.next());
         in.next();
         address[0] = loadWords(in);
         address[1] = loadWords(in);
         address[2] = loadWords(in);
         staff.setAddress ( address );
         staff.setPhoneNr ( in.next());
         in.next();
         staff.setPassword ( in.next());
         in.next();
         staff.setID ( in.next());
         in.next();
         staff.setAccessLevel ( in.nextInt());
         in.next();
         staff.setHours ( in.nextInt());
         in.next();
         staff.setSalary ( in.nextDouble());
         in.next();
         staff.setVacation ( in.nextInt());
         array.add ( staff );
      }
            
      return array;
   }
   
      // Bookings - Archived & Active
   public ArrayList<Booking> loadBookings ( boolean isArchived ) 
                           throws FileNotFoundException, Exception
   {
      File file;
      if ( isArchived )
      {
         file = new File ( archivedBookingListPath );
      }
      else 
      {
         file = new File ( bookingListPath );
      }
      Booking booking = new Booking();
      ArrayList<Booking> array = new ArrayList<Booking>();
      Scanner in = new Scanner ( file );
      
      while ( in.hasNext () ) 
      {
         booking = new Booking();
         booking.setBookingID ( in.nextInt() );
         booking.setUserID ( in.next() );
         booking.setRoomID ( in.nextInt() );
         booking.setIsExtended ( in.nextBoolean() );
         booking.setStartDate ( in.nextInt() );
         booking.setEndDate ( in.nextInt() );
         booking.setHasInternet ( in.nextBoolean() );
         booking.setPrice ( in.nextInt() );
         array.add ( booking );
      }
            
      return array;
   }   
   
      // Counters
   public ArrayList<Integer> loadCounters () 
                           throws FileNotFoundException 
   {
      File file = new File ( counterListPath );
      ArrayList<Integer> array = new ArrayList<Integer>();
      Scanner in = new Scanner ( file );      
      
      if ( in.hasNext() ) {
         array.add ( in.nextInt() );
         array.add ( in.nextInt() );
         array.add ( in.nextInt() );
      }            
      return array;
   }
   
   public String loadWords ( Scanner in )
   {
      String text = "";
      String word = "";
      
      word = in.next();
      while ( !word.equals("|") )
      {
         text += " " + word;
         word = in.next();
      } 
      return text;
   }
     
      // Savers
      
   /*
      Savers print all the object information they contain in the corresponding file.
      There is a saveFileName() method for all the files that are being used.
         - In the case of the bookings, they can be either active or archived and the saveBookings
           method takes in a boolean parameter 'isArchived' to help decide where to save the contents of the array.
   */
   
   public void saveData(Information info) throws FileNotFoundException, NullPointerException
   {
      if(info.bookingList != null)
      {
         mf.createLog("FM>:Saving BookingList", Log.Type.INFO);
         saveBookings(info.bookingList, false);
      }
      
      if(info.archivedBookingList != null)
      {
         mf.createLog("FM>:Saving archived BookingList", Log.Type.INFO);
         saveBookings(info.archivedBookingList, true);
      }
      
      if(info.roomList != null)
      {
         mf.createLog("FM>:Saving RoomList", Log.Type.INFO);
         saveRooms(info.roomList);
      }
      
      if(info.guestList != null)
      {
         mf.createLog("FM>:Saving GuestList", Log.Type.INFO);
         saveGuests(info.guestList);
      }
      
      if(info.staffList != null)
      {
         mf.createLog("FM>:Saving StaffList", Log.Type.INFO);
         saveStaff(info.staffList);
      }
      if ( info.counterList != null)
      {
         mf.createLog("FM>:Saving CounterList", Log.Type.INFO);
         saveCounters(info.counterList);
      }
   }

      // Rooms      
   public void saveRooms ( ArrayList<Room> array ) 
                     throws FileNotFoundException 
   {
      File file = new File ( roomListPath );
      File calFile = new File ( calendarPath );
      PrintStream out = new PrintStream ( file );
      PrintStream calOut = new PrintStream ( calFile );
      for ( int i = 0; i < array.size(); i ++ ) 
      {
         Room room = array.get(i);
         out.println ( room.fileFormatString() );
         int[] calendar = room.getCalendar();
         for ( int j = 0; j < calendar.length; j++ ) {
            calOut.print ( calendar[j] + " " ); 
         }
         calOut.println();
      }
      calOut.flush();
      calOut.close();
      out.flush();
      out.close();   
   }
   
      // Guests
   public void saveGuests ( ArrayList<Guest> array ) 
                     throws FileNotFoundException 
   {
      File file = new File ( guestListPath );
      PrintStream out = new PrintStream ( file );
      for ( int i = 0; i < array.size(); i ++ ) 
      {
         Guest guest = array.get(i);
         out.println ( guest.fileFormatString() );
      }
      out.flush();
      out.close();   
   }
   
      // Staff
   public void saveStaff ( ArrayList<Staff> array ) 
                     throws FileNotFoundException 
   {
      File file = new File ( staffListPath );
      PrintStream out = new PrintStream ( file );
      for ( int i = 0; i < array.size(); i ++ ) 
      {
         Staff staff = array.get(i);
         out.println ( staff.fileFormatString() );
      }
      out.flush();
      //out.close();   
   }
   
      // Bookings - Archived & Active
   public void saveBookings ( ArrayList<Booking> array, boolean isArchived ) 
                     throws FileNotFoundException 
   {
      File file;
      if ( isArchived )
      {
         file = new File ( archivedBookingListPath );
      }
      else 
      {
         file = new File ( bookingListPath );
      }
      PrintStream out = new PrintStream ( file );
      for ( int i = 0; i < array.size(); i ++ ) 
      {
         Booking booking = array.get(i);
         out.println ( booking.fileFormatString() );
      }
      out.flush();
      out.close();   
   }
   
       // Counters
   public void saveCounters ( ArrayList<Integer> array ) 
                     throws FileNotFoundException 
   {
      File file = new File ( counterListPath );
      PrintStream out = new PrintStream ( file );
      for ( int i = 0; i < array.size(); i ++ ) 
      {
         out.println ( array.get(i) );
      }
      out.flush();
      out.close();   
   }



      // Getters
   
   public String getFilePath () 
   {
      return filePath;
   }
   
      // Setters
      
   public void setFilePath ( String filePath ) 
   {
      this.filePath = filePath;
   } 
}