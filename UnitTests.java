// Unit testing class
// Calls Different functions and expects certain result

import java.util.ArrayList;
import java.util.Properties;
import java.io.FileInputStream;

// Musik
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.UnsupportedAudioFileException; 

// Exception handling && logs
import java.io.StringWriter; 
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

public class UnitTests
{
   private Assert as;
   private boolean testPassed;
   private String testName;
   private int testCount;
   private int testsPassedCount;
   
   private FileManagement fileManager;
   private MainFrame mainFrame;
   private boolean printDebugInfo;
   private boolean printLogInfo;
   
   public UnitTests(boolean debug)
   {
      this.printDebugInfo = debug;
      this.printLogInfo = debug;
      mainFrame = new MainFrame(false, true, this.printDebugInfo);
      this.fileManager = mainFrame.getFileManager();
      log("Unit Test Run initiated");
   }
   
   //////////////////// Actual Test Execution ////////////////////
   public void runTests()
   {
      // Debugging means printing logs created in MainFrame and Exception details to console
      log("New Unit test run");
      if(printDebugInfo) log("Debugging messages ON");
      else log("Debugging messages OFF");
      log("----------");

      as = new Assert();
      testCount = 0;
      testsPassedCount = 0;
      
      ////////// File saving & loading Tests //////////
      log("# File saving && loading tests");
      try
      {
         testCount++;
         testName = "File-save and load BookingList";
         saveAndLoadBookingListTest();
         log(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { log(testName + " | Failed: " + e.getMessage()); }
      catch (FileNotFoundException e) { log(testName + " | Failed: ", e); }
      catch (Exception e) { log(testName + " | Failed: ", e); }
      
      try
      {
         testCount++;
         testName = "File-save and load ArchivedBookingList";
         saveAndLoadArchivedBookingListTest();
         log(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { log(testName + " | Failed: " + e.getMessage()); }
      catch (FileNotFoundException e) { log(testName + " | Failed: ", e); }
      catch (Exception e) { log(testName + " | Failed: ", e); }
      
      try
      {
         testCount++;
         testName = "File-save and load RoomList";
         saveAndLoadRoomListTest();
         log(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { log(testName + " | Failed: " + e.getMessage()); }
      catch (FileNotFoundException e) { log(testName + " | Failed: ", e); }
      catch (Exception e) { log(testName + " | Failed: ", e); }
      
      try
      {
         testCount++;
         testName = "File-save and load GuestList";
         saveAndLoadGuestListTest();
         log(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { log(testName + " | Failed: " + e.getMessage()); }
      catch (FileNotFoundException e) { log(testName + " | Failed: ", e); }
      catch (Exception e) { log(testName + " | Failed: ", e); }
      
      try
      {
         testCount++;
         testName = "File-save and load StaffList";
         saveAndLoadStaffListTest();
         log(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { log(testName + " | Failed: " + e.getMessage()); }
      catch (FileNotFoundException e) { log(testName + " | Failed: ", e); }
      catch (Exception e) { log(testName + " | Failed: ", e); }
      
      try
      {
         testCount++;
         testName = "File-save and load CounterList";
         saveAndLoadCounterListTest();
         log(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { log(testName + " | Failed: " + e.getMessage()); }
      catch (FileNotFoundException e) { log(testName + " | Failed: ", e); }
      catch (Exception e) { log(testName + " | Failed: ", e); }
      
      ////////// MF ArrayList setting Tests //////////
      log("# MainFrame arrays tests");
      try
      {
         testCount++;
         testName = "MF-ArrayLists-setBookingList()";
         setBookingListTest();
         log(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { log(testName + " | Failed: " + e.getMessage()); }
      catch (Exception e) { log(testName + " | Failed: ", e); }
      try
      {
         testCount++;
         testName = "MF-ArrayLists-setArchivedBookingList()";
         setArchivedBookingListTest();
         log(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { log(testName + " | Failed: " + e.getMessage()); }
      catch (Exception e) { log(testName + " | Failed: ", e); }
      
      try
      {
         testCount++;
         testName = "MF-ArrayLists-setRoomList()";
         setRoomListTest();
         log(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { log(testName + " | Failed: " + e.getMessage()); }
      catch (Exception e) { log(testName + " | Failed: ", e); }
      
      try
      {
         testCount++;
         testName = "MF-ArrayLists-setGuestList()";
         setGuestListTest();
         log(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { log(testName + " | Failed: " + e.getMessage()); }
      catch (Exception e) { log(testName + " | Failed: ", e); }
      
      try
      {
         testCount++;
         testName = "MF-ArrayLists-setStaffList()";
         setStaffListTest();
         log(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { log(testName + " | Failed: " + e.getMessage()); }
      catch (Exception e) { log(testName + " | Failed: ", e); }
      
      try
      {
         testCount++;
         testName = "MF-ArrayLists-setCounterList()";
         setCounterListTest();
         log(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { log(testName + " | Failed: " + e.getMessage()); }
      catch (Exception e) { log(testName + " | Failed: ", e); }
      
      ////////// Counter Tests 1.6 //////////
      log("# Counter 1.6 tests");
      try
      {
         testCount++;
         testName = "generateBookingID()";
         generateBookingID();
         log(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { log(testName + " | Failed: " + e.getMessage()); }
      catch (Exception e) { log(testName + " | Failed: ", e); }
      
      try
      {
         testCount++;
         testName = "generateRoomID()";
         generateRoomID();
         log(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { log(testName + " | Failed: " + e.getMessage()); }
      catch (Exception e) { log(testName + " | Failed: ", e); }
      
      try
      {
         testCount++;
         testName = "generateGuestID()";
         generateGuestID();
         log(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { log(testName + " | Failed: " + e.getMessage()); }
      catch (Exception e) { log(testName + " | Failed: ", e); }
      
      try
      {
         testCount++;
         testName = "generateStaffID()";
         generateStaffID();
         log(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { log(testName + " | Failed: " + e.getMessage()); }
      catch (Exception e) { log(testName + " | Failed: ", e); }
      
      ////////// Misc Tests //////////
      log("# Misc. tests");
      try
      {
         testCount++;
         testName = "validateGuestLogin()";
         validateLoginGuestTest();
         log(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { log(testName + " | Failed: " + e.getMessage()); }
      catch (Exception e) { log(testName + " | Failed: ", e); }
      
      try
      {
         testCount++;
         testName = "validateStaffLogin()";
         validateLoginStaffTest();
         log(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { log(testName + " | Failed: " + e.getMessage()); }
      catch (Exception e) { log(testName + " | Failed: ", e); }
      
      try
      {
         testCount++;
         testName = "load Config";
         loadConfigTest();
         log(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { log(testName + " | Failed: " + e.getMessage());}
      catch (FileNotFoundException e) { log(testName + " | Failed: ", e); }
      catch (Exception e) { log(testName + " | Failed: ", e); }
      
      try
      {
         testCount++;
         testName = "play Music";
         playMusicTest();
         log(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { log(testName + " | Failed: " + e.getMessage());}
      catch (FileNotFoundException e) { log(testName + " | Failed: ", e); }
      catch (Exception e) { log(testName + " | Failed: ", e); }
      
      try
      {
         testCount++;
         testName = "Alex Brain Smoll";
         alexBrainSmollTest();
         log(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { log(testName + " | Failed: " + e.getMessage());}
      catch (FileNotFoundException e) { log(testName + " | Failed: ", e); }
      catch (Exception e) { log(testName + " | Failed: ", e); }
      
      log("----------");
      log("Unit testing finished. Passed " + testsPassedCount + "/" + testCount + " tests\n\n");
   }
   
   //////////////////// Unit Tests ////////////////////
   public void templateTest() throws TestException
   {
      // Arrange
      // Act
      // Assert
      as.assertEquals(null, null);
   }
   
   ////////// Information Tests //////////
   
   public void saveAndLoadBookingListTest() throws TestException, FileNotFoundException, NullPointerException, Exception
   {
      // Arrange
      Information info;
      MainFrame mf = new MainFrame(false, this.printLogInfo, this.printDebugInfo);
      FileManagement file = mf.getFileManager();
      ArrayList<Booking> list = new ArrayList<Booking>();
      list.add(new Booking(0, 0, "yeet", 0, 0, 0, false));
      // Act
      file.saveData(new Information(list, null, null, null, null, null));
      info = file.loadData(new Information(true, false, false, false, false, false));
      // Assert
      as.assertEquals(Boolean.toString(info.bookingList.isEmpty()), Boolean.toString(false));
   }
   
   public void saveAndLoadArchivedBookingListTest() throws TestException, FileNotFoundException, NullPointerException, Exception
   {
      // Arrange
      Information info;
      MainFrame mf = new MainFrame(false, this.printLogInfo, this.printDebugInfo);
      FileManagement file = mf.getFileManager();
      ArrayList<Booking> list = new ArrayList<Booking>();
      list.add(new Booking(0, 0, "yeet", 0, 0, 0, false));
      // Act
      file.saveData(new Information(null, list, null, null, null, null));
      info = file.loadData(new Information(false, true, false, false, false, false));
      // Assert
      as.assertEquals(Boolean.toString(info.archivedBookingList.isEmpty()), Boolean.toString(false));
   }
   
   public void saveAndLoadRoomListTest() throws TestException, FileNotFoundException, NullPointerException, Exception
   {
      // Arrange
      Information info;
      MainFrame mf = new MainFrame(false, this.printLogInfo, this.printDebugInfo);
      FileManagement file = mf.getFileManager();
      ArrayList<Room> list = new ArrayList<Room>();
      list.add(new Room(0, 1));
      // Act
      file.saveData(new Information(null, null, list, null, null, null));
      info = file.loadData(new Information(false, false, true, false, false, false));
      // Assert
      as.assertEquals(Boolean.toString(info.roomList.isEmpty()), Boolean.toString(false));
   }
   
   public void saveAndLoadGuestListTest() throws TestException, FileNotFoundException, NullPointerException, Exception
   {
      // Arrange
      Information info;
      MainFrame mf = new MainFrame(false, this.printLogInfo, this.printDebugInfo);
      FileManagement file = mf.getFileManager();
      ArrayList<Guest> list = new ArrayList<Guest>();
      String[] arr = new String[3];
      arr[0] = "Yeet";
      arr[1] = "Yote";
      arr[2] = "yoten";
      list.add(new Guest ("Faisal", "Boolyan", "1234561234", arr, "12345678", "passwd", 0));
      // Act
      file.saveData(new Information(null, null, null, list, null, null));
      info = file.loadData(new Information(false, false, false, true, false, false));
      // Assert
      as.assertEquals(Boolean.toString(info.guestList.isEmpty()), Boolean.toString(false));
   }
   
   public void saveAndLoadStaffListTest() throws TestException, FileNotFoundException, NullPointerException, Exception
   {
      // Arrange
      Information info;
      MainFrame mf = new MainFrame(false, this.printLogInfo, this.printDebugInfo);
      FileManagement file = mf.getFileManager();
      ArrayList<Staff> list = new ArrayList<Staff>();
      String[] arr = new String[3];
      arr[0] = "Yeet";
      arr[1] = "Yote";
      arr[2] = "yoten";
      list.add(new Staff ("Faisal", "Stud", "Boolyan", "1234561234", arr, "12345678", "passwd", 0, 0, 0.0, 0));
      // Act
      file.saveData(new Information(null, null, null, null, list, null));
      info = file.loadData(new Information(false, false, false, false, true, false));
      // Assert
      as.assertEquals(Boolean.toString(info.staffList.isEmpty()), Boolean.toString(false));
   }
   
   public void saveAndLoadCounterListTest() throws TestException, FileNotFoundException, NullPointerException, Exception
   {
      // Arrange
      Information info;
      MainFrame mf = new MainFrame(false, this.printLogInfo, this.printDebugInfo);
      FileManagement file = mf.getFileManager();
      ArrayList<Integer> list = new ArrayList<Integer>();
      list.add(1);
      list.add(2);
      list.add(3);
      list.add(4);
      // Act
      file.saveData(new Information(null, null, null, null, null, list));
      info = file.loadData(new Information(false, false, false, false, false, true));
      // Assert
      as.assertEquals(Boolean.toString(info.counterList.isEmpty()), Boolean.toString(false));
   }
   
   ////////// Setting ArrayLists Tests //////////
   
   public void setBookingListTest() throws TestException
   {
      // Arrange
      MainFrame mf = new MainFrame(false, this.printLogInfo, this.printDebugInfo);
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
      MainFrame mf = new MainFrame(false, this.printLogInfo, this.printDebugInfo);
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
      MainFrame mf = new MainFrame(false, this.printLogInfo, this.printDebugInfo);
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
      MainFrame mf = new MainFrame(false, this.printLogInfo, this.printDebugInfo);
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
      MainFrame mf = new MainFrame(false, this.printLogInfo, this.printDebugInfo);
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
   
   public void setCounterListTest() throws TestException
   {
      // Arrange
      MainFrame mf = new MainFrame(false, this.printLogInfo, this.printDebugInfo);
      mf.setSaveToFile(false);
      ArrayList<Integer> list = new ArrayList<Integer>();
      list.add(1);
      list.add(9);
      list.add(3);
      list.add(9);
      // Act
      mf.setCounterList(list);
      // Assert
      as.assertEquals(Boolean.toString(mf.getCounterList().isEmpty()), Boolean.toString(false));
   }
   
   ////////// Counter Test : Local Testing //////////
   
   public void generateBookingID() throws TestException, FileNotFoundException, Exception
   {
      // Arrange
      MainFrame mf = new MainFrame(false, this.printLogInfo, this.printDebugInfo);
      int newValue = 1;
      mf.setCounterList(0,0,0,0);
      // Act
      mf.setBookingCounter(newValue);
      // Assert
      as.assertEquals(Integer.toString(mf.generateBookingID()), Integer.toString(newValue + 1));
   }
   
   public void generateRoomID() throws TestException, FileNotFoundException, Exception
   {
      // Arrange
      MainFrame mf = new MainFrame(false, this.printLogInfo, this.printDebugInfo);
      int newValue = 1;
      mf.setCounterList(0,0,0,0);
      // Act
      mf.setRoomCounter(newValue);
      // Assert
      as.assertEquals(Integer.toString(mf.generateRoomID()), Integer.toString(newValue + 1));
   }
   
   public void generateGuestID() throws TestException, FileNotFoundException, Exception
   {
      // Arrange
      MainFrame mf = new MainFrame(false, this.printLogInfo, this.printDebugInfo);
      int newValue = 1;
      mf.setCounterList(0,0,0,0);
      // Act
      mf.setGuestCounter(newValue);
      // Assert
      as.assertEquals(Integer.toString(mf.generateGuestID()), Integer.toString(newValue + 1));
   }
   
   public void generateStaffID() throws TestException, FileNotFoundException, Exception
   {
      // Arrange
      MainFrame mf = new MainFrame(false, this.printLogInfo, this.printDebugInfo);
      int newValue = 1;
      mf.setCounterList(0,0,0,0);
      // Act
      mf.setStaffCounter(newValue);
      // Assert
      as.assertEquals(Integer.toString(mf.generateStaffID()), Integer.toString(newValue + 1));
   }
   
   
   ////////// Misc tests //////////
   
   public void validateLoginGuestTest() throws TestException, NullPointerException
   {
      // Arrange
      MainFrame mf = new MainFrame(false, this.printLogInfo, this.printDebugInfo);
      ArrayList<Guest> list = new ArrayList<Guest>();
      String[] arr = new String[3];
      arr[0] = "Yeet";
      arr[1] = "Yote";
      arr[2] = "yoten";
      String phoneNumber = "+69 420";
      String password = "yeet";
      list.add(new Guest("Faisal", "Boolyan", "1234561234", arr, phoneNumber, password, 0));
      mf.setGuestList(list);
      // Act
      Guest guest = mf.validateLoginGuest(phoneNumber, password);
      //Assert
       as.assertEquals(guest.getPhoneNumber(), phoneNumber);
   }
   
   public void validateLoginStaffTest() throws TestException, NullPointerException
   {
      // Arrange
      MainFrame mf = new MainFrame(false, this.printLogInfo, this.printDebugInfo);
      ArrayList<Staff> list = new ArrayList<Staff>();
      String[] arr = new String[3];
      arr[0] = "Yeet";
      arr[1] = "Yote";
      arr[2] = "yoten";
      String phoneNumber = "+69 420";
      String password = "yeet";
      list.add(new Staff("Faisal", "Strong", "123456-1234", "ST", arr, phoneNumber, password, 0, 37, 140.0, 5));
      mf.setStaffList(list);
      // Act
      Staff staff = mf.validateLoginStaff(phoneNumber, password);
      //Assert
      as.assertEquals(staff.getPhoneNumber(), phoneNumber);
   }
   
   public void loadConfigTest() throws TestException, FileNotFoundException, IOException
   {
      // Arrange
      Properties config = new Properties();
      String configName = "config.properties";
      // Act
      config.load(new FileInputStream(configName));
      // Assert
      as.assertEquals(config.getProperty("testVar").toString(), "yeet");
   }
   
   public void playMusicTest() throws TestException, UnsupportedAudioFileException, IOException, LineUnavailableException
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
   public void alexBrainSmollTest() throws TestException, UnsupportedAudioFileException, IOException, LineUnavailableException
   {
      // Arrange
      class Alex { public int brainSize; }
      Alex alex = new Alex();
      String result;
      // Act
      alex.brainSize = -10;
      // Assert
      result = alex.brainSize < 0 ? "true": "false";
      as.assertEquals(result, "true");
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
      mainFrame.createTestLog(message, Log.Type.INFO);
   }
   
   public void log(String message, Exception e)
   {
      mainFrame.createTestLog(message + e, Log.Type.WARNING);
      if ( this.printDebugInfo)
      {
         StringWriter sw = new StringWriter();
         PrintWriter pw = new PrintWriter(sw);
         e.printStackTrace(pw);
         mainFrame.createTestLog(sw.toString(), Log.Type.ERROR);
      }
   }
}