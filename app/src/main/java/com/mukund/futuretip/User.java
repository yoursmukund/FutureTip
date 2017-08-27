package com.mukund.futuretip;

public class User {
  private String zodiac;
  private Integer credits;
  private DailyReading dailyReading;
  private WeeklyReading weeklyReading;
  private ShortReading shortReading;
  private LongReading longReading;

  /**
   * No args constructor for use in serialization
   *
   */
  public User() {
  }

  /**
   *
   * @param weeklyReading
   * @param shortReading
   * @param zodiac
   * @param dailyReading
   * @param longReading
   * @param credits
   */
  public User(String zodiac, Integer credits, DailyReading dailyReading, WeeklyReading weeklyReading, ShortReading shortReading, LongReading longReading) {
    super();
    this.zodiac = zodiac;
    this.credits = credits;
    this.dailyReading = dailyReading;
    this.weeklyReading = weeklyReading;
    this.shortReading = shortReading;
    this.longReading = longReading;
  }

  public String getZodiac() {
    return zodiac;
  }

  public void setZodiac(String zodiac) {
    this.zodiac = zodiac;
  }

  public Integer getCredits() {
    return credits;
  }

  public void setCredits(Integer credits) {
    this.credits = credits;
  }

  public DailyReading getDailyReading() {
    return dailyReading;
  }

  public void setDailyReading(DailyReading dailyReading) {
    this.dailyReading = dailyReading;
  }

  public WeeklyReading getWeeklyReading() {
    return weeklyReading;
  }

  public void setWeeklyReading(WeeklyReading weeklyReading) {
    this.weeklyReading = weeklyReading;
  }

  public ShortReading getShortReading() {
    return shortReading;
  }

  public void setShortReading(ShortReading shortReading) {
    this.shortReading = shortReading;
  }

  public LongReading getLongReading() {
    return longReading;
  }

  public void setLongReading(LongReading longReading) {
    this.longReading = longReading;
  }

}
