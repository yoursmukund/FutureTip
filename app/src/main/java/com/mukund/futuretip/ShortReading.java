package com.mukund.futuretip;

public class ShortReading {
  private String id;

  /**
   * No args constructor for use in serialization
   *
   */
  public ShortReading() {
  }

  /**
   *
   * @param id
   */
  public ShortReading(String id) {
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
