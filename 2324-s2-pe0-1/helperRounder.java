public class helperRounder {

  public static String round2DP(double sth) {
    int res = (int) sth;
    int res2 = (int) (sth * 100);
    int res3 = res2 - res * 100;
    String res_ = "" + res + "." + res3;
    if (res3 == 0) {
      res_ += "0";
    }
    return  res_;
  }

}
