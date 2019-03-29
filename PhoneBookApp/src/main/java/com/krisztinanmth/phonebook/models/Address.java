package com.krisztinanmth.phonebook.models;

import java.util.Objects;
import java.util.UUID;

public class Address {

  private String country;
  private String zipCode;
  private String city;
  private String street;

  public Address() {
	  this("", "", "", "");
  }

  public Address(String country, String zipCode, String city, String street) {
    this.country = country;
    this.zipCode = zipCode;
    this.city = city;
    this.street = street;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  @Override
  public String toString() {
    return country + " " + zipCode + " " + city + " " + street;
  }
  
  
  @Override
  public boolean equals(Object obj) {
	  final Address otherAddress = (Address) obj;
	  if (!this.country.equals(otherAddress.country))
		  return false;
	  if (!this.zipCode.equals(otherAddress.zipCode))
		  return false;
	  if (!this.city.equals(otherAddress.city)) 
		  return false;
	  if (!this.street.equals(otherAddress.street))
		  return false;
	  return true;
  }
  
  @Override
	public int hashCode() {
	  Objects.hash(this.country, this.zipCode, this.city, this.street);
		return super.hashCode();
	}

}
