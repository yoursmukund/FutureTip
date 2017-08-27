package com.mukund.futuretip;

public class Subscription {

  private Aries aries;
  private Taurus taurus;
  private Gemini gemini;
  private Cancer cancer;
  private Leo leo;
  private Virgo virgo;
  private Libra libra;
  private Scorpio scorpio;
  private Saggitarius saggitarius;
  private Capricorn capricorn;
  private Aquarius aquarius;
  private Pisces pisces;
  private WeeklySubs weeklySubs;

  /**
   * No args constructor for use in serialization
   *
   */
  public Subscription() {
  }

  /**
   *
   * @param pisces
   * @param aries
   * @param libra
   * @param gemini
   * @param scorpio
   * @param weeklySubs
   * @param leo
   * @param aquarius
   * @param capricorn
   * @param saggitarius
   * @param virgo
   * @param cancer
   * @param taurus
   */
  public Subscription(Aries aries, Taurus taurus, Gemini gemini, Cancer cancer, Leo leo, Virgo virgo, Libra libra, Scorpio scorpio, Saggitarius saggitarius, Capricorn capricorn, Aquarius aquarius, Pisces pisces, WeeklySubs weeklySubs) {
    super();
    this.aries = aries;
    this.taurus = taurus;
    this.gemini = gemini;
    this.cancer = cancer;
    this.leo = leo;
    this.virgo = virgo;
    this.libra = libra;
    this.scorpio = scorpio;
    this.saggitarius = saggitarius;
    this.capricorn = capricorn;
    this.aquarius = aquarius;
    this.pisces = pisces;
    this.weeklySubs = weeklySubs;
  }

  public Aries getAries() {
    return aries;
  }

  public void setAries(Aries aries) {
    this.aries = aries;
  }

  public Taurus getTaurus() {
    return taurus;
  }

  public void setTaurus(Taurus taurus) {
    this.taurus = taurus;
  }

  public Gemini getGemini() {
    return gemini;
  }

  public void setGemini(Gemini gemini) {
    this.gemini = gemini;
  }

  public Cancer getCancer() {
    return cancer;
  }

  public void setCancer(Cancer cancer) {
    this.cancer = cancer;
  }

  public Leo getLeo() {
    return leo;
  }

  public void setLeo(Leo leo) {
    this.leo = leo;
  }

  public Virgo getVirgo() {
    return virgo;
  }

  public void setVirgo(Virgo virgo) {
    this.virgo = virgo;
  }

  public Libra getLibra() {
    return libra;
  }

  public void setLibra(Libra libra) {
    this.libra = libra;
  }

  public Scorpio getScorpio() {
    return scorpio;
  }

  public void setScorpio(Scorpio scorpio) {
    this.scorpio = scorpio;
  }

  public Saggitarius getSaggitarius() {
    return saggitarius;
  }

  public void setSaggitarius(Saggitarius saggitarius) {
    this.saggitarius = saggitarius;
  }

  public Capricorn getCapricorn() {
    return capricorn;
  }

  public void setCapricorn(Capricorn capricorn) {
    this.capricorn = capricorn;
  }

  public Aquarius getAquarius() {
    return aquarius;
  }

  public void setAquarius(Aquarius aquarius) {
    this.aquarius = aquarius;
  }

  public Pisces getPisces() {
    return pisces;
  }

  public void setPisces(Pisces pisces) {
    this.pisces = pisces;
  }

  public WeeklySubs getWeeklySubs() {
    return weeklySubs;
  }

  public void setWeeklySubs(WeeklySubs weeklySubs) {
    this.weeklySubs = weeklySubs;
  }


}
