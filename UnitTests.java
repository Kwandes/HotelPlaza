// Unit testing class
// Calls Different functions and expects certain result

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
   
   public void runTests(boolean debug)
   {
      // Debugging means printing logs created in MainFrame etc to console
      // Enable to see what exactly is going on, Disable for simple Test Passed/Failed
      this.printDebugInfo = debug;
      if(printDebugInfo) System.out.println("Debugging messages ON");
      else System.out.println("Debugging messages OFF");
      System.out.println("----------");

      as = new Assert();
      testCount = 0;
      testsPassedCount = 0;
      
      try
      {
         testCount++;
         testName = "Information-loadBookingList";
         loadBookingListTest();
         System.out.println(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { System.out.println(testName + " | Failed: " + e.getMessage()); }
      catch (Exception e) { System.out.println(testName + " | Failed: " + e); }
      
      try
      {
         testCount++;
         testName = "Information-loadArchivedBookingList";
         loadArchivedBookingListTest();
         System.out.println(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { System.out.println(testName + " | Failed: " + e.getMessage()); }
      catch (Exception e) { System.out.println(testName + " | Failed: " + e); }
      
      try
      {
         testCount++;
         testName = "Information-loadRoomList";
         loadRoomListTest();
         System.out.println(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { System.out.println(testName + " | Failed: " + e.getMessage()); }
      catch (Exception e) { System.out.println(testName + " | Failed: " + e); }
      
      try
      {
         testCount++;
         testName = "Information-loadGuestList";
         loadGuestListTest();
         System.out.println(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { System.out.println(testName + " | Failed: " + e.getMessage()); }
      catch (Exception e) { System.out.println(testName + " | Failed: " + e); }
      
      try
      {
         testCount++;
         testName = "Information-loadStaffList";
         loadStaffListTest();
         System.out.println(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { System.out.println(testName + " | Failed: " + e.getMessage()); }
      catch (Exception e) { System.out.println(testName + " | Failed: " + e); }
      
      try
      {
         testCount++;
         testName = "validateGuestLogin()";
         validateLoginGuestTest();
         System.out.println(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { System.out.println(testName + " | Failed: " + e.getMessage()); }
      catch (Exception e) { System.out.println(testName + " | Failed: " + e); }
      
      try
      {
         testCount++;
         testName = "validateStaffLogin()";
         validateLoginStaffTest();
         System.out.println(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { System.out.println(testName + " | Failed: " + e.getMessage()); }
      catch (Exception e) { System.out.println(testName + " | Failed: " + e); }
      
      try
      {
         testCount++;
         testName = "load Config";
         playMusic();
         System.out.println(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { System.out.println(testName + " | Failed: " + e.getMessage());}
      catch (Exception e) { System.out.println(testName + " | Failed: " + e); }
      
      try
      {
         testCount++;
         testName = "play Music";
         loadConfig();
         System.out.println(testName + " | " + "Passed");
         testsPassedCount++;
      }
      catch (TestException e) { System.out.println(testName + " | Failed: " + e.getMessage());}
      catch (Exception e) { System.out.println(testName + " | Failed: " + e); }
      
      System.out.println("----------");
      System.out.println("Unit testing finished. Passed " + testsPassedCount + "/" + testCount + " tests\n\n");
   }
   
   
   
   ////////// unit tests //////////
   public void templateTest() throws TestException
   {
      // Arrange
      // Act
      // Assert
      as.assertEquals(null, null);
   }
   
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
      // Assert
      as.assertEquals(yes.getStatus(), "playing");
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
}