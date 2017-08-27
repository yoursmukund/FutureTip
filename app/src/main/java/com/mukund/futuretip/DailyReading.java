package com.mukund.futuretip;

public class DailyReading {

  private String id;

  /**
   * No args constructor for use in serialization
   *
   */
  public DailyReading() {
  }

  /**
   *
   * @param id
   */
  public DailyReading(String id) {
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
