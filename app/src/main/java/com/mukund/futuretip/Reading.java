package com.mukund.futuretip;

public class Reading {

  private Id id;

  /**
   * No args constructor for use in serialization
   *
   */
  public Reading() {
  }

  /**
   *
   * @param id
   */
  public Reading(Id id) {
    super();
    this.id = id;
  }

  public Id getId() {
    return id;
  }

  public void setId(Id id) {
    this.id = id;
  }
}
