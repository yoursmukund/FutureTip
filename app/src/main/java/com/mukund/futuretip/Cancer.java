package com.mukund.futuretip;

public class Cancer {
  private String username;

  /**
   * No args constructor for use in serialization
   *
   */
  public Cancer() {
  }

  /**
   *
   * @param username
   */
  public Cancer(String username) {
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