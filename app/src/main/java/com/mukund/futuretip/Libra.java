package com.mukund.futuretip;

public class Libra {
  private String username;

  /**
   * No args constructor for use in serialization
   *
   */
  public Libra() {
  }

  /**
   *
   * @param username
   */
  public Libra(String username) {
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
