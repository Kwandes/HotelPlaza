// Main, does nothing but call MF or run tests

public class Main
{
   private final static boolean RUN_UNIT_TESTS = true;
   private final static boolean PRINT_TEST_DETAILS = false;
   
   public static void main(String [] args)
   {
      if(RUN_UNIT_TESTS)
      {
         UnitTests test = new UnitTests(PRINT_TEST_DETAILS, "Testing");
         test.runTests();
      }
      else
      {
         MainFrame hotel = new MainFrame(true);
         hotel.init();
         //if(hotel.getInitStatus())
         //{
            hotel.playMusic();
            hotel.openCLI();
         //}
      }
   }
}