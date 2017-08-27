package com.mukund.futuretip;

public class WeeklySubs {

  private String username;

  /**
   * No args constructor for use in serialization
   *
   */
  public WeeklySubs() {
  }

  /**
   *
   * @param username
   */
  public WeeklySubs(String username) {
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
