package com.mukund.futuretip;

public class Reading {
  private String subject;
  private String topConcern;
  private String problem;
  private String solution;
  private String type;
  private String username;
  private String timestamp;

  /**
   * No args constructor for use in serialization
   *
   */
  public Reading() {
  }

  /**
   *
   * @param timestamp
   * @param username
   * @param topConcern
   * @param subject
   * @param problem
   * @param type
   * @param solution
   */
  public Reading(String subject, String topConcern, String problem, String solution, String type, String username, String timestamp) {
    super();
    this.subject = subject;
    this.topConcern = topConcern;
    this.problem = problem;
    this.solution = solution;
    this.type = type;
    this.username = username;
    this.timestamp = timestamp;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getTopConcern() {
    return topConcern;
  }

  public void setTopConcern(String topConcern) {
    this.topConcern = topConcern;
  }

  public String getProblem() {
    return problem;
  }

  public void setProblem(String problem) {
    this.problem = problem;
  }

  public String getSolution() {
    return solution;
  }

  public void setSolution(String solution) {
    this.solution = solution;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

}
