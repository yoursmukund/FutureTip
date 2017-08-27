package com.mukund.futuretip;

public class Gemini {
  private String username;

  /**
   * No args constructor for use in serialization
   *
   */
  public Gemini() {
  }

  /**
   *
   * @param username
   */
  public Gemini(String username) {
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
