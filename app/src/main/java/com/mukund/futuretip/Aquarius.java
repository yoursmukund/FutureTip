package com.mukund.futuretip;

public class Aquarius {
  private String username;

  /**
   * No args constructor for use in serialization
   *
   */
  public Aquarius() {
  }

  /**
   *
   * @param username
   */
  public Aquarius(String username) {
    super();
    this.username = username;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
