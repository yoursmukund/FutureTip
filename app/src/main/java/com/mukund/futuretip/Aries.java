package com.mukund.futuretip;

public class Aries {
  private String username;

  /**
   * No args constructor for use in serialization
   *
   */
  public Aries() {
  }

  /**
   *
   * @param username
   */
  public Aries(String username) {
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
