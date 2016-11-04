package com.kimbsy.app;

import java.util.*;

/**
 * This class represent a single transaction.
 * 
 * @author Dave Kimber <github.com/Kimbsy>
 */
public class Entry
{

  /**
   * The date the transaction took place.
   */
  protected Date date;

  /**
   * The description of the transaction, generally the name of the payee.
   */
  protected String description;

  /**
   * The type of transaction, PAYMENT|PURCHASE|CREDIT etc.
   */
  protected String type;

  /**
   * The change to the balance.
   */
  protected float change;

  /**
   * The balance of the account.
   */
  protected float balance;

  /**
   * Gets the date the transaction took place.
   *
   * @return     The date.
   */
  public Date getDate()
  {
    return this.date;
  }

  /**
   * Sets the date the transaction took place.
   *
   * @param      date  The date object.
   *
   * @return     The Entry object.
   */
  public Entry setDate(Date date)
  {
    this.date = date;
    return this;
  }

  /**
   * Gets the description.
   *
   * @return     The description.
   */
  public String getDescription()
  {
    return this.description;
  }

  /**
   * Sets the description.
   *
   * @param      description  The description.
   *
   * @return     The Entry object.
   */
  public Entry setDescription(String description)
  {
    this.description = description;
    return this;
  }

  /**
   * Gets the type.
   *
   * @return     The type of transaction.
   */
  public String getType()
  {
    return this.type;
  }

  /**
   * Sets the type.
   *
   * @param      type  The type of transaction.
   *
   * @return     The Entry object.
   */
  public Entry setType(String type)
  {
    this.type = type;
    return this;
  }

  /**
   * Gets the change in balance.
   *
   * @return     The change in balance.
   */
  public float getChange()
  {
    return this.change;
  }

  /**
   * Sets the change in balance.
   *
   * @param      change  The change in balance.
   *
   * @return     The Entry object.
   */
  public Entry setChange(float change)
  {
    this.change = change;
    return this;
  }

  /**
   * Gets the balance.
   *
   * @return     The balance.
   */
  public float getBalance()
  {
    return this.balance;
  }

  /**
   * Sets the balance.
   *
   * @param      balance  The balance.
   *
   * @return     The Entry object.
   */
  public Entry setBalance(float balance)
  {
    this.balance = balance;
    return this;
  }
}
