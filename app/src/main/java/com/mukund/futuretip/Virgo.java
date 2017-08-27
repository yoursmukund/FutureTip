package com.mukund.futuretip;

public class Virgo {
  private String username;

  /**
   * No args constructor for use in serialization
   *
   */
  public Virgo() {
  }

  /**
   *
   * @param username
   */
  public Virgo(String username) {
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
