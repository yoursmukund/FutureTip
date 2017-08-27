package com.mukund.futuretip;

public class Leo {
  private String username;

  /**
   * No args constructor for use in serialization
   *
   */
  public Leo() {
  }

  /**
   *
   * @param username
   */
  public Leo(String username) {
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
