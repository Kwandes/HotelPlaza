// Unit testing class
// Calls Different functions and expects certain result

import java.util.ArrayList;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

// Musik
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.UnsupportedAudioFileException; 

public class UnitTests
{
   private Assert as;
   private boolean testPassed;
   private String testName;
   private boolean printDebugInfo;
   private int testCount;
   private int testsPassedCount;
   private FileManagement fileManager;
   
   public UnitTests()
   {
      this.fileManager = new FileManagement();
   }
   
   public void runTests(boolean debug)
   {
      // Debugging means printing logs created in MainFrame etc to console
      // Enable to see what exactly is going on, Disable for simple Test Passed/Failed
      log("New Unit test run");
      this.printDebugInfo = debug;
      if(printDebugInfo) log("Debugging messages ON");
      else log("Debugging messages OFF");
      log("----------");

      as = new Assert();
      testCount = 0;
      testsPassedCount = 0;
      
      ////////// Information Tests //////////
      try
      {
         testCount++;
         testName = "Information-loadBookingList";
         loadBookingListTest();
         log(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { log(testName + " | Failed: " + e.getMessage()); }
      catch (Exception e) { log(testName + " | Failed: " + e); }
      
      try
      {
         testCount++;
         testName = "Information-loadArchivedBookingList";
         loadArchivedBookingListTest();
         log(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { log(testName + " | Failed: " + e.getMessage()); }
      catch (Exception e) { log(testName + " | Failed: " + e); }
      
      try
      {
         testCount++;
         testName = "Information-loadRoomList";
         loadRoomListTest();
         log(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { log(testName + " | Failed: " + e.getMessage()); }
      catch (Exception e) { log(testName + " | Failed: " + e); }
      
      try
      {
         testCount++;
         testName = "Information-loadGuestList";
         loadGuestListTest();
         log(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { log(testName + " | Failed: " + e.getMessage()); }
      catch (Exception e) { log(testName + " | Failed: " + e); }
      
      try
      {
         testCount++;
         testName = "Information-loadStaffList";
         loadStaffListTest();
         log(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { log(testName + " | Failed: " + e.getMessage()); }
      catch (Exception e) { log(testName + " | Failed: " + e); }
      
      ////////// ArrayList setting Tests //////////
      try
      {
         testCount++;
         testName = "MF-ArrayLists-setBookingList()";
         setBookingListTest();
         log(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { log(testName + " | Failed: " + e.getMessage()); }
      catch (Exception e) { log(testName + " | Failed: " + e); }
      
      try
      {
         testCount++;
         testName = "MF-ArrayLists-setArchivedBookingList()";
         setArchivedBookingListTest();
         log(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { log(testName + " | Failed: " + e.getMessage()); }
      catch (Exception e) { log(testName + " | Failed: " + e); }
      
      try
      {
         testCount++;
         testName = "MF-ArrayLists-setRoomList()";
         setRoomListTest();
         log(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { log(testName + " | Failed: " + e.getMessage()); }
      catch (Exception e) { log(testName + " | Failed: " + e); }
      
      try
      {
         testCount++;
         testName = "MF-ArrayLists-setGuestList()";
         setGuestListTest();
         log(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { log(testName + " | Failed: " + e.getMessage()); }
      catch (Exception e) { log(testName + " | Failed: " + e); }
      
      try
      {
         testCount++;
         testName = "MF-ArrayLists-setStaffgList()";
         setStaffListTest();
         log(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { log(testName + " | Failed: " + e.getMessage()); }
      catch (Exception e) { log(testName + " | Failed: " + e); }
      
      ////////// Misc Tests //////////
      try
      {
         testCount++;
         testName = "validateGuestLogin()";
         validateLoginGuestTest();
         log(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { log(testName + " | Failed: " + e.getMessage()); }
      catch (Exception e) { log(testName + " | Failed: " + e); }
      
      try
      {
         testCount++;
         testName = "validateStaffLogin()";
         validateLoginStaffTest();
         log(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { log(testName + " | Failed: " + e.getMessage()); }
      catch (Exception e) { log(testName + " | Failed: " + e); }
      
      try
      {
         testCount++;
         testName = "load Config";
         loadConfig();
         log(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { log(testName + " | Failed: " + e.getMessage());}
      catch (Exception e) { log(testName + " | Failed: " + e); }
      
      try
      {
         testCount++;
         testName = "play Music";
         playMusic();
         log(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { log(testName + " | Failed: " + e.getMessage());}
      catch (Exception e) { log(testName + " | Failed: " + e); }
      
      log("----------");
      log("Unit testing finished. Passed " + testsPassedCount + "/" + testCount + " tests\n\n");
   }
   
   
   
   ////////// unit tests //////////
   public void templateTest() throws TestException
   {
      // Arrange
      // Act
      // Assert
      as.assertEquals(null, null);
   }
   
   ////////// Information Tests //////////
   
   public void loadBookingListTest() throws TestException
   {
      // Arrange
      Information info = new Information(true, false, false, false, false);
      MainFrame mf = new MainFrame(this.printDebugInfo);
      FileManagement file = new FileManagement(mf);
      file.setFilePath("logs");
      // Act
      info = file.loadData(info);
      // Assert
      as.assertEquals(Boolean.toString(info.bookingList.isEmpty()), Boolean.toString(false));
   }
   
   public void loadArchivedBookingListTest() throws TestException
   {
      // Arrange
      Information info = new Information(false, true, false, false, false);
      MainFrame mf = new MainFrame(this.printDebugInfo);
      FileManagement file = new FileManagement(mf);
      file.setFilePath("logs");
      // Act
      info = file.loadData(info);
      // Assert
      as.assertEquals(Boolean.toString(info.archivedBookingList.isEmpty()), Boolean.toString(false));
   }
   
   public void loadRoomListTest() throws TestException
   {
      // Arrange
      Information info = new Information(false, false, true, false, false);
      MainFrame mf = new MainFrame(this.printDebugInfo);
      FileManagement file = new FileManagement(mf);
      file.setFilePath("logs");
      // Act
      info = file.loadData(info);
      // Assert
      as.assertEquals(Boolean.toString(info.roomList.isEmpty()), Boolean.toString(false));
   }
   
   public void loadGuestListTest() throws TestException
   {
      // Arrange
      Information info = new Information(false, false, false, true, false);
      MainFrame mf = new MainFrame(this.printDebugInfo);
      FileManagement file = new FileManagement(mf);
      file.setFilePath("logs");
      // Act
      info = file.loadData(info);
      // Assert
      as.assertEquals(Boolean.toString(info.guestList.isEmpty()), Boolean.toString(false));
   }
   
   public void loadStaffListTest() throws TestException
   {
      // Arrange
      Information info = new Information(false, false, false, false, true);
      MainFrame mf = new MainFrame(this.printDebugInfo);
      FileManagement file = new FileManagement(mf);
      file.setFilePath("logs");
      // Act
      info = file.loadData(info);
      // Assert
      as.assertEquals(Boolean.toString(info.staffList.isEmpty()), Boolean.toString(false));
   }
   
   ////////// Setting ArrayLists Tests //////////
   
   public void setBookingListTest() throws TestException
   {
      // Arrange
      MainFrame mf = new MainFrame(this.printDebugInfo);
      mf.setSaveToFile(false);
      ArrayList<Booking> list = new ArrayList<Booking>();
      list.add(new Booking(0, 0, "yeet", 0, 0, 0, false));
      // Act
      mf.setBookingList(list);
      // Assert
      as.assertEquals(Boolean.toString(mf.getBookingList().isEmpty()), Boolean.toString(false));
   }
   
   public void setArchivedBookingListTest() throws TestException
   {
      // Arrange
      MainFrame mf = new MainFrame(this.printDebugInfo);
      mf.setSaveToFile(false);
      ArrayList<Booking> list = new ArrayList<Booking>();
      list.add(new Booking(0, 0, "yeet", 0, 0, 0, false));
      // Act
      mf.setArchivedBookingList(list);
      // Assert
      as.assertEquals(Boolean.toString(mf.getArchivedBookingList().isEmpty()), Boolean.toString(false));
   }
   
   public void setRoomListTest() throws TestException
   {
      // Arrange
      MainFrame mf = new MainFrame(this.printDebugInfo);
      mf.setSaveToFile(false);
      ArrayList<Room> list = new ArrayList<Room>();
      list.add(new Room(0, 1));
      // Act
      mf.setRoomList(list);
      // Assert
      as.assertEquals(Boolean.toString(mf.getRoomList().isEmpty()), Boolean.toString(false));
   }
   
   public void setGuestListTest() throws TestException
   {
      // Arrange
      MainFrame mf = new MainFrame(this.printDebugInfo);
      mf.setSaveToFile(false);
      ArrayList<Guest> list = new ArrayList<Guest>();
      
      String[] arr = new String[3];
      arr[0] = "Yeet";
      arr[1] = "Yote";
      arr[2] = "yoten";
      list.add(new Guest ("Faisal", "Boolyan", "1234561234", arr, "12345678", "passwd", 0));
      // Act
      mf.setGuestList(list);
      // Assert
      as.assertEquals(Boolean.toString(mf.getGuestList().isEmpty()), Boolean.toString(false));
   }
   
   public void setStaffListTest() throws TestException
   {
      // Arrange
      MainFrame mf = new MainFrame(this.printDebugInfo);
      mf.setSaveToFile(false);
      ArrayList<Staff> list = new ArrayList<Staff>();
      
      String[] arr = new String[3];
      arr[0] = "Yeet";
      arr[1] = "Yote";
      arr[2] = "yoten";
      list.add(new Staff ("Faisal", "Stud", "Boolyan", "1234561234", arr, "12345678", "passwd", 0, 0, 0.0, 0));
      // Act
      mf.setStaffList(list);
      // Assert
      as.assertEquals(Boolean.toString(mf.getStaffList().isEmpty()), Boolean.toString(false));
   }
   
   ////////// Misc tests //////////
   
   public void validateLoginGuestTest() throws TestException
   {
      // Arrange
      MainFrame mf = new MainFrame(this.printDebugInfo);
      mf.init();
      String phoneNumber = "+69 420";
      String password = "yeet";
      // Act
      Guest guest = mf.validateLoginGuest(phoneNumber, password);
      //Assert
       as.assertEquals(guest.getPhoneNumber(), phoneNumber);
   }
   
   public void validateLoginStaffTest() throws TestException
   {
      // Arrange
      MainFrame mf = new MainFrame(this.printDebugInfo);
      mf.init();
      String phoneNumber = "+69 420";
      String password = "yeet";
      // Act
      Staff staff = mf.validateLoginStaff(phoneNumber, password);
      //Assert
      as.assertEquals(staff.getPhoneNumber(), phoneNumber);
   }
   
   public void loadConfig() throws TestException, FileNotFoundException, IOException
   {
      // Arrange
      Properties config = new Properties();
      String configName = "config.properties";
      // Act
      config.load(new FileInputStream(configName));
      // Assert
      as.assertEquals(config.getProperty("testVar").toString(), "yeet");
   }
   
   public void playMusic() throws TestException, UnsupportedAudioFileException, IOException, LineUnavailableException
   {
      // Arrange
      String filePath = "musik/depression.wav";
      Musik yes = new Musik(filePath);
      // Act
      yes.play();
      yes.stop();
      // Assert
      as.assertEquals(yes.getStatus(), "stopped");
   }
   
   
   ////////// Test handling classes //////////
   public class Assert extends TestException
   {
      public void assertEquals(String actual, String expected) throws TestException
      {
         if(!actual.equals(expected)) throw new TestException("Failed - Expected: " + expected + " | actual: " + actual);
      }
      
      public void assertNotNull(String actual, String expected) throws TestException
      {
         if(actual == null) throw new TestException("Failed - Expected: " + expected + " | actual: " + actual);
      }
   }
   
   public class TestException extends Exception
   {
      public TestException() { super(); }
      public TestException(String message) { super(message); }
      public TestException(String message, Throwable cause) { super(message, cause); }
      public TestException(Throwable cause) { super(cause); }
   }
   
   public void log(String message)
   {
      fileManager.appendToFile(message, true);
   }
}