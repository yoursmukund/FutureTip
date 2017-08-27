package com.mukund.futuretip;

public class Saggitarius {
  private String username;

  /**
   * No args constructor for use in serialization
   *
   */
  public Saggitarius() {
  }

  /**
   *
   * @param username
   */
  public Saggitarius(String username) {
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
