package com.mukund.futuretip;

public class WeeklyReading {
  private String id;

  /**
   * No args constructor for use in serialization
   *
   */
  public WeeklyReading() {
  }

  /**
   *
   * @param id
   */
  public WeeklyReading(String id) {
    super();
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

}
