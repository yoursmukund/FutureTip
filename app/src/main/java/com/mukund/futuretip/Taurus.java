package com.mukund.futuretip;


public class Taurus {
  private String username;

  /**
   * No args constructor for use in serialization
   *
   */
  public Taurus() {
  }

  /**
   *
   * @param username
   */
  public Taurus(String username) {
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
